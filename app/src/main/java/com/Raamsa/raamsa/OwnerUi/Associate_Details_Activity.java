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
import android.view.View;
import android.widget.TextView;

import com.Raamsa.raamsa.Adapter.WalletHistoryAdapter;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.AssociateData;
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

public class Associate_Details_Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_AgentName,
            tv_mob_No,
            tv_email,
            tv_noData;
    RecyclerView rv_walletHistory;
    CircleImageView civ_Profile;
    AssociateData associateData;
    List<HashMap<String,String>>arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_details);
        findViewByIds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        Intent intent = getIntent();
        associateData = (AssociateData) intent.getSerializableExtra("associateDetail");
        if (associateData != null) {
            if(associateData.getProfile_image()!=null&&!associateData.getProfile_image().equalsIgnoreCase("")) {
                Picasso.with(Associate_Details_Activity.this).load(associateData.getProfile_image()).placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(civ_Profile);
            }else {
                Picasso.with(Associate_Details_Activity.this).load("ddfg").placeholder(R.drawable.raamsha_mainlogo)
                        .error(R.drawable.raamsha_mainlogo).fit().into(civ_Profile);
            }
            tv_AgentName.setText(associateData.getFirst_name() + " " + associateData.getLast_name());
            tv_mob_No.setText(associateData.getMobile());
            tv_email.setText(associateData.getEmail());
            getHistory();
        }
    }

    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(Associate_Details_Activity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        String url = "wallet-history";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //   Toast.makeText(WalletHistory.this, response, Toast.LENGTH_SHORT).show();
                    arrayList.clear();
                    if (jsonObject.getBoolean("status")) {
                        if (jsonObject.has("data") && jsonObject.getJSONArray("data").length() > 0) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("status", jsonObject1.getString("status"));
                                hm.put("message", jsonObject1.getString("message"));
                                hm.put("show_amount", jsonObject1.getString("show_amount"));
                                hm.put("updated_at", jsonObject1.getString("updated_at"));
                                arrayList.add(hm);
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(Associate_Details_Activity.this, 1);
                            WalletHistoryAdapter walletHistoryAdapter = new WalletHistoryAdapter(Associate_Details_Activity.this, arrayList);
                            rv_walletHistory.setLayoutManager(gridLayoutManager);
                            rv_walletHistory.setAdapter(walletHistoryAdapter);
                            rv_walletHistory.setVisibility(View.VISIBLE);
                            tv_noData.setVisibility(View.GONE);
                           /* if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);*/
                        }
                    } else {
                        tv_noData.setVisibility(View.VISIBLE);
                        rv_walletHistory.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tv_noData.setVisibility(View.VISIBLE);
                    rv_walletHistory.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                tv_noData.setVisibility(View.VISIBLE);
                rv_walletHistory.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", associateData.getId());
                //   Constants.getSavedPreferences(WalletHistory.this, LOGINKEY, "")
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Associate_Details_Activity.this);
        requestQueue.add(stringRequest);
    }

    private void findViewByIds() {
        toolbar=findViewById(R.id.toolbar);
        civ_Profile = findViewById(R.id.civ_Profile);
        tv_AgentName = findViewById(R.id.tv_AgentName);
        tv_mob_No = findViewById(R.id.tv_mob_No);
        tv_email = findViewById(R.id.tv_email);
        tv_noData = findViewById(R.id.tv_noData);
        rv_walletHistory = findViewById(R.id.rv_walletHistory);
    }
}
