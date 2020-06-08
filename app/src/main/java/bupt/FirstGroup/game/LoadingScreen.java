package bupt.FirstGroup.game;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import bupt.FirstGroup.framework.Game;
import bupt.FirstGroup.framework.Graphics;
import bupt.FirstGroup.framework.Image;
import bupt.FirstGroup.framework.Screen;
import bupt.FirstGroup.framework.impl.RTGame;
import bupt.FirstGroup.framework.impl.RTMusic;
import bupt.FirstGroup.models.Difficulty;


public class LoadingScreen extends Screen {
    private Difficulty _diff;
    private static final String IMAGE_PATH = "img/";
    private static final String SOUND_EFFECTS_PATH = "audio/";
    private static final String MUSIC_PATH = "music/";
    private static final String MUSIC_SCORE_PATH= "musicScore/";
    private static final String ANIM_PATH = "Animator/";


    public LoadingScreen(Game game, Difficulty difficulty) {
        super(game);
        this._diff = difficulty;
    }


    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        //加载按钮资源
        Assets.flower_key1=g.newImage(IMAGE_PATH+"flower_key_1.png",Graphics.ImageFormat.ARGB4444);

        //加载特效资源
        Assets.anim1 = new Image[32];//触摸波纹
        for (int i=0;i<32;i++){
            Assets.anim1[i]=g.newImage(ANIM_PATH+"click_screen"+String.valueOf(i+1)+".png",Graphics.ImageFormat.ARGB4444);
        }
        Assets.anim2 = new Image[23];//完美击中
        for (int i=0;i<23;i++){
            Assets.anim2[i]=g.newImage(ANIM_PATH+"perfect_hit"+String.valueOf(i+1)+".png",Graphics.ImageFormat.ARGB4444);
            Assets.anim2[i]=Assets.anim2[i].resetSize(8*Assets.flower_key1.getWidth(),8*Assets.flower_key1.getHeight());
        }
        Assets.anim3 = new Image[23];//优秀击中
        for (int i=0;i<23;i++){
            Assets.anim3[i]=g.newImage(ANIM_PATH+"great_click"+String.valueOf(i+1)+".png",Graphics.ImageFormat.ARGB4444);
            Assets.anim3[i]=Assets.anim3[i].resetSize(8*Assets.flower_key1.getWidth(),8*Assets.flower_key1.getHeight());
        }
        Assets.anim4 = new Image[23];//消失
        for (int i=0;i<23;i++){
            Assets.anim4[i]=g.newImage(ANIM_PATH+"disappear"+String.valueOf(i+1)+".png",Graphics.ImageFormat.ARGB4444);
            Assets.anim4[i]=Assets.anim4[i].resetSize(4*Assets.flower_key1.getWidth(),4*Assets.flower_key1.getHeight());
        }
        //结束界面
        //结束界面背景
        Assets.score_bg= g.newImage(IMAGE_PATH+"score_bg.png",Graphics.ImageFormat.ARGB4444);
        //结束界面结果
        Assets.success= g.newImage(IMAGE_PATH+"success.png",Graphics.ImageFormat.ARGB4444);
        Assets.fail= g.newImage(IMAGE_PATH+"fail.png",Graphics.ImageFormat.ARGB4444);
        //结束界面按钮
        Assets.return_= g.newImage(IMAGE_PATH+"return.png",Graphics.ImageFormat.ARGB4444);
        Assets.continue_= g.newImage(IMAGE_PATH+"continue.png",Graphics.ImageFormat.ARGB4444);
        Assets.resume_= g.newImage(IMAGE_PATH+"resume.png",Graphics.ImageFormat.ARGB4444);

