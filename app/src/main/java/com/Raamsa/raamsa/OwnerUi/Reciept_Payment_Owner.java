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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Adapter.Payment_RecieptAdapter;
import com.Raamsa.raamsa.Adapter.WalletHistoryAdapter;
import com.Raamsa.raamsa.Helper.NetworkConnectionHelper;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.Payment_Reciept_Data;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class Reciept_Payment_Owner extends AppCompatActivity {
    Toolbar toolbar;
    CircleImageView civ_user;
    TextView tv_userName,
            tv_walletBal,
            txt_nodata;
    LinearLayout ll_main;
    RecyclerView rv_Receipt;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept__payment__owner);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        gridLayoutManager = new GridLayoutManager(Reciept_Payment_Owner.this, 1);
        rv_Receipt.setHasFixedSize(true);
        rv_Receipt.setLayoutManager(gridLayoutManager);

        if (NetworkConnectionHelper.isOnline(Reciept_Payment_Owner.this)) {
            getDetails();
            getWallet();
            getHistory();
        } else {
            Toast.makeText(this, "Please check your Internet Connection...", Toast.LENGTH_LONG).show();
        }

    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        civ_user = findViewById(R.id.civ_user);
        tv_userName = findViewById(R.id.tv_userName);
        tv_walletBal = findViewById(R.id.tv_walletBal);
        txt_nodata = findViewById(R.id.txt_nodata);
        rv_Receipt = findViewById(R.id.rv_Receipt);
        ll_main = findViewById(R.id.ll_main);
    }

    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(Reciept_Payment_Owner.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        String url = "owner-paymentreceipt-list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //   Toast.makeText(WalletHistory.this, response, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getJSONObject("data").getString("purchases_payments");
                        String TotalAmount = jsonObject.getJSONObject("data").getString("total_amount");
                        if (data != null) {
                            List<Payment_Reciept_Data> paymentRecieptDataList = Payment_Reciept_Data.createListFromJson(data);
                            if (paymentRecieptDataList != null && paymentRecieptDataList.size() > 0) {
                                Payment_RecieptAdapter payment_recieptAdapter = new Payment_RecieptAdapter(Reciept_Payment_Owner.this, paymentRecieptDataList);
                                rv_Receipt.setAdapter(payment_recieptAdapter);
                                if (TotalAmount != null &&!TotalAmount.equalsIgnoreCase("")) {
                                    tv_walletBal.setText(Reciept_Payment_Owner.this.getResources().getString(R.string.sym_rs) + " " + TotalAmount);
                                } else {
                                    tv_walletBal.setText(Reciept_Payment_Owner.this.getResources().getString(R.string.sym_rs) + " 0");
                                }
                            }
                        }
                    } else {
                        ll_main.setVisibility(View.GONE);
                        txt_nodata.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    ll_main.setVisibility(View.GONE);
                    txt_nodata.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ll_main.setVisibility(View.GONE);
                txt_nodata.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(Reciept_Payment_Owner.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(getApplicationContext(), LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Reciept_Payment_Owner.this);
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
        final ProgressDialog progressDialog = new ProgressDialog(Reciept_Payment_Owner.this);
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
                            Picasso.with(Reciept_Payment_Owner.this).load(jsonObject1.getString("profile_image")).into(civ_user);

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
                params.put("agent_id", Constants.getSavedPreferences(Reciept_Payment_Owner.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Reciept_Payment_Owner.this);
        requestQueue.add(stringRequest);
    }

    void getWallet() {
        final ProgressDialog progressDialog = new ProgressDialog(Reciept_Payment_Owner.this);
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
                                tv_walletBal.setText(Reciept_Payment_Owner.this.getResources().getString(R.string.sym_rs) + " " + wallet.getWallet_amount());
                            } else {
                                tv_walletBal.setText(Reciept_Payment_Owner.this.getResources().getString(R.string.sym_rs) + " 0");
                            }
                        }
                    } else {
                        Toast.makeText(Reciept_Payment_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Reciept_Payment_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(Reciept_Payment_Owner.this, LOGINKEY, ""));
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Reciept_Payment_Owner.this);
        requestQueue.add(stringRequest);
    }
}
