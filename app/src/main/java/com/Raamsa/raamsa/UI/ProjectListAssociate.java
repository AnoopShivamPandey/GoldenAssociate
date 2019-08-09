package com.Raamsa.raamsa.UI;

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

import com.Raamsa.raamsa.Adapter.AdapterForRunningProjectsOwner;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.OwnerUi.ProjectListOwner;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.Projects;
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

public class ProjectListAssociate extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv_projects;
    GridLayoutManager gridLayoutManager;
    int page = 1;
    TextView tv_Nodata;
    private boolean isLast, isLoad;
    List<Projects> projectsListAdapter = new ArrayList<>();
    AdapterForRunningProjectsOwner adapterForRunningProjects;
    String fromWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list_associate);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        fromWhere = getIntent().getStringExtra("fromWhere");
        gridLayoutManager = new GridLayoutManager(ProjectListAssociate.this, 1);
        rv_projects.setHasFixedSize(true);
        rv_projects.setLayoutManager(gridLayoutManager);
        forRunningProjects();
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        rv_projects = findViewById(R.id.rv_projects);
        tv_Nodata = findViewById(R.id.tv_Nodata);
    }

    private void forRunningProjects() {
        final ProgressDialog progressDialog = new ProgressDialog(ProjectListAssociate.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "project-type-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getString("data");
                        if (data != null) {
                            final List<Projects> projectsList = Projects.createListFromJson(data);
                            if (projectsList != null && projectsList.size() > 0) {
                                isLoad = false;
                                for (int i = 0; i < projectsList.size(); i++) {
                                    projectsListAdapter.add(projectsList.get(i));
                                }
                                if (page == 1) {
                                    rv_projects.setVisibility(View.VISIBLE);
                                    adapterForRunningProjects = new AdapterForRunningProjectsOwner(ProjectListAssociate.this, projectsListAdapter, fromWhere);
                                    rv_projects.setAdapter(adapterForRunningProjects);
                                } else {
                                    adapterForRunningProjects.notifyDataSetChanged();
                                }

                            }
                        }
                    } else {
                        tv_Nodata.setVisibility(View.VISIBLE);
                        rv_projects.setVisibility(View.GONE);
                        Toast.makeText(ProjectListAssociate.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_Nodata.setVisibility(View.VISIBLE);
                rv_projects.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(ProjectListAssociate.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(ProjectListAssociate.this, LOGINKEY, ""));
                params.put("project_type", fromWhere);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ProjectListAssociate.this);
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        rv_projects.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
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
                return projectsListAdapter.size();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
