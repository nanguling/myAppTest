package com.example.constellation.luckfrg;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.constellation.R;
import com.example.constellation.bean.StarInfoBean;

import java.util.List;


public class LuckFragment extends Fragment {

    GridView luckFrgGv;

    List<StarInfoBean.StarinfoBean> beans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_luck, container, false);

        luckFrgGv = (GridView) view.findViewById(R.id.luck_frag_gv);

        //获取主页面传过来的共享数据
        Bundle bundle = getArguments();
        StarInfoBean infoBean = (StarInfoBean) bundle.getSerializable("info");
        beans = infoBean.getStarinfo();
        //创建GridView的适配器
        LuckBaseAdapter adapter = new LuckBaseAdapter(beans,getContext());
        luckFrgGv.setAdapter(adapter);

        //为GridView设置点击事件
        setOnCLickLintenerForGv();

        return view;
    }

    private void setOnCLickLintenerForGv() {
        luckFrgGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                StarInfoBean.StarinfoBean bean = beans.get(position);
                Intent intent = new Intent(getContext(),LuckAnalysisActivity.class);
                intent.putExtra("luck",bean);
                startActivity(intent);
            }
        });
    }

}
