package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.OwnerUi.Associate_Details_Activity;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.entity.AssociateData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdapterForAssociteList_Owner extends RecyclerView.Adapter<AdapterForAssociteList_Owner.ViewHolder> {
    Context context;
    List<AssociateData> associateDataList = new ArrayList<>();

    public AdapterForAssociteList_Owner(Context context, List<AssociateData> associateDataList) {
        this.context = context;
        this.associateDataList = associateDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_associates, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        AnimationHelper.animatate(context,viewHolder.itemView,R.anim.alfa_animation);
        viewHolder.tv_sno.setText((i + 1) + ". ");
        if(associateDataList.get(i).getFirst_name()!=null&&!associateDataList.get(i).getFirst_name().equalsIgnoreCase("")) {
            viewHolder.tv_AgentName.setText(associateDataList.get(i).getFirst_name() + " " + associateDataList.get(i).getLast_name());
        }else{
            viewHolder.tv_AgentName.setText("N/A");
        }
        if(associateDataList.get(i).getMobile()!=null&&!associateDataList.get(i).getMobile().equalsIgnoreCase("")) {
            viewHolder.tv_MobileNo.setText(associateDataList.get(i).getMobile());
        }
        if(associateDataList.get(i).getBalance()!=null&&!associateDataList.get(i).getBalance().equalsIgnoreCase("")) {
            viewHolder.tv_WalletAmount.setText(context.getResources().getString(R.string.sym_rs) + associateDataList.get(i).getBalance());
        }else{
            viewHolder.tv_WalletAmount.setText("N/A");
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Associate_Details_Activity.class);
                intent.putExtra("associateDetail", (Serializable) associateDataList.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return associateDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sno,
                tv_AgentName,
                tv_MobileNo,
                tv_WalletAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sno = itemView.findViewById(R.id.tv_sno);
            tv_AgentName = itemView.findViewById(R.id.tv_AgentName);
            tv_MobileNo = itemView.findViewById(R.id.tv_MobileNo);
            tv_WalletAmount = itemView.findViewById(R.id.tv_WalletAmount);
        }
    }
}
