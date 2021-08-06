package com.example.constellation.starfrg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.constellation.R;

import java.util.ArrayList;
import java.util.List;

public class AddFooterViewAdapter extends RecyclerView.Adapter<AddFooterViewAdapter.ViewHolder> {

    Context context;

    List<String> list = new ArrayList<>();

    public static final int NORMAL_TYPE = 0;

    public static final int FOOT_TYPE = 1;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_star_analysis,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView footContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            footContent = (TextView) itemView.findViewById(R.id.bottom_star_tv);
        }
    }
}
