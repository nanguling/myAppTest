package com.example.constellation.starfrg;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.constellation.R;
import com.example.constellation.bean.StarAnalysisBean;

import java.util.List;

public class StarAnalysisAdapter extends RecyclerView.Adapter<StarAnalysisAdapter.ViewHolder> {

    Context context;

    List<StarAnalysisBean> beanRv;

    public StarAnalysisAdapter(Context context, List<StarAnalysisBean> beanRv) {
        this.context = context;
        this.beanRv = beanRv;
        Log.e("Adapter","构造方法执行");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_star_analysis,parent,false);
        ViewHolder holder = new ViewHolder(view);
        Log.e("Adapter","onCreateViewHolder方法执行");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StarAnalysisBean bean = beanRv.get(position);
        holder.itemTitle.setText(bean.getTitle());
        Log.e("Adapter","title == " + bean.getTitle());
        holder.itemContent.setText(bean.getContent());
        Log.e("Adapter","content == " + bean.getContent());
        holder.itemContent.setBackgroundResource(bean.getColor());
        Log.e("Adapter","color == " + bean.getColor());
    }

    @Override
    public int getItemCount() {
        return beanRv.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;

        TextView itemContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_star_tv_title);
            itemContent = (TextView) itemView.findViewById(R.id.item_star_tv_content);
            Log.e("Adapter","ViewHolder被创建");
        }
    }
}
