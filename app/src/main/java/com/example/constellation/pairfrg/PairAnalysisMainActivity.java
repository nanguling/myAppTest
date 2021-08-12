package com.example.constellation.pairfrg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.constellation.R;
import com.example.constellation.bean.PairAnalysisBean;
import com.example.constellation.util.AssetsUtil;
import com.example.constellation.util.LoadDataAsyncTask;
import com.example.constellation.util.URLContent;
import com.google.gson.Gson;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PairAnalysisMainActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView manIv,womanIv;

    TextView manTv,womanTv;

    TextView pairTv,contrastTv,scoreTv,weightTv,analysisTv,careTv;

    ImageView backIv;

    TextView titleTv;
    private Map<String, Bitmap> bitmapMap;
    private String manStarName;
    private String manStarLogoName;
    private String womanStarName;
    private String womanStarLogoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_analysis_main);

        initView();

        //接收上一个页面传过来的intent，获取共享数据
        getDataAndSet();

        //获取网络路径地址
        String url = URLContent.getPairURL(manStarName, womanStarName);

        //加载网络数据
        //1.创建自定义的异步任务对象
        LoadDataAsyncTask asyncTask = new LoadDataAsyncTask(this, new LoadDataAsyncTask.OnGetNetDataListener() {
            @Override
            public void onSuccess(String json) {
                //解析数据
                if (!TextUtils.isEmpty(json)) {
                    Gson gson = new Gson();
                    PairAnalysisBean pairAnalysisBean = gson.fromJson(json, PairAnalysisBean.class);
                    PairAnalysisBean.PairBean pairBean = pairAnalysisBean.getResult();
                    scoreTv.setText("配对评分： " + pairBean.getZhishu() + "  " + pairBean.getJieguo());
                    weightTv.setText("星座比重： " + pairBean.getBizhong());
                    analysisTv.setText("解析：\n" + pairBean.getLianai());
                    careTv.setText("注意事项：\n" + pairBean.getZhuyi());
                }
            }
        });
        asyncTask.execute(url);
    }

    private void initView() {
        manIv = (CircleImageView) findViewById(R.id.pair_analysis_man_iv);
        womanIv = (CircleImageView) findViewById(R.id.pair_analysis_woman_iv);
        manTv = (TextView) findViewById(R.id.pair_analysis_man_tv);
        womanTv = (TextView) findViewById(R.id.pair_analysis_woman_tv);
        pairTv = (TextView) findViewById(R.id.pair_analysis_iv_pair);
        contrastTv = (TextView) findViewById(R.id.pair_analysis_iv_contrast);
        scoreTv = (TextView) findViewById(R.id.pair_analysis_iv_score);
        weightTv = (TextView) findViewById(R.id.pair_analysis_iv_weight);
        analysisTv = (TextView) findViewById(R.id.pair_analysis_iv_analysis);
        careTv = (TextView) findViewById(R.id.pair_analysis_iv_care);
        backIv = (ImageView) findViewById(R.id.title_iv_back);
        titleTv = (TextView) findViewById(R.id.title_tv);

        //为backIv绑定监听器
        backIv.setOnClickListener(this);
        //为标题栏设置标题内容
        titleTv.setText("配对详情");
    }


    @Override
    public void onClick(View view) {
        finish();
    }

    private void getDataAndSet() {
        Intent intent = getIntent();
        manStarName = intent.getStringExtra("man_starName");
        manStarLogoName = intent.getStringExtra("man_starLogoName");
        womanStarName = intent.getStringExtra("woman_starName");
        womanStarLogoName = intent.getStringExtra("woman_starLogoName");
        manTv.setText(manStarName);
        womanTv.setText(womanStarName);
        bitmapMap = AssetsUtil.getContentLogoIamMap();
        Bitmap bitmapMan = bitmapMap.get(manStarLogoName);
        manIv.setImageBitmap(bitmapMan);
        Bitmap bitmapWoman = bitmapMap.get(womanStarLogoName);
        womanIv.setImageBitmap(bitmapWoman);
        pairTv.setText("星座配对： " + manStarName + "男 & " + womanStarName + "女");
        contrastTv.setText(manStarName + " VS " + womanStarName);
    }
}
