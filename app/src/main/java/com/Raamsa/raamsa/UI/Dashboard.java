package com.Raamsa.raamsa.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.fragments.HomeFragment;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mdrawer;
    ActionBarDrawerToggle mtoggle;
    NavigationView nvmain;
    static TextView notifCount;
    static int mNotifCount = 0;
    ActionBar toolbar;
    TextView tv_header;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = getSupportActionBar();
        tv_header=(TextView)findViewById(R.id.tv_header);

        navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(this);
        HomeFragment homeFragment=new HomeFragment();
        loadFragment(homeFragment);
        navigation.getItemIconTintList();
        tv_header.setText("Dashboard");
        navigation.setSelectedItemId(R.id.bnav_Home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigation.setSelectedItemId(R.id.bnav_Home);
    }

/* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.bnav_Home:
                    HomeFragment homeFragment=new HomeFragment();
                    loadFragment(homeFragment);
                    tv_header.setText("Dashboard");
                    break;
                case R.id.bnav_wallet:
                    startActivity(new Intent(Dashboard.this,UserProfile.class));
                  *//*  HistoryFragment historyFragment=new HistoryFragment();
                    loadFragment(historyFragment);
                    tv_header.setText("My Wallet");*//*
                    break;
                case R.id.bnav_History:
                    startActivity(new Intent(Dashboard.this,WalletHistory.class));
                  *//*  WalletFragment walletFragment=new WalletFragment();
                    loadFragment(walletFragment);
                    tv_header.setText("History");*//*
                    break;
            }
            return false;
        }
    };*/

    private void setNotifCount(int count) {
        mNotifCount = count;
        invalidateOptionsMenu();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        //transaction.addToBackStack(null);
        fragmentManager.popBackStack();
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void getdata(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Otp otp = Otp.createobjectFromJson(response.toString());
                    if (jsonObject.getBoolean("status")) {
                        // JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    } else {

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
                Toast.makeText(Dashboard.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
        requestQueue.add(stringRequest);

    }*/

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setMessage("Are you sure want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.bnav_Home:
                HomeFragment homeFragment=new HomeFragment();
                loadFragment(homeFragment);
                tv_header.setText("Dashboard");
                break;
            case R.id.bnav_wallet:
                startActivity(new Intent(Dashboard.this,UserProfile.class));
                 /*  HistoryFragment historyFragment=new HistoryFragment();
                loadFragment(historyFragment);
                tv_header.setText("My Wallet");*/
                break;
            case R.id.bnav_History:
                startActivity(new Intent(Dashboard.this,WalletHistory.class));
                   /* WalletFragment walletFragment=new WalletFragment();
                loadFragment(walletFragment);
                tv_header.setText("History");*/
                break;
        }
        return true;
    }
}
