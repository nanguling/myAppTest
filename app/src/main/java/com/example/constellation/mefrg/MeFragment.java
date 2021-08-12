package com.example.constellation.mefrg;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.constellation.R;
import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.luckfrg.LuckAnalysisAdapter;
import com.example.constellation.luckfrg.LuckBaseAdapter;
import com.example.constellation.util.AssetsUtil;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MeFragment extends Fragment implements View.OnClickListener {

    CircleImageView iconIv;

    TextView nameTv;
    private List<StarInfoBean.StarinfoBean> beans;
    private Map<String, Bitmap> map;
    private SharedPreferences me;

    int selectPos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);

        //获取主页面传递的共享数据
        Bundle bundle = getArguments();
        StarInfoBean infoBean = (StarInfoBean) bundle.getSerializable("info");
        beans = infoBean.getStarinfo();

        //利用共享参数保存选择的星座
        me = getContext().getSharedPreferences("me", Context.MODE_PRIVATE);

        initView(view);

        return view;
    }

    /*初始化view*/
    private void initView(View view) {
        iconIv = (CircleImageView) view.findViewById(R.id.me_frag_cv);
        nameTv = (TextView) view.findViewById(R.id.me_frag_tv_name);
        map = AssetsUtil.getContentLogoIamMap();
        iconIv.setOnClickListener(this);

        //读取共享参数中保存的星座名称和logo名称
        String name = me.getString("name", "白羊座");
        String logoName = me.getString("logoName", "baiyang");

        Bitmap bitmap = map.get(logoName);
        iconIv.setImageBitmap(bitmap);
        nameTv.setText(name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_frag_cv:
                //弹出一个对话框
                showDialog();
                break;
        }
    }

    /*弹出一个对话框*/
    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.me_dialog,null);
        dialog.setContentView(dialogView);
        dialog.setTitle("请选择您的星座");
        GridView dialogGv = dialogView.findViewById(R.id.me_dialog_gv);

        //创建适配器
        LuckBaseAdapter adapter = new LuckBaseAdapter(beans,getContext());
        dialogGv.setAdapter(adapter);

        //设置dialog可以被取消
        dialog.setCancelable(true);
        //设置点击外部可以被取消
        dialog.setCanceledOnTouchOutside(true);

        //为GridView设置点击事件
        dialogGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                StarInfoBean.StarinfoBean bean = beans.get(position);
                String name = bean.getName();
                String logoName = bean.getLogoname();
                Bitmap bitmap = map.get(logoName);
                nameTv.setText(name);
                iconIv.setImageBitmap(bitmap);
                //保存选择的位置
                selectPos = position;
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        StarInfoBean.StarinfoBean bean = beans.get(selectPos);
        String name = bean.getName();
        String logoName = bean.getLogoname();
        //获取向共享参数写入数据的对象
        SharedPreferences.Editor edit = me.edit();
        edit.putString("name",name);
        edit.putString("logoName",logoName);
        edit.commit();
    }
}
