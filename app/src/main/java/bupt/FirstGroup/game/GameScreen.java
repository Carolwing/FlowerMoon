package bupt.FirstGroup.game;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Vibrator;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import bupt.FirstGroup.GameActivity;
import bupt.FirstGroup.MainActivity;
import bupt.FirstGroup.entity.Record;
import bupt.FirstGroup.framework.FileIO;
import bupt.FirstGroup.framework.Game;
import bupt.FirstGroup.framework.Graphics;
import bupt.FirstGroup.framework.Image;
import bupt.FirstGroup.framework.Input;
import bupt.FirstGroup.framework.Music;
import bupt.FirstGroup.framework.Screen;
import bupt.FirstGroup.framework.Input.TouchEvent;
import bupt.FirstGroup.framework.impl.AnimatorImage;
import bupt.FirstGroup.framework.impl.ButtonImage;
import bupt.FirstGroup.framework.impl.RTGame;
import bupt.FirstGroup.framework.impl.RTGraphics;
import bupt.FirstGroup.game.models.Ball;
import bupt.FirstGroup.game.models.MusicPoint;
import bupt.FirstGroup.models.Difficulty;

//加载的游戏场景
public class GameScreen extends Screen {
    private static final String TAG = "GameScreenTag";
    //游戏状态
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    // game and device
    //游戏高度
    private int _gameHeight;
    //游戏宽度
    private int _gameWidth;
    //随机种子？
    private Random _rand;
    //难度字段
    private Difficulty _difficulty;
    //生命值
    private int _lifes;
    //振动器
    private Vibrator _vibrator;
    //游戏是否结束
    private boolean _isEnding;

    // score
    //分数
    private int _score;
    //2倍分数
    private int _multiplier;
    //连击数
    private int _streak;

    // tickers
    //计数器？？
    private int _tick;
    //2倍计数器？？
    private int _doubleMultiplierTicker;
    //炸弹计数器
    private int _explosionTicker;
    //当前时间
    private float _currentTime;
    //结束计数器
    private int _endTicker;
    //当前音符出现时间序列（ms)
    private ArrayList<MusicPoint> list_LEFT;
    private ArrayList<MusicPoint> list_RIGHT;
    //当前已产生的最新音符索引
    private int last_key_left =-1;
    private int last_key_right =-1;
    //从音符出现到消失所用时间
    private float go_time;
    //左边按钮序列
    private ArrayList<ButtonImage> list_Flower_Left;
    private ArrayList<ButtonImage> list_Flower_Right;
    //左右最需要按下的按钮
    ButtonImage left = null;
    ButtonImage right= null;


    // balls
    //左边音符
    private List<Ball> _ballsLeft;
    //中间音符
    private List<Ball> _ballsMiddle;
    //右边音符
    private List<Ball> _ballsRight;


    // difficulty params
    //产生音符间隔
    private float _spawnInterval;
    //音符速度
    private int _ballSpeed;
    //产生正常音符概率
    private final double _spawnChance_normal = 0.26; // TODO dynamic
    //产生加命音符概率
    private final double _spawnChance_oneup = _spawnChance_normal + 0.003;
    //产生双倍分数音符概率
    private final double _spawnChance_multiplier = _spawnChance_oneup + 0.001;
    //产生加速音符概率
    private final double _spawnChance_speeder = _spawnChance_multiplier + 0.003;
    //产生炸弹概率
    private final double _spawnChance_bomb = _spawnChance_speeder + 0.0005;
    //产生骷髅概率
    private final double _spawnChance_skull = _spawnChance_bomb + 0.014;

    // audio
    //背景音乐
    private Music _currentTrack;

    // ui
    //分数显示界面
    private Paint _paintScore;
    //游戏结束界面
    private Paint _paintGameover;
    //连击数显示界面
    private Paint _paintCombo;
    //击中精确度（0Miss,1great,2perfect）
    private int _grade=-1;

