package com.Raamsa.raamsa.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.MainActivity;
import com.Raamsa.raamsa.OwnerUi.AssociateList_Owner;
import com.Raamsa.raamsa.OwnerUi.OwnerMonthlyIncomeActivity;
import com.Raamsa.raamsa.OwnerUi.ProjectListOwner;
import com.Raamsa.raamsa.OwnerUi.Reciept_Payment_Owner;
import com.Raamsa.raamsa.OwnerUi.ToDayIncome_Owner;
import com.Raamsa.raamsa.OwnerUi.Wallet_Owner;
import com.Raamsa.raamsa.UI.AssociateActivity;
import com.Raamsa.raamsa.UI.CustomerActivity;
import com.Raamsa.raamsa.UI.LoginActivity;
import com.Raamsa.raamsa.UI.NotificationActivity;
import com.Raamsa.raamsa.UI.ProjectListAssociate;
import com.Raamsa.raamsa.UI.UserProfile;
import com.Raamsa.raamsa.UI.WalletActivity;
import com.Raamsa.raamsa.Util.CommonUtill;
import com.Raamsa.raamsa.entity.Blocks;
import com.Raamsa.raamsa.entity.Notification;
import com.Raamsa.raamsa.entity.Plots;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Raamsa.raamsa.Adapter.AdapterForRunningProjects;
import com.Raamsa.raamsa.Adapter.BannerAdapter;
import com.Raamsa.raamsa.Adapter.PagerAdapterForCommingSoonProjects;
import com.Raamsa.raamsa.Adapter.PagerAdapterForSlider;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.Banner;
import com.Raamsa.raamsa.entity.Projects;
import com.Raamsa.raamsa.entity.User;
import com.Raamsa.raamsa.storage.SharedPrefereceUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String url = "project-list";


    ViewPager viewPager, vp_CommingProjects;
    LinearLayout sliderDotspanel, SliderDots1;
    private int dotscount, dotscountc;
    private ImageView[] dots, dotsc;
    int sliderCont = 0;
    int[] sliderImages = {R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img6, R.drawable.img7, R.drawable.img7};
    RecyclerView rv_RunningProjects;
    LinearLayout ll_propertyIfOne;
    ImageView iv_property;
    TextView tv_propertyName, tv_propertyLength;
    HorizontalScrollView hs_ifpropertymorethanone;
    ImageView profile, menudot;
    TextView txt_notify, tv_header, tv_count;
    int size;
    User user;
    String Id;
    String general = "general-setting";
    String contactusurl, count;
    Activity activity;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

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
            ll_Bussiness_Partner,
            ll_Customers;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // activity.recreate();
                        pullToRefresh.setRefreshing(false);
                    }
                }, 1000);
                //  refreshData(); // your code

            }
        });
        findViewByIds(view);
        forBanner();
        generalSetting();
        unseencount();
        user = User.createobjectFromJson(new SharedPrefereceUserData(activity).getSharedData().getString("User", null));
        if (user != null) {
            if (user.getId() != null && !user.getId().equalsIgnoreCase("")) {
                Id = user.getId();
            }
        }
        view.findViewById(R.id.iv_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, NotificationActivity.class));
                // showDocsList();
            }
        });
        view.findViewById(R.id.img_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, WalletActivity.class));
               /* Fragment fragment = new WalletFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
            }
        });
        view.findViewById(R.id.iv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Are you sure want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.clearSavedPreferences(activity, LOGINKEY);
                        final SharedPreferences.Editor sharedPreferences = new SharedPrefereceUserData(activity).getRemove();
                        sharedPreferences.putString("User", null);
                        sharedPreferences.clear();
                        sharedPreferences.commit();
                        startActivity(new Intent(activity, LoginActivity.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
        contactUs();
        menudot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(activity, menudot);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.poupup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.share) {
                            CommonUtill.shareApp(activity, "Hey Download the Raamsa Asociate App for become a member of our coumpany", "com.bpd.bestpricedelivery");

                        } else if (id == R.id.contact) {

                            CommonUtill.openBrowser(activity, contactusurl);

                        }
                       /* Toast.makeText(
                                activity,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();*/
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method
        onClickListeners();
        return view;
    }

    private void findViewByIds(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        rv_RunningProjects = (RecyclerView) view.findViewById(R.id.rv_RunningProjects);
        ll_propertyIfOne = (LinearLayout) view.findViewById(R.id.ll_propertyIfOne);
        iv_property = (ImageView) view.findViewById(R.id.iv_property);
        tv_propertyName = (TextView) view.findViewById(R.id.tv_propertyName);
        tv_propertyLength = (TextView) view.findViewById(R.id.tv_propertyLength);
        vp_CommingProjects = (ViewPager) view.findViewById(R.id.vp_CommingProjects);
        SliderDots1 = (LinearLayout) view.findViewById(R.id.SliderDots1);
        txt_notify = view.findViewById(R.id.txt_notify);
        tv_header = view.findViewById(R.id.tv_header);
        menudot = view.findViewById(R.id.menudot);
        tv_count = view.findViewById(R.id.tv_count);

        ll_MyProject = view.findViewById(R.id.ll_MyProject);
        ll_RowHouses = view.findViewById(R.id.ll_RowHouses);
        ll_Flats = view.findViewById(R.id.ll_Flats);
        ll_Plots = view.findViewById(R.id.ll_Plots);
        ll_MyWallet = view.findViewById(R.id.ll_MyWallet);
        ll_Recipt_Payment = view.findViewById(R.id.ll_Recipt_Payment);
        ll_Associate = view.findViewById(R.id.ll_Associate);
        ll_Customers = view.findViewById(R.id.ll_Customers);

        tv_Project_count = view.findViewById(R.id.tv_Project_count);
        tv_RowHouses_count = view.findViewById(R.id.tv_RowHouses_count);
        tv_Flats_count = view.findViewById(R.id.tv_Flats_count);
        tv_Plots_count = view.findViewById(R.id.tv_Plots_count);
        tv_Reciept_count = view.findViewById(R.id.tv_Reciept_count);
        tv_Associate_count = view.findViewById(R.id.tv_Associate_count);

        ll_dayBook = view.findViewById(R.id.ll_dayBook);
        ll_KisaanReport = view.findViewById(R.id.ll_KisaanReport);
        tv_Daybook_count = view.findViewById(R.id.tv_Daybook_count);
        tv_kissan_report_count = view.findViewById(R.id.tv_kissan_report_count);
        ll_Bussiness_Partner = view.findViewById(R.id.ll_Bussiness_Partner);
        tv_BusinessPartners_count = view.findViewById(R.id.tv_BusinessPartners_count);
        //hs_ifpropertymorethanone = (HorizontalScrollView) view.findViewById(R.id.hs_ifpropertymorethanone);
    }

    private void onClickListeners() {
        ll_RowHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListAssociate.class);
                intent.putExtra("fromWhere", "row houses");
                startActivity(intent);
            }
        });
        ll_Flats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListAssociate.class);
                intent.putExtra("fromWhere", "flat");
                startActivity(intent);
            }
        });
        ll_Plots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListAssociate.class);
                intent.putExtra("fromWhere", "plot");
                startActivity(intent);
            }
        });
        ll_MyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });
        ll_Associate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AssociateActivity.class);
                startActivity(intent);
            }
        });

        ll_Customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                startActivity(intent);
            }
        });
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
                            //  tv_Project_count.setText(user.getProject_count());
                            tv_RowHouses_count.setText(user.getRow_houses_count());
                            tv_Flats_count.setText(user.getFlat_count());
                            tv_Plots_count.setText(user.getPlot_count());
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

    private void forRunningProjects() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
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
                            final List<Projects> projectsList = Projects.createListFromJson(data);
                            if (projectsList != null && projectsList.size() > 0) {
                               /* if (projectsList.size() == 1) {
                                    ll_propertyIfOne.setVisibility(View.VISIBLE);
                                    rv_RunningProjects.setVisibility(View.GONE);
                                    Picasso.with(activity).load(projectsList.get(0).getImage()).fit().into(iv_property);
                                    tv_propertyName.setText(projectsList.get(0).getProject_name());
                                    tv_propertyLength.setVisibility(View.GONE);
                                    ll_propertyIfOne.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(activity, PropertyDetails.class);
                                            intent.putExtra("propertyId", projectsList.get(0).getId());
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                } else if (projectsList.size() > 1) {*/
                                ll_propertyIfOne.setVisibility(View.GONE);
                                rv_RunningProjects.setVisibility(View.VISIBLE);
                                AdapterForRunningProjects adapterForRunningProjects = new AdapterForRunningProjects(activity, projectsList);
                                rv_RunningProjects.setHasFixedSize(true);
                                //  LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                //   layoutManager.setReverseLayout(false);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);
                                rv_RunningProjects.setLayoutManager(gridLayoutManager);
                                rv_RunningProjects.setAdapter(adapterForRunningProjects);


                                //  }
                            }
                        }
                    } else {
                        Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(activity, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }


    private void forslider() {
        final PagerAdapterForSlider viewPagerAdapter = new PagerAdapterForSlider(activity, sliderImages);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(activity.getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.active_dot));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 3000);
                if (viewPager.getCurrentItem() == sliderImages.length - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        }, 3000);*/
    }

    public void forComingSoon() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.BASE_URL + "project-commingsoon", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getString("data");
                        if (data != null) {
                            final List<Projects> projectsList = Projects.createListFromJson(data);
                            if (projectsList != null && projectsList.size() > 0) {
                                final PagerAdapterForCommingSoonProjects viewPagerAdapter = new PagerAdapterForCommingSoonProjects(activity, projectsList);
                                vp_CommingProjects.setAdapter(viewPagerAdapter);
                                vp_CommingProjects.setAdapter(viewPagerAdapter);
                                dotscountc = viewPagerAdapter.getCount();
                                dotsc = new ImageView[dotscountc];

                                for (int i = 0; i < dotscountc; i++) {
                                    try {
                                        dotsc[i] = new ImageView(activity);
                                        dotsc[i].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.non_active_dot));

                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(8, 0, 8, 0);
                                        SliderDots1.addView(dotsc[i], params);
                                    } catch (Exception e) {

                                    }
                                }
                                try {
                                    dotsc[0].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.active_dot));
                                } catch (Exception e) {

                                }

                                vp_CommingProjects.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        try {
                                            for (int i = 0; i < dotscountc; i++) {
                                                dotsc[i].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.non_active_dot));
                                            }

                                            dotsc[position].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.active_dot));
                                        } catch (Exception e) {

                                        }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                                if (projectsList.size() > 1) {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            handler.postDelayed(this, 5000);
                                            if (vp_CommingProjects.getCurrentItem() == projectsList.size() - 1) {
                                                vp_CommingProjects.setCurrentItem(0);
                                            } else {
                                                vp_CommingProjects.setCurrentItem(vp_CommingProjects.getCurrentItem() + 1);
                                            }
                                        }
                                    }, 5000);
                                } else {
                                    SliderDots1.setVisibility(View.GONE);
                                }
                            }
                        }
                    }/* else {
                        Toast.makeText(getActivity(), "Some Thing Wrong", Toast.LENGTH_SHORT).show();
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);

    }

    public void forBanner() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
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
                                final BannerAdapter viewPagerAdapter = new BannerAdapter(activity, bannerList);
                                viewPager.setAdapter(viewPagerAdapter);
                                viewPager.setAdapter(viewPagerAdapter);
                                dotscount = viewPagerAdapter.getCount();
                                dots = new ImageView[dotscount];

                                for (int i = 0; i < dotscount; i++) {
                                    try {
                                        dots[i] = new ImageView(activity.getApplicationContext());
                                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_active_dot));

                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(8, 0, 8, 0);
                                        sliderDotspanel.addView(dots[i], params);
                                    } catch (Exception e) {

                                    }
                                }
                                try {
                                    dots[0].setImageDrawable(getResources().getDrawable(R.drawable.active_dot));
                                } catch (Exception e) {

                                }

                                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        try {
                                            for (int i = 0; i < dotscount; i++) {
                                                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_active_dot));
                                            }
                                            dots[position].setImageDrawable(getResources().getDrawable(R.drawable.active_dot));
                                        } catch (Exception e) {

                                        }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                                if (bannerList.size() > 1) {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            handler.postDelayed(this, 5000);
                                            if (viewPager.getCurrentItem() == bannerList.size() - 1) {
                                                viewPager.setCurrentItem(0);
                                            } else {
                                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                            }
                                        }
                                    }, 5000);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(activity, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);

    }

    public void generalSetting() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + general, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        tv_header.setText(jsonObject1.getString("site_title"));
                    }
                } catch (JSONException e) {
                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void contactUs() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.BASE_URL + "contact_us", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        contactusurl = jsonObject1.getString("contact_us");
                    }
                } catch (JSONException e) {
                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void unseencount() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + "unseencount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        count = jsonObject1.getString("notification_count");
                        tv_count.setText(count);
                    }
                } catch (JSONException e) {
                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(activity, LOGINKEY, ""));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void showDocsList() {
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        final List<Notification> notificationList = new ArrayList<>();
        String url = "seennotification";
        final Dialog dialog = new Dialog(activity);
        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_notify_dialog);
        dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //dialog.setCancelable(false);
        dialog.show();
        final RecyclerView rv_notify = dialog.findViewById(R.id.rv_notify);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        ImageView goBack = dialog.findViewById(R.id.menuImg);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {

                        String data = jsonObject.getJSONObject("data").toString();
                        if (data != null) {
                            Notification notification = Notification.createobjectFromJson(data);
                            notificationList.add(notification);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1);
                        MyAdapter myAdapter = new MyAdapter(activity, notificationList);
                        rv_notify.setLayoutManager(gridLayoutManager);
                        rv_notify.setAdapter(myAdapter);

                 /*       JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("unseen");
                        JSONArray jsonArray = jsonObject2.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("message", jsonObject3.getString("notification_msg"));
                            arrayList.add(hm);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1);
                        MyAdapter myAdapter = new MyAdapter(activity, arrayList);
                        rv_notify.setLayoutManager(gridLayoutManager);
                        rv_notify.setAdapter(myAdapter);
                        if (arrayList.size() > 0) {
                            txt_notify.setVisibility(View.VISIBLE);
                            txt_notify.setText(arrayList.size() + "");
                        } else txt_notify.setVisibility(View.GONE);
*/
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //   Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(activity, LOGINKEY, ""));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
        Context context;
        List<Notification> arrayList;

        public MyAdapter(Activity activity, List<Notification> arrayList) {
            this.context = activity;
            this.arrayList = arrayList;
        }


        @NonNull
        @Override
        public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.notify_list, viewGroup, false);
            MyAdapter.VH viewHolder = new MyAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.VH vh, int i) {
            //  vh.txt_notifl_list.setText(Html.fromHtml(arrayList.get(i).get("message")));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView txt_notifl_list;

            public VH(@NonNull View itemView) {
                super(itemView);
                txt_notifl_list = itemView.findViewById(R.id.txt_notifl_list);
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
