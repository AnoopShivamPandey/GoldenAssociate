package com.Raamsa.raamsa.OwnerUi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


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
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class Payment_Reciept_Details_Owner extends AppCompatActivity {
    Toolbar toolbar;
    CircleImageView civ_user;
    TextView tv_userName,
            tv_walletBal,
            tv_InviceId,
            tv_PaymentMode,
            tv_ChequeNo,
            tv_BankBranch,
            tv_BankName,
            tv_CheckDate,
            tv_Amount,
            tv_ApproveDate,
            tv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__reciept__details__owner);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        Payment_Reciept_Data payment_reciept_data = (Payment_Reciept_Data) getIntent().getSerializableExtra("PaymentData");
        getDetails();
        tv_walletBal.setVisibility(View.GONE);
        //getWallet();

        if (payment_reciept_data != null) {
            if (payment_reciept_data.getInvice_number() != null && !payment_reciept_data.getInvice_number().equalsIgnoreCase("")) {
                tv_InviceId.setText(payment_reciept_data.getInvice_number());
            } else {
                tv_InviceId.setText("N/A");
            }
            if (payment_reciept_data.getPayment_mode() != null && !payment_reciept_data.getPayment_mode().equalsIgnoreCase("")) {
                tv_PaymentMode.setText(payment_reciept_data.getPayment_mode());
            } else {
                tv_PaymentMode.setText("N/A");
            }
            if (payment_reciept_data.getCheque_number() != null && !payment_reciept_data.getCheque_number().equalsIgnoreCase("")) {
                tv_ChequeNo.setText(payment_reciept_data.getCheque_number());
            } else {
                tv_ChequeNo.setText("N/A");
            }
            if (payment_reciept_data.getCheque_branch_name() != null && !payment_reciept_data.getCheque_branch_name().equalsIgnoreCase("")) {
                tv_BankBranch.setText(payment_reciept_data.getCheque_branch_name());
            } else {
                tv_BankBranch.setText("N/A");
            }
            if (payment_reciept_data.getCheque_bank_name() != null && !payment_reciept_data.getCheque_bank_name().equalsIgnoreCase("")) {
                tv_BankName.setText(payment_reciept_data.getCheque_bank_name());
            } else {
                tv_BankName.setText("N/A");
            }
            if (payment_reciept_data.getCheck_date() != null && !payment_reciept_data.getCheck_date().equalsIgnoreCase("")) {
                tv_CheckDate.setText(payment_reciept_data.getCheck_date());
            } else {
                tv_CheckDate.setText("N/A");
            }
            if (payment_reciept_data.getAmount() != null && !payment_reciept_data.getAmount().equalsIgnoreCase("")) {
                tv_Amount.setText(payment_reciept_data.getAmount());
            } else {
                tv_Amount.setText("N/A");
            }
            if (payment_reciept_data.getApprove_date() != null && !payment_reciept_data.getApprove_date().equalsIgnoreCase("")) {
                tv_ApproveDate.setText(payment_reciept_data.getApprove_date());
            } else {
                tv_ApproveDate.setText("N/A");
            }
            if (payment_reciept_data.getStatus() != null && !payment_reciept_data.getStatus().equalsIgnoreCase("")) {
                tv_status.setText(payment_reciept_data.getStatus());
            } else {
                tv_status.setText("N/A");
            }
        }
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        civ_user = findViewById(R.id.civ_user);
        tv_userName = findViewById(R.id.tv_userName);
        tv_walletBal = findViewById(R.id.tv_walletBal);
        tv_InviceId = findViewById(R.id.tv_InviceId);
        tv_PaymentMode = findViewById(R.id.tv_PaymentMode);
        tv_ChequeNo = findViewById(R.id.tv_ChequeNo);
        tv_BankBranch = findViewById(R.id.tv_BankBranch);
        tv_BankName = findViewById(R.id.tv_BankName);
        tv_CheckDate = findViewById(R.id.tv_CheckDate);
        tv_Amount = findViewById(R.id.tv_Amount);
        tv_ApproveDate = findViewById(R.id.tv_ApproveDate);
        tv_status = findViewById(R.id.tv_status);
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
        final ProgressDialog progressDialog = new ProgressDialog(Payment_Reciept_Details_Owner.this);
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
                            Picasso.with(Payment_Reciept_Details_Owner.this).load(jsonObject1.getString("profile_image")).into(civ_user);

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
                params.put("agent_id", Constants.getSavedPreferences(Payment_Reciept_Details_Owner.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Payment_Reciept_Details_Owner.this);
        requestQueue.add(stringRequest);
    }

    void getWallet() {
        final ProgressDialog progressDialog = new ProgressDialog(Payment_Reciept_Details_Owner.this);
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
                                tv_walletBal.setText(Payment_Reciept_Details_Owner.this.getResources().getString(R.string.sym_rs) + " " + wallet.getWallet_amount());
                            } else {
                                tv_walletBal.setText(Payment_Reciept_Details_Owner.this.getResources().getString(R.string.sym_rs) + " 0");
                            }
                        }
                    } else {
                        Toast.makeText(Payment_Reciept_Details_Owner.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Payment_Reciept_Details_Owner.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(Payment_Reciept_Details_Owner.this, LOGINKEY, ""));
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Payment_Reciept_Details_Owner.this);
        requestQueue.add(stringRequest);
    }
}
