package com.Raamsa.raamsa.UI;

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

import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.fragments.AssociateList;
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
import java.util.Map;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class AssociateActivity extends AppCompatActivity {

    String Agent_list = "agent_list";
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    RecyclerView rv_AssociteAndCusto;
    TextView txt_nodata;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_button, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        rv_AssociteAndCusto = (RecyclerView) findViewById(R.id.rv_AssociteAndCusto);
        txt_nodata = (TextView) findViewById(R.id.txt_nodata);
        getAgentList();
    }

    public void getAgentList() {
        final ProgressDialog progressDialog = new ProgressDialog(AssociateActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + Agent_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //   Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //   Toast.makeText(WalletHistory.this, response, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("status")) {
                        if (jsonObject.has("data") && jsonObject.getJSONArray("data").length() > 0) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("first_name", jsonObject1.getString("first_name"));
                                hm.put("last_name", jsonObject1.getString("last_name"));
                                hm.put("message", jsonObject1.getString("message"));
                                hm.put("mobile", jsonObject1.getString("mobile"));
                                hm.put("email", jsonObject1.getString("email"));
                                arrayList.add(hm);
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(AssociateActivity.this, 1);
                            MyAdapter walletHistoryAdapter = new MyAdapter(AssociateActivity.this, arrayList);
                            rv_AssociteAndCusto.setLayoutManager(gridLayoutManager);
                            rv_AssociteAndCusto.setAdapter(walletHistoryAdapter);
                            if (arrayList.size() == 0) {
                                txt_nodata.setVisibility(View.VISIBLE);
                            } else
                                txt_nodata.setVisibility(View.GONE);
                        }
                    } else {
                        rv_AssociteAndCusto.setVisibility(View.GONE);
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
                Toast.makeText(AssociateActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                // Constants.getSavedPreferences(getActivity(),LOGINKEY,"");
                params.put("agent_id", Constants.getSavedPreferences(AssociateActivity.this, LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AssociateActivity.this);
        requestQueue.add(stringRequest);
    }

    public class MyAdapter extends RecyclerView.Adapter<AssociateActivity.MyAdapter.VH> {
        Context context;
        ArrayList<HashMap<String, String>> arrayList;

        public MyAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> arrayList) {
            this.context = activity;
            this.arrayList = arrayList;
        }


        @NonNull
        @Override
        public AssociateActivity.MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.rowfor_associate, viewGroup, false);
            AssociateActivity.MyAdapter.VH viewHolder = new AssociateActivity.MyAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AssociateActivity.MyAdapter.VH vh, int i) {
            vh.tv_sno.setText((i + 1) + ". ");
            vh.tv_name.setText(arrayList.get(i).get("first_name") + " " + (arrayList.get(i).get("last_name")));
            vh.tv_mob_No.setText(arrayList.get(i).get("mobile").toString());
            vh.tv_Email.setText(arrayList.get(i).get("email").toString());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView tv_name, tv_mob_No, tv_Email,tv_sno;

            public VH(@NonNull View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_sno = (TextView) itemView.findViewById(R.id.tv_sno);
                tv_mob_No = (TextView) itemView.findViewById(R.id.tv_mob_No);
                tv_Email = (TextView) itemView.findViewById(R.id.tv_Email);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
