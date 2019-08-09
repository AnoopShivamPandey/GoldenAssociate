package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.OwnerUi.FlatsDetails_Owner;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.entity.FlatData;
import com.Raamsa.raamsa.entity.Row_HousesData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForRowHouses_Owner extends RecyclerView.Adapter<AdapterForRowHouses_Owner.ViewHolder> {
    Context context;
    List<Row_HousesData> row_HouseList;

    public AdapterForRowHouses_Owner(Context context, List<Row_HousesData> row_HouseList) {
        this.context = context;
        this.row_HouseList = row_HouseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_row_house, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (row_HouseList.size() > 0) {
            AnimationHelper.animatate(context, viewHolder.itemView, R.anim.alfa_animation);
            /*if (FlatsList.get(i).getImage() != null && !FlatsList.get(i).getImage().equalsIgnoreCase("")) {
                Picasso.with(context).load(FlatsList.get(i).get()).placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(viewHolder.iv_flats);
            }else {*/
            Picasso.with(context).load("ggg").placeholder(R.drawable.raamsha_mainlogo)
                    .error(R.drawable.raamsha_mainlogo).fit().into(viewHolder.iv_rowHouse);

            if (row_HouseList.get(i).getBlock_name() != null && !row_HouseList.get(i).getBlock_name().equalsIgnoreCase("")) {
                viewHolder.tv_row_HouseName.setText(row_HouseList.get(i).getBlock_name());
            } else {
                viewHolder.tv_row_HouseName.setText("N/A");
            }
            viewHolder.tv_row_HouseLenth.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    Toast.makeText(context, "Work in Progress...", Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(context, FlatsDetails_Owner.class);
                    intent.putExtra("flatData", row_HouseList.get(i));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);*/
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return row_HouseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_rowHouse;
        TextView tv_row_HouseName, tv_row_HouseLenth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_rowHouse = (ImageView) itemView.findViewById(R.id.iv_rowHouse);
            tv_row_HouseName = (TextView) itemView.findViewById(R.id.tv_row_HouseName);
            tv_row_HouseLenth = (TextView) itemView.findViewById(R.id.tv_row_HouseLenth);
        }
    }
}
