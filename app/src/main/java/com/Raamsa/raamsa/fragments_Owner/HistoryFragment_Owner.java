package com.Raamsa.raamsa.fragments_Owner;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Adapter.WalletHistoryAdapter;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.WalletHistory;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;


public class HistoryFragment_Owner extends Fragment {
    public HistoryFragment_Owner() {
        // Required empty public constructor
    }

    RecyclerView wallet_histroy;
    TextView txt_nodata;
    List<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_fragment__owner, container, false);
        findViewByIds(view);
        getHistory();
        return view;
    }

    private void findViewByIds(View view) {
        wallet_histroy = view.findViewById(R.id.wallet_histroy);
        txt_nodata = view.findViewById(R.id.txt_nodata);
    }


    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                            WalletHistoryAdapter walletHistoryAdapter = new WalletHistoryAdapter(getActivity(), arrayList);
                            wallet_histroy.setLayoutManager(gridLayoutManager);
                            wallet_histroy.setAdapter(walletHistoryAdapter);
                            wallet_histroy.setVisibility(View.VISIBLE);
                            txt_nodata.setVisibility(View.GONE);
                           /* if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);*/
                        }
                    } else {
                        txt_nodata.setVisibility(View.VISIBLE);
                        wallet_histroy.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    txt_nodata.setVisibility(View.VISIBLE);
                    wallet_histroy.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                txt_nodata.setVisibility(View.VISIBLE);
                wallet_histroy.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(getActivity(), LOGINKEY, ""));
                //   Constants.getSavedPreferences(WalletHistory.this, LOGINKEY, "")
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
