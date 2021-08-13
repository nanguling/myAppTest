package com.example.constellation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.constellation.R;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    Button register,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //初始化控件
        register = (Button) findViewById(R.id.first_register);
        login = (Button) findViewById(R.id.first_login);

        //为按钮绑定点击事件
        register.setOnClickListener(this);
        login.setOnClickListener(this);

        //隐藏状态栏和导航栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_register:
                Intent intent = new Intent(FirstActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.first_login:
                Intent intent1 = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
