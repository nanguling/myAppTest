package com.example.constellation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.constellation.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userNameEt,passwordEt,againPwEt;

    ImageView delUserName,delPassword,seePassword,delPwAgain,seePwAgain,registerIv;

    private String userName;
    private String password;

    boolean pwCanSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        initView();

        //帐号输入状态监听事件
        userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本改变之后进行判断
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                //判断如果此时输入框中的帐号密码都不为空，则替换登录图片
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                    registerIv.setEnabled(true);//打开图片的响应事件
                    registerIv.setImageResource(R.drawable.login_focus);
                }else {
                    registerIv.setEnabled(false);
                    registerIv.setImageResource(R.drawable.login_normal);
                }
            }
        });
        //密码输入状态监听
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本改变之后进行判断
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                //判断如果此时输入框中的帐号密码都不为空，则替换登录图片
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                    registerIv.setEnabled(true);//打开图片的响应事件
                    registerIv.setImageResource(R.drawable.login_focus);
                }else {
                    registerIv.setEnabled(false);
                    registerIv.setImageResource(R.drawable.login_normal);
                }
            }
        });
        //密码再次输入状态监听
        againPwEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本改变之后进行判断
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                //判断如果此时输入框中的帐号密码都不为空，则替换登录图片
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                    registerIv.setEnabled(true);//打开图片的响应事件
                    registerIv.setImageResource(R.drawable.login_focus);
                }else {
                    registerIv.setEnabled(false);
                    registerIv.setImageResource(R.drawable.login_normal);
                }
            }
        });
        //帐号输入焦点监听
        userNameEt.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                delUserName.setVisibility(View.VISIBLE);
            }else {
                delUserName.setVisibility(View.INVISIBLE);
            }
        });
        //密码输入焦点监听
        passwordEt.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                seePassword.setVisibility(View.VISIBLE);
                delPassword.setVisibility(View.VISIBLE);
            }else {
                seePassword.setVisibility(View.INVISIBLE);
                delPassword.setVisibility(View.INVISIBLE);
            }
        });
        //密码再次输入焦点监听
        againPwEt.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                seePwAgain.setVisibility(View.VISIBLE);
                delPwAgain.setVisibility(View.VISIBLE);
            }else {
                seePwAgain.setVisibility(View.INVISIBLE);
                delPwAgain.setVisibility(View.INVISIBLE);
            }
        });

        //隐藏状态栏和导航栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    //初始化控件
    private void initView() {
        userNameEt = (EditText) findViewById(R.id.re_user_name);
        passwordEt = (EditText) findViewById(R.id.re_password);
        againPwEt = (EditText) findViewById(R.id.re_again_password);
        delUserName = (ImageView) findViewById(R.id.re_delete_user_name_iv);
        delPassword = (ImageView) findViewById(R.id.re_delete_password_iv);
        seePassword = (ImageView) findViewById(R.id.re_see_password_iv);
        delPwAgain = (ImageView) findViewById(R.id.re_again_delete_password_iv);
        seePwAgain = (ImageView) findViewById(R.id.re_again_see_password_iv);
        registerIv = (ImageView) findViewById(R.id.re_register_iv);

        //初始密码不可见
        pwCanSee = false;
        delUserName.setVisibility(View.INVISIBLE);
        delPassword.setVisibility(View.INVISIBLE);
        seePassword.setVisibility(View.INVISIBLE);
        delPwAgain.setVisibility(View.INVISIBLE);
        seePwAgain.setVisibility(View.INVISIBLE);

        //设置点击事件
        delUserName.setOnClickListener(this);
        delPassword.setOnClickListener(this);
        delPwAgain.setOnClickListener(this);
        seePassword.setOnClickListener(this);
        seePwAgain.setOnClickListener(this);
        registerIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_delete_user_name_iv:
                userNameEt.setText("");
                break;
            case R.id.re_delete_password_iv:
                passwordEt.setText("");
                break;
            case R.id.re_see_password_iv:
                setSeePassword(passwordEt,seePassword);
                break;
            case R.id.re_again_delete_password_iv:
                againPwEt.setText("");
                break;
            case R.id.re_again_see_password_iv:
                setSeePassword(againPwEt,seePwAgain);
                break;
            case R.id.re_register_iv:
                //将得到的帐号和密码保存在数据库
                break;
        }
    }

    public void setSeePassword(EditText editText,ImageView imageView) {
        if (pwCanSee) {
            //设置不可见
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.nosee);
            editText.setSelection(passwordEt.getText().length());
            pwCanSee = false;
        }else {
            //设置可见
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.see);
            editText.setSelection(passwordEt.getText().length());
            pwCanSee = true;
        }
    }
}
