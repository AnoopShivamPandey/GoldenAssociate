
package com.Raamsa.raamsa.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.OwnerUi.Flats_Owner;
import com.Raamsa.raamsa.OwnerUi.Project_Details_Owner;
import com.Raamsa.raamsa.OwnerUi.RowHouses_Owner;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyDetails extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    int[] sliderImages = {R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img6, R.drawable.img7, R.drawable.img7};
    String Url = "project-list";
    String project_id;
    ImageView img_slider;
    TextView txt_projectName, tv_propertyDetails, txt_location, txt_developmentCharge, txt_totalprice;
    String fromWhere;
    Button btn_avilplot, btn_bookplot;
    LinearLayout ll_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        project_id = getIntent().getStringExtra("propertyId");
        // Toast.makeText(this, project_id, Toast.LENGTH_SHORT).show();
        findViewByIds();
        fromWhere = getIntent().getStringExtra("fromWhere");
        if (fromWhere != null && fromWhere.equalsIgnoreCase("project")) {
            ll_btn.setVisibility(View.GONE);
        } else if (fromWhere != null && fromWhere.equalsIgnoreCase("plot")) {
            ll_btn.setVisibility(View.VISIBLE);
            btn_avilplot.setText("Available Plots");
            btn_bookplot.setText("Selled Plots");
        } else if (fromWhere != null && fromWhere.equalsIgnoreCase("flat")) {
            ll_btn.setVisibility(View.VISIBLE);
            btn_avilplot.setText("Available Flats");
            btn_bookplot.setText("Selled Flats");
        } else if (fromWhere != null && fromWhere.equalsIgnoreCase("row houses")) {
            ll_btn.setVisibility(View.VISIBLE);
            btn_avilplot.setText("Available Row Houses");
            btn_bookplot.setText("Selled Row Houses");
        }
        img_slider = findViewById(R.id.img_slider);
        txt_projectName = findViewById(R.id.txt_projectName);
        tv_propertyDetails = findViewById(R.id.tv_propertyDetails);
        txt_location = findViewById(R.id.txt_location);
        txt_developmentCharge = findViewById(R.id.txt_developmentCharge);
        txt_totalprice = findViewById(R.id.txt_totalprice);
        getDetails();
        findViewById(R.id.menuImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_avilplot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromWhere != null && fromWhere.equalsIgnoreCase("plot")) {
                    startActivity(new Intent(PropertyDetails.this, BlocksActivity.class).putExtra("from_where", "available").putExtra("project_id", project_id));
                } else if (fromWhere != null && fromWhere.equalsIgnoreCase("flat")) {
                    startActivity(new Intent(PropertyDetails.this, Flats_Owner.class).putExtra("from_where", "available").putExtra("project_id", project_id));
                } else if (fromWhere != null && fromWhere.equalsIgnoreCase("row houses")) {
                    startActivity(new Intent(PropertyDetails.this, RowHouses_Owner.class).putExtra("from_where", "available").putExtra("project_id", project_id));
                }
            }
        });
        btn_bookplot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromWhere != null && fromWhere.equalsIgnoreCase("plot")) {
                    startActivity(new Intent(PropertyDetails.this, BlocksActivity.class).putExtra("from_where", "booked").putExtra("project_id", project_id));
                } else if (fromWhere != null && fromWhere.equalsIgnoreCase("flat")) {
                    startActivity(new Intent(PropertyDetails.this, Flats_Owner.class).putExtra("from_where", "booked").putExtra("project_id", project_id));
                } else if (fromWhere != null && fromWhere.equalsIgnoreCase("row houses")) {
                    startActivity(new Intent(PropertyDetails.this, RowHouses_Owner.class).putExtra("from_where", "booked").putExtra("project_id", project_id));
                }
            }
        });
    }

    private void findViewByIds() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        btn_avilplot = findViewById(R.id.btn_avilplot);
        btn_bookplot = findViewById(R.id.btn_bookplot);
        ll_btn = findViewById(R.id.ll_btn);
    }

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(PropertyDetails.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        //  JSONObject jsonObject2 = jsonObject1.getJSONObject("0");
                        // Toast.makeText(PropertyDetails.this, jsonObject1+"", Toast.LENGTH_SHORT).show();
                        if (jsonObject1.getString("image") != null && !jsonObject1.getString("image").equalsIgnoreCase("")) {

                            Picasso.with(PropertyDetails.this).load(jsonObject1.getString("image")).fit().into(img_slider);
                        }
                        txt_projectName.setText(jsonObject1.getString("project_name"));
                        tv_propertyDetails.setText(jsonObject1.getString("description"));
                        txt_location.setText(jsonObject1.getString("location_detail"));
                        txt_totalprice.setText(jsonObject1.getString("price"));
                        txt_developmentCharge.setText(jsonObject1.getString("development_charge"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(PropertyDetails.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PropertyDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("project_id", project_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PropertyDetails.this);
        requestQueue.add(stringRequest);
    }
}
