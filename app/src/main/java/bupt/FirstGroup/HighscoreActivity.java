package bupt.FirstGroup;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.SharedPreferences;

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

        _easyTxtView = (TextView) this.findViewById(R.id.tv_score1);
        _medTxtView = (TextView) this.findViewById(R.id.tv_score2);
        _hardTxtView = (TextView) this.findViewById(R.id.tv_score3);
        time1 = (TextView) this.findViewById(R.id.tv_time1);
        time2 = (TextView) this.findViewById(R.id.tv_time2);
        time3 = (TextView) this.findViewById(R.id.tv_time3);
        u1 = (TextView) this.findViewById(R.id.tv_name1);
        u2 = (TextView) this.findViewById(R.id.tv_name2);
        u3 = (TextView) this.findViewById(R.id.tv_name3);
        item[0][0] = (TextView) this.findViewById(R.id.tv_score1);
        item[1][0] = (TextView) this.findViewById(R.id.tv_score2);
        item[2][0] = (TextView) this.findViewById(R.id.tv_score3);
        item[0][1] = (TextView) this.findViewById(R.id.tv_time1);
        item[1][1] = (TextView) this.findViewById(R.id.tv_time2);
        item[2][1] = (TextView) this.findViewById(R.id.tv_time3);
        item[0][2] = (TextView) this.findViewById(R.id.tv_name1);
        item[1][2] = (TextView) this.findViewById(R.id.tv_name2);
        item[2][2] = (TextView) this.findViewById(R.id.tv_name3);
        // load highscores
        //_prefs = PreferenceManager.getDefaultSharedPreferences(this);
/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnection db = new DBConnection();
                result1 = db.findMaxScore(1);
                _easyTxtView.setText(result1[1]);
                time1.setText(result1[0]);
                u1.setText(result1[2]);
                result2 = db.findMaxScore(2);
                _medTxtView.setText(result2[1]);
                time2.setText(result2[0]);
                u2.setText(result2[2]);
                result3 = db.findMaxScore(3);
                _hardTxtView.setText(result3[1]);
                time3.setText(result3[0]);
                u3.setText(result3[2]);

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnection db = new DBConnection();
                System.out.println(" worldrank");
                String[] r1=db.findMaxScore(1);
                String[] r2=db.findMaxScore(2);
                String[] r3=db.findMaxScore(3);
                System.out.println(" worldrank "+r1[2]+r2[2]);
                WorldRank.setResult(new String[][]{r1, r2, r3});
            }
        }).start();



        String[][] print = WorldRank.getResult();
        for (int i = 0; i < item.length; i++) {
            for (int j = 0; j < item[i].length; j++) {
                System.out.println("End of thread result["+i+"]["+j+"]:"+print[i][j]);
                //item[i][j].setText(print[i][j]);
            }
        }

*/
        //_easyTxtView.setText(easyMode);
        // _medTxtView.setText(mediumMode);
        // _hardTxtView.setText(hardMode);
        HighscoreActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBConnection db = new DBConnection();
                        System.out.println(" worldrank");
                        String[] r1 = db.findMaxScore(1);
                        String[] r2 = db.findMaxScore(2);
                        String[] r3 = db.findMaxScore(3);
                        System.out.println(" worldrank " + r1[2] + r2[2]);
                        WorldRank.setResult(new String[][]{r1, r2, r3});
                        System.out.println(WorldRank.result[2][1] + " --WorldRank.result[2][1] in Run");
                        String[][] print = WorldRank.getResult();
                        for (int i = 0; i < item.length; i++) {
                            for (int j = 0; j < item[i].length; j++) {
                                System.out.println("End of thread result[" + i + "][" + j + "]:" + print[i][j]);
                                item[i][j].setText(print[i][j]);
                            }
                        }
                    }
                }).start();

            }
        });

    }
}
