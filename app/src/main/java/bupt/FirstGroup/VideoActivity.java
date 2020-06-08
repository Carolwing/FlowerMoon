package bupt.FirstGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import bupt.FirstGroup.framework.Game;
import bupt.FirstGroup.models.Difficulty;

public class VideoActivity extends AppCompatActivity {
    public static final String EASY_TAG = "easy";
    public static final String MED_TAG = "medium";
    public static final String HARD_TAG = "hard";
//    public static final String HEAD_TAG = "head";
    public static final String END_TAG = "end";
    private Difficulty _diff;
    private ImageButton skip;
    private VideoView videoView;
    private String uri;
    private String tag;

//    private final Difficulty _diffEasy =
//            new Difficulty(Difficulty.EASY_TAG, "easy.mp3", 115f / 2, 8, "easy.txt");
//    private final Difficulty _diffMid =
//            new Difficulty(Difficulty.MED_TAG, "super_meat_boy_power_of_the_meat.mp3", 128, 10, "easy.txt");
//    private final Difficulty _diffHard =
//            new Difficulty(Difficulty.HARD_TAG, "high.mp3", 180, 15, "high.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
        _diff = (Difficulty) this.getIntent().getSerializableExtra("difficulty");
        tag = (String) this.getIntent().getSerializableExtra("TAG");
        if (tag==null)
            tag="head";
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.video_view);
        skip = findViewById(R.id.btn);

        if (ContextCompat.checkSelfPermission(VideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
            Log.i("lalala","init成功");
        }
    }

    private void initVideoPath() {
        switch (tag) {
            case EASY_TAG:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.easy;
                break;
            case MED_TAG:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.medium;
                break;
            case HARD_TAG:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.hard;
                break;
//            case HEAD_TAG:
//                uri = "android.resource://" + getPackageName() + "/" + R.raw.head7;
//                break;
            case END_TAG:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.ending;
                break;
            default:
                uri = "android.resource://" + getPackageName() + "/" + R.raw.head7;
                break;
        }
        videoView.setVideoPath(String.valueOf(Uri.parse(uri)));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
            //1.得到视频的总时长
            int duration =videoView.getDuration();
            videoView.start();
            }
        });
        Log.i("lalala","isplay:"+String.valueOf(videoView.isPlaying()));

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra)
            {
                //设置屏幕显示信息
                Log.i("lalala","未知错误");

                return false;
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                Toast.makeText(VideoActivity.this, "播放完成", Toast.LENGTH_LONG).show();
                Intent i;
                switch (tag){
                    case EASY_TAG:
                    case MED_TAG:
                    case HARD_TAG:
                        i = new Intent(VideoActivity.this, GameActivity.class);
                        i.putExtra("difficulty", _diff);
                        startActivity(i);
                        break;
                    case END_TAG:
                        i = new Intent(VideoActivity.this,MainActivity.class);
                        startActivity(i);
                        break;
                    default:
                        i = new Intent(VideoActivity.this,LoginActivity.class);
                        startActivity(i);
                        break;
                }
            }
        });

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
                videoView.stopPlayback();
                Intent i;
                switch (tag){
                    case EASY_TAG:
                    case MED_TAG:
                    case HARD_TAG:
                        i = new Intent(VideoActivity.this, GameActivity.class);
                        i.putExtra("difficulty", _diff);
                        startActivity(i);
                        break;
                    case END_TAG:
                        i = new Intent(VideoActivity.this,MainActivity.class);
                        startActivity(i);
                        break;
                    default:
                        i = new Intent(VideoActivity.this,LoginActivity.class);
                        startActivity(i);
                        break;
                }
            }
        });
    }
//
//    @Override
//    public void onClick(View v) {
//        Intent i;
//        i = new Intent(this, GameActivity.class);
//        i.putExtra("difficulty", this._diff);
//        this.startActivity(i);
//    }

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
