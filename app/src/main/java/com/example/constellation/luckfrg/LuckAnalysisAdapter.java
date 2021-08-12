package com.example.constellation.luckfrg;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.constellation.R;
import com.example.constellation.bean.LuckItemBean;

import java.util.List;

public class LuckAnalysisAdapter extends RecyclerView.Adapter<LuckAnalysisAdapter.ViewHolder> {

    Context context;

    List<LuckItemBean> beans;

    public LuckAnalysisAdapter(Context context, List<LuckItemBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_luck_analysis_rv,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LuckItemBean bean = beans.get(position);
        holder.itemTitle.setText(bean.getTitle());
        GradientDrawable drawable = (GradientDrawable) holder.itemTitle.getBackground();
        drawable.setColor(bean.getColor());
        holder.itemContent.setText(bean.getContent());
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;

        TextView itemContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_luck_analysis_tv_title);
            itemContent = (TextView) itemView.findViewById(R.id.item_luck_analysis_tv_content);
        }
    }
}
