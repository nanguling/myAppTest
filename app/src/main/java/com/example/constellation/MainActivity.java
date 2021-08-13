package com.example.constellation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.luckfrg.LuckFragment;
import com.example.constellation.mefrg.MeFragment;
import com.example.constellation.pairfrg.PairFragment;
import com.example.constellation.slidemenu.NavMenuActivity;
import com.example.constellation.starfrg.StarFragment;
import com.example.constellation.util.AssetsUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    RadioGroup mainGr;

    //声明四个按钮对应的Fragment
    Fragment starFrag;

    Fragment pairFrag;

    Fragment luckFrag;

    Fragment meFrag;

    private FragmentManager fragmentManager;

    NavigationView navView;

    LinearLayout autographLay;

    DrawerLayout drawableLayout;

    TextView autographTv;

    CircleImageView headerCv;

    private SharedPreferences autograph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainGr = (RadioGroup) findViewById(R.id.main_rg);
        //设置监听事件
        mainGr.setOnCheckedChangeListener(this);

        autograph = getSharedPreferences("autograph", MODE_PRIVATE);

        initView();

        //加载星座相关数据
        StarInfoBean infoBean = loadData();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",infoBean);

        //初始化四个Fragment对象
        starFrag = new StarFragment();
        starFrag.setArguments(bundle);

        pairFrag = new PairFragment();
        pairFrag.setArguments(bundle);

        luckFrag = new LuckFragment();
        luckFrag.setArguments(bundle);

        meFrag = new MeFragment();
        meFrag.setArguments(bundle);
        //将四个Fragment进行动态加载，动态加载到布局。replace   add/hide/show
        addFragmentPage();

    }

    private void initView() {
        drawableLayout = (DrawerLayout) findViewById(R.id.drawablelayout);
        navView = (NavigationView) findViewById(R.id.navView);
        //得到滑动菜单的头部布局
        View header = navView.inflateHeaderView(R.layout.nav_header);
        //通过头部布局得到布局里的控件
        autographLay = (LinearLayout) header.findViewById(R.id.autograph_layout);
        autographTv = (TextView) header.findViewById(R.id.autographText);
        headerCv = (CircleImageView) findViewById(R.id.main_header_cv);

        //获取共享参数里的签名信息，赋值到签名中
        autographTv.setText(autograph.getString("key",""));

        //为滑动菜单设置监听事件
        navView.setNavigationItemSelectedListener(this);
        //为autograph设置监听事件
        autographLay.setOnClickListener(this);
        //为主页面的小头像设置监听事件
        headerCv.setOnClickListener(view -> drawableLayout.openDrawer(GravityCompat.START));
    }

    /**
     * 读取assets文件夹下xzcontent.json文件
     */
    private StarInfoBean loadData() {
        String json = AssetsUtil.getJsonFromAssets(this, "xzcontent/xzcontent.json");
        Gson gson = new Gson();
        Log.e("MainActivity",json);
        StarInfoBean infoBean = gson.fromJson(json, StarInfoBean.class);
        AssetsUtil.saveBitmapFromAssets(this,infoBean);
        return infoBean;
    }

    /**
     * 将主页当中的Fragment一起加载进入布局，将有用的显示，暂时无用的隐藏
     */
    private void addFragmentPage() {
        //1.创建Fragment管理者对象
        fragmentManager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //3.将四个Fragment同意添加到布局
        transaction.add(R.id.main_layout_center,starFrag);
        transaction.add(R.id.main_layout_center,pairFrag);
        transaction.add(R.id.main_layout_center,luckFrag);
        transaction.add(R.id.main_layout_center,meFrag);
        //4.最开始显示第一个，隐藏后面的三个
        transaction.hide(pairFrag);
        transaction.hide(luckFrag);
        transaction.hide(meFrag);
        //5.提交事务
        transaction.commit();
    }

    /*为底部button绑定点击事件*/
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.main_rb_star:
                transaction.hide(pairFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.show(starFrag);
                break;
            case R.id.main_rb_pair:
                transaction.hide(starFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.show(pairFrag);
                break;
            case R.id.main_rb_luck:
                transaction.hide(starFrag);
                transaction.hide(pairFrag);
                transaction.hide(meFrag);
                transaction.show(luckFrag);
                break;
            case R.id.main_rb_me:
                transaction.hide(starFrag);
                transaction.hide(pairFrag);
                transaction.hide(luckFrag);
                transaction.show(meFrag);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /*为滑动菜单每一项绑定点击事件*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.member:
                Toast.makeText(MainActivity.this,"点击了我的会员",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wallet:
                Toast.makeText(MainActivity.this,"点击了我的钱包",Toast.LENGTH_SHORT).show();
                break;
            case R.id.attire:
                Toast.makeText(MainActivity.this,"点击了我的装扮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.collection:
                Toast.makeText(MainActivity.this,"点击了我的收藏",Toast.LENGTH_SHORT).show();
                break;
            case R.id.quit:
                new AlertDialog.Builder(this)
                        .setTitle("是否退出应用？")
                        .setCancelable(false)
                        .setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }

        return true;
    }

    /*为签名绑定点击事件*/
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, NavMenuActivity.class);
        SharedPreferences.Editor edit = autograph.edit();
        edit.putString("key", autographTv.getText().toString());
        edit.commit();
        drawableLayout.closeDrawers();
        startActivity(intent);
    }

    /**
     * 从个签修改界面跳到主页面时，主页面一般不会执行onCreate()方法。因此在onStart()方法中进行共享参数的获取，并且赋值到个签，从而完成个签的修改
     */
    @Override
    protected void onStart() {
        super.onStart();
            autographTv.setText(autograph.getString("key",""));
    }

}
