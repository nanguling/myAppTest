package com.example.constellation.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.constellation.MainActivity;
import com.example.constellation.R;
import com.example.constellation.entity.User;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userNameEt,passwordEt;

    ImageView delUserName,delPassword,seePassword,loginIv;

    TextView forgetPassword,registerUser;

    private String userName;
    private String password;

    boolean pwCanSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        initView();

        //帐号输入状态监听
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
                    loginIv.setEnabled(true);//打开图片的响应事件
                    loginIv.setImageResource(R.drawable.login_focus);
                }else {
                    loginIv.setEnabled(false);
                    loginIv.setImageResource(R.drawable.login_normal);
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
                    loginIv.setEnabled(true);//打开图片的响应事件
                    loginIv.setImageResource(R.drawable.login_focus);
                }else {
                    loginIv.setEnabled(false);
                    loginIv.setImageResource(R.drawable.login_normal);
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

        //隐藏状态栏和导航栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

    }

    private void initView() {
        userNameEt = (EditText) findViewById(R.id.user_name);
        passwordEt = (EditText) findViewById(R.id.password);
        delUserName = (ImageView) findViewById(R.id.delete_user_name_iv);
        delPassword = (ImageView) findViewById(R.id.delete_password_iv);
        seePassword = (ImageView) findViewById(R.id.see_password_iv);
        loginIv = (ImageView) findViewById(R.id.login_iv);
        forgetPassword = (TextView) findViewById(R.id.forget_password_tv);
        registerUser = (TextView) findViewById(R.id.register_user_tv);

        pwCanSee = false;//初始密码不可见
        delUserName.setVisibility(View.INVISIBLE);
        delPassword.setVisibility(View.INVISIBLE);
        seePassword.setVisibility(View.INVISIBLE);

        //为控件设置监听事件
        delUserName.setOnClickListener(this);
        delPassword.setOnClickListener(this);
        seePassword.setOnClickListener(this);
        loginIv.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        registerUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_user_name_iv:
                userNameEt.setText("");
                break;
            case R.id.delete_password_iv:
                passwordEt.setText("");
                break;
            case R.id.see_password_iv:
                if (pwCanSee) {
                    //设置不可见
                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    seePassword.setImageResource(R.drawable.nosee);
                    passwordEt.setSelection(passwordEt.getText().length());
                    pwCanSee = false;
                }else {
                    //设置可见
                    passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    seePassword.setImageResource(R.drawable.see);
                    passwordEt.setSelection(passwordEt.getText().length());
                    pwCanSee = true;
                }
                break;
            case R.id.login_iv:
                //在数据库中查询是否存在该用户
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                List<User> users = LitePal.where("userName = ?", userName).find(User.class);
                if (users.size() != 0) {
                    List<User> users1 = LitePal.where("password = ?", password).find(User.class);
                    if (users1.size() != 0) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        new AlertDialog.Builder(this)
                                .setTitle("登录失败")
                                .setMessage("帐号或密码错误，请重新输入")
                                .show();
                        break;
                    }
                }else {
                    new AlertDialog.Builder(this)
                                .setTitle("登录失败")
                                .setMessage("帐号或密码错误，请重新输入")
                                .show();
                    break;
                }
                break;
            case R.id.forget_password_tv:
                break;
            case R.id.register_user_tv:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
