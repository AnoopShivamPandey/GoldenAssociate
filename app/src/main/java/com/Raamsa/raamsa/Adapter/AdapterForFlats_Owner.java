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
import com.Raamsa.raamsa.UI.PropertyDetails;
import com.Raamsa.raamsa.entity.FlatData;
import com.Raamsa.raamsa.entity.Projects;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForFlats_Owner extends RecyclerView.Adapter<AdapterForFlats_Owner.ViewHolder> {
    Context context;
    List<FlatData> FlatsList;

    public AdapterForFlats_Owner(Context context, List<FlatData> FlatsList) {
        this.context = context;
        this.FlatsList = FlatsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_flats, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (FlatsList.size() > 0) {
            AnimationHelper.animatate(context, viewHolder.itemView, R.anim.alfa_animation);
            /*if (FlatsList.get(i).getImage() != null && !FlatsList.get(i).getImage().equalsIgnoreCase("")) {
                Picasso.with(context).load(FlatsList.get(i).get()).placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(viewHolder.iv_flats);
            }else {*/
            Picasso.with(context).load("ggg").placeholder(R.drawable.raamsha_mainlogo)
                    .error(R.drawable.raamsha_mainlogo).fit().into(viewHolder.iv_flats);

            if(FlatsList.get(i).getName()!=null&&!FlatsList.get(i).getName().equalsIgnoreCase("")) {
                viewHolder.tv_flatName.setText(FlatsList.get(i).getName());
            }else{
                viewHolder.tv_flatName.setText("N/A");
            }
            viewHolder.tv_flatLength.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FlatsDetails_Owner.class);
                    intent.putExtra("flatData", FlatsList.get(i));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return FlatsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_flats;
        TextView tv_flatName, tv_flatLength;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_flats = (ImageView) itemView.findViewById(R.id.iv_flats);
            tv_flatName = (TextView) itemView.findViewById(R.id.tv_flatName);
            tv_flatLength = (TextView) itemView.findViewById(R.id.tv_flatLength);
        }
    }
}
