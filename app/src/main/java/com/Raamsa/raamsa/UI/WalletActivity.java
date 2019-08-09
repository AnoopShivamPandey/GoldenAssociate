package com.Raamsa.raamsa.UI;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Helper.AnimationHelper;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.Wallet;
import com.Raamsa.raamsa.fragments.AssociateList;
import com.Raamsa.raamsa.fragments.CustomersList;
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

public class WalletActivity extends AppCompatActivity {
    CircleImageView civ_user;
    TextView tv_userName, tv_walletBal;
    String url = "general-setting";
    String profile = "view-profile";
    String FullName, Img_URL;
    Button btn_associates, btn_customers;
    RecyclerView rv_AssociteAndCusto;
    TextView tv_noData;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    LinearLayout ll_customerheader;
    EditText et_bankName,
            et_accno,
            et_ifsccode,
            et_referalcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet2);
        findViewByIds();
        btn_associates = findViewById(R.id.btn_associates);
        btn_customers = findViewById(R.id.btn_customers);
        Fragment fragment = new AssociateList();
        //   replaceFragment(fragment);
        greenAssociate();

        btn_associates.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                greenAssociate();
                getAssociateList();
               /* Fragment fragment = new AssociateList();
                replaceFragment(fragment);*/
            }
        });

        btn_customers.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                greencutomer();
                getCustomerList();
               /* Fragment fragment = new CustomersList();
                replaceFragment(fragment);*/
            }
        });
        findViewById(R.id.menuImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getDetails();
        final ProgressDialog progressDialog = new ProgressDialog(WalletActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
                                tv_walletBal.setText(WalletActivity.this.getResources().getString(R.string.sym_rs) + " " + wallet.getWallet_amount());
                            } else {
                                tv_walletBal.setText(WalletActivity.this.getResources().getString(R.string.sym_rs) + " 0");
                            }
                        }
                    } else {
                        Toast.makeText(WalletActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(WalletActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(WalletActivity.this, LOGINKEY, ""));
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);
        requestQueue.add(stringRequest);
    }

    private void findViewByIds() {
        civ_user = (CircleImageView) findViewById(R.id.civ_user);
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_walletBal = (TextView) findViewById(R.id.tv_walletBal);
        rv_AssociteAndCusto = (RecyclerView) findViewById(R.id.rv_AssociteAndCusto);
        tv_noData = (TextView) findViewById(R.id.tv_noData);
        ll_customerheader=findViewById(R.id.ll_customerheader);
        et_bankName = findViewById(R.id.et_bankName);
        et_accno = findViewById(R.id.et_accno);
        et_ifsccode =  findViewById(R.id.et_ifsccode);
        et_referalcode = findViewById(R.id.et_referalcode);
    }

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(WalletActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + profile, new Response.Listener<String>() {
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
                            Picasso.with(WalletActivity.this).load(jsonObject1.getString("profile_image")).into(civ_user);
                            if(jsonObject1.getString("bank_name").equalsIgnoreCase("")|| jsonObject1.getString("bank_name")==null)
                                et_bankName.setText("N/A");
                            else
                                et_bankName.setText(jsonObject1.getString("bank_name"));

                            if(jsonObject1.getString("account_number").equalsIgnoreCase("")|| jsonObject1.getString("account_number")==null)
                                et_accno.setText("N/A");
                            else
                                et_accno.setText(jsonObject1.getString("account_number"));

                            if(jsonObject1.getString("ifsc_code").equalsIgnoreCase("")|| jsonObject1.getString("ifsc_code")==null)
                                et_ifsccode.setText("N/A");
                            else
                                et_ifsccode.setText(jsonObject1.getString("ifsc_code"));

                            if(jsonObject1.getString("referel_code").equalsIgnoreCase("")|| jsonObject1.getString("referel_code")==null)
                                et_referalcode.setText("N/A");
                            else
                                et_referalcode.setText(jsonObject1.getString("referel_code"));



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
                params.put("agent_id", Constants.getSavedPreferences(WalletActivity.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);
        requestQueue.add(stringRequest);
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction trasection = getSupportFragmentManager().beginTransaction();

        if (!newFragment.isAdded()) {
            try {
                //FragmentTransaction trasection =
                getSupportFragmentManager().beginTransaction();
                trasection.replace(R.id.ll_fragment, newFragment);
                trasection.addToBackStack(null);
                trasection.commit();
            } catch (Exception e) {
                // TODO: handle exception
                // AppConstants.printLog(e.getMessage());
            }
        } else {
            trasection.show(newFragment);
        }

    }

    void greenAssociate() {
        btn_associates.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_associates.setTextColor(getResources().getColor(R.color.white));
        btn_customers.setBackgroundColor(getResources().getColor(R.color.grey));
        btn_customers.setTextColor(getResources().getColor(R.color.black));
    }

    void greencutomer() {
        btn_customers.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_customers.setTextColor(getResources().getColor(R.color.white));
        btn_associates.setBackgroundColor(getResources().getColor(R.color.grey));
        btn_associates.setTextColor(getResources().getColor(R.color.black));
    }


    public void getCustomerList() {
        final ProgressDialog progressDialog = new ProgressDialog(WalletActivity.this);
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
                    arrayList.clear();
                    //   Toast.makeText(WalletHistory.this, response, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("status")) {
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
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(WalletActivity.this, 1);
                            MyAdapter walletHistoryAdapter = new MyAdapter(WalletActivity.this, arrayList);
                            rv_AssociteAndCusto.setLayoutManager(gridLayoutManager);
                            rv_AssociteAndCusto.setAdapter(walletHistoryAdapter);
                            rv_AssociteAndCusto.setVisibility(View.VISIBLE);
                            tv_noData.setVisibility(View.GONE);
                           /* if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);*/
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
                Toast.makeText(WalletActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                //Constants.getSavedPreferences(getActivity(),LOGINKEY,"");
                params.put("agent_id", Constants.getSavedPreferences(WalletActivity.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);
        requestQueue.add(stringRequest);
    }

    public void getAssociateList() {
        final ProgressDialog progressDialog = new ProgressDialog(WalletActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "agent_list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    arrayList.clear();
                    //   Toast.makeText(WalletHistory.this, response, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("status")) {
                        if (jsonObject.has("data") && jsonObject.getJSONArray("data").length() > 0) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("name", jsonObject1.getString("first_name") + " " + jsonObject1.getString("last_name"));
                                hm.put("message", jsonObject1.getString("message"));
                                hm.put("mobile", jsonObject1.getString("mobile"));
                                hm.put("email", jsonObject1.getString("email"));
                                arrayList.add(hm);
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(WalletActivity.this, 1);
                            MyAdapter walletHistoryAdapter = new MyAdapter(WalletActivity.this, arrayList);
                            rv_AssociteAndCusto.setLayoutManager(gridLayoutManager);
                            rv_AssociteAndCusto.setAdapter(walletHistoryAdapter);
                            rv_AssociteAndCusto.setVisibility(View.VISIBLE);
                            tv_noData.setVisibility(View.GONE);
                           /* if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);*/
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
                Toast.makeText(WalletActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                //Constants.getSavedPreferences(getActivity(),LOGINKEY,"");
                params.put("agent_id", Constants.getSavedPreferences(WalletActivity.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);
        requestQueue.add(stringRequest);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
        Context context;
        ArrayList<HashMap<String, String>> arrayList;

        public MyAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> arrayList) {
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
        public void onBindViewHolder(@NonNull VH vh, int i) {
            AnimationHelper.animatate(context,vh.itemView,R.anim.alfa_animation);
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
}
