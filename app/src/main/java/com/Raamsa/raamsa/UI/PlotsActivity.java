package com.Raamsa.raamsa.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Raamsa.raamsa.Adapter.AdapterForPlots;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.entity.Blocks;
import com.Raamsa.raamsa.entity.Plots;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlotsActivity extends AppCompatActivity {
    RecyclerView rv_Plots;
    String block_id;
    String url="plot-list";
    boolean isScrolling=false;
    int currentitem,totalItem,scrolloutItems;
    LinearLayoutManager linearLayoutManager;
    int pagecount=1;
    ProgressBar progressBar;
    List<Plots> plotsList=new ArrayList<>();
    AdapterForPlots adapterForPlots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots);
        findViewByIds();
        linearLayoutManager=new LinearLayoutManager(PlotsActivity.this);
        rv_Plots.setHasFixedSize(true);
        rv_Plots.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        block_id = intent.getStringExtra("block_id");
        if (block_id != null) {
            final ProgressDialog progressDialog = new ProgressDialog(PlotsActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            String data=(jsonObject.getJSONObject("data")).getString("blocks");
                            if (data != null) {
                                Blocks blocks = Blocks.createobjectFromJson(data);
                                if (blocks != null && blocks.getFlatdetails() != null && blocks.getFlatdetails().size() > 0) {
                                    for(int i=0;i<blocks.getFlatdetails().size();i++){
                                        plotsList.add(blocks.getFlatdetails().get(i));
                                    }
                                    adapterForPlots = new AdapterForPlots(PlotsActivity.this, plotsList);
                                    rv_Plots.setAdapter(adapterForPlots);
                                    pagecount++;
                                    rv_Plots.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                        @Override
                                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                            super.onScrollStateChanged(recyclerView, newState);
                                            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                                                isScrolling=true;
                                            }
                                        }

                                        @Override
                                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                            super.onScrolled(recyclerView, dx, dy);
                                            currentitem=linearLayoutManager.getChildCount();
                                            totalItem=linearLayoutManager.getItemCount();
                                            scrolloutItems=linearLayoutManager.findFirstVisibleItemPosition();
                                            if(isScrolling&&(currentitem+totalItem==scrolloutItems)){
                                                isFetchingData();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(PlotsActivity.this, "Plots not available!!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(PlotsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PlotsActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("block_id", block_id);
                    params.put("page", String.valueOf(pagecount));
                    return params;
                    // return super.getParams();
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(PlotsActivity.this);
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
                        String data=(jsonObject.getJSONObject("data")).getString("blocks");
                        if (data != null) {
                            Blocks blocks = Blocks.createobjectFromJson(data);
                            if (blocks != null && blocks.getFlatdetails() != null && blocks.getFlatdetails().size() > 0) {
                                for(int i=0;i<blocks.getFlatdetails().size();i++){
                                    plotsList.add(blocks.getFlatdetails().get(i));
                                }
                                adapterForPlots.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(PlotsActivity.this, "No Plots available!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(PlotsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PlotsActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("block_id", block_id);
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PlotsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void findViewByIds() {
        rv_Plots=(RecyclerView)findViewById(R.id.rv_Plots);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
    }
}
