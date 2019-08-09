package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.OwnerUi.Payment_Reciept_Details_Owner;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.entity.Payment_Reciept_Data;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Payment_RecieptAdapter extends RecyclerView.Adapter<Payment_RecieptAdapter.VH> {
    Context context;
    List<Payment_Reciept_Data> arrayList;

    public Payment_RecieptAdapter(Context context, List<Payment_Reciept_Data> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_for_payment_reciept, viewGroup, false);
        Payment_RecieptAdapter.VH viewHolder = new Payment_RecieptAdapter.VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {
        AnimationHelper.animatate(context, vh.itemView, R.anim.alfa_animation);
        if (arrayList.get(i).getInvice_number() != null && !arrayList.get(i).getInvice_number().equalsIgnoreCase("")) {
            vh.tv_Invoice_Id.setText(arrayList.get(i).getInvice_number());
        } else {
            vh.tv_Invoice_Id.setText("N/A");
        }
        if (arrayList.get(i).getAmount() != null && !arrayList.get(i).getAmount().equalsIgnoreCase("")) {
            vh.tv_amount.setText(context.getResources().getString(R.string.sym_rs) + " " + arrayList.get(i).getAmount());
        } else {
            vh.tv_amount.setText("N/A");
        }
        if (arrayList.get(i).getInvice_number() != null && !arrayList.get(i).getInvice_number().equalsIgnoreCase("")) {
            vh.tv_status.setText(arrayList.get(i).getStatus());
        } else {
            vh.tv_status.setText("N/A");
        }
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Payment_Reciept_Details_Owner.class);
                intent.putExtra("PaymentData", arrayList.get(i));
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tv_Invoice_Id, tv_amount, tv_status;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv_Invoice_Id = itemView.findViewById(R.id.tv_Invoice_Id);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
