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

import com.Raamsa.raamsa.Adapter.WalletHistoryAdapter;
import com.Raamsa.raamsa.Helper.NetworkConnectionHelper;
import com.Raamsa.raamsa.Helper.PaginationScrollListener;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.WalletHistory;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
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
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class OwnerMonthlyIncomeActivity extends AppCompatActivity {

    String Url = "owner-wallet-per-month";
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    RecyclerView wallet_histroy;
    TextView txt_nodata;
    CircleImageView civ_user;
    TextView tv_userName, tv_walletBal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_monthly_income);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        wallet_histroy = findViewById(R.id.wallet_histroy);
        txt_nodata = findViewById(R.id.txt_nodata);
        civ_user = findViewById(R.id.civ_user);
        tv_userName = findViewById(R.id.tv_userName);
        tv_walletBal = findViewById(R.id.tv_walletBal);
        if (NetworkConnectionHelper.isOnline(OwnerMonthlyIncomeActivity.this)) {
            getDetails();
            //getWallet();
            getHistory();
        } else {
            Toast.makeText(this, "Check Internet Connection...", Toast.LENGTH_SHORT).show();
        }
    }

    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(OwnerMonthlyIncomeActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //   Toast.makeText(WalletHistory.this, response, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("status")) {
                        if (jsonObject.has("data") && jsonObject.getJSONObject("data").getJSONArray("wallet_history").length() > 0) {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("wallet_history");
                            String total = jsonObject.getJSONObject("data").getString("total_balance");
                            if (total != null && !total.equalsIgnoreCase("")) {
                                tv_walletBal.setText(OwnerMonthlyIncomeActivity.this.getResources().getString(R.string.sym_rs) + " " + total);
                            } else {
                                tv_walletBal.setText(OwnerMonthlyIncomeActivity.this.getResources().getString(R.string.sym_rs) + " 0");
                            }
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("message", jsonObject1.getString("message"));
                                hm.put("show_amount", jsonObject1.getString("balance"));
                                hm.put("updated_at", jsonObject1.getString("created_at"));
                                arrayList.add(hm);
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(OwnerMonthlyIncomeActivity.this, 1);
                            WalletHistoryAdapter walletHistoryAdapter = new WalletHistoryAdapter(OwnerMonthlyIncomeActivity.this, arrayList);
                            wallet_histroy.setLayoutManager(gridLayoutManager);
                            wallet_histroy.setAdapter(walletHistoryAdapter);
                           /* if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);*/
                        }
                    } else {
                        txt_nodata.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(OwnerMonthlyIncomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(getApplicationContext(), LOGINKEY, ""));
                //    Constants.getSavedPreferences(getApplicationContext(), LOGINKEY, "")
                //   Constants.getSavedPreferences(WalletHistory.this, LOGINKEY, "")
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OwnerMonthlyIncomeActivity.this);
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

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(OwnerMonthlyIncomeActivity.this);
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
                            Picasso.with(OwnerMonthlyIncomeActivity.this).load(jsonObject1.getString("profile_image")).into(civ_user);

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
                params.put("agent_id", Constants.getSavedPreferences(OwnerMonthlyIncomeActivity.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OwnerMonthlyIncomeActivity.this);
        requestQueue.add(stringRequest);
    }

    void getWallet() {
        final ProgressDialog progressDialog = new ProgressDialog(OwnerMonthlyIncomeActivity.this);
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
                                tv_walletBal.setText(OwnerMonthlyIncomeActivity.this.getResources().getString(R.string.sym_rs) + " " + wallet.getWallet_amount());
                            } else {
                                tv_walletBal.setText(OwnerMonthlyIncomeActivity.this.getResources().getString(R.string.sym_rs) + " 0");
                            }
                        }
                    } else {
                        Toast.makeText(OwnerMonthlyIncomeActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(OwnerMonthlyIncomeActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(OwnerMonthlyIncomeActivity.this, LOGINKEY, ""));
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OwnerMonthlyIncomeActivity.this);
        requestQueue.add(stringRequest);
    }

}
