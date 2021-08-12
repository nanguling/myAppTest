package com.example.constellation.welcome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.constellation.MainActivity;
import com.example.constellation.R;

public class WelcomeActivity extends AppCompatActivity {

    TextView tv;

    int count = 2;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                count--;
                if (count == 0) {
                    //判断是否是第一次进入App，以此来决定需要跳转哪个页面
                    boolean isFirst = first.getBoolean("isFirst", true);
                    Intent intent = null;
                    if (isFirst) {
                        //第一次进入App，跳转引导界面
                        intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                        //为了下一次不跳转引导界面，更改值
                        SharedPreferences.Editor edit = first.edit();
                        edit.putBoolean("isFirst",false);
                        edit.commit();
                    }else {
                        //不是第一次，则不需要跳转引导界面
                        intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }else {
                    tv.setText(count+"");
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            }
        }
    };
    private SharedPreferences first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv = (TextView) findViewById(R.id.welcome_tv);

        //创建一个共享参数用来保存状态
        first = getSharedPreferences("first", MODE_PRIVATE);

        handler.sendEmptyMessageDelayed(1,1000);

        //隐藏状态栏和导航栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

}
