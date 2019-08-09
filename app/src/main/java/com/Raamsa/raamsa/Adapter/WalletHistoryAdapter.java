package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.WalletHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.VH> {
    Context context;
    List<HashMap<String,String>> arrayList;

    public WalletHistoryAdapter(Context context, List<HashMap<String,String>> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public WalletHistoryAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_history_list, viewGroup, false);
        WalletHistoryAdapter.VH viewHolder = new WalletHistoryAdapter.VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistoryAdapter.VH vh, int i) {
        AnimationHelper.animatate(context,vh.itemView,R.anim.alfa_animation);
        vh.txt_details.setText(Html.fromHtml(arrayList.get(i).get("message")));
        vh.txt_datetime.setText(arrayList.get(i).get("updated_at"));
        //   Toast.makeText(context, arrayList.get(i).get("amount"), Toast.LENGTH_SHORT).show();
        vh.txt_rs.setText(Html.fromHtml(arrayList.get(i).get("show_amount")));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView txt_details, txt_datetime, txt_rs;
        CircleImageView img_icon;
        public VH(@NonNull View itemView) {
            super(itemView);
            txt_details = itemView.findViewById(R.id.txt_details);
            txt_datetime = itemView.findViewById(R.id.txt_datetime);
            txt_rs = itemView.findViewById(R.id.txt_rs);
            img_icon = itemView.findViewById(R.id.img_icon);
        }
    }
}