    // constants
    // how far the screen should scroll after the track ends
    //结束位置
    private static final int END_TIME = 0;
    // initial y coordinate of spawned balls
    //产生音符的位置
    private static final int BALL_INITIAL_Y = -50;
    // hitbox is the y-range within a ball can be hit by a press in its lane
    //击中音符的区间
    private static final int HITBOX_CENTER = 1760;
    private static final int HITBOX_HEIGHT = 280;
    // if no ball is in the hitbox when pressed, remove the lowest ball in the
    // miss zone right above the hitbox (it still counts as a miss)
    //如果没击中某音符，将它删除，设为一次miss
    private static final int MISS_ZONE_HEIGHT = 150;
    private static final int MISS_FLASH_INITIAL_ALPHA = 240;
    private static final int DOUBLE_MULTIPLIER_TIME = 600;
    // explosion
    //爆炸的顶部位置？
    private static final int EXPLOSION_TOP = 600;
    //爆炸时间
    private static final int EXPLOSION_TIME = 150;

    //初始状态为游戏准备中
    private GameState state = GameState.Ready;

    //游戏上下文
    private Context context;

    //游戏特效资源绘制
    ArrayList<AnimatorImage> animatorImages;

    //游戏按钮
    ButtonImage pause;

    //游戏结束界面按钮
    ButtonImage first;
    ButtonImage second;

    GameScreen(Game game, Difficulty difficulty) {
        super(game);
//        context=game.get();

        _difficulty = difficulty;
        // init difficulty parameters
        _ballSpeed = _difficulty.getBallSpeed();
        _spawnInterval = _difficulty.getSpawnInterval();

        // Initialize game objects
        _gameHeight = game.getGraphics().getHeight();
        _gameWidth = game.getGraphics().getWidth();
        Log.i("lalala",String.valueOf(_gameHeight)+","+String.valueOf(_gameWidth));
        _vibrator = game.getVibrator();
        _multiplier = 1;
        _doubleMultiplierTicker = 0;
        _score = 0;
        _streak = 0;
        _ballsLeft = new ArrayList<>();
        _ballsMiddle = new ArrayList<>();
        _ballsRight = new ArrayList<>();
        _rand = new Random();
        _tick = 0;
        _endTicker = END_TIME / _difficulty.getBallSpeed();
        _currentTime = 0f;
        _explosionTicker = 0;
        _lifes = 10;
        _currentTrack = Assets.musicTrack;
        _isEnding = false;

        // paints for text

        _paintScore = new Paint();
        _paintScore.setTextSize(70);
        _paintScore.setTextSkewX(-0.5f);
        _paintScore.setFakeBoldText(true);
        _paintScore.setTextAlign(Paint.Align.CENTER);
        _paintScore.setAntiAlias(true);
        _paintScore.setColor(Color.WHITE);//颜色
        _paintScore.setFlags(Paint.ANTI_ALIAS_FLAG);//消除齿距

        _paintCombo = new Paint();
        _paintCombo.setTextSize(100);
//        _paintCombo.setTextSkewX(-0.5f);
        _paintCombo.setFakeBoldText(true);
        _paintCombo.setTextAlign(Paint.Align.CENTER);
        _paintCombo.setAntiAlias(true);
        _paintCombo.setColor(Color.YELLOW);//颜色
        _paintCombo.setFlags(Paint.ANTI_ALIAS_FLAG);//消除齿距
        _paintCombo.setStrokeWidth(5);


        _paintGameover = new Paint();
        _paintGameover.setTextSize(100);
        _paintGameover.setTextAlign(Paint.Align.CENTER);
        _paintGameover.setAntiAlias(true);
        _paintGameover.setColor(Color.BLACK);

        //1.读入音谱到list_Left和list_Right中
        try {
            readMusicPoint(Assets.musicScore);
        }catch (Exception e){
            e.printStackTrace();
        }

        list_Flower_Left=new ArrayList<>();
        list_Flower_Right=new ArrayList<>();
        go_time=(_gameHeight)/_ballSpeed;
        Log.i("lalala","gotime"+String.valueOf(go_time));

        //特效的生成
        animatorImages = new ArrayList<>();

        //暂停键的设置
        pause = new ButtonImage(Assets.pause,Assets.pause.getFormat(),_gameWidth/2-Assets.pause.getWidth()/2,0,null,null,null);

    }

