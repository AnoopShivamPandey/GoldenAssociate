package com.Raamsa.raamsa.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Raamsa.raamsa.Adapter.AdapterForBlocks;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.entity.Blocks;
import com.Raamsa.raamsa.entity.Projects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlocksActivity extends AppCompatActivity {
    RecyclerView rv_Blocks;
    String propertyId;
    String url = "plot-listing";
    boolean isScrolling = false;
    int currentitem, totalItem, scrolloutItems;
    List<Blocks> blocksList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AdapterForBlocks adapterForBlocks;
    int pagecount = 1;
    ProgressBar progressBar;
    String from_where;
    TextView tv_header, tv_totalcount;
    DrawerLayout drawer;
    TextView tv_clearFilter, tv_ApplyFilter, tv_nodata;
    RadioGroup radiofilter, radiofilter2;
    RadioButton radiocommericail, radioresidental, radiocorner, radiopark, radiocornerpark;
    String f1 = "", f2 = "";
    int listsize = 0;
    ImageView menuImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocks);
        findViewByIds();
        linearLayoutManager = new LinearLayoutManager(BlocksActivity.this);
        rv_Blocks.setHasFixedSize(true);
        rv_Blocks.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        propertyId = intent.getStringExtra("project_id");
        from_where = intent.getStringExtra("from_where");
        if (from_where.equalsIgnoreCase("available")) {
            tv_header.setText("Available Plot");
        } else if (from_where.equalsIgnoreCase("booked")) {
            tv_header.setText("Booked Plot");
        }

        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
/*
        findViewById(R.id.tv_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
*/

