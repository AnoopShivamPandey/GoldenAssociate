package com.Raamsa.raamsa.OwnerUi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Adapter.AdapterForFlats_Owner;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.FlatData;
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

public class Flats_Owner extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv_Flats;
    GridLayoutManager gridLayoutManager;
    int page = 1;
    TextView tv_Nodata;
    private boolean isLast, isLoad;
    List<FlatData> flatListAdapter = new ArrayList<>();
    AdapterForFlats_Owner adapterForFlats_Owner;
    String from_where, project_id;
    RadioGroup rb_BHK;
    String flat_filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flats__owner);
        findViewByIds();
        Intent intent = getIntent();
        from_where = intent.getStringExtra("from_where");
        project_id = intent.getStringExtra("project_id");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        gridLayoutManager = new GridLayoutManager(Flats_Owner.this, 1);
        rv_Flats.setHasFixedSize(true);
        rv_Flats.setLayoutManager(gridLayoutManager);
        forRunningProjects();
        implementPagination(gridLayoutManager);
        rb_BHK.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                flat_filter = ((RadioButton) findViewById(id)).getText().toString();
                page = 1;
                flatListAdapter.clear();
                adapterForFlats_Owner.notifyDataSetChanged();
                forRunningProjects();
            }
        });

    }


    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        rv_Flats = findViewById(R.id.rv_Flats);
        tv_Nodata = findViewById(R.id.tv_Nodata);
        rb_BHK = findViewById(R.id.rb_BHK);
    }

    private void forRunningProjects() {
        final ProgressDialog progressDialog = new ProgressDialog(Flats_Owner.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "owner-flat-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getJSONObject("data").getJSONObject("paginate").getString("data");
                        if (data != null) {
                            final List<FlatData> flatList = FlatData.createListFromJson(data);
                            if (flatList != null && flatList.size() > 0) {
                                isLoad = false;
                                for (int i = 0; i < flatList.size(); i++) {
                                    flatListAdapter.add(flatList.get(i));
                                }
                                if (page == 1) {
                                    rv_Flats.setVisibility(View.VISIBLE);
                                    adapterForFlats_Owner = new AdapterForFlats_Owner(Flats_Owner.this, flatListAdapter);
                                    rv_Flats.setAdapter(adapterForFlats_Owner);
                                } else {
                                    adapterForFlats_Owner.notifyDataSetChanged();
                                }

                            }
                        }
                    } else {
                        if (flatListAdapter != null && flatListAdapter.size() > 0) {
                            adapterForFlats_Owner.notifyDataSetChanged();
                        } else {
                            tv_Nodata.setVisibility(View.VISIBLE);
                            rv_Flats.setVisibility(View.GONE);
                            Toast.makeText(Flats_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_Nodata.setVisibility(View.VISIBLE);
                rv_Flats.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(Flats_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("project_id", project_id);
                params.put("booking_status", from_where);
                params.put("page", String.valueOf(page));
                if (flat_filter != null && !flat_filter.equalsIgnoreCase("")) {
                    params.put("plot_type", flat_filter);
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Flats_Owner.this);
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        rv_Flats.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLast = false;
                isLoad = true;
                page++;
                // CommonUtill.print("loadMoreItems ==" + pageNo);
                forRunningProjects();
                //rec_prodeuctData.addOnScrollListener(this);
            }

            @Override
            public int getTotalPageCount() {
                return flatListAdapter.size();
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