    //读入音谱
    public void readMusicPoint(InputStream inputStream) throws IOException {
        DataInputStream dataIO = null;
        try {
            list_LEFT=new ArrayList<>();
            list_RIGHT=new ArrayList<>();
            dataIO = new DataInputStream(inputStream);
            String strLine = null;
            while((strLine=dataIO.readLine())!=null) {
                Log.i("lalala",strLine);
                String[] all = strLine.split("\\s+");
                int time = Integer.parseInt(all[0]);
                int type = Integer.parseInt(all[1]);
                MusicPoint musicPoint = new MusicPoint(type,time);
//                如果是左边音符
                if (type<4){
                    list_LEFT.add(musicPoint);
                }else
                    list_RIGHT.add(musicPoint);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataIO.close();
            inputStream.close();
        }
    }

    // 获取当前触摸事件，更新状态
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if (state == GameState.Ready)
            updateReady(touchEvents);
        else if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        else if (state == GameState.Paused)
            updatePaused(touchEvents);
        else if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    //当按下屏幕任意位置，游戏开始
    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0) {
            state = GameState.Running;
            touchEvents.clear();
            _currentTrack.setLooping(false);
            _currentTrack.setVolume(0.25f);
            _currentTrack.play();
        }
    }

    // 对触摸事件进行处理，检查是否死亡，音乐是否结束，音符位置更新
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        // 1. All touch input is handled here:
        handleTouchEvents(touchEvents);

        // 2. Check miscellaneous events like death:
        checkDeath();
        checkEnd();

        // 3. Individual update() methods.
        // 球下落
        updateVariables(deltaTime);
    }

    //判断音乐是否结束
    private void checkEnd() {
        if (_currentTrack.isStopped()) {
            _isEnding = true;
        }
    }

    //检查是否死亡
    private void checkDeath() {
        if (_lifes <= 0) {
            endGame();
        }
    }

    // To-Do:分数更新
    private void endGame() {
        state = GameState.GameOver;
        // update highscore
        FileIO fileIO = game.getFileIO();
        SharedPreferences prefs = fileIO.getSharedPref();
        int oldScore;
        // 设置按钮
        boolean success = _lifes>0;
        Graphics g = game.getGraphics();
        if (success){
            first = new ButtonImage(Assets.continue_,Assets.continue_.getFormat(),
                    _gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.continue_.getWidth()),
                    _gameHeight/2+Assets.score_bg.getHeight()/2-Assets.success.getHeight()+Assets.continue_.getHeight(),
                    null,null,null);
            second = new ButtonImage(Assets.return_,Assets.return_.getFormat(),
                    _gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.return_.getWidth()),
                    _gameHeight/2+Assets.score_bg.getHeight()/2-Assets.success.getHeight()+2*Assets.continue_.getHeight()+20,
                    null,null,null);
        }else{
            first = new ButtonImage(Assets.resume_,Assets.resume_.getFormat(),
                    _gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.resume_.getWidth()),
                    _gameHeight/2+Assets.score_bg.getHeight()/2-Assets.fail.getHeight()+Assets.resume_.getHeight(),
                    null,null,null);
            second = new ButtonImage(Assets.return_,Assets.return_.getFormat(),
                    _gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.return_.getWidth()),
                    _gameHeight/2+Assets.score_bg.getHeight()/2-Assets.fail.getHeight()+2*Assets.resume_.getHeight()+20,
                    null,null,null);
        }

        switch(_difficulty.getMode()) {
            case Difficulty.EASY_TAG:
                oldScore = prefs.getInt(Difficulty.EASY_TAG,0);
                break;
            case Difficulty.MED_TAG:
                oldScore = prefs.getInt(Difficulty.MED_TAG,0);
                break;
            case Difficulty.HARD_TAG:
                oldScore = prefs.getInt(Difficulty.HARD_TAG,0);
                break;
            default:
                oldScore = 0;
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("0.0)GameScreen.new Thread");
                DBConnection db;
                db = new DBConnection();
                Record record = new Record(_score, _difficulty.getMode());
                System.out.println("1.1)GameScreen." + record.toString());
                //addRecord(String time,int score,int grade,String userName)
                String r1 = db.addRecord(record.getTime(), record.getScore(), record.getGrade(), record.getName());
                System.out.println("1.2)GameScreen." + r1);

            }
        }).start();

        if(_score > oldScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(_difficulty.getMode(), _score);
            editor.apply();
        }
    }

    // 处理触摸事件
    private void handleTouchEvents(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();

        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_DOWN) {
                int x = event.x;
                int y = event.y;

                //判断是否按下暂停键
                if (x>=pause.getX()&&x<=pause.getX()+pause.getWidth()&&y>=pause.getY()&&y<=pause.getY()+pause.getHeight()){
                    pause();
                    Log.i("Testpause","已经按下按钮,状态更新为"+state);
                    return;
                }

                //创建特效图片集
                AnimatorImage touch = new AnimatorImage(Assets.anim1,x,y,3);
                //加入特效列表
                animatorImages.add(touch);

                if (left!=null&&x>=left.getX()&&x<=left.getX()+left.getWidth()&&y>=left.getY()&&y<=left.getY()+left.getHeight()){
                    hitLane(_ballsLeft,list_Flower_Left);
                }else if (right!=null&&x>=right.getX()&&x<=right.getX()+right.getWidth()&&y>=right.getY()&&y<=right.getY()+right.getHeight()){
                    hitLane(_ballsRight,list_Flower_Right);
                }
            }
        }
    }

    // update all the games variables each tick
    private void updateVariables(float deltatime) {
        // update timer
        _currentTime += deltatime;

        // update ball position
        for (Ball b: _ballsLeft) {
            b.update((int) (_ballSpeed * deltatime));
        }

        for (Ball b: _ballsMiddle) {
            b.update((int) (_ballSpeed * deltatime));
        }

        for (Ball b: _ballsRight) {
            b.update((int) (_ballSpeed * deltatime));
        }

        // remove missed balls
        if (removeMissed(_ballsLeft.iterator(),list_Flower_Left.iterator())) {
        }


        if (removeMissed(_ballsRight.iterator(),list_Flower_Right.iterator())) {
        }

        if (!_isEnding) {
            spawnBalls();
        }

        //获取当前应当按下的花，放大两倍
        resetFlower();

        // update tickers
        _doubleMultiplierTicker -= Math.min(1, _doubleMultiplierTicker);
        _explosionTicker -= Math.min(1, _explosionTicker);
        _tick = (_tick + 1) % 100000;

        if (_isEnding) {
            _endTicker -= Math.min(1, _endTicker);

            if (_endTicker <= 0) {
                endGame();
            }
        }
    }

    //获取当前最该按下的花朵，并放大
    void resetFlower(){
        ButtonImage FirstFlower=null;
        if (list_Flower_Left.size()>0){
            FirstFlower = list_Flower_Left.get(0);
            left=FirstFlower;
            if (!FirstFlower.getImage().getIsrest()){
                FirstFlower.reset(2*FirstFlower.getImage().getWidth(),2*FirstFlower.getImage().getHeight());
            }
        }
        if (list_Flower_Right.size()>0){
            FirstFlower = list_Flower_Right.get(0);
            right=FirstFlower;
            if (!FirstFlower.getImage().getIsrest()){
                FirstFlower.reset(2*FirstFlower.getImage().getWidth(),2*FirstFlower.getImage().getHeight());
            }
        }
    }

    // remove the balls from an iterator that have fallen through the hitbox
    private boolean removeMissed(Iterator<Ball> iterator,Iterator<ButtonImage> floweriter) {
        while (iterator.hasNext()) {
            Ball b = iterator.next();
            ButtonImage btn = floweriter.next();
            if (b.y>_gameHeight+Assets.ballNormal.getHeight()/2){
                iterator.remove();
                floweriter.remove();
                Log.d(TAG, "fail press");
                onMiss(b);
            }
        }
        return false;
    }

    // handles a TouchEvent on a certain lane
    private boolean hitLane(List<Ball> balls,List<ButtonImage>flowers) {
//        Iterator<Ball> iter = balls.iterator();
//        Ball lowestBall = null;
        Ball lowestBall=null;
        ButtonImage lowestFlower=null;
        if (balls.size()>0){
            lowestBall=balls.get(0);
            lowestFlower=flowers.get(0);
        }
        else
            return true;
        float distance = Math.abs(lowestBall.y-_gameHeight+Assets.ballNormal.getHeight()/2);
        Log.i("lalala","distance:"+String.valueOf(distance));
        if (distance>=90&&distance<180){
            balls.remove(lowestBall);
            flowers.remove(lowestFlower);
            _grade=1;
            onHit(lowestBall);
            return true;
        }else if (distance>=180&&distance<=270){
            balls.remove(lowestBall);
            flowers.remove(lowestFlower);
            _grade=0;
            onMiss(null);
            return false;
        }else if (distance<90){
            balls.remove(lowestBall);
            flowers.remove(lowestFlower);
            _grade=2;
            onHit(lowestBall);
            return true;
        }else{
            return true;
        }
//        if (Math.abs(lowestBall.y-(_gameHeight-Assets.ballNormal.getHeight()/2))<120){
//            balls.remove(lowestBall);
//            flowers.remove(lowestFlower);
//            onHit(lowestBall);
//            return true;
//        }else if (Math.abs(lowestBall.y-(_gameHeight-Assets.ballNormal.getHeight()/2))>120&&Math.abs(lowestBall.y-(_gameHeight-Assets.ballNormal.getHeight()/2))<180){
//            balls.remove(lowestBall);
//            flowers.remove(lowestFlower);
//            onMiss(null);
//            return false;
//        }else{
//            return true;
//        }

//        while (iter.hasNext()) {
//            Ball b = iter.next();
//            if (lowestBall == null || b.y > lowestBall.y) {
//                lowestBall = b;
//            }
//        }

//        if (lowestBall != null && lowestBall.y > HITBOX_CENTER - HITBOX_HEIGHT / 2) {
//            balls.remove(lowestBall);
//            onHit(lowestBall);
//            return lowestBall.type != Ball.BallType.Skull;
//        } else {
//            if (lowestBall != null && lowestBall.y > HITBOX_CENTER - HITBOX_HEIGHT / 2 - MISS_ZONE_HEIGHT) {
//                balls.remove(lowestBall);
//            }
//            onMiss(null);
//
//            return false;
//        }
    }

    // triggers when a lane gets tapped that has currently no ball in its hitbox
    private void onMiss(Ball b) {
        _vibrator.vibrate(100);
        _streak = 0;
//        _score -= Math.min(_score, 50);
        _multiplier = 1;
        --_lifes;
        _grade=0;
//        updateMultipliers();
    }

    // triggers when a lane gets tapped that currently has a ball in its hitbox
    private void onHit(Ball b) {
        _streak++;
//        switch(b.type) {
//            case OneUp: {
//                ++_lifes;
//            } break;
//            case Multiplier: {
//                _doubleMultiplierTicker = DOUBLE_MULTIPLIER_TIME;
//            } break;
//            case Bomb: {
//                _explosionTicker = EXPLOSION_TIME;
//                Assets.soundExplosion.play(0.7f);
//            } break;
//            case Skull: {
//                onMiss(null); // hitting a skull counts as a miss
//                Assets.soundCreepyLaugh.play(1);
//                return;
//            }
//        }

//        updateMultipliers();
//        _score += 10 * _multiplier
//                * (_doubleMultiplierTicker > 0 ? 2 : 1);
        _score+=10*_grade;
    }

    // triggers after a touch event was handled by hitLane()
    private void updateMultipliers() {
        if (_streak > 80) {
            _multiplier = 10;
        }
        else if (_streak > 40) {
            _multiplier = 5;
        }
        else if (_streak > 30) {
            _multiplier = 4;
        }
        else if (_streak > 20) {
            _multiplier = 3;
        }
        else if (_streak > 10) {
            _multiplier = 2;
        }
        else {
            _multiplier = 1;
        }
    }

    //生成音符
    private void spawnBalls() {
        while (last_key_left+1<list_LEFT.size()&&_currentTime+go_time>=list_LEFT.get(last_key_left+1).getTime()){
            int x = (_gameWidth/2-Assets.placeholder.getWidth()*4/10+_gameWidth/2-Assets.placeholder.getWidth()/10)/2;
            int y = -Assets.ballNormal.getHeight()/2;
            Log.i("lalala","ball:y-"+String.valueOf(y)+"\nplaceholder:x-"+String.valueOf(Assets.placeholder.getWidth()/2));
            spawnBall(_ballsLeft,x,y,list_LEFT.get(last_key_left+1).getType(), _currentTime+go_time);
            x = _rand.nextInt(_gameWidth/4);
            spawnFlower(list_Flower_Left,x);
            last_key_left++;
        }
        while (last_key_right+1<list_RIGHT.size()&&_currentTime+go_time>=list_RIGHT.get(last_key_right+1).getTime()){
            int x = (_gameWidth/2+Assets.placeholder.getWidth()*4/10+_gameWidth/2+Assets.placeholder.getWidth()/10)/2;
            int y = -Assets.ballNormal.getHeight()/2;
            spawnBall(_ballsRight,x,y,list_RIGHT.get(last_key_right+1).getType(), _currentTime+go_time);
            x = _gameWidth/4*3+_rand.nextInt(_gameWidth/4)-Assets.flower_key1.getWidth();
            spawnFlower(list_Flower_Right,x);
            last_key_right++;
        }
    }

    //生成单个音符
    private void spawnBall(List<Ball>balls, int ballX, int ballY, int type,float time){
        balls.add(new Ball(ballX, ballY, type, time));
    }

    //生成花朵
    private void spawnFlower(List<ButtonImage>flowers,int x){
        int y = Assets.toprect.getHeight()+_rand.nextInt(_gameHeight/3*2);
        Graphics g = game.getGraphics();
        Image image = g.newImage("img/flower_key_1.png",Graphics.ImageFormat.ARGB4444);
        ButtonImage btn = new ButtonImage(image,image.getFormat(),x,y,null,null,null);
        flowers.add(btn);
    }

    //暂停状态
    private void updatePaused(List<TouchEvent> touchEvents) {
        if (_currentTrack.isPlaying()) {
            _currentTrack.pause();
        }

        Log.i("Testpause","更新pause状态读取触摸事件");
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                int x = event.x;
                int y = event.y;
                if (x>=pause.getX()&&x<=pause.getX()+pause.getWidth()&&y>=pause.getY()&&y<=pause.getY()+pause.getHeight()){
                    resume();
                    return;
                }
            }
        }
    }

    //To-do:结束界面
    private void updateGameOver(List<TouchEvent> touchEvents) {
        if (!_currentTrack.isStopped()) {
            _currentTrack.pause();
        }

        boolean success = _lifes>0;
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > first.getX() && event.x < first.getX()+first.getWidth()
                        && event.y > first.getY() && event.y < first.getY()+first.getHeight()) {
                    if (success) {
                        //进行下一关
                        //获取当前等级
                        String TAG = _difficulty.getMode();
                        Difficulty next = _difficulty;
                        switch (TAG){
                            case Difficulty.EASY_TAG:
                                next = new Difficulty(Difficulty.MED_TAG, "super_meat_boy_power_of_the_meat.mp3", 128, 10,"easy.txt");
                                break;
                            case Difficulty.MED_TAG:
                                next = new Difficulty(Difficulty.HARD_TAG, "high.mp3", 180, 15,"high.txt");
                                break;
                            case Difficulty.HARD_TAG:
                                //通关页面
                                game.goToActivity(MainActivity.class);
                                break;
                        }
                        game.setScreen(new LoadingScreen(game, next));
//                        game.goToActivity(MainActivity.class);
//                        return;
                    }else{
                        //满血复活
                        state = GameState.Paused;
                        _lifes=10;
                        resume();
                    }
                } else if (event.x >= second.getX() && event.x < second.getX()+second.getWidth()
                        && event.y > second.getY() && event.y < second.getY()+second.getHeight()) {
                    game.goToActivity(MainActivity.class);
                }
            }
        }

    }

    //每一帧的绘制函数
    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        // First draw the game elements.
        // Example:
        g.drawScaledImage(Assets.background, 0, 0,_gameWidth,_gameHeight,10,10,Assets.background.getWidth()-10,Assets.background.getHeight()-10);
        g.drawImage(Assets.placeholder,_gameWidth/2-Assets.placeholder.getWidth()/2,0);

        /*球击中的位置*/
        g.drawImage(Assets.scale, _gameWidth / 2 - (Assets.placeholder.getWidth() / 20 * 9), _gameHeight-Assets.scale.getHeight());
        g.drawImage(Assets.scale, _gameWidth / 2 + Assets.placeholder.getWidth() / 19, _gameHeight-Assets.scale.getHeight());

        /*画出球的位置*/
        for (Ball b: _ballsLeft) {
            paintBall(g, b);
        }

        for (Ball b: _ballsRight) {
            paintBall(g, b);
        }

        for (ButtonImage b: list_Flower_Left){
            paintFlower(g,b);
        }

        for (ButtonImage b: list_Flower_Right){
            paintFlower(g,b);
        }

        drawAllAnimator(g, animatorImages);

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();
    }

    private void drawAllAnimator(Graphics g, ArrayList<AnimatorImage> animatorImages){
        ArrayList<Integer> remove =new ArrayList<>();
        int i=0;
        for (AnimatorImage anim:animatorImages){
            if (anim.getI()<anim.getMax_i()){
                int half_x = anim.getAnim1(0).getWidth()/2;
                int half_y = anim.getAnim1(0).getHeight()/2;
                g.drawImage(anim.getAnim1(anim.getI()),anim.getX()-half_x,anim.getY()-half_y);
                anim.setI(anim.getI()+anim.getSpeed());
            }else{
                remove.add(i);
            }
            i++;
        }

        for (Integer j:remove){
            animatorImages.remove(j);
        }
    }

    //绘制音符的位置
    private void paintBall(Graphics g, Ball b) {
        switch(b.type) {
            case 1:
            case 4:
                g.drawImage(Assets.ballNormal, b.x - Assets.ballNormal.getWidth()/2, b.y-Assets.ballNormal.getHeight()/2);
                break;
            case 2:
                g.drawImage(Assets.ballOneUp, b.x - 90, b.y - 90);
                break;
            case 3:
                g.drawImage(Assets.ballMultiplier, b.x - 90, b.y - 90);
                break;
            case 5:
                g.drawImage(Assets.ballBomb,  b.x - 90, b.y - 90);
                break;
            case 6:
                g.drawImage(Assets.ballSkull, b.x - 90, b.y - 90);
                break;
        }
    }

    //绘制花朵
    private void paintFlower(Graphics g, ButtonImage b){
        g.drawImage(b.getImage(), b.getX(),b.getY());
    }

    private void nullify() {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        _paintScore = null;

        // Call garbage collector to clean up memory.
        System.gc();
    }

    //绘制准备界面
    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap to start!", _gameWidth / 2, _gameHeight / 2, _paintScore);
    }

    //绘制运行界面
    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.toprect,_gameWidth/2-Assets.toprect.getWidth()/2,0);
        //lst version
