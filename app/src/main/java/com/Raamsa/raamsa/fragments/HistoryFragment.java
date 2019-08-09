package com.Raamsa.raamsa.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.Raamsa.raamsa.UI.LoginActivity;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.storage.SharedPrefereceUserData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;


public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }

    Toolbar toolbar;

    CircleImageView profile_image;
    EditText et_Name, et_F_Name, et_Pincode, et_Aadhar, et_address, et_PanNo;
    TextView tv_mob_No, tv_Email, tv_Dob, tv_Gender, tv_State, tv_City;


    Spinner sp_Gender, sp_state, sp_City;

    String[] gender = {"--select--", "Male", "Female"};

    FloatingActionButton fab_edit;
    TextView tv_save;
    String Url = "view-profile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        findViewByIds(view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
      /*  setSu(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        final SharedPreferences sharedPreferences = new SharedPrefereceUserData(getActivity()).getSharedData();
        sp_Gender.setEnabled(false);
        sp_City.setEnabled(false);
        sp_state.setEnabled(false);

        sp_Gender.setAdapter(new ArrayAdapter(getActivity(), R.layout.spinnerlayout, gender));
        view.findViewById(R.id.img_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.clearSavedPreferences(getActivity(), LOGINKEY);
                        final SharedPreferences.Editor sharedPreferences = new SharedPrefereceUserData(getActivity()).getRemove();
                        sharedPreferences.putString("User",null);
                        sharedPreferences.clear();
                        sharedPreferences.commit();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
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
        view.findViewById(R.id.menuImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        String jsonString = sharedPreferences.getString("User", null);
/*
        if (jsonString != null && !jsonString.equalsIgnoreCase("")) {
            User user = User.createobjectFromJson(jsonString);
            if (user != null) {
                if (user.getUsername() != null && !user.getUsername().equalsIgnoreCase("")) {
                    et_Name.setText(user.getName_prefix() + user.getUsername());
                } else {
                    et_Name.setText("N/A");
                }
                if (user.getMobile() != null && !user.getMobile().equalsIgnoreCase("")) {
                    tv_mob_No.setText(user.getMobile());
                } else {
                    tv_mob_No.setText("N/A");
                }
                if (user.getEmail() != null && !user.getEmail().equalsIgnoreCase("")) {
                    tv_Email.setText(user.getEmail());
                } else {
                    tv_Email.setText("N/A");
                }
                if (user.getFather_name() != null && !user.getFather_name().equalsIgnoreCase("")) {
                    et_F_Name.setText(user.getFather_name());
                } else {
                    et_F_Name.setText("N/A");
                }
                if (user.getAddress() != null && !user.getAddress().equalsIgnoreCase("")) {
                    et_address.setText(user.getAddress());
                } else {
                    et_address.setText("N/A");
                }
                if (user.getPincode() != null && !user.getPincode().equalsIgnoreCase("")) {
                    et_Pincode.setText(user.getPincode());
                } else {
                    et_Pincode.setText("N/A");
                }

                if (user.getDateofbirth() != null && !user.getDateofbirth().equalsIgnoreCase("")) {
                    tv_Dob.setText(user.getDateofbirth());
                } else {
                    tv_Dob.setText("N/A");
                }

                if (user.getAadhar_no() != null && !user.getAadhar_no().equalsIgnoreCase("")) {
                    et_Aadhar.setText(user.getAadhar_no());
                } else {
                    tv_Dob.setText("N/A");
                }

                if (user.getGender() != null && !user.getGender().equalsIgnoreCase("")) {
                    tv_Gender.setText(user.getGender());
*/
/*
                    for (int i = 0; i < gender.length; i++) {
                        if (gender[i].equalsIgnoreCase(user.getGender())) {
                            sp_Gender.setSelection(i);
                            break;
                        }
                    }
*//*

                } else {
                    tv_Dob.setText("N/A");
                }
                if (user.getState() != null && !user.getState().equalsIgnoreCase("")) {
                    tv_State.setText(user.getState());
                } else {
                    tv_Dob.setText("N/A");
                }
                if (user.getCity() != null && !user.getCity().equalsIgnoreCase("")) {
                    tv_City.setText(user.getCity());
                } else {
                    tv_Dob.setText("N/A");
                }

            }
        }
*/
        onClickListeners();
        getDetails();
      //  Toast.makeText(getActivity(), Constants.getSavedPreferences(getActivity(),LOGINKEY,""), Toast.LENGTH_SHORT).show();
        return view;
    }

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            et_Name.setText(jsonObject1.getString("name_prefix") + jsonObject1.getString("first_name") + jsonObject1.getString("last_name"));
                            et_F_Name.setText(jsonObject1.getString("father_name"));
                            tv_mob_No.setText(jsonObject1.getString("mobile"));
                            tv_Email.setText(jsonObject1.getString("email"));
                            tv_City.setText(jsonObject1.getString("city"));
                            tv_State.setText(jsonObject1.getString("state_name"));
                            tv_Gender.setText(jsonObject1.getString("gender"));
                            et_Aadhar.setText(jsonObject1.getString("aadhar_no"));
                            tv_Dob.setText(jsonObject1.getString("dateofbirth"));
                            et_Pincode.setText(jsonObject1.getString("pincode"));
                            et_address.setText(jsonObject1.getString("address"));
                            et_PanNo.setText(jsonObject1.getString("pan_no"));
                            Picasso.with(getActivity()).load(jsonObject1.getString("profile_image")).fit().into(profile_image);

                        }
                    }
                    else {
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
              // params.put("agent_id", Constants.getSavedPreferences(getActivity(), LOGINKEY, ""));
                params.put("agent_id", "7");

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void onClickListeners() {
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                tv_save.setVisibility(View.VISIBLE);
                et_Name.setEnabled(true);
                et_Name.setBackgroundDrawable(null);
                et_F_Name.setEnabled(true);
                et_F_Name.setBackgroundDrawable(null);
                et_Pincode.setEnabled(true);
                et_Pincode.setBackgroundDrawable(null);
                et_Aadhar.setEnabled(true);
                et_Aadhar.setBackgroundDrawable(null);
                et_address.setEnabled(true);
                et_address.setBackgroundDrawable(null);
                sp_Gender.setEnabled(true);
                sp_state.setEnabled(true);
                sp_City.setEnabled(true);
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                fab_edit.setVisibility(View.VISIBLE);
            }
        });
    }

    private void findViewByIds(View view) {
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        et_Name = (EditText) view.findViewById(R.id.et_Name);
        et_F_Name = (EditText) view.findViewById(R.id.et_F_Name);
        et_Pincode = (EditText) view.findViewById(R.id.et_Pincode);
        et_Aadhar = (EditText) view.findViewById(R.id.et_Aadhar);
        et_address = (EditText) view.findViewById(R.id.et_address);
        tv_mob_No = (TextView) view.findViewById(R.id.tv_mob_No);
        tv_Email = (TextView) view.findViewById(R.id.tv_Email);
        tv_Dob = (TextView) view.findViewById(R.id.tv_Dob);
        sp_Gender = (Spinner) view.findViewById(R.id.sp_Gender);
        sp_state = (Spinner) view.findViewById(R.id.sp_state);
        sp_City = (Spinner) view.findViewById(R.id.sp_City);
        tv_Gender = (TextView) view.findViewById(R.id.tv_Gender);
        tv_State = (TextView) view.findViewById(R.id.tv_State);
        tv_City = (TextView) view.findViewById(R.id.tv_City);
        et_PanNo = (EditText) view.findViewById(R.id.et_PanNo);
        fab_edit = (FloatingActionButton) view.findViewById(R.id.fab_edit);
        tv_save = (TextView) view.findViewById(R.id.tv_save);
    }

}
