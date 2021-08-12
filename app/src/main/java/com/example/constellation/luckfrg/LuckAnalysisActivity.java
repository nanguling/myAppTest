package com.example.constellation.luckfrg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.constellation.R;
import com.example.constellation.bean.LuckAnalysisBean;
import com.example.constellation.bean.LuckItemBean;
import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.util.LoadDataAsyncTask;
import com.example.constellation.util.URLContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LuckAnalysisActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView luckRv;

    TextView titleTv;

    ImageView backIv;

    String starName;

    List<LuckItemBean> luckItemBeans;

    LuckAnalysisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_analysis);
        Intent intent = getIntent();
        StarInfoBean.StarinfoBean starBean = (StarInfoBean.StarinfoBean) intent.getSerializableExtra("luck");
        starName = starBean.getName();

        //获取URL地址
        String url = URLContent.getLuckURL(starName);

        initView();
        luckItemBeans = new ArrayList<>();

        //加载网络数据
        //创建自定义异步任务对象
        LoadDataAsyncTask asyncTask = new LoadDataAsyncTask(this, new LoadDataAsyncTask.OnGetNetDataListener() {
            @Override
            public void onSuccess(String json) {
                //不为空则解析数据
                if (!TextUtils.isEmpty(json)) {
                    Gson gson = new Gson();
                    LuckAnalysisBean luckAnalysisBeans = gson.fromJson(json, LuckAnalysisBean.class);
                    //为了方便显示在RecyclerView上，重新整理解析的数据,整理成集合的形式
                    addDataToList(luckAnalysisBeans);
                    //指定RecyclerView布局方式
                    LinearLayoutManager manager = new LinearLayoutManager(LuckAnalysisActivity.this);
                    luckRv.setLayoutManager(manager);
                    //创建适配器
                    adapter = new LuckAnalysisAdapter(LuckAnalysisActivity.this,luckItemBeans);
                    luckRv.setAdapter(adapter);
                }
            }
        });
        asyncTask.execute(url);
    }

    /*整理数据到集合中*/
    private void addDataToList(LuckAnalysisBean luckAnalysisBeans) {
        LuckItemBean bean1 = new LuckItemBean("综合运势",luckAnalysisBeans.getMima().getText().get(0), Color.BLUE);
        LuckItemBean bean2 = new LuckItemBean("爱情运势",luckAnalysisBeans.getLove().get(0),Color.RED);
        LuckItemBean bean3 = new LuckItemBean("事业学业",luckAnalysisBeans.getCareer().get(0),Color.GRAY);
        LuckItemBean bean4 = new LuckItemBean("健康运势",luckAnalysisBeans.getHealth().get(0),Color.GREEN);
        LuckItemBean bean5 = new LuckItemBean("财富运势",luckAnalysisBeans.getFinance().get(0),Color.BLACK);
        luckItemBeans.add(bean1);
        luckItemBeans.add(bean2);
        luckItemBeans.add(bean3);
        luckItemBeans.add(bean4);
        luckItemBeans.add(bean5);

    }

    private void initView() {
        luckRv = (RecyclerView) findViewById(R.id.luck_analysis_rv);
        titleTv = (TextView) findViewById(R.id.title_tv);
        backIv = (ImageView) findViewById(R.id.title_iv_back);

        backIv.setOnClickListener(this);
        titleTv.setText(starName);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
