package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.entity.NotificationData;


import java.util.ArrayList;
import java.util.List;

public class AdapterForNotification extends RecyclerView.Adapter<AdapterForNotification.ViewHolder> {
    Context context;
    List<NotificationData> list = new ArrayList<>();
    GridLayoutManager gridLayoutManager;


    public AdapterForNotification(Context context, List<NotificationData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_notification, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        AnimationHelper.animatate(context, holder.itemView, R.anim.alfa_animation);
        if (list.get(i).getNotification_msg() != null && !list.get(i).getNotification_msg().equalsIgnoreCase("")) {
            holder.tv_msg.setText(list.get(i).getNotification_msg());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_msg);
        }
    }
}
