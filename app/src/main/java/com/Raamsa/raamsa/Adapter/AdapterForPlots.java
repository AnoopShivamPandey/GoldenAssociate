package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.entity.Plots;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterForPlots extends RecyclerView.Adapter<AdapterForPlots.ViewHolder> {
    Context context;
    List<Plots> Plots = new ArrayList<>();

    public AdapterForPlots(Context context, List<Plots> Plots) {
        this.context = context;
        this.Plots = Plots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_plots, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (Plots.size() > 0) {
            AnimationHelper.animatate(context, viewHolder.itemView, R.anim.alfa_animation);
            if (Plots.get(i).getImage() != null && !Plots.get(i).getImage().equalsIgnoreCase("")) {
                Picasso.with(context).load(Plots.get(i).getImage()).placeholder(R.drawable.raamsha_mainlogo).error(R.drawable.raamsha_mainlogo).into(viewHolder.iv_plotImg);
            } else {
                Picasso.with(context).load("dfsd").placeholder(R.drawable.raamsha_mainlogo).error(R.drawable.raamsha_mainlogo).into(viewHolder.iv_plotImg);
            }
            if (Plots.get(i).getName() != null && !Plots.get(i).getName().equalsIgnoreCase("")) {
                viewHolder.tv_PlotName.setText(Plots.get(i).getName());
            } else {
                viewHolder.tv_PlotName.setText("N/A");
            }
            if (Plots.get(i).getArea() != null && !Plots.get(i).getArea().equalsIgnoreCase("")) {
                viewHolder.tv_AreaUnit.setText(Plots.get(i).getArea() + " " + Plots.get(i).getUnit_name());
            } else {
                viewHolder.tv_AreaUnit.setText("N/A");
            }
            if (Plots.get(i).getFaceing() != null && !Plots.get(i).getFaceing().equalsIgnoreCase("")) {
                viewHolder.tv_Faceing.setText(Plots.get(i).getFaceing());
            } else {
                viewHolder.tv_Faceing.setText("N/A");
            }

            if (Plots.get(i).getBooking_status() != null && !Plots.get(i).getBooking_status().equalsIgnoreCase("")) {
                viewHolder.tv_Booking_Status.setText(Plots.get(i).getBooking_status());
            } else {
                viewHolder.tv_Booking_Status.setText("N/A");
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent=new Intent(context, BlocksActivity.class);
                    intent.putExtra("propertyId",blocks.get(i).getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);*/
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Plots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_plotImg;
        TextView tv_PlotName, tv_AreaUnit, tv_Faceing, tv_Booking_Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_plotImg = (ImageView) itemView.findViewById(R.id.iv_plotImg);
            tv_PlotName = (TextView) itemView.findViewById(R.id.tv_PlotName);
            tv_AreaUnit = (TextView) itemView.findViewById(R.id.tv_AreaUnit);
            tv_Faceing = (TextView) itemView.findViewById(R.id.tv_Faceing);
            tv_Booking_Status = (TextView) itemView.findViewById(R.id.tv_Booking_Status);

        }
    }
}
