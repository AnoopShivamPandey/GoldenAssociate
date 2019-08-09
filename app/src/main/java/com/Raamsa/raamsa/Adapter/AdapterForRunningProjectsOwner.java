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

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.OwnerUi.Project_Details_Owner;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.PropertyDetails;
import com.Raamsa.raamsa.entity.Projects;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForRunningProjectsOwner extends RecyclerView.Adapter<AdapterForRunningProjectsOwner.ViewHolder> {
    Context context;
    List<Projects> projectsList;
    String fromWhere;

    public AdapterForRunningProjectsOwner(Context context, List<Projects> projectsList,String fromWhere) {
        this.context = context;
        this.projectsList = projectsList;
        this.fromWhere=fromWhere;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_running_projects, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (projectsList.size() > 0) {
            AnimationHelper.animatate(context, viewHolder.itemView, R.anim.alfa_animation);
            if (projectsList.get(i).getImage() != null && !projectsList.get(i).getImage().equalsIgnoreCase("")) {
                Picasso.with(context).load(projectsList.get(i).getImage()).placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(viewHolder.iv_property);
            } else {
                Picasso.with(context).load("ggg").placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(viewHolder.iv_property);
            }
            if (projectsList.get(i).getProject_name() != null && !projectsList.get(i).getProject_name().equalsIgnoreCase("")) {
                viewHolder.tv_propertyName.setText(projectsList.get(i).getProject_name());
            } else {
                viewHolder.tv_propertyName.setText("N/A");
            }
            viewHolder.tv_propertyLength.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Intent intent=new Intent(context, BlocksActivity.class);
                    Intent intent = new Intent(context, Project_Details_Owner.class);
                    intent.putExtra("propertyId", projectsList.get(i).getId());
                    intent.putExtra("fromWhere",fromWhere);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_property;
        TextView tv_propertyName, tv_propertyLength;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_property = (ImageView) itemView.findViewById(R.id.iv_property);
            tv_propertyName = (TextView) itemView.findViewById(R.id.tv_propertyName);
            tv_propertyLength = (TextView) itemView.findViewById(R.id.tv_propertyLength);
        }
    }
}
