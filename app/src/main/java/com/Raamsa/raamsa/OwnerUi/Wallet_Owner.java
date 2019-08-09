package com.Raamsa.raamsa.OwnerUi;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Adapter.AdapterForPaymentProject;
import com.Raamsa.raamsa.Adapter.AdapterForPlots_Owner;
import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.WalletActivity;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.Plots;
import com.Raamsa.raamsa.entity.Projects;
import com.Raamsa.raamsa.entity.Wallet;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class Wallet_Owner extends AppCompatActivity {
    Toolbar toolbar;
    CircleImageView civ_user;
    TextView tv_userName, tv_walletBal;
    LinearLayout ll_customerheader;
    RecyclerView rv_AssociteAndCusto;
    TextView tv_noData;
    List<HashMap<String, String>> arrayList = new ArrayList<>();
    GridLayoutManager gridLayoutManager = new GridLayoutManager(Wallet_Owner.this, 1);
    MyAdapter walletHistoryAdapter;
    int pageNo = 1;
    private boolean isLast, isLoad;
    List<Projects> projectsListAdapter = new ArrayList<>();
    AdapterForPaymentProject adapterForPaymentProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet__owner);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        gridLayoutManager = new GridLayoutManager(Wallet_Owner.this, 1);
        rv_AssociteAndCusto.setHasFixedSize(true);
        rv_AssociteAndCusto.setLayoutManager(gridLayoutManager);
        getDetails();
        getWallet();
        getProjectPayment();
        implementPagination(gridLayoutManager);
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        civ_user = findViewById(R.id.civ_user);
        tv_userName = findViewById(R.id.tv_userName);
        tv_walletBal = findViewById(R.id.tv_walletBal);
        ll_customerheader = findViewById(R.id.ll_customerheader);
        rv_AssociteAndCusto = findViewById(R.id.rv_AssociteAndCusto);
        tv_noData = findViewById(R.id.tv_noData);
    }

    public void getCustomerList() {
        final ProgressDialog progressDialog = new ProgressDialog(Wallet_Owner.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "customer_list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //   Toast.makeText(WalletHistory.this, response, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("status")) {
                        isLoad = false;
                        if (jsonObject.has("data") && jsonObject.getJSONArray("data").length() > 0) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("name", jsonObject1.getString("name"));
                                hm.put("message", jsonObject1.getString("message"));
                                hm.put("mobile", jsonObject1.getString("mobile"));
                                hm.put("email", jsonObject1.getString("email"));
                                arrayList.add(hm);
                            }
                            if (pageNo == 1) {
                                walletHistoryAdapter = new MyAdapter(Wallet_Owner.this, arrayList);
                                rv_AssociteAndCusto.setAdapter(walletHistoryAdapter);
                                rv_AssociteAndCusto.setVisibility(View.VISIBLE);
                                tv_noData.setVisibility(View.GONE);
                            } else {
                                walletHistoryAdapter.notifyDataSetChanged();
                            }

                        }
                    } else {
                        rv_AssociteAndCusto.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.VISIBLE);
                        ll_customerheader.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Wallet_Owner.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                //Constants.getSavedPreferences(getActivity(),LOGINKEY,"");
                params.put("agent_id", Constants.getSavedPreferences(Wallet_Owner.this, LOGINKEY, ""));
                params.put("page", String.valueOf(pageNo));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Wallet_Owner.this);
        requestQueue.add(stringRequest);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
        Context context;
        List<HashMap<String, String>> arrayList;

        public MyAdapter(FragmentActivity activity, List<HashMap<String, String>> arrayList) {
            this.context = activity;
            this.arrayList = arrayList;
        }


        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.rowfor_associate, viewGroup, false);
            VH viewHolder = new VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.VH vh, int i) {
            AnimationHelper.animatate(context, vh.itemView, R.anim.alfa_animation);
            vh.tv_name.setText(arrayList.get(i).get("name"));
            vh.tv_mob_No.setText(arrayList.get(i).get("mobile").toString());
            vh.tv_Email.setText(arrayList.get(i).get("email").toString());
        }

       /* @Override
        public void onBindViewHolder(@NonNull CustomersList.MyAdapter.VH vh, int i) {
            vh.tv_name.setText(arrayList.get(i).get("name"));
            vh.tv_mob_No.setText(arrayList.get(i).get("mobile").toString());
            vh.tv_Email.setText(arrayList.get(i).get("email").toString());
        }*/

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView tv_name, tv_mob_No, tv_Email;

            public VH(@NonNull View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_mob_No = (TextView) itemView.findViewById(R.id.tv_mob_No);
                tv_Email = (TextView) itemView.findViewById(R.id.tv_Email);
            }
        }
    }

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(Wallet_Owner.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "view-profile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            //    Toast.makeText(getActivity(), jsonObject1.getString("profile_image"), Toast.LENGTH_SHORT).show();
                            tv_userName.setText(jsonObject1.getString("name_prefix") + " " + jsonObject1.getString("first_name") + " " + jsonObject1.getString("last_name"));
                            // Img_URL=jsonObject1.getString("profile_image");
                            Picasso.with(Wallet_Owner.this).load(jsonObject1.getString("profile_image")).into(civ_user);

                            //   Toast.makeText(getActivity(), jsonObject1+"", Toast.LENGTH_SHORT).show();
                        /*    et_F_Name.setText(jsonObject1.getString("father_name"));
                            tv_mob_No.setText(jsonObject1.getString("mobile"));
                            tv_Email.setText(jsonObject1.getString("email"));
                            tv_City.setText(jsonObject1.getString("city"));
                            tv_State.setText(jsonObject1.getString("state_name"));
                            tv_Gender.setText(jsonObject1.getString("gender"));
                            et_Aadhar.setText(jsonObject1.getString("aadhar_no"));
                            tv_Dob.setText(jsonObject1.getString("dateofbirth"));
                            et_Pincode.setText(jsonObject1.getString("pan_no"));*/

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    rv_AssociteAndCusto.setVisibility(View.GONE);
                    tv_noData.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                // if (Constants.getSavedPreferences(getActivity(), LOGINKEY, "") != null)
                params.put("agent_id", Constants.getSavedPreferences(Wallet_Owner.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Wallet_Owner.this);
        requestQueue.add(stringRequest);
    }

    void getWallet() {
        final ProgressDialog progressDialog = new ProgressDialog(Wallet_Owner.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "general-setting";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getString("data");
                        if (data != null) {
                            Wallet wallet = Wallet.createobjectFromJson(data);
                            if (wallet != null && wallet.getWallet_amount() != null && !wallet.getWallet_amount().equalsIgnoreCase("")) {
                                tv_walletBal.setText(Wallet_Owner.this.getResources().getString(R.string.sym_rs) + " " + wallet.getWallet_amount());
                            } else {
                                tv_walletBal.setText(Wallet_Owner.this.getResources().getString(R.string.sym_rs) + " 0");
                            }
                        }
                    } else {
                        Toast.makeText(Wallet_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(OtpVerify.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Wallet_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(Wallet_Owner.this, LOGINKEY, ""));
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Wallet_Owner.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        rv_AssociteAndCusto.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLast = false;
                isLoad = true;
                pageNo++;
                // CommonUtill.print("loadMoreItems ==" + pageNo);
                getProjectPayment();
                //rec_prodeuctData.addOnScrollListener(this);
            }

            @Override
            public int getTotalPageCount() {
                return arrayList.size();
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

    public void getProjectPayment() {
        final ProgressDialog progressDialog = new ProgressDialog(Wallet_Owner.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "owner-payment-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getJSONObject("data").getJSONObject("paginate").getString("data");
                        String total_Amount=jsonObject.getJSONObject("data").getString("total_amount");
                        if(total_Amount!=null&&!total_Amount.equalsIgnoreCase("")){
                            tv_walletBal.setText(Wallet_Owner.this.getResources().getString(R.string.sym_rs) + " " +total_Amount);
                        }else{
                            tv_walletBal.setText(Wallet_Owner.this.getResources().getString(R.string.sym_rs) + " 0" );
                        }
                        if (data != null) {
                            final List<Projects> plotsList = Projects.createListFromJson(data);
                            if (plotsList != null && plotsList.size() > 0) {
                                isLoad = false;
                                for (int i = 0; i < plotsList.size(); i++) {
                                    projectsListAdapter.add(plotsList.get(i));
                                }
                                if (pageNo == 1) {
                                    rv_AssociteAndCusto.setVisibility(View.VISIBLE);
                                    adapterForPaymentProject = new AdapterForPaymentProject(Wallet_Owner.this, projectsListAdapter);
                                    rv_AssociteAndCusto.setAdapter(adapterForPaymentProject);
                                } else {
                                    adapterForPaymentProject.notifyDataSetChanged();
                                }
                            }
                        }
                    } else {
                        tv_noData.setVisibility(View.VISIBLE);
                        rv_AssociteAndCusto.setVisibility(View.GONE);
                        Toast.makeText(Wallet_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_noData.setVisibility(View.VISIBLE);
                rv_AssociteAndCusto.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(Wallet_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(Wallet_Owner.this, LOGINKEY, ""));
                params.put("page", String.valueOf(pageNo));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Wallet_Owner.this);
        requestQueue.add(stringRequest);
    }
}
