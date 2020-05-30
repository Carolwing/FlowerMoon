package bupt.FirstGroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import bupt.FirstGroup.entity.CurrentUser;
import bupt.FirstGroup.models.Difficulty;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        //_highscoreView = (ListView)this.findViewById(R.id.highscore_list_view);

        _easyTxtView = (TextView) this.findViewById(R.id.tv_score1);
        _medTxtView = (TextView) this.findViewById(R.id.tv_score2);
        _hardTxtView = (TextView) this.findViewById(R.id.tv_score3);
        time1 = (TextView) this.findViewById(R.id.tv_time1);
        time2 = (TextView) this.findViewById(R.id.tv_time2);
        time3 = (TextView) this.findViewById(R.id.tv_time3);
        u1 = (TextView) this.findViewById(R.id.tv_name1);
        u2 = (TextView) this.findViewById(R.id.tv_name2);
        u3 = (TextView) this.findViewById(R.id.tv_name3);

        // load highscores
        //_prefs = PreferenceManager.getDefaultSharedPreferences(this);

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
                //以上为jdbc注册
            }
        }).start();

        //_easyTxtView.setText(easyMode);
        // _medTxtView.setText(mediumMode);
        // _hardTxtView.setText(hardMode);
    }
}
