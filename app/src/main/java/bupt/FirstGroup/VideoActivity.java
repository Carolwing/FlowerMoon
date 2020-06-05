package bupt.FirstGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import bupt.FirstGroup.models.Difficulty;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EASY_TAG = "easy";
    public static final String MED_TAG = "medium";
    public static final String HARD_TAG = "hard";
    private Difficulty _diff;
    private ImageButton skip;
    private VideoView videoView;
    private String uri;

    private final Difficulty _diffEasy =
            new Difficulty(Difficulty.EASY_TAG, "easy.mp3", 115f / 2, 8, "easy.txt");
    private final Difficulty _diffMid =
            new Difficulty(Difficulty.MED_TAG, "super_meat_boy_power_of_the_meat.mp3", 128, 10, "easy.txt");
    private final Difficulty _diffHard =
            new Difficulty(Difficulty.HARD_TAG, "high.mp3", 180, 15, "high.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _diff = (Difficulty) this.getIntent().getSerializableExtra("difficulty");
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.video_view);
        skip = findViewById(R.id.btn);
        if (ContextCompat.checkSelfPermission(VideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
        }
    }

    private void initVideoPath() {
        switch (_diff.getMode()) {
            case EASY_TAG:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.easy;
                break;
            case MED_TAG:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.medium;
                break;
            case HARD_TAG:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.hard;
                break;
        }
        videoView.setVideoPath(String.valueOf(Uri.parse(uri)));
        videoView.start();
        if (!videoView.isPlaying()) {
            Intent i = new Intent(this, VideoActivity.class);
            i.putExtra("difficulty", this._diffEasy);
            this.startActivity(i);
        }
        skip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.skip));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.skip1));
                }
                return false;
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.suspend();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent i;
        i = new Intent(this, VideoView.class);
        i.putExtra("difficulty", this._diff);
        this.startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "权限拒绝", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }
    }
}
