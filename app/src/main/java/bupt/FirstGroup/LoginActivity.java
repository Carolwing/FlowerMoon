package bupt.FirstGroup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import android.os.Looper;


import bupt.FirstGroup.entity.CurrentUser;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TRY";
    private EditText name;
    private EditText password, phoneNumber;
    private ImageButton login_btn, register_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phone);


        login_btn = (ImageButton) findViewById(R.id.login);
        register_btn = (ImageButton) findViewById(R.id.register);
        login_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.signin1));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.signin));
                }
                return false;
            }
        });

        register_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击按钮
                    //重新设置按下去时的按钮图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.register1));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//松开按钮
                    //再修改为正常抬起时的图片
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.mipmap.register));
                }
                return false;
            }
        });
    }

    //用户根据点击事件来找到相应的功能
    public void fun(View v) {
        switch (v.getId()) {
            case R.id.register:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String n = name.getText().toString().trim();
                        String psw = password.getText().toString().trim();
                        String phone = phoneNumber.getText().toString().trim();      //添加inputText
                        if (n.equals("") || psw.equals("")) {
                            Looper.prepare();
                            Toast toast = Toast.makeText(LoginActivity.this, "输入不能为空！", Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        } else if (phone.equals("")) {
                            Looper.prepare();
                            Toast toast = Toast.makeText(LoginActivity.this, "注册时电话号码不能为空！", Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                        DBConnection db = new DBConnection();
                        String result = db.logUp(n, phone, psw);
                        Looper.prepare();
                        Toast toast = Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT);
                        toast.show();
                        if (result.equals("注册成功")) {
                            //一下代码为跳转界面
                            Looper.myLooper().quit();
                            CurrentUser.setName(n);
                            System.out.println(CurrentUser.getName());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //intent.putExtra("name",n);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                        Looper.loop();
                        //以上为jdbc注册
                    }
                }).start();
                break;
            case R.id.login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String n = name.getText().toString().trim();
                        String psw = password.getText().toString().trim();
                        if (n.equals("") || psw.equals("")) {
                            Looper.prepare();
                            Toast toast = Toast.makeText(LoginActivity.this, "输入不能为空！", Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                        DBConnection db = new DBConnection();
                        String result = db.logIn(n, psw);
                        Looper.prepare();
                        Toast toast = Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT);
                        toast.show();
                        System.out.println(result);
                        System.out.println(result.equals("登录成功"));
                        if (result.equals("登录成功")) {
                            //一下代码为跳转界面
                            CurrentUser.setName(n);
                            System.out.println(CurrentUser.getName());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Looper.myLooper().quit();
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                        Looper.loop();

                        //以上为jdbc登录
                    }
                }).start();

        }

    }
}
