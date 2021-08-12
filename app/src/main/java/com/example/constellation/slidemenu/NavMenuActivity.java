package com.example.constellation.slidemenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.constellation.MainActivity;
import com.example.constellation.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class NavMenuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView titleIv;

    Button saveAutograph;

    EditText editAutograph;

    TextView autographTv;

    SharedPreferences autograph;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_menu);

        autograph = getSharedPreferences("autograph", MODE_PRIVATE);
        String content = autograph.getString("key", "");

        //初始化控件
        initView();

        editAutograph.setText(content);


    }

    private void initView() {
        titleIv = (ImageView) findViewById(R.id.title_iv_back_nav);
        saveAutograph = (Button) findViewById(R.id.save_autograph);
        editAutograph = (EditText) findViewById(R.id.edit_autograph);
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        autographTv = (TextView) view.findViewById(R.id.autographText);

        //获取EditText的焦点，并将光标移动到文本末尾
        editAutograph.setFocusableInTouchMode(true);
        editAutograph.requestFocus();
        editAutograph.setSelection(editAutograph.getText().length());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //弹出手机键盘
                getWindow().getDecorView().post(() -> {
                    InputMethodManager inputManager =
                            (InputMethodManager) getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editAutograph, 0);
                });
            }
        },500);

        //获取当前页面的view
        rootView = getWindow().getDecorView();
        rootView.setOnClickListener(view1 -> {
            if (getCurrentFocus() != null) {
                hideKeyboard(rootView,getCurrentFocus().getWindowToken());
            }
        });

        //设置监听事件
        titleIv.setOnClickListener(this);
        saveAutograph.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back_nav:
                if (autograph.getString("key","").contentEquals(editAutograph.getText())) {
                    finish();
                }else {
                    new AlertDialog.Builder(this)
                            .setTitle("是否保留此次编辑")
                            .setPositiveButton("保留", (dialogInterface, i) -> {
                                Editable text = editAutograph.getText();
                                autographTv.setText(text);
                                SharedPreferences.Editor edit = autograph.edit();
                                edit.putString("key",text.toString());
                                edit.commit();
                                finish();
                            })
                            .setNegativeButton("不保留", (dialogInterface, i) -> finish())
                            .setCancelable(false)
                            .show();
                }
                break;
            case R.id.save_autograph:
                Editable text = editAutograph.getText();
                autographTv.setText(text);
                SharedPreferences.Editor edit = autograph.edit();
                edit.putString("key", text.toString());
                edit.commit();
                finish();
                break;
        }
    }

    private void hideKeyboard(View rootView, IBinder windowToken) {
        if (windowToken == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) rootView.getContext().getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null){
            return;
        }
        boolean active = inputMethodManager.isActive();
        if (active) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
