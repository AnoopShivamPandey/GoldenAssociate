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

public class PlotDetails_Owner extends AppCompatActivity {
    Toolbar toolbar;
    ImageView iv_imgFlat;
    TextView tv_Plot_Name,
            tv_Project_Name,
            tv_projectType,
            tv_Area,
            tv_Price,
            tv_Development_Charge,
            tv_BlockName,
            tv_BlockType,
            tv_availableFor,
            tv_Booking_Status,
            tv_facing,
            tv_Nearby,
            tv_Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_details__owner);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        Intent intent = getIntent();
        Plots plotData = (Plots) intent.getSerializableExtra("plotData");
        if (plotData != null) {
            if (plotData.getImage() != null && !plotData.getImage().equalsIgnoreCase("")) {
                Picasso.with(PlotDetails_Owner.this).load(plotData.getImage()).placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(iv_imgFlat);
            } else {
                Picasso.with(PlotDetails_Owner.this).load("sfdd").placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(iv_imgFlat);
            }

            if (plotData.getPlot_name() != null && !plotData.getPlot_name().equalsIgnoreCase("")) {
                tv_Plot_Name.setText(plotData.getPlot_name());
            } else {
                tv_Plot_Name.setText("N/A");
            }

            if (plotData.getPlot_name() != null && !plotData.getPlot_name().equalsIgnoreCase("")) {
                tv_Project_Name.setText(plotData.getPlot_name());
            } else {
                tv_Project_Name.setText("N/A");
            }
            if (plotData.getProject_type() != null && !plotData.getProject_type().equalsIgnoreCase("")) {
                tv_projectType.setText(plotData.getProject_type());
            } else {
                tv_projectType.setText("N/A");
            }

            if (plotData.getArea() != null && !plotData.getArea().equalsIgnoreCase("")) {
                tv_Area.setText(plotData.getArea() + " " + plotData.getUnit_name());
            } else {
                tv_Area.setText("N/A");
            }

            if (plotData.getAmount() != null && !plotData.getAmount().equalsIgnoreCase("")) {
                tv_Price.setText(getResources().getString(R.string.sym_rs) + " " + plotData.getAmount());
            } else {
                tv_Price.setText("N/A");
            }

            if (plotData.getDevelopment_charge() != null && !plotData.getDevelopment_charge().equalsIgnoreCase("")) {
                tv_Development_Charge.setText(getResources().getString(R.string.sym_rs) + " " + plotData.getDevelopment_charge());
            } else {
                tv_Development_Charge.setText("N/A");
            }

            if (plotData.getBlock_name() != null && !plotData.getBlock_name().equalsIgnoreCase("")) {
                tv_BlockName.setText(plotData.getBlock_name());
            } else {
                tv_BlockName.setText("N/A");
            }

            if (plotData.getBlock_type() != null && !plotData.getBlock_type().equalsIgnoreCase("")) {
                tv_BlockType.setText(plotData.getBlock_type());
            } else {
                tv_BlockType.setText("N/A");
            }

            if (plotData.getAvalibilable_for() != null && !plotData.getAvalibilable_for().equalsIgnoreCase("")) {
                tv_availableFor.setText(plotData.getAvalibilable_for());
            } else {
                tv_availableFor.setText("N/A");
            }

            if (plotData.getBooking_status() != null && !plotData.getBooking_status().equalsIgnoreCase("")) {
                tv_Booking_Status.setText(plotData.getBooking_status());
            } else {
                tv_Booking_Status.setText("N/A");
            }

            if (plotData.getFace_name() != null && !plotData.getFace_name().equalsIgnoreCase("")) {
                tv_facing.setText(plotData.getFace_name());
            } else {
                tv_facing.setText("N/A");
            }

            if (plotData.getNearest_location() != null && !plotData.getNearest_location().equalsIgnoreCase("")) {
                tv_Nearby.setText(plotData.getNearest_location());
            } else {
                tv_Nearby.setText("N/A");
            }

            if (plotData.getDescription() != null && !plotData.getDescription().equalsIgnoreCase("")) {
                tv_Description.setText(plotData.getDescription());
            } else {
                tv_Description.setText("N/A");
            }
        }
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        iv_imgFlat = findViewById(R.id.iv_imgFlat);
        tv_Plot_Name = findViewById(R.id.tv_Plot_Name);
        tv_Project_Name = findViewById(R.id.tv_Project_Name);
        tv_projectType = findViewById(R.id.tv_projectType);
        tv_Area = findViewById(R.id.tv_Area);
        tv_Price = findViewById(R.id.tv_Price);
        tv_Development_Charge = findViewById(R.id.tv_Development_Charge);
        tv_BlockName = findViewById(R.id.tv_BlockName);
        tv_BlockType = findViewById(R.id.tv_BlockType);
        tv_availableFor = findViewById(R.id.tv_availableFor);
        tv_Booking_Status = findViewById(R.id.tv_Booking_Status);
        tv_facing = findViewById(R.id.tv_facing);
        tv_Nearby = findViewById(R.id.tv_Nearby);
        tv_Description = findViewById(R.id.tv_Description);
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
