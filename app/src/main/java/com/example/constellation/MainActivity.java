package com.example.constellation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.luckfrg.LuckFragment;
import com.example.constellation.mefrg.MeFragment;
import com.example.constellation.pairfrg.PairFragment;
import com.example.constellation.starfrg.StarFragment;
import com.example.constellation.util.AssetsUtil;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup mainGr;

    //声明四个按钮对应的Fragment
    Fragment starFrag;

    Fragment pairFrag;

    Fragment luckFrag;

    Fragment meFrag;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainGr = (RadioGroup) findViewById(R.id.main_rg);
        //设置监听事件
        mainGr.setOnCheckedChangeListener(this);

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
}