//        g.drawImage(Assets.hpframe, Assets.placeholder.getWidth() / 8,
//                Assets.toprect.getHeight() / 3);
//        g.drawImage(Assets.hp, Assets.placeholder.getWidth() / 8,
//                Assets.toprect.getHeight() / 3);
        //hy version
        g.drawScaledImage(Assets.hpframe,_gameWidth/192,Assets.toprect.getHeight()/5,_gameWidth/4,Assets.toprect.getHeight()/2,0,0,Assets.hpframe.getWidth(),Assets.hpframe.getHeight());
        g.drawScaledImage(Assets.hp,_gameWidth/192,Assets.toprect.getHeight()/5,
                _gameWidth/4-(10-_lifes)*_gameWidth/40,Assets.toprect.getHeight()/2,
                      0,0,Assets.hp.getWidth()-(10-_lifes)*Assets.hp.getWidth()/10,Assets.hp.getHeight());

//        g.drawImage(Assets.hpframe,Assets.placeholder.getWidth()/8,Assets.toprect.getHeight()/3);
//        g.drawImage(Assets.hp,Assets.placeholder.getWidth()/8,Assets.toprect.getHeight()/3);
        g.drawImage(Assets.score,_gameWidth/2+Assets.placeholder.getWidth(),Assets.toprect.getHeight()/6);
        g.drawImage(Assets.pause,_gameWidth/2-Assets.pause.getWidth()/2,0);

