package bupt.FirstGroup;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.SharedPreferences;

import android.view.WindowManager;
import android.widget.TextView;



public class HighscoreActivity extends AppCompatActivity {
    public static String PREF_FILE = "HighscorePrefFile";
    public static String[] result1 = new String[4];
    public static String[] result2 = new String[4];
    public static String[] result3 = new String[4];
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
