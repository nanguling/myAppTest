package com.example.constellation.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.constellation.MainActivity;
import com.example.constellation.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    ViewPager guideVp;

    //存放三张引导界面的图片地址
    int resId[] = {R.mipmap.loading1,R.mipmap.loading2,R.mipmap.loading3};

    List<ImageView> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideVp = (ViewPager) findViewById(R.id.guide_vp);
        images = new ArrayList<>();

        //初始化ViewPager
        initPager();

        //为第三个引导界面的图片设置监听事件
        setListener();

        //设置导航栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void setListener() {
        ImageView imageView = images.get(images.size() - 1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initPager() {
        for (int i = 0; i < resId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(resId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置等比例放大
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            images.add(imageView);
        }

        //创建适配器
        GuideAdapter adapter = new GuideAdapter(images);
        guideVp.setAdapter(adapter);

    }
}
