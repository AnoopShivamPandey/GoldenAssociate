package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.entity.Projects;

import java.util.ArrayList;
import java.util.List;

public class AdapterForPaymentProject extends RecyclerView.Adapter<AdapterForPaymentProject.VH> {
    Context context;
    List<Projects> projectsList = new ArrayList<>();

    public AdapterForPaymentProject(Context context, List<Projects> projectsList) {
        this.context = context;
        this.projectsList = projectsList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_project_payments, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        AnimationHelper.animatate(context, vh.itemView, R.anim.alfa_animation);
        vh.tv_sno.setText((i + 1) + ". ");
        if (projectsList.get(i).getProject_name() != null && !projectsList.get(i).getProject_name().equalsIgnoreCase("")) {
            vh.tv_projectName.setText(projectsList.get(i).getProject_name());
        } else {
            vh.tv_projectName.setText("N/A");
        }
        if (projectsList.get(i).getAmount() != null && !projectsList.get(i).getAmount().equalsIgnoreCase("")) {
            vh.tv_Amount.setText(context.getResources().getString(R.string.sym_rs)+" "+projectsList.get(i).getAmount());
        } else {
            vh.tv_Amount.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tv_sno, tv_projectName, tv_Amount;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv_sno = itemView.findViewById(R.id.tv_sno);
            tv_projectName = itemView.findViewById(R.id.tv_projectName);
            tv_Amount = itemView.findViewById(R.id.tv_Amount);
        }
    }
}
