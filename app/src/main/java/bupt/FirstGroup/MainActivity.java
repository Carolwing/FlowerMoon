package bupt.FirstGroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import bupt.FirstGroup.entity.CurrentUser;
import bupt.FirstGroup.models.Difficulty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton strat_btn;
    private ImageButton highscore_btn;
    private ImageButton about_btn;
    private ImageButton level1_btn;
    private ImageButton level2_btn;
    private ImageButton level3_btn;
    private TextView showuser;


    private final Difficulty _diffEasy =
            new Difficulty(Difficulty.EASY_TAG, "easy.mp3", 115f/2, 8,"easy.txt");
    private final Difficulty _diffMid =
            new Difficulty(Difficulty.MED_TAG, "super_meat_boy_power_of_the_meat.mp3", 128, 10,"easy.txt");
    private final Difficulty _diffHard =
            new Difficulty(Difficulty.HARD_TAG, "Aquaria_Minibadass_OC_ReMix.mp3", 180, 15,"easy.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(CurrentUser.getName());
        showuser = (TextView) findViewById(R.id.userName);
        strat_btn = (ImageButton) findViewById(R.id.main_start_button);
        highscore_btn = (ImageButton) findViewById(R.id.main_highscore_button);
        about_btn = (ImageButton) findViewById(R.id.main_about_button);
        this.level1_btn = (ImageButton) findViewById(R.id.main_first_button);
        this.level2_btn = (ImageButton) findViewById(R.id.main_second_button);
        this.level3_btn = (ImageButton) findViewById(R.id.main_third_button);
        this.level1_btn.setOnClickListener(this);
        this.level2_btn.setOnClickListener(this);
        this.level3_btn.setOnClickListener(this);
        showuser.setText("欢迎 " + CurrentUser.getName());
        showuser.setTextColor(Color.WHITE);


        strat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ImageAnimation.class);//记得改回来！！！这里只是一个测试
                MainActivity.this.startActivity(i);
            }
        });

        highscore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBConnection db = new DBConnection();
                        System.out.println("1.0)MainActivity.new Thread");
                        String[] r1 = db.findMaxScore(1);
                        String[] r2 = db.findMaxScore(2);
                        String[] r3 = db.findMaxScore(3);
                        // System.out.println(" worldrank "+r1[2]+r2[2]);
                        System.out.println("1.2)WorldRank.setString");
                        WorldRank.setResult(new String[][]{r1, r2, r3});
                        System.out.println("1.3)WorldRank.result[2][1]" + WorldRank.result[2][1]);
                        System.out.println("1.4)MainActivity.this.startActivity(i)");
                        Intent i = new Intent(MainActivity.this, HighscoreActivity.class);
                        MainActivity.this.startActivity(i);
                    }
                }).start();
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(i);
            }
        });


        strat_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.start_btn2));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.start_btn1));
                }
                return false;
            }
        });

        highscore_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.score2));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.score1));
                }
                return false;
            }
        });

        about_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.about2));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.about1));
                }
                return false;
            }
        });

        level1_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.level1unclicked));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.level1clicked));
                }
                return false;
            }
        });

        level2_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.level2unclicked));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.level2clicked));
                }
                return false;
            }
        });

        level3_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.level3unclicked));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.level3clicked));
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.main_first_button:
                i = new Intent(this, GameActivity.class);
                i.putExtra("difficulty", this._diffEasy);
                this.startActivity(i);
                break;
            case R.id.main_second_button:
                i = new Intent(this, GameActivity.class);
                i.putExtra("difficulty", this._diffMid);
                this.startActivity(i);
                break;
            case R.id.main_third_button:
                i = new Intent(this, GameActivity.class);
                i.putExtra("difficulty", this._diffHard);
                this.startActivity(i);
                break;
            default:
                Log.e("", "unexpected id!");
        }
    }
}
