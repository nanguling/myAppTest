package com.example.constellation.starfrg;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.constellation.R;
import com.example.constellation.bean.StarInfoBean;
import com.example.constellation.util.AssetsUtil;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StarBaseAdapter extends BaseAdapter {
    Context context;

    List<StarInfoBean.StarinfoBean> starinfo;

    Map<String, Bitmap> logoMap;

    public StarBaseAdapter(Context context, List<StarInfoBean.StarinfoBean> starinfo) {
        this.context = context;
        this.starinfo = starinfo;
        this.logoMap = AssetsUtil.getLogoIamMap();
    }

    @Override
    public int getCount() {
        return starinfo.size();
    }

    @Override
    public Object getItem(int position) {
        return starinfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_star_gv,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        //获取指定位置的数据
        final StarInfoBean.StarinfoBean bean = starinfo.get(position);
        holder.tv.setText(bean.getName());
        //获得图片名称，根据名称在内存中进行查找
        String logoname = bean.getLogoname();
        Bitmap bitmap = logoMap.get(logoname);
        holder.iv.setImageBitmap(bitmap);
        return view;
    }
}

//对于item当中的控件进行声明和初始化
class ViewHolder{
    CircleImageView iv;
    TextView tv;

    public ViewHolder(View view) {
        iv = view.findViewById(R.id.item_star_iv);
        tv = view.findViewById(R.id.item_star_tv);
    }
}
