package com.Raamsa.raamsa.fragments_Owner;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Raamsa.raamsa.R;
import com.Raamsa.raamsa.UI.UserProfile;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
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
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.Raamsa.raamsa.Util.Constants.LOGINKEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment_Owener extends Fragment {


    public ProfileFragment_Owener() {
        // Required empty public constructor
    }

    CircleImageView profile_image;
    EditText et_Name, et_F_Name, et_Pincode, et_Aadhar, et_address, et_PanNo;
    TextView tv_mob_No, tv_Email, tv_Dob, tv_Gender, tv_State, tv_City;
    Spinner sp_Gender, sp_state, sp_City;
    FloatingActionButton fab_edit;
    TextView tv_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_fragment__owener, container, false);
        findViewByIds(view);
        getDetails();
        return view;
    }

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "view-profile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            et_Name.setText(jsonObject1.getString("name_prefix") + " " + jsonObject1.getString("first_name") + " " + jsonObject1.getString("last_name"));
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
                params.put("agent_id", Constants.getSavedPreferences(getActivity(), LOGINKEY, ""));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
        fab_edit = (FloatingActionButton) view.findViewById(R.id.fab_edit);
        tv_save = (TextView) view.findViewById(R.id.tv_save);
        et_PanNo = (EditText) view.findViewById(R.id.et_PanNo);
    }
}
