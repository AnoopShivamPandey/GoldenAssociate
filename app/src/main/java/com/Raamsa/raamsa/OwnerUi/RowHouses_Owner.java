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
import com.Raamsa.raamsa.Adapter.AdapterForRowHouses_Owner;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.entity.FlatData;
import com.Raamsa.raamsa.entity.Row_HousesData;
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

public class RowHouses_Owner extends AppCompatActivity {

    String from_where, project_id;
    Toolbar toolbar;
    RecyclerView rv_RowHouses;
    TextView tv_Nodata;
    boolean isLoad, isLast;
    int page = 1;
    List<Row_HousesData> lst_HousesDataAdapter = new ArrayList<>();
    private AdapterForRowHouses_Owner adapterForFlats_Owner;
    GridLayoutManager gridLayoutManager;
    RadioGroup rg_filter;
    String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_houses__owner);
        findViewByIds();
        Intent intent = getIntent();
        from_where = intent.getStringExtra("from_where");
        project_id = intent.getStringExtra("project_id");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        gridLayoutManager = new GridLayoutManager(RowHouses_Owner.this, 1);
        rv_RowHouses.setHasFixedSize(true);
        rv_RowHouses.setLayoutManager(gridLayoutManager);
        forRunningProjects();
        implementPagination(gridLayoutManager);
        rg_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                filter = ((RadioButton) findViewById(id)).getText().toString();
                page=1;
                lst_HousesDataAdapter.clear();
                forRunningProjects();
            }
        });
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        rv_RowHouses = findViewById(R.id.rv_RowHouses);
        tv_Nodata = findViewById(R.id.tv_Nodata);
        rg_filter = findViewById(R.id.rg_filter);
    }

    private void forRunningProjects() {
        final ProgressDialog progressDialog = new ProgressDialog(RowHouses_Owner.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "owner-rowhouses-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getJSONObject("data").getJSONObject("paginate").getString("data");
                        if (data != null && !data.equalsIgnoreCase("[]")) {
                            final List<Row_HousesData> Row_HouseList = Row_HousesData.createListFromJson(data);
                            if (Row_HouseList != null && Row_HouseList.size() > 0) {
                                isLoad = false;
                                for (int i = 0; i < Row_HouseList.size(); i++) {
                                    lst_HousesDataAdapter.add(Row_HouseList.get(i));
                                }
                                if (page == 1) {
                                    rv_RowHouses.setVisibility(View.VISIBLE);
                                    adapterForFlats_Owner = new AdapterForRowHouses_Owner(RowHouses_Owner.this, lst_HousesDataAdapter);
                                    rv_RowHouses.setAdapter(adapterForFlats_Owner);
                                } else {
                                    adapterForFlats_Owner.notifyDataSetChanged();
                                }
                            }
                        }
                    } else {
                        if (lst_HousesDataAdapter != null && lst_HousesDataAdapter.size() > 0) {
                            adapterForFlats_Owner.notifyDataSetChanged();
                        }
                        tv_Nodata.setVisibility(View.VISIBLE);
                        rv_RowHouses.setVisibility(View.GONE);
                        Toast.makeText(RowHouses_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_Nodata.setVisibility(View.VISIBLE);
                rv_RowHouses.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(RowHouses_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("project_id", project_id);
                params.put("booking_status", from_where);
                params.put("page", String.valueOf(page));
                if (filter != null && !filter.equalsIgnoreCase("")) {
                    params.put("block_type", filter);
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RowHouses_Owner.this);
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        rv_RowHouses.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
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
                return lst_HousesDataAdapter.size();
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
