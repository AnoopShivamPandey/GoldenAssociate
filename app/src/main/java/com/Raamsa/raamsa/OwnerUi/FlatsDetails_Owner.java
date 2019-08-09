package com.Raamsa.raamsa.OwnerUi;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.entity.FlatData;
import com.Raamsa.raamsa.entity.Plots;
import com.squareup.picasso.Picasso;

public class FlatsDetails_Owner extends AppCompatActivity {
    Toolbar toolbar;
    ImageView iv_imgFlat;
    TextView tv_Flat_Name,
            tv_Amount,
            tv_Flat_Type,
            tv_Flat_Facing,
            tv_Booking_Status,
            tv_floor_no,
            tv_Bedroom,
            tv_bathroom,
            tv_studyroom,
            tv_furnished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flats_details__owner);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        Intent intent = getIntent();
        FlatData flatData = (FlatData) intent.getSerializableExtra("flatData");
        if (flatData != null) {
            /*if (flatData.getImage() != null && !flatData.getImage().equalsIgnoreCase("")) {
                Picasso.with(FlatsDetails_Owner.this).load(flatData.getImage()).placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(iv_imgFlat);
            } else {*/
            Picasso.with(FlatsDetails_Owner.this).load("sfsd").placeholder(R.drawable.raamsha_mainlogo)
                    .error(R.drawable.raamsha_mainlogo).fit().into(iv_imgFlat);


            if (flatData.getName() != null && !flatData.getName().equalsIgnoreCase("")) {
                tv_Flat_Name.setText(flatData.getName());
            } else {
                tv_Flat_Name.setText("N/A");
            }

            if (flatData.getPrice() != null && !flatData.getPrice().equalsIgnoreCase("")) {
                tv_Amount.setText(getResources().getString(R.string.sym_rs) + " " + flatData.getPrice());
            } else {
                tv_Amount.setText("N/A");
            }

            if (flatData.getPlot_type() != null && !flatData.getPlot_type().equalsIgnoreCase("")) {
                tv_Flat_Type.setText(flatData.getPlot_type());
            } else {
                tv_Flat_Type.setText("N/A");
            }

            if (flatData.getIs_faceing() != null && !flatData.getIs_faceing().equalsIgnoreCase("")) {
                tv_Flat_Facing.setText(flatData.getIs_faceing());
            } else {
                tv_Flat_Facing.setText("N/A");
            }

            if (flatData.getBooking_status() != null && !flatData.getBooking_status().equalsIgnoreCase("")) {
                tv_Booking_Status.setText(flatData.getBooking_status());
            } else {
                tv_Booking_Status.setText("N/A");
            }

            if (flatData.getFloor_no() != null && !flatData.getFloor_no().equalsIgnoreCase("")) {
                tv_floor_no.setText(flatData.getFloor_no());
            } else {
                tv_floor_no.setText("N/A");
            }

            if (flatData.getBedroom() != null && !flatData.getBedroom().equalsIgnoreCase("")) {
                tv_Bedroom.setText(flatData.getBedroom());
            } else {
                tv_Bedroom.setText("N/A");
            }

            if (flatData.getBathroom() != null && !flatData.getBathroom().equalsIgnoreCase("")) {
                tv_bathroom.setText(flatData.getBathroom());
            } else {
                tv_bathroom.setText("N/A");
            }

            if (flatData.getStudyroom() != null && !flatData.getStudyroom().equalsIgnoreCase("")) {
                tv_studyroom.setText(flatData.getStudyroom());
            } else {
                tv_studyroom.setText("N/A");
            }

            if (flatData.getFurnished() != null && !flatData.getFurnished().equalsIgnoreCase("")) {
                tv_furnished.setText(flatData.getFurnished());
            } else {
                tv_furnished.setText("N/A");
            }
        }
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        iv_imgFlat = findViewById(R.id.iv_imgFlat);
        tv_Flat_Name = findViewById(R.id.tv_Flat_Name);
        tv_Amount = findViewById(R.id.tv_Amount);
        tv_Flat_Type = findViewById(R.id.tv_Flat_Type);
        tv_Flat_Facing = findViewById(R.id.tv_Flat_Facing);
        tv_Booking_Status = findViewById(R.id.tv_Booking_Status);
        tv_floor_no = findViewById(R.id.tv_floor_no);
        tv_Bedroom = findViewById(R.id.tv_Bedroom);
        tv_bathroom = findViewById(R.id.tv_bathroom);
        tv_studyroom = findViewById(R.id.tv_studyroom);
        tv_furnished = findViewById(R.id.tv_furnished);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