/*
        tv_ApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
        radiocommericail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pagecount = 1;
                    radiofilter.setVisibility(View.GONE);
                    radiofilter2.setVisibility(View.VISIBLE);
                    f1 = "commercial";
                    getData();

                }
            }
        });
        radioresidental.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pagecount = 1;
                    radiofilter.setVisibility(View.GONE);
                    radiofilter2.setVisibility(View.VISIBLE);
                    f1 = "residential";
                    getData();
                }
            }
        });
        radiocorner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pagecount = 1;
                    f2 = "1";
                    getData();
                }

            }
        });
        radiopark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pagecount = 1;
                    f2 = "2";
                    getData();
                }
            }
        });
        radiocornerpark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pagecount = 1;
                    f2 = "3";
                    getData();

                }
            }
        });
        getData();
        if (from_where.equalsIgnoreCase("available")) {

        } else {

        }
    }

    public void getData() {
        if (propertyId != null) {
            final ProgressDialog progressDialog = new ProgressDialog(BlocksActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            String data = jsonObject.getJSONObject("data").getString("data");
                            if (data != null) {
                                List<Blocks> blocks = Blocks.createListFromJson(data);
                                //blocksList.add(blocks);
                                adapterForBlocks = new AdapterForBlocks(BlocksActivity.this, blocks, from_where);
                                GridLayoutManager layoutManager = new GridLayoutManager(BlocksActivity.this, 4);
                                rv_Blocks.setLayoutManager(layoutManager);
                                rv_Blocks.setAdapter(adapterForBlocks);
                                pagecount++;
                                listsize = blocks.size();
                                tv_totalcount.setText("No. of plot = " + String.valueOf(listsize));
                                if (blocks.size() == 0) {
                                    tv_totalcount.setText("No. of plot = " + "0");
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    rv_Blocks.setVisibility(View.GONE);
                                } else {
                                    tv_nodata.setVisibility(View.GONE);
                                    rv_Blocks.setVisibility(View.VISIBLE);
                                }
                                rv_Blocks.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                        super.onScrollStateChanged(recyclerView, newState);
                                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                            isScrolling = true;
                                        }
                                    }

                                    @Override
                                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);
                                        currentitem = linearLayoutManager.getChildCount();
                                        totalItem = linearLayoutManager.getItemCount();
                                        scrolloutItems = linearLayoutManager.findFirstVisibleItemPosition();
                                        if (isScrolling && (currentitem + totalItem == scrolloutItems)) {
                                            isFetchingData();
                                        }
                                    }
                                });

/*
                                if (projects != null && projects.getBlocks() != null && projects.getBlocks().size() > 0) {
                                    for (int i = 0; i < projects.getBlocks().size(); i++) {
                                        blocksList.add(projects.getBlocks().get(i));
                                    }
                                    adapterForBlocks = new AdapterForBlocks(BlocksActivity.this, blocksList);
                                    rv_Blocks.setAdapter(adapterForBlocks);
                                    pagecount++;
                                    rv_Blocks.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                        @Override
                                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                            super.onScrollStateChanged(recyclerView, newState);
                                            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                                isScrolling = true;
                                            }
                                        }

                                        @Override
                                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                            super.onScrolled(recyclerView, dx, dy);
                                            currentitem = linearLayoutManager.getChildCount();
                                            totalItem = linearLayoutManager.getItemCount();
                                            scrolloutItems = linearLayoutManager.findFirstVisibleItemPosition();
                                            if (isScrolling && (currentitem + totalItem == scrolloutItems)) {
                                                isFetchingData();
                                            }
                                        }
                                    });
                                }
*/
                            }
                        } else {
                            //   Toast.makeText(BlocksActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            tv_nodata.setVisibility(View.VISIBLE);
                            rv_Blocks.setVisibility(View.GONE);
                            tv_totalcount.setText("No. of plot = " + "0");
//jsonObject.getString("error_code");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(BlocksActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("project_id", propertyId);
                    params.put("page", String.valueOf(pagecount));
                    params.put("booking_status", from_where);
                    // params.put("avalibilable_for", from_where);
                    if (f2 != null && !f2.equalsIgnoreCase("")) {
                        params.put("is_faceing", f2);
                    }
                    if (f1 != null && !f1.equalsIgnoreCase("")) {
                        params.put("block_type", f1);
                    }
                    return params;
                    // return super.getParams();
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(BlocksActivity.this);
            requestQueue.add(stringRequest);
        }

    }

    private void isFetchingData() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getString("data");
                        if (data != null) {
                            List<Blocks> blocks = Blocks.createListFromJson(data);
                            //blocksList.add(blocks);
                            adapterForBlocks = new AdapterForBlocks(BlocksActivity.this, blocks, from_where);
                            GridLayoutManager layoutManager = new GridLayoutManager(BlocksActivity.this, 3);
                            rv_Blocks.setLayoutManager(layoutManager);
                            rv_Blocks.setAdapter(adapterForBlocks);
                            adapterForBlocks.notifyDataSetChanged();

                        /*    Projects projects = Projects.createobjectFromJson(data);
                            if (projects != null && projects.getBlocks() != null && projects.getBlocks().size() > 0) {
                                for (int i = 0; i < projects.getBlocks().size(); i++) {
                                    blocksList.add(projects.getBlocks().get(i));
                                }
                                adapterForBlocks.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(BlocksActivity.this, "No more blocks available!!!", Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    } else {
                        Toast.makeText(BlocksActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//jsonObject.getString("error_code");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BlocksActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("project_id", propertyId);
                params.put("booking_status", from_where);
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BlocksActivity.this);
        requestQueue.add(stringRequest);
    }

    private void findViewByIds() {
        rv_Blocks = (RecyclerView) findViewById(R.id.rv_Blocks);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_header = (TextView) findViewById(R.id.tv_header);
        // drawer = findViewById(R.id.drawer_layout);
        tv_clearFilter = findViewById(R.id.tv_clearFilter);
        tv_ApplyFilter = findViewById(R.id.tv_ApplyFilter);
        radiofilter = findViewById(R.id.radiofilter);
        radiofilter2 = findViewById(R.id.radiofilter2);
        radiocommericail = findViewById(R.id.radiocommericail);
        radioresidental = findViewById(R.id.radioresidental);
        radiocorner = findViewById(R.id.radiocorner);
        radiopark = findViewById(R.id.radiopark);
        radiocornerpark = findViewById(R.id.radiocornerpark);
        tv_nodata = findViewById(R.id.tv_nodata);
        tv_totalcount = findViewById(R.id.tv_totalcount);
        menuImg = findViewById(R.id.menuImg);
    }

    @Override
    public void onBackPressed() {
        if (radiofilter2.getVisibility() == View.VISIBLE) {
            radiofilter2.setVisibility(View.GONE);
            radiofilter.setVisibility(View.VISIBLE);
            radiocorner.setChecked(false);
            radiopark.setChecked(false);
            radiocornerpark.setChecked(false);
            f2 = "";
            pagecount = 1;
            getData();
        } else {
            super.onBackPressed();
        }
    }
}
