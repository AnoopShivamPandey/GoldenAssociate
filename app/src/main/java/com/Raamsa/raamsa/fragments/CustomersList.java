package com.Raamsa.raamsa.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomersList extends Fragment {


    public CustomersList() {
        // Required empty public constructor
    }

    String Customer_list = "customer_list";
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    RecyclerView wallet_histroy;
    LinearLayout ll_customerheader;
    TextView txt_nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customers_list, container, false);
        wallet_histroy = view.findViewById(R.id.wallet_histroy);
        ll_customerheader=view.findViewById(R.id.ll_customerheader);
        txt_nodata = view.findViewById(R.id.txt_nodata);
        getCustomerList();
        return view;
    }

    public void getCustomerList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + Customer_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
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
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                            CustomersList.MyAdapter walletHistoryAdapter = new CustomersList.MyAdapter(getActivity(), arrayList);
                            wallet_histroy.setLayoutManager(gridLayoutManager);
                            wallet_histroy.setAdapter(walletHistoryAdapter);
                            ll_customerheader.setVisibility(View.VISIBLE);
                           /* if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);*/
                        }
                    } else {
                        wallet_histroy.setVisibility(View.GONE);
                        txt_nodata.setVisibility(View.VISIBLE);
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
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                //Constants.getSavedPreferences(getActivity(),LOGINKEY,"");
                params.put("agent_id", Constants.getSavedPreferences(getActivity(), LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public class MyAdapter extends RecyclerView.Adapter<CustomersList.MyAdapter.VH> {
        Context context;
        ArrayList<HashMap<String, String>> arrayList;

        public MyAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> arrayList) {
            this.context = activity;
            this.arrayList = arrayList;
        }


        @NonNull
        @Override
        public CustomersList.MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.rowfor_associate, viewGroup, false);
            CustomersList.MyAdapter.VH viewHolder = new CustomersList.MyAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomersList.MyAdapter.VH vh, int i) {
            vh.tv_name.setText(arrayList.get(i).get("name"));
            vh.tv_mob_No.setText(arrayList.get(i).get("mobile").toString());
            vh.tv_Email.setText(arrayList.get(i).get("email").toString());
        }

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
