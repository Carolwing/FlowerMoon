package bupt.FirstGroup;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import bupt.FirstGroup.framework.Image;
import bupt.FirstGroup.game.models.MusicPoint;
import bupt.FirstGroup.models.Difficulty;

public class SelfMusicActivity extends AppCompatActivity {

    private TextView path1;
    private TextView path;
    private ImageButton button;
    private SeekBar seekBar;
    private TextView textView8;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        path1 = (TextView) findViewById(R.id.path1);
        path = (TextView)findViewById(R.id.path);
        button = findViewById(R.id.button3);
        seekBar = findViewById(R.id.seekBar3);
        textView8 = findViewById(R.id.textView8);

        seekBar.setMax(15);
        seekBar.setProgress(8);
        seekBar.setMin(8);
        textView8.setText(seekBar.getProgress());

        // 设置拖动条改变监听器
        SeekBar.OnSeekBarChangeListener osbcl = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textView8.setText(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        };

        // 为拖动条绑定监听器
        seekBar.setOnSeekBarChangeListener(osbcl);

        button.setOnTouchListener(new View.OnTouchListener() {
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (check(path1.getText().toString(),path.getText().toString())){

                        File cacheDir=getCacheDir();
                        if (!cacheDir.exists()){
                            cacheDir.mkdirs();
                        }
                        //音谱文件载入
                        File fromFile = new File(path1.getText().toString());
                        File outFile =new File(cacheDir,"music.mp3");
                        outFile.delete();
                        outFile.createNewFile();
                        Log.i("lalala",outFile.toString());
                        mCopyFile(fromFile,outFile);

                        //音谱
                        fromFile = new File(path.getText().toString());
                        outFile = new File(cacheDir,"music.txt");
                        outFile.delete();
                        outFile.createNewFile();
                        mCopyFile(fromFile,outFile);

                        Difficulty difficulty = new Difficulty(Difficulty.SELF_TAG,"music.mp3",120,seekBar.getProgress(),"music.txt");
                        Intent i;
                        i = new Intent(SelfMusicActivity.this,GameActivity.class);
                        i.putExtra("difficulty",difficulty);
                        startActivity(i);
                    }else{
                        Toast.makeText(SelfMusicActivity.this, "您选择的文件有误，请重新选择！s", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    boolean check(String path1,String path) throws FileNotFoundException {
        if (path1==null||path1.equals("")||path==null||path1.equals("")){
            return false;
        }
        if (!path1.endsWith("mp3")&&path1.endsWith("WMA")&&path1.endsWith("APE")&&path1.endsWith("FLAC")&&path1.endsWith("AAC")
        &&path1.endsWith("AC3")&&path1.endsWith("mmf")&&path1.endsWith("m4a")&&path1.endsWith("ogg")){
            return false;
        }


        FileInputStream inputStream = new FileInputStream(new File(path));
        DataInputStream dataIO = null;
        try {
            dataIO = new DataInputStream(inputStream);
            String strLine = null;
            while ((strLine = dataIO.readLine()) != null) {
                String[] all = strLine.split("\\s+");
                int time = Integer.parseInt(all[0]);
                int type = Integer.parseInt(all[1]);
                switch (type){
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void mCopyFile(File fromFile, File toFile) {
        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024 * 1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
        } catch (FileNotFoundException e) {
            Log.i("复制文件异常", e.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void select1(View view) {
        Intent intent = new Intent(SelfMusicActivity.this, FileExplorerActivity.class);
        startActivityForResult(intent,1001);
    }

    public void select2(View view) {
        Intent intent = new Intent(SelfMusicActivity.this, FileExplorerActivity.class);
        startActivityForResult(intent,1002);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(1001==requestCode&&RESULT_OK==resultCode){
            String apk_path = data.getStringExtra("apk_path");
            path1.setText(apk_path);
        }else if (1002==requestCode&&RESULT_OK==resultCode) {
            String apk_path = data.getStringExtra("apk_path");
            path.setText(apk_path);
        }
    }
}
