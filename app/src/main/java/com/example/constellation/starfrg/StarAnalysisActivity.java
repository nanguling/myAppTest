package com.example.constellation.starfrg;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.constellation.R;
import com.example.constellation.bean.StarAnalysisBean;
import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.util.AssetsUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StarAnalysisActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title;

    ImageView backIv;

    CircleImageView iconIv;

    TextView nameTv,dateTv;

    RecyclerView analysisRv;

    StarInfoBean.StarinfoBean bean;

    //创建RecyclerView子元素的bean集合
    List<StarAnalysisBean> beanRv;

    private StarAnalysisAdapter adapter;

    private TextView footContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_analysis);
        //接收Intent传过来的共享数据
        Intent intent = getIntent();
        bean = (StarInfoBean.StarinfoBean) intent.getSerializableExtra("star");

        //初始化控件
        initView();

        //初始化RecyclerView子元素的bean集合
        beanRv = new ArrayList<>();
        //创建LinearLayoutManager对象设置到RcyclerView中，用来指定其布局方式
        //LinearLayoutManager为线性布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        analysisRv.setLayoutManager(manager);
        //创建适配器
        //此时没有数据，因为beanRv中没有数据
        adapter = new StarAnalysisAdapter(this, beanRv);
        analysisRv.setAdapter(adapter);

        //向beanRv中添加数据
        addDataToItem();

        //向RecyclerView添加底布局
        addFootViewToRecyclerView();
    }

    /*初始化控件*/
    private void initView() {
        title = (TextView) findViewById(R.id.title_tv);
        backIv = (ImageView) findViewById(R.id.title_iv_back);
        iconIv = (CircleImageView) findViewById(R.id.staranalysis_iv);
        nameTv = (TextView) findViewById(R.id.staranalysis_tv_name);
        dateTv = (TextView) findViewById(R.id.staranalysis_tv_date);
        analysisRv = (RecyclerView) findViewById(R.id.staranalysis_rv);

        title.setText("星座详情");
        backIv.setOnClickListener(this);
        nameTv.setText(bean.getName());
        dateTv.setText(bean.getDate());
        Map<String, Bitmap> logoMap = AssetsUtil.getContentLogoIamMap();
        Bitmap bitmap = logoMap.get(bean.getLogoname());
        iconIv.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    /*向RecycleView的子元素添加数据*/
    private void addDataToItem() {
        StarAnalysisBean bean1 = new StarAnalysisBean("性格特点 ：",bean.getTd(),R.color.lightblue);
        StarAnalysisBean bean2 = new StarAnalysisBean("掌管宫位 ：",bean.getGw(),R.color.lightpink);
        StarAnalysisBean bean3 = new StarAnalysisBean("显阴阳性 ：",bean.getYy(),R.color.lightgreen);
        StarAnalysisBean bean4 = new StarAnalysisBean("最大特征 ：",bean.getTz(),R.color.purple);
        StarAnalysisBean bean5 = new StarAnalysisBean("主管星球 ：",bean.getZg(),R.color.orange);
        StarAnalysisBean bean6 = new StarAnalysisBean("幸运颜色 ：",bean.getYs(),R.color.colorAccent);
        StarAnalysisBean bean7 = new StarAnalysisBean("搭配珠宝 ：",bean.getZb(),R.color.colorPrimary);
        StarAnalysisBean bean8 = new StarAnalysisBean("幸运号码 ：",bean.getHm(),R.color.grey);
        StarAnalysisBean bean9 = new StarAnalysisBean("相配金属 ：",bean.getJs(),R.color.darkblue);
        beanRv.add(bean1);
        beanRv.add(bean2);
        beanRv.add(bean3);
        beanRv.add(bean4);
        beanRv.add(bean5);
        beanRv.add(bean6);
        beanRv.add(bean7);
        beanRv.add(bean8);
        beanRv.add(bean9);

        //通知适配器数据发生改变
        adapter.notifyDataSetChanged();
    }

    /*向RecyclerView添加底布局*/
    private void addFootViewToRecyclerView() {

    }
}
