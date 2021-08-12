package com.example.constellation.pairfrg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.constellation.R;
import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.util.AssetsUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TemplatesHandler;


public class PairFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ImageView manIv,womanIv;

    Spinner manSp,womanSp;

    Button luckDraw;

    Button pair;

    private List<StarInfoBean.StarinfoBean> beans;

    //Spinner的item元素的集合，存放星座名称
    List<String> starNames;

    private Map<String, Bitmap> contentLogoIamMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pair, container, false);
        initView(view);
        Bundle bundle = getArguments();
        StarInfoBean starInfoBean = (StarInfoBean) bundle.getSerializable("info");
        beans = starInfoBean.getStarinfo();
        //获取Spinner适配器所需要的数据源
        getDataforList();
        //创建适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_pair_spinner, R.id.item_pair_sp, starNames);
        //设置适配器
        manSp.setAdapter(adapter);
        womanSp.setAdapter(adapter);

        //让图片默认选中第一个
        String logoName = beans.get(0).getLogoname();
        contentLogoIamMap = AssetsUtil.getContentLogoIamMap();
        Bitmap bitmap = contentLogoIamMap.get(logoName);
        manIv.setImageBitmap(bitmap);
        womanIv.setImageBitmap(bitmap);

        return view;
    }

    private void initView(View view) {
        manIv = (ImageView) view.findViewById(R.id.pair_man_iv);
        womanIv = (ImageView) view.findViewById(R.id.pair_woman_iv);
        manSp = (Spinner) view.findViewById(R.id.pair_man_spinner);
        womanSp = (Spinner) view.findViewById(R.id.pair_woman_spinner);
        luckDraw = (Button) view.findViewById(R.id.pair_button_luck_draw);
        pair = (Button) view.findViewById(R.id.pair_button_pair);

        //设置Button的监听器
        luckDraw.setOnClickListener(this);
        pair.setOnClickListener(this);

        //设置Spinner的监听器
        manSp.setOnItemSelectedListener(this);
        womanSp.setOnItemSelectedListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pair_button_luck_draw:
                break;
            case R.id.pair_button_pair:
                int manSel = manSp.getSelectedItemPosition();
                int womanSel = womanSp.getSelectedItemPosition();
                Intent intent = new Intent(getContext(), PairAnalysisMainActivity.class);
                intent.putExtra("man_starName",beans.get(manSel).getName());
                intent.putExtra("man_starLogoName",beans.get(manSel).getLogoname());
                intent.putExtra("woman_starName",beans.get(womanSel).getName());
                intent.putExtra("woman_starLogoName",beans.get(womanSel).getLogoname());
                startActivity(intent);
                break;
        }
    }

    private void getDataforList() {
        starNames = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            starNames.add(beans.get(i).getName());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.pair_man_spinner:
                String logonameMan = beans.get(position).getLogoname();
                Bitmap bitmapMan = contentLogoIamMap.get(logonameMan);
                manIv.setImageBitmap(bitmapMan);
                break;
            case R.id.pair_woman_spinner:
                String logonameWoman = beans.get(position).getLogoname();
                Bitmap bitmapWoman = contentLogoIamMap.get(logonameWoman);
                womanIv.setImageBitmap(bitmapWoman);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
