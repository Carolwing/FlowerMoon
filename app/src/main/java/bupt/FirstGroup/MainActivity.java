package bupt.FirstGroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import bupt.FirstGroup.models.Difficulty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button login_btn;
    private ImageButton strat_btn;
    private ImageButton highscore_btn;
    private ImageButton about_btn;
    private ImageButton level1_btn;
    private ImageButton level2_btn;
    private ImageButton level3_btn;

    private final Difficulty _diffEasy =
            new Difficulty(Difficulty.EASY_TAG, "Spyro_Year_of_the_Dragon_Acoustic_Fields_OC_ReMix.mp3", 115f/2, 8);
    private final Difficulty _diffMid =
            new Difficulty(Difficulty.MED_TAG, "super_meat_boy_power_of_the_meat.mp3", 128, 10);
    private final Difficulty _diffHard =
            new Difficulty(Difficulty.HARD_TAG, "Aquaria_Minibadass_OC_ReMix.mp3", 180, 15);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = (Button)findViewById(R.id.login_btn);
        strat_btn=(ImageButton)findViewById(R.id.main_start_button);
        highscore_btn=(ImageButton)findViewById(R.id.main_highscore_button);
        about_btn=(ImageButton)findViewById(R.id.main_about_button);
        this.level1_btn=(ImageButton)findViewById(R.id.main_first_button);
        this.level2_btn=(ImageButton)findViewById(R.id.main_second_button);
        this.level3_btn=(ImageButton)findViewById(R.id.main_third_button);
        this.level1_btn.setOnClickListener(this);
        this.level2_btn.setOnClickListener(this);
        this.level3_btn.setOnClickListener(this);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(i);
            }
        });
        strat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,DifficultySelectionActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        highscore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,HighscoreActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AboutActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        /*
        level1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ImageAnimation.class);
                MainActivity.this.startActivity(i);
            }
        });*/

        strat_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.start_btn2));
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.start_btn1));
                }
                return false;
            }
        });

        highscore_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.score2));
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.score1));
                }
                return false;
            }
        });

        about_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.about2));
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.about1));
                }
                return false;
            }
        });

        level1_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.level1unclicked));
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.level1clicked));
                }
                return false;
            }
        });

        level2_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.level2unclicked));
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.level2clicked));
                }
                return false;
            }
        });

        level3_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.level3unclicked));
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.level3clicked));
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
                Log.e("","unexpected id!");
        }
    }
}