        //加载其他资源
        Assets.combo=g.newImage(IMAGE_PATH+"combo.png",Graphics.ImageFormat.ARGB4444);
        Assets.perfect=g.newImage(IMAGE_PATH+"perfect1.png",Graphics.ImageFormat.ARGB4444);
        Assets.great=g.newImage(IMAGE_PATH+"great1.png",Graphics.ImageFormat.ARGB4444);
        Assets.miss=g.newImage(IMAGE_PATH+"miss1.png",Graphics.ImageFormat.ARGB4444);
        Assets.perfect.resetSize(2*Assets.perfect.getWidth(),2*Assets.perfect.getHeight());
        Assets.great.resetSize(2*Assets.great.getWidth(),2*Assets.great.getHeight());
        Assets.miss.resetSize(2*Assets.miss.getWidth(),2*Assets.miss.getHeight());
        //加载图片资源
        Assets.scale = g.newImage(IMAGE_PATH+"scale_1.png",Graphics.ImageFormat.RGB565);
        Assets.background = g.newImage(IMAGE_PATH + "background4.png", Graphics.ImageFormat.RGB565);
        Assets.placeholder =g.newImage(IMAGE_PATH+"placeholder.png",Graphics.ImageFormat.RGB565);
        Assets.hpframe= g.newImage(IMAGE_PATH+"hp.png",Graphics.ImageFormat.RGB565);
        Assets.hp = g.newImage(IMAGE_PATH+"hp_2.png",Graphics.ImageFormat.RGB565);
        Assets.toprect=g.newImage(IMAGE_PATH+"top.png",Graphics.ImageFormat.RGB565);
        Assets.gameover = g.newImage(IMAGE_PATH + "gameover.png", Graphics.ImageFormat.RGB565);
        Assets.pause = g.newImage(IMAGE_PATH + "pause.png", Graphics.ImageFormat.RGB565);
        Assets.pauseclicked = g.newImage(IMAGE_PATH + "pauseclicked.png", Graphics.ImageFormat.RGB565);
        Assets.play = g.newImage(IMAGE_PATH+"replay.png",Graphics.ImageFormat.ARGB4444);
        Assets.score = g.newImage(IMAGE_PATH + "score.png", Graphics.ImageFormat.RGB565);
        Assets.streak = g.newImage(IMAGE_PATH + "streak.png", Graphics.ImageFormat.RGB565);

        Assets.ballNormal = g.newImage(IMAGE_PATH + "key_cut.png", Graphics.ImageFormat.RGB565);
        Assets.ballMultiplier = g.newImage(IMAGE_PATH + "ball_multiplier.png", Graphics.ImageFormat.RGB565);
        Assets.ballOneUp = g.newImage(IMAGE_PATH + "ball_oneup.png", Graphics.ImageFormat.RGB565);
        Assets.ballSpeeder = g.newImage(IMAGE_PATH + "ball_speeder.png", Graphics.ImageFormat.RGB565);
        Assets.ballBomb = g.newImage(IMAGE_PATH + "ball_bomb.png", Graphics.ImageFormat.RGB565);
        Assets.explosion = g.newImage(IMAGE_PATH + "explosion.png", Graphics.ImageFormat.RGB565);
        Assets.explosionBright = g.newImage(IMAGE_PATH + "explosion_bright.png", Graphics.ImageFormat.RGB565);
        Assets.ballSkull = g.newImage(IMAGE_PATH + "skull-ball-icon.png", Graphics.ImageFormat.RGB565);
        Assets.sirens = g.newImage(IMAGE_PATH + "sirens.png", Graphics.ImageFormat.RGB565);
        //加载音频资源
        Assets.soundClick = game.getAudio().createSound(SOUND_EFFECTS_PATH + "sound_guiclick.ogg");
        Assets.soundExplosion = game.getAudio().createSound(SOUND_EFFECTS_PATH + "sound_explosion.ogg");
        Assets.soundCreepyLaugh = game.getAudio().createSound(SOUND_EFFECTS_PATH + "sound_creepy_laugh.mp3");
        //加载音乐资源
        if (_diff.getMode().equals("self")){
            File cacheDir= game.getContext().getCacheDir();
            String path = cacheDir.getAbsolutePath()+"/music.mp3";
            Assets.musicTrack = new RTMusic(path);
            Log.i("lalala",path);
            try {
                Assets.musicScore = new FileInputStream(new File(String.valueOf(cacheDir.getAbsolutePath()), "music.txt").getAbsolutePath());
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.i("lalala",new File(String.valueOf(cacheDir.getAbsolutePath()),"music.txt").getAbsolutePath());
        }else {
            Assets.musicTrack = game.getAudio().createMusic(MUSIC_PATH + _diff.getMusic());
            try {
                Assets.musicScore = game.getFileIO().readAsset(MUSIC_SCORE_PATH + _diff.getScoreTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        game.setScreen(new GameScreen((RTGame)game, _diff));
    }
    @Override
    public void paint(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
