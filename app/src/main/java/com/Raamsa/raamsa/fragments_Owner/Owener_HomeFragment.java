package com.Raamsa.raamsa.fragments_Owner;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.Adapter.BannerAdapter;
import com.Raamsa.raamsa.OwnerUi.AssociateList_Owner;
import com.Raamsa.raamsa.OwnerUi.Flats_Owner;
import com.Raamsa.raamsa.OwnerUi.OwnerMonthlyIncomeActivity;
import com.Raamsa.raamsa.OwnerUi.Payment_Reciept_Details_Owner;
import com.Raamsa.raamsa.OwnerUi.Plots_Owner;
import com.Raamsa.raamsa.OwnerUi.ProjectListOwner;
import com.Raamsa.raamsa.OwnerUi.Reciept_Payment_Owner;
import com.Raamsa.raamsa.OwnerUi.ToDayIncome_Owner;
import com.Raamsa.raamsa.OwnerUi.Wallet_Owner;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.UserProfile;
import com.Raamsa.raamsa.UI.WalletActivity;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.Banner;
import com.Raamsa.raamsa.entity.User;
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

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class Owener_HomeFragment extends Fragment {

    public Owener_HomeFragment() {
        // Required empty public constructor
    }

    ViewPager vp_banner;
    LinearLayout ll_MyProject,
            ll_RowHouses,
            ll_Flats,
            ll_Plots,
            ll_TodayIncome,
            ll_MothlyIncome,
            ll_MyWallet,
            ll_Recipt_Payment,
            ll_Associate,
            ll_dayBook,
            ll_KisaanReport,
            ll_Bussiness_Partner;
    TextView tv_Project_count,
            tv_RowHouses_count,
            tv_Flats_count,
            tv_Plots_count,
            tv_TodayIncome_count,
            tv_MonthlyIncome_count,
            tv_Reciept_count,
            tv_Associate_count,
            tv_Daybook_count,
            tv_kissan_report_count,
            tv_BusinessPartners_count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owener__home, container, false);
        findViewByIds(view);

        forBanner();
        getDetails();
        onClickListeners();
        return view;
    }

    private void onClickListeners() {
        ll_MyProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListOwner.class);
                intent.putExtra("fromWhere", "project");
                startActivity(intent);
            }
        });
        ll_RowHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListOwner.class);
                intent.putExtra("fromWhere", "rowhouse");
                startActivity(intent);
            }
        });
        ll_Flats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListOwner.class);
                intent.putExtra("fromWhere", "Flat");
                startActivity(intent);
            }
        });
        ll_Plots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListOwner.class);
                intent.putExtra("fromWhere", "plot");
                startActivity(intent);
            }
        });
        ll_TodayIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ToDayIncome_Owner.class);
                startActivity(intent);
            }
        });
        ll_MothlyIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OwnerMonthlyIncomeActivity.class);
                startActivity(intent);
            }
        });
        ll_MyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Wallet_Owner.class);
                startActivity(intent);
            }
        });
        ll_Recipt_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Reciept_Payment_Owner.class);
                startActivity(intent);
            }
        });
        ll_Associate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AssociateList_Owner.class);
                startActivity(intent);
            }
        });

        ll_dayBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Work in Progress...", Toast.LENGTH_SHORT).show();
            }
        });

        ll_KisaanReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Work in Progress...", Toast.LENGTH_SHORT).show();
            }
        });
        ll_Bussiness_Partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Work in Progress...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViewByIds(View view) {
        vp_banner = view.findViewById(R.id.vp_banner);
        ll_MyProject = view.findViewById(R.id.ll_MyProject);
        ll_RowHouses = view.findViewById(R.id.ll_RowHouses);
        ll_Flats = view.findViewById(R.id.ll_Flats);
        ll_Plots = view.findViewById(R.id.ll_Plots);
        ll_TodayIncome = view.findViewById(R.id.ll_TodayIncome);
        ll_MothlyIncome = view.findViewById(R.id.ll_MothlyIncome);
        ll_MyWallet = view.findViewById(R.id.ll_MyWallet);
        ll_Recipt_Payment = view.findViewById(R.id.ll_Recipt_Payment);
        ll_Associate = view.findViewById(R.id.ll_Associate);

        tv_Project_count = view.findViewById(R.id.tv_Project_count);
        tv_RowHouses_count = view.findViewById(R.id.tv_RowHouses_count);
        tv_Flats_count = view.findViewById(R.id.tv_Flats_count);
        tv_Plots_count = view.findViewById(R.id.tv_Plots_count);
        tv_TodayIncome_count = view.findViewById(R.id.tv_TodayIncome_count);
        tv_MonthlyIncome_count = view.findViewById(R.id.tv_MonthlyIncome_count);
        tv_Reciept_count = view.findViewById(R.id.tv_Reciept_count);
        tv_Associate_count = view.findViewById(R.id.tv_Associate_count);

        ll_dayBook = view.findViewById(R.id.ll_dayBook);
        ll_KisaanReport = view.findViewById(R.id.ll_KisaanReport);
        tv_Daybook_count = view.findViewById(R.id.tv_Daybook_count);
        tv_kissan_report_count = view.findViewById(R.id.tv_kissan_report_count);
        ll_Bussiness_Partner = view.findViewById(R.id.ll_Bussiness_Partner);
        tv_BusinessPartners_count = view.findViewById(R.id.tv_BusinessPartners_count);
    }

    public void forBanner() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.BASE_URL + "banner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getString("data");
                        if (data != null) {
                            final List<Banner> bannerList = Banner.createListFromJson(data);
                            if (bannerList != null && bannerList.size() > 0) {
                                final BannerAdapter viewPagerAdapter = new BannerAdapter(getActivity(), bannerList);
                                vp_banner.setAdapter(viewPagerAdapter);
                                vp_banner.setAdapter(viewPagerAdapter);

                                if (bannerList.size() > 1) {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            handler.postDelayed(this, 5000);
                                            if (vp_banner.getCurrentItem() == bannerList.size() - 1) {
                                                vp_banner.setCurrentItem(0);
                                            } else {
                                                vp_banner.setCurrentItem(vp_banner.getCurrentItem() + 1);
                                            }
                                        }
                                    }, 5000);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//jsonObject.getString("error_code");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String Url = "view-profile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        User user = User.createListFromJson(jsonObject.getString("data")).get(0);
                        if (user != null) {
                            tv_Project_count.setText(user.getProject_count());
                            tv_RowHouses_count.setText(user.getRow_houses_count());
                            tv_Flats_count.setText(user.getFlat_count());
                            tv_Plots_count.setText(user.getPlot_count());
                            tv_TodayIncome_count.setText(user.getToday_income());
                            tv_MonthlyIncome_count.setText(user.getMonthly_income());
                            tv_Reciept_count.setText(user.getPayment_receipt());
                            tv_Associate_count.setText(user.getAgent_count());
                        }
                    } else {
                        Toast.makeText(getActivity(), "SomeThings Went Wrong", Toast.LENGTH_SHORT).show();
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
                params.put("agent_id", Constants.getSavedPreferences(getActivity(), LOGINKEY, ""));
                // params.put("agent_id", "7");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
