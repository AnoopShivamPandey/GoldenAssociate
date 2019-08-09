package com.Raamsa.raamsa.OwnerUi;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Adapter.AdapterForFlats_Owner;
import com.Raamsa.raamsa.Adapter.AdapterForPlots;
import com.Raamsa.raamsa.Adapter.AdapterForPlots_Owner;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.FlatData;
import com.Raamsa.raamsa.entity.Plots;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class Plots_Owner extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv_Plats;
    TextView tv_Nodata;
    boolean isLoad, isLast;
    List<Plots> plotsListAdapter = new ArrayList<>();
    int page = 1;
    GridLayoutManager gridLayoutManager;

    AdapterForPlots_Owner adapterForPlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots__owner);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        gridLayoutManager = new GridLayoutManager(Plots_Owner.this, 1);
        rv_Plats.setHasFixedSize(true);
        rv_Plats.setLayoutManager(gridLayoutManager);
        forPlots();
        implementPagination(gridLayoutManager);
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        rv_Plats = findViewById(R.id.rv_Plats);
        tv_Nodata = findViewById(R.id.tv_Nodata);
    }

    private void forPlots() {
        final ProgressDialog progressDialog = new ProgressDialog(Plots_Owner.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "owner-plot-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getJSONObject("data").getJSONObject("paginate").getString("data");
                        if (data != null) {
                            final List<Plots> plotsList = Plots.createListFromJson(data);
                            if (plotsList != null && plotsList.size() > 0) {
                                isLoad = false;
                                for (int i = 0; i < plotsList.size(); i++) {
                                    plotsListAdapter.add(plotsList.get(i));
                                }
                                if (page == 1) {
                                    rv_Plats.setVisibility(View.VISIBLE);
                                    adapterForPlots = new AdapterForPlots_Owner(Plots_Owner.this, plotsListAdapter);
                                    rv_Plats.setAdapter(adapterForPlots);
                                } else {
                                    adapterForPlots.notifyDataSetChanged();
                                }

                            }
                        }
                    } else {
                        tv_Nodata.setVisibility(View.VISIBLE);
                        rv_Plats.setVisibility(View.GONE);
                        Toast.makeText(Plots_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_Nodata.setVisibility(View.VISIBLE);
                rv_Plats.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(Plots_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(Plots_Owner.this, LOGINKEY, ""));
                params.put("view_type", "flat");
                params.put("page", String.valueOf(page));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Plots_Owner.this);
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        rv_Plats.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLast = false;
                isLoad = true;
                page++;
                forPlots();
            }

            @Override
            public int getTotalPageCount() {
                return plotsListAdapter.size();
            }

            @Override
            public boolean isLastPage() {
                return isLast;
            }

            @Override
            public boolean isLoading() {
                return isLoad;
            }
        });

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
