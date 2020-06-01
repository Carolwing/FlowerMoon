package bupt.FirstGroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import android.os.Looper;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bupt.FirstGroup.entity.CurrentUser;
import bupt.FirstGroup.game.DBConnection;


public class LoginActivity extends AppCompatActivity {
    private EditText name;
    private EditText password, phoneNumber;
    private ImageButton login_btn, register_btn;
    private CheckBox checkBox;
    public static final String PREFERENCE_NAME = "LoginSetting";
    public static int MODE = Context.MODE_PRIVATE;
    private String namestr, pwdstr, phonestr, checkstr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phone);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        login_btn = (ImageButton) findViewById(R.id.login);
        register_btn = (ImageButton) findViewById(R.id.register);
        loadSharedPreferences();


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

    //简单存储
    private void saveSharedPreferences() {
        System.out.println("4)saveSharedPreferences");
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Check", String.valueOf(checkBox.isChecked()));
        System.out.println("4.1)String.valueOf(checkBox.isChecked())" + String.valueOf(checkBox.isChecked()));
        editor.putString("Name", name.getText().toString().trim());
        editor.putString("Pwd", password.getText().toString().trim());
        editor.putString("Phone", phoneNumber.getText().toString().trim());
        editor.commit();
        //loadSharedPreferences();
    }


    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        System.out.println("1)loadSharedPreferences");
        checkstr = sharedPreferences.getString("Check", "false");
        namestr = sharedPreferences.getString("Name", "");
        pwdstr = sharedPreferences.getString("Pwd", "");
        phonestr = sharedPreferences.getString("Phone", "");
        setParaments();
        //Toast.makeText(this, "调用loadShared", Toast.LENGTH_SHORT).show();
        changeText();
    }

    private void changeText() {
        System.out.println("3)changeText");
        checkBox.setChecked(Boolean.parseBoolean(checkstr));
        System.out.println("3.1)Boolean.parseBoolean(checkstr):" + Boolean.parseBoolean(checkstr));
        if (Boolean.parseBoolean(checkstr)) {
            name.setText(namestr);
            phoneNumber.setText(phonestr);
            password.setText(pwdstr);
        }
    }

    private void setParaments() {
        System.out.println("2)setParaments");
        checkBox.setChecked(Boolean.parseBoolean(checkstr));
        if (Boolean.parseBoolean(checkstr)) {
            name.setText(namestr);
            phoneNumber.setText(phonestr);
            password.setText(pwdstr);
        }
    }

    //用户根据点击事件来找到相应的功能
    public void fun(View v) {
        switch (v.getId()) {
            case R.id.register:
                if (checkBox.isChecked()) {
                    saveSharedPreferences();
                }
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
                            CurrentUser.setName(n);
                            System.out.println(CurrentUser.getName());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                        Looper.loop();
                        //以上为jdbc注册
                    }
                }).start();
                break;
            case R.id.login:
                if (checkBox.isChecked()) saveSharedPreferences();
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
