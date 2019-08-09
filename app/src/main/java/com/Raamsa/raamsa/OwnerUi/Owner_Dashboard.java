package com.Raamsa.raamsa.OwnerUi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.Raamsa.raamsa.Adapter.SectionsPagerAdapterBottom;
import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.Dashboard;
import com.Raamsa.raamsa.UI.LoginActivity;
import com.Raamsa.raamsa.UI.OtpVerify;
import com.Raamsa.raamsa.UI.UserProfile;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.storage.SharedPrefereceUserData;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

public class Owner_Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ViewPager viewPager;
    SectionsPagerAdapterBottom sectionsPagerAdapter;
    BottomNavigationView bottom_navigation;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__dashboard);
        findViewByIds();
        setSupportActionBar(toolbar);
        //Constants.savePreferences(Owner_Dashboard.this, LOGINKEY, "11");

        sectionsPagerAdapter = new SectionsPagerAdapterBottom(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(1);

        getWindow().setStatusBarColor(Color.parseColor("#B2C968"));

        bottom_navigation.setSelectedItemId(R.id.bnav_O_Home);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
        onViewPagerSelection();
    }

    private void onViewPagerSelection() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                bottom_navigation.setSelectedItemId(R.id.bnav_O_wallet);
                } else if (i==1) {
                    bottom_navigation.setSelectedItemId(R.id.bnav_O_Home);
                } else {
                    bottom_navigation.setSelectedItemId(R.id.bnav_History);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        bottom_navigation = findViewById(R.id.bottom_navigation);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.bnav_O_wallet) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.bnav_O_Home) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.bnav_History) {
            viewPager.setCurrentItem(2);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu_owner, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ow_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Owner_Dashboard.this);
            builder.setMessage("Are you sure want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Constants.clearSavedPreferences(Owner_Dashboard.this, LOGINKEY);
                    final SharedPreferences.Editor sharedPreferences = new SharedPrefereceUserData(Owner_Dashboard.this).getRemove();
                    sharedPreferences.putString("User", null);
                    sharedPreferences.clear();
                    sharedPreferences.commit();
                    startActivity(new Intent(Owner_Dashboard.this, LoginActivity.class));
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Owner_Dashboard.this);
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
        } else {
            viewPager.setCurrentItem(1);
        }
    }
}
