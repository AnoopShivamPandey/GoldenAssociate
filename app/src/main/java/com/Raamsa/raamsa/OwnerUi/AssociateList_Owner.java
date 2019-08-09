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

import com.Raamsa.raamsa.Adapter.AdapterForAssociteList_Owner;
import com.Raamsa.raamsa.Adapter.AdapterForRunningProjects;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.AssociateData;
import com.Raamsa.raamsa.entity.Projects;
import com.Raamsa.raamsa.fragments.AssociateList;
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

public class AssociateList_Owner extends AppCompatActivity {
    RecyclerView rv_Associate_List;
    GridLayoutManager gridLayoutManager;
    int pageNo = 1;
    Toolbar toolbar;
    boolean isLoad, isLast;
    TextView tv_Nodata;
    AdapterForAssociteList_Owner adapterForAssociteList_owner;
    List<AssociateData> associateDataListAdapter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_list__owner);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        gridLayoutManager = new GridLayoutManager(AssociateList_Owner.this, 1);
        rv_Associate_List.setHasFixedSize(true);
        rv_Associate_List.setLayoutManager(gridLayoutManager);
        getAssociates();
        implementPagination(gridLayoutManager);

    }

    private void findViewByIds() {
        toolbar=findViewById(R.id.toolbar);
        rv_Associate_List = findViewById(R.id.rv_Associate_List);
        tv_Nodata = findViewById(R.id.tv_Nodata);
    }

    private void getAssociates() {
        final ProgressDialog progressDialog = new ProgressDialog(AssociateList_Owner.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "owner-data-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getJSONObject("data").getJSONObject("agent").getString("data");
                        if (data != null) {
                            final List<AssociateData> associateDataList = AssociateData.createListFromJson(data);
                            if (associateDataList != null && associateDataList.size() > 0) {
                                isLoad = false;
                                for (int i = 0; i < associateDataList.size(); i++) {
                                    associateDataListAdapter.add(associateDataList.get(i));
                                }
                                if (pageNo == 1) {
                                    rv_Associate_List.setVisibility(View.VISIBLE);
                                    adapterForAssociteList_owner = new AdapterForAssociteList_Owner(AssociateList_Owner.this, associateDataListAdapter);
                                    rv_Associate_List.setAdapter(adapterForAssociteList_owner);
                                } else {
                                    adapterForAssociteList_owner.notifyDataSetChanged();
                                }

                            }
                        }
                    } else {
                        tv_Nodata.setVisibility(View.VISIBLE);
                        rv_Associate_List.setVisibility(View.GONE);
                        Toast.makeText(AssociateList_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_Nodata.setVisibility(View.VISIBLE);
                rv_Associate_List.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(AssociateList_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(AssociateList_Owner.this, LOGINKEY, ""));
                params.put("view_type", "agent");
                params.put("page", String.valueOf(pageNo));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AssociateList_Owner.this);
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        rv_Associate_List.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLast = false;
                isLoad = true;
                pageNo++;
                getAssociates();
            }

            @Override
            public int getTotalPageCount() {
                return associateDataListAdapter.size();
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
