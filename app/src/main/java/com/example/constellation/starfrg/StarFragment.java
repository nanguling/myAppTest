package com.example.constellation.starfrg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.constellation.R;
import com.example.constellation.bean.StarInfoBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * 星座Fragment
 * 包括ViewPager和GridView
 */
public class StarFragment extends Fragment {

    ViewPager starVp;

    GridView starGv;

    LinearLayout pointLayout;

    private List<StarInfoBean.StarinfoBean> beans;

    private StarBaseAdapter starBaseAdapter;

    //声明图片数组用来管理图片
    int[] imagIds = {R.mipmap.pic_guanggao,R.mipmap.pic_star};
    //声明ViewPager数据源
    List<ImageView> vpDataList;
    //声明小圆点集合
    List<ImageView> pointList;

    //完成定时装置，实现自动滑动图片的效果
    Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                //获取当前ViewPager显示的页面
                int currentItem = starVp.getCurrentItem();
                //判断是否为最后一张，如果是最后一张则切换到第一张
                if (currentItem == vpDataList.size()-1) {
                    //切换到第一张
                    starVp.setCurrentItem(0);
                }else {
                    //切换到下一张
                    currentItem++;
                    starVp.setCurrentItem(currentItem);
                }
                //实现循环切换，接收消息的同时，发送消息
                handler.sendEmptyMessageDelayed(1,5000);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_star, container, false);
        initView(view);
        Bundle bundle = getArguments();
        StarInfoBean starInfoBean = (StarInfoBean) bundle.getSerializable("info");
        beans = starInfoBean.getStarinfo();//获取关于星座信息数据的集合
        //创建适配器
        starBaseAdapter = new StarBaseAdapter(getContext(), beans);
        starGv.setAdapter(starBaseAdapter);

        //初始化ViewPager控件
        initPager();

        //设置ViewPager监听事件
        setViewPagerListener();

        //设置GridView监听事件
        setGridViewListener();

        //发送延迟消息，5秒切换一次图片
        handler.sendEmptyMessageDelayed(1,5000);

        return view;
    }

    /*初始化控件*/
    private void initView(View view) {
        starVp = (ViewPager) view.findViewById(R.id.starfrag_vp);
        starGv = (GridView) view.findViewById(R.id.starfrag_gv);
        pointLayout = (LinearLayout) view.findViewById(R.id.starfrag_layout);
    }

    /*设置ViewPager显示的页面*/

    private void initPager() {
        vpDataList = new ArrayList<>();
        pointList = new ArrayList<>();
        for (int i = 0; i < imagIds.length; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(imagIds[i]);
            //设置图片属性
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//设置全部显示
            //设置图片的宽高
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            //将图片加载到集合中
            vpDataList.add(iv);

            //创建图片对应的小圆点
            ImageView piv = new ImageView(getContext());
            piv.setImageResource(R.mipmap.point_normal);
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            plp.setMargins(20,0,0,0);//设置外边距
            piv.setLayoutParams(plp);
            //将小圆点添加到布局当中
            pointLayout.addView(piv);
            //将小圆点添加到集合中
            pointList.add(piv);
        }
        //将第一个小圆点设置为焦点状态
        pointList.get(0).setImageResource(R.mipmap.point_focus);
        //创建适配器
        StarPagerAdapter starPagerAdapter = new StarPagerAdapter(getContext(), vpDataList);
        starVp.setAdapter(starPagerAdapter);
    }

    /*设置ViewPager监听*/
    private void setViewPagerListener() {
        starVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).setImageResource(R.mipmap.point_normal);
                }
                pointList.get(position).setImageResource(R.mipmap.point_focus);
            }
        });
    }

    /*设置GridView监听事件*/
    private void setGridViewListener() {
        starGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                StarInfoBean.StarinfoBean bean = beans.get(position);
                Intent intent = new Intent(getContext(), StarAnalysisActivity.class);
                intent.putExtra("star",bean);
                startActivity(intent);
            }
        });
    }

}
