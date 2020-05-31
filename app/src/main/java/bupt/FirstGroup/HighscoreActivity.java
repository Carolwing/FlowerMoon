package bupt.FirstGroup;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


public class HighscoreActivity extends AppCompatActivity {
    public static String PREF_FILE = "HighscorePrefFile";
    private ImageButton goBack;
    private SharedPreferences _prefs;

    /* views */
    //private ListView _highscoreView;
    //private ArrayAdapter<Highscore> _adapter;
    //private ArrayList<Highscore> _arrayOfScores = new ArrayList<Highscore>();

    private TextView _easyTxtView;
    private TextView _medTxtView;
    private TextView _hardTxtView;
    private TextView time1, time2, time3;
    private TextView u1, u2, u3;
    private TextView[][] item = new TextView[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        goBack = (ImageButton) findViewById(R.id.im3);
        item[0][0] = (TextView) this.findViewById(R.id.tv_score1);
        item[1][0] = (TextView) this.findViewById(R.id.tv_score2);
        item[2][0] = (TextView) this.findViewById(R.id.tv_score3);
        item[0][1] = (TextView) this.findViewById(R.id.tv_time1);
        item[1][1] = (TextView) this.findViewById(R.id.tv_time2);
        item[2][1] = (TextView) this.findViewById(R.id.tv_time3);
        item[0][2] = (TextView) this.findViewById(R.id.tv_name1);
        item[1][2] = (TextView) this.findViewById(R.id.tv_name2);
        item[2][2] = (TextView) this.findViewById(R.id.tv_name3);
        System.out.println("2.1)HighScoreActivity.WorldRank.getString : " + WorldRank.getString());
        System.out.println("2.2)WorldRank.result[2][1]" + WorldRank.result[2][1]);

        goBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.xx1));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.xx2));
                }
                return false;
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighscoreActivity.this, MainActivity.class);
                HighscoreActivity.this.startActivity(intent);
            }
        });

        HighscoreActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[][] print = WorldRank.getResult();
                for (int i = 0; i < item.length; i++) {
                    for (int j = 0; j < item[i].length; j++) {
                        System.out.println("3.1)runOnUiThread SetText.item[" + i + "][" + j + "]:" + print[i][j]);
                        item[i][j].setText(print[i][j]);
                    }
                }
            }
        });
    }

}