//        if (_doubleMultiplierTicker > 0) {
//            g.drawImage(Assets.sirens, 0, 100);
//        }
        g.drawString(String.valueOf(_score),
                _gameWidth / 2 + Assets.placeholder.getWidth() + Assets.score.getWidth()+70,
                Assets.toprect.getHeight() - Assets.hp.getHeight(), _paintScore);

        g.drawImage(Assets.combo,_gameWidth/2-Assets.combo.getWidth()/2,Assets.toprect.getHeight()+20);
        g.drawString(String.valueOf(_streak),_gameWidth/2,Assets.toprect.getHeight()+Assets.combo.getHeight()+100,_paintCombo);

        if (_grade!=-1){
            switch (_grade){
                case 0:
                    g.drawImage(Assets.miss,_gameWidth/2-Assets.miss.getWidth()/2,Assets.toprect.getHeight()+Assets.combo.getHeight()+100);
                    break;
                case 1:
                    g.drawImage(Assets.great,_gameWidth/2-Assets.great.getWidth()/2,Assets.toprect.getHeight()+Assets.combo.getHeight()+100);
                    break;
                case 2:
                    g.drawImage(Assets.perfect,_gameWidth/2-Assets.perfect.getWidth()/2,Assets.toprect.getHeight()+Assets.combo.getHeight()+100);
                    break;
            }

        }
    }

    //绘制暂停界面
    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        g.drawARGB(155, 0, 0, 0);
        g.drawImage(Assets.play, _gameWidth/2-Assets.pause.getWidth()/2,0);
        g.drawString("TAP TO CONTINUE", _gameWidth / 2, _gameHeight / 2, _paintScore);
    }

    //绘制游戏结束UI
    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawARGB(205, 0, 0, 0);
        g.drawImage(Assets.score_bg, _gameWidth/2-Assets.score_bg.getWidth()/2, _gameHeight/2-Assets.score_bg.getHeight()/2);
        boolean success = _lifes>0;
        if (success){
            g.drawImage(Assets.success,_gameWidth/2-Assets.success.getWidth()/2, _gameHeight/2-Assets.success.getHeight()/2);
            g.drawImage(Assets.continue_,_gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.continue_.getWidth()),_gameHeight/2+Assets.score_bg.getHeight()/2-Assets.success.getHeight()+Assets.continue_.getHeight());
            g.drawImage(Assets.return_,_gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.return_.getWidth()),_gameHeight/2+Assets.score_bg.getHeight()/2-Assets.success.getHeight()+2*Assets.continue_.getHeight()+20);
        }else{
            g.drawImage(Assets.fail,_gameWidth/2-Assets.fail.getWidth()/2, _gameHeight/2-Assets.fail.getHeight()/2);
            g.drawImage(Assets.resume_,_gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.resume_.getWidth()),_gameHeight/2+Assets.score_bg.getHeight()/2-Assets.fail.getHeight()+Assets.resume_.getHeight());
            g.drawImage(Assets.return_,_gameWidth/2+Assets.score_bg.getWidth()/2-(int)(1.6*Assets.return_.getWidth()),_gameHeight/2+Assets.score_bg.getHeight()/2-Assets.fail.getHeight()+2*Assets.resume_.getHeight()+20);
        }
        g.drawString(String.valueOf(_score),first.getX()+first.getWidth()/2,_gameHeight/2,_paintGameover);
    }

    //绘制暂停界面
    @Override
    public void pause() {
        if (state == GameState.Running) {
            state = GameState.Paused;
            _currentTrack.pause();
        }
    }

    //绘制恢复界面
    @Override
    public void resume() {
        if (state == GameState.Paused) {
            state = GameState.Running;
            _currentTrack.play();
        }
    }

    @Override
    public void dispose() {
        if(_currentTrack.isPlaying()) {
            _currentTrack.stop();
        }
    }

    @Override
    public void backButton() {
        dispose();
    }
}
