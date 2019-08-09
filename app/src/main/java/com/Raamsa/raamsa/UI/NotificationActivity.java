package com.Raamsa.raamsa.UI;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Adapter.AdapterForNotification;
import com.Raamsa.raamsa.Helper.NetworkConnectionHelper;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.NotificationData;
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

public class NotificationActivity extends AppCompatActivity {
    RecyclerView rv_UnSeen, rv_Seen;
    TextView tv_noDataUnSeen, tv_noDataSeen;
    List<NotificationData> lst_SeenData = new ArrayList<>();
    List<NotificationData> lst_UnSeenData = new ArrayList<NotificationData>();
    int page = 1;
    private boolean isLast, isLoad;
    GridLayoutManager gridLayoutManager;
    private boolean isScrolling;
    private int currentitem, totalItem, scrolloutItems;
    AdapterForNotification adapterForNotification;
    List<NotificationData> lstNotifydata = new ArrayList<>();
    ImageView goBack;
    NestedScrollView nscv;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findViewByIds();
        lst_SeenData.clear();
        lst_UnSeenData.clear();
        gridLayoutManager = new GridLayoutManager(NotificationActivity.this, 1);
        layoutManager=new GridLayoutManager(NotificationActivity.this, 1);

       // implementPagination(gridLayoutManager);
        rv_UnSeen.setHasFixedSize(true);
        rv_UnSeen.setLayoutManager(gridLayoutManager);
        rv_Seen.setNestedScrollingEnabled(true);
        rv_UnSeen.setNestedScrollingEnabled(true);
        getUnseenData();
        getNotificationData();

/*
        rv_UnSeen.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                currentitem = gridLayoutManager.getChildCount();
                totalItem = gridLayoutManager.getItemCount();
                scrolloutItems = gridLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentitem + totalItem == scrolloutItems)) {
                    if (NetworkConnectionHelper.isOnline(NotificationActivity.this)) {
                        getNotificationData();
                    } else {
                        Toast.makeText(NotificationActivity.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
*/
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nscv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (isLoad) {
                    page++;
                    isLoad = false;
                    getNotificationData();
                    if(scrollX<0){
                        isLoad=false;
                    }
                }
                if (!isLoad) {
                    isLoad = false;
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        //End of list
                        //   page++;
                        //loader = LOADER.BOTTOM;
                        //  hitApiRequest(ApiConstants.REQUEST_TYPE.GET_REFERRAL_LIST);
                        //   getNotificationData();
                        isLoad=true;
                    }
                }
               int topRowVerticalPosition = (rv_Seen == null || rv_Seen.getChildCount() == 0) ? 0 : rv_Seen.getChildAt(0).getTop();
                // swipeRefreshLayout.setEnabled(scrollY <= 0);

            }
        });
    }

    private void findViewByIds() {
        rv_UnSeen = findViewById(R.id.rv_UnSeen);
        rv_Seen = findViewById(R.id.rv_Seen);
        tv_noDataUnSeen = findViewById(R.id.tv_noDataUnSeen);
        tv_noDataSeen = findViewById(R.id.tv_noDataSeen);
        goBack = findViewById(R.id.menuImg);
        nscv = findViewById(R.id.nscv);
    }

    /*  @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();
          if (id == android.R.id.home) {
              onBackPressed();
          }
          return super.onOptionsItemSelected(item);
      }*/
    void getUnseenData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        String url = "notify-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String notifyData = jsonObject.getString("data");
                    if (jsonObject.getBoolean("status")) {
                        if (notifyData != null && !notifyData.equalsIgnoreCase("{}")) {
                            List<NotificationData> notificationDataList = NotificationData.createJsonInList(notifyData);
                            AdapterForNotification adapterForNotification = new AdapterForNotification(NotificationActivity.this, notificationDataList);
                            layoutManager = new GridLayoutManager(NotificationActivity.this, 1);
                            rv_UnSeen.setLayoutManager(layoutManager);
                            rv_UnSeen.setAdapter(adapterForNotification);
                            rv_UnSeen.setHasFixedSize(true);
                            if (notificationDataList.size() == 0) {
                                tv_noDataUnSeen.setVisibility(View.VISIBLE);
                                rv_UnSeen.setVisibility(View.GONE);
                            } else {
                                tv_noDataUnSeen.setVisibility(View.GONE);
                                rv_UnSeen.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tv_noDataUnSeen.setVisibility(View.VISIBLE);
                            rv_UnSeen.setVisibility(View.GONE);
                        }
                    } else {
                        tv_noDataUnSeen.setVisibility(View.VISIBLE);
                        rv_UnSeen.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(NotificationActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(NotificationActivity.this, "Some Thing went wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(NotificationActivity.this, LOGINKEY, ""));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);
        requestQueue.add(stringRequest);
    }

    void getNotificationData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        String url = "seennotification";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //    Toast.makeText(SendOtpActivity.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String notifyData = jsonObject.getJSONObject("data").getJSONObject("seen").getString("data");
                        isLoad = false;
                        if (notifyData != null && !notifyData.equalsIgnoreCase("{}")) {
                            List<NotificationData> listNotification = NotificationData.createJsonInList(notifyData.toString());

                            List<NotificationData> notificationDataList = NotificationData.createJsonInList(notifyData);
                          /*  AdapterForNotification adapterForNotification = new AdapterForNotification(NotificationActivity.this, notificationDataList);
                            layoutManager = new GridLayoutManager(NotificationActivity.this, 1);
                            rv_Seen.setLayoutManager(layoutManager);
                            rv_Seen.setAdapter(adapterForNotification);
                            rv_Seen.setHasFixedSize(true);
                            rv_Seen.setNestedScrollingEnabled(false);*/
                            if (notificationDataList.size() == 0) {
                                isLast = false;
                                tv_noDataSeen.setVisibility(View.VISIBLE);
                                rv_Seen.setVisibility(View.GONE);
                            } else {
                                isLast = true;
                                tv_noDataSeen.setVisibility(View.GONE);
                                rv_Seen.setVisibility(View.VISIBLE);
                            }

                            if (notificationDataList != null && notificationDataList.size() > 0) {
                                for (int i = 0; i < listNotification.size(); i++) {
                                    lstNotifydata.add(notificationDataList.get(i));
                                }

                                if (page > 1) {
                                    adapterForNotification.notifyDataSetChanged();
                                } else {
                                    adapterForNotification = new AdapterForNotification(NotificationActivity.this, lstNotifydata);
                                    rv_Seen.setLayoutManager(layoutManager);
                                    rv_Seen.setAdapter(adapterForNotification);
                                    adapterForNotification.notifyDataSetChanged();
                                    rv_Seen.setHasFixedSize(true);
                                }
                            }
                           /* if (lstNotifydata.size() > 0) {
                                isLast = false;
                                tv_noDataUnSeen.setVisibility(View.GONE);
                                rv_UnSeen.setVisibility(View.VISIBLE);
                                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                isLast = true;
                                tv_noDataUnSeen.setVisibility(View.VISIBLE);
                                rv_UnSeen.setVisibility(View.GONE);
                                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }*/
                        } else {
                            tv_noDataSeen.setVisibility(View.VISIBLE);
                            rv_Seen.setVisibility(View.GONE);
                            //    Toast.makeText(NotificationActivity.this, "Data not found..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        tv_noDataSeen.setVisibility(View.VISIBLE);
                        rv_Seen.setVisibility(View.GONE);
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(NotificationActivity.this, LOGINKEY, ""));
                params.put("page", String.valueOf(page));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        rv_Seen.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLast = false;
                isLoad = true;
                page++;
                // CommonUtill.print("loadMoreItems ==" + pageNo);
                getNotificationData();
                //rec_prodeuctData.addOnScrollListener(this);
            }

            @Override
            public int getTotalPageCount() {
                return lst_UnSeenData.size();
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
}
