package com.example.constellation.luckfrg;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.constellation.R;
import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.util.AssetsUtil;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LuckBaseAdapter extends BaseAdapter {

    List<StarInfoBean.StarinfoBean> beans;

    Map<String, Bitmap> logoMap;

    Context context;

    public LuckBaseAdapter(List<StarInfoBean.StarinfoBean> beans, Context context) {
        this.beans = beans;
        this.context = context;
        this.logoMap = AssetsUtil.getContentLogoIamMap();
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_luck_gv,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        StarInfoBean.StarinfoBean bean = beans.get(position);
        holder.luckItemTv.setText(bean.getName());
        holder.luckItemIv.setImageBitmap(logoMap.get(bean.getLogoname()));
        return view;
    }

    class ViewHolder {
        CircleImageView luckItemIv;

        TextView luckItemTv;

        public ViewHolder(View view) {
            luckItemIv = (CircleImageView) view.findViewById(R.id.item_luck_iv);
            luckItemTv = (TextView) view.findViewById(R.id.item_luck_tv);
        }
    }
}
