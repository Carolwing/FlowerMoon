package bupt.FirstGroup;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import bupt.FirstGroup.models.Difficulty;

public class DifficultySelectionActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnEasy;
    private Button btnMid;
    private Button btnHard;

    private final Difficulty _diffEasy =
            new Difficulty(Difficulty.EASY_TAG, "easy.mp3", 115f/2, 8, "easy.txt");
    private final Difficulty _diffMid =
            new Difficulty(Difficulty.MED_TAG, "medium.mp3", 128, 10,"medium.txt");
    private final Difficulty _diffHard =
            new Difficulty(Difficulty.HARD_TAG, "high.mp3", 180, 15,"high.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);

        this.btnEasy = (Button)this.findViewById(R.id.diff_btn_easy);
        this.btnEasy.setOnClickListener(this);
        this.btnMid = (Button)this.findViewById(R.id.diff_btn_mid);
        this.btnMid.setOnClickListener(this);
        this.btnHard = (Button)this.findViewById(R.id.diff_btn_hard);
        this.btnHard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.diff_btn_easy:
                i = new Intent(this, GameActivity.class);
                i.putExtra("difficulty", this._diffEasy);
                this.startActivity(i);
                break;
            case R.id.diff_btn_mid:
                i = new Intent(this, GameActivity.class);
                i.putExtra("difficulty", this._diffMid);
                this.startActivity(i);
                break;
            case R.id.diff_btn_hard:
                i = new Intent(this, GameActivity.class);
                i.putExtra("difficulty", this._diffHard);
                this.startActivity(i);
                break;
            default:
                Log.e("","unexpected id!");
        }
    }
}
