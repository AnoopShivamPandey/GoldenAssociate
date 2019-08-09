package com.Raamsa.raamsa.UI;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Raamsa.raamsa.Adapter.WalletHistoryAdapter;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.User;
import com.Raamsa.raamsa.storage.SharedPrefereceUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class WalletHistory extends AppCompatActivity {
    String Url = "wallet-history";
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    RecyclerView wallet_histroy;
    TextView txt_nodata;
    User user;
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);
        wallet_histroy = findViewById(R.id.wallet_histroy);
        txt_nodata = findViewById(R.id.txt_nodata);
        findViewById(R.id.menuImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        user = User.createobjectFromJson(new SharedPrefereceUserData(getApplicationContext()).getSharedData().getString("User", null));
        if (user != null) {
            if (user.getId() != null && !user.getId().equalsIgnoreCase("")) {
                Id=user.getId();
            }
        }
        getHistory();
    }

    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(WalletHistory.this);
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
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(WalletHistory.this, 1);
                            WalletHistoryAdapter walletHistoryAdapter = new WalletHistoryAdapter(WalletHistory.this, arrayList);
                            wallet_histroy.setLayoutManager(gridLayoutManager);
                            wallet_histroy.setAdapter(walletHistoryAdapter);
                           /* if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);*/
                        }
                    }
                    else {
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
                Toast.makeText(WalletHistory.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(getApplicationContext(), LOGINKEY, ""));
                //   Constants.getSavedPreferences(WalletHistory.this, LOGINKEY, "")
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(WalletHistory.this);
        requestQueue.add(stringRequest);
    }
}
