package com.example.constellation.starfrg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.constellation.R;
import com.example.constellation.bean.StarAnalysisBean;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public class AddFooterViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    List<String> headList;

    List<StarAnalysisBean> bodyList;

    List<String> footList;

    public static final int HEAD_TYPE = 1;

    public static final int BODY_TYPE = 2;

    public static final int FOOT_TYPE = 3;

    public AddFooterViewAdapter(Context context, List<String> headList, List<StarAnalysisBean> bodyList, List<String> footList) {
        this.context = context;
        this.headList = headList;
        this.bodyList = bodyList;
        this.footList = footList;
    }

    //定义规则，在什么情况下使用哪一种ViewType
    @Override
    public int getItemViewType(int position) {
        int viewType = -1;
        if (position < getHeadCount()) {
            viewType = HEAD_TYPE;
        }else if (position > getHeadCount() + getBodyCount() - 1) {
            viewType = FOOT_TYPE;
        }else {
            viewType = BODY_TYPE;
        }
        return viewType;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        //根据ViewType来决定加载哪个布局，返回相应的ViewHolder
        switch (viewType) {
            case HEAD_TYPE:
                return null;
            case BODY_TYPE:
                view = inflater.inflate(R.layout.item_star_analysis, parent, false);
                return new BodyHolder(view);
            case FOOT_TYPE:
                view = inflater.inflate(R.layout.bottom_star_analysis, parent, false);
                return new FootHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BodyHolder) {
            BodyHolder bodyHolder = (BodyHolder) holder;
            StarAnalysisBean bean = bodyList.get(position - getHeadCount());
            bodyHolder.itemTitle.setText(bean.getTitle());
            bodyHolder.itemContent.setText(bean.getContent());
            bodyHolder.itemContent.setBackgroundResource(bean.getColor());
        }
        if (holder instanceof FootHolder) {
            FootHolder footHolder = (FootHolder) holder;
            String content = footList.get(position - getHeadCount() - getBodyCount());
            footHolder.footContent.setText(content);
        }
    }

    @Override
    public int getItemCount() {
        return getHeadCount() + getBodyCount() + getFootCount();
    }

    //身体布局Holder
    class BodyHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;

        TextView itemContent;

        public BodyHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_star_tv_title);
            itemContent = (TextView) itemView.findViewById(R.id.item_star_tv_content);
        }
    }

    //脚布局holder
    class FootHolder extends RecyclerView.ViewHolder {

        TextView footContent;

        public FootHolder(@NonNull View itemView) {
            super(itemView);
            footContent = (TextView) itemView.findViewById(R.id.bottom_star_tv);
        }
    }

    private int getHeadCount() {
        return headList.size();
    }

    private int getFootCount() {
        return footList.size();
    }

    private int getBodyCount() {
        return bodyList.size();
    }

}
