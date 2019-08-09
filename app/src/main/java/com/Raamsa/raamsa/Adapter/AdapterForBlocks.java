package com.Raamsa.raamsa.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.PlotsActivity;
import com.Raamsa.raamsa.entity.Blocks;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterForBlocks extends RecyclerView.Adapter<AdapterForBlocks.ViewHolder> {
    Context context;
    List<Blocks> blocks = new ArrayList<>();
    String from_where;

    public AdapterForBlocks(Context context, List<Blocks> blocks, String from_where) {
        this.context = context;
        this.blocks = blocks;
        this.from_where = from_where;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_blocks, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (blocks.size() > 0) {
            AnimationHelper.animatate(context, viewHolder.itemView, R.anim.alfa_animation);
            final Bitmap image = null;

            if (from_where.equalsIgnoreCase("available")) {
                viewHolder.ll2.setBackgroundColor(context.getColor(R.color.green));
            } else {
                viewHolder.ll2.setBackgroundColor(Color.RED);
            }
            Picasso.with(context).load(blocks.get(i).getBlock_photo()).fit()
                    .placeholder(R.drawable.bg)
                    .error(R.drawable.bg).into(viewHolder.ll1);
            if (blocks.get(i).getBlock_name() != null && !blocks.get(i).getBlock_name().equalsIgnoreCase("N/A")) {
                viewHolder.name.setText(blocks.get(i).getBlock_name() + " " + "(" + blocks.get(i).getName() + ")");
            } else {
                viewHolder.name.setText("N/A");
            }

            if (blocks.get(i).getBlock_type() != null && !blocks.get(i).getBlock_type().equalsIgnoreCase("")) {
                viewHolder.type.setText(blocks.get(i).getBlock_type());
            } else {
                viewHolder.type.setText("N/A");
            }
            if (blocks.get(i).getArea() != null && !blocks.get(i).getArea().equalsIgnoreCase("")) {
                viewHolder.size.setText(blocks.get(i).getArea() + " " + blocks.get(i).getUnit_name());
            } else {
                viewHolder.size.setText("N/A");
            }
            if (blocks.get(i).getFaceing_name() != null && !blocks.get(i).getFaceing_name().equalsIgnoreCase("")) {
                viewHolder.faching.setText(blocks.get(i).getFaceing_name());
            } else {
                viewHolder.faching.setText("N/A");
            }

        }
    }

    @Override
    public int getItemCount() {
        return blocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_blockImg;
        TextView tv_blockName, tv_blocktype, tv_Avalibilable_for, name, type, size, faching;
        ImageView ll1;
        LinearLayout ll2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll1 = (ImageView) itemView.findViewById(R.id.ll1);
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
            size = (TextView) itemView.findViewById(R.id.size);
            faching = (TextView) itemView.findViewById(R.id.faching);
            ll2 = (LinearLayout) itemView.findViewById(R.id.ll2);
           /* iv_blockImg = (ImageView) itemView.findViewById(R.id.iv_blockImg);
            tv_blockName = (TextView) itemView.findViewById(R.id.tv_blockName);
            tv_blocktype = (TextView) itemView.findViewById(R.id.tv_blocktype);
            tv_Avalibilable_for = (TextView) itemView.findViewById(R.id.tv_Avalibilable_for);*/

        }
    }
}
