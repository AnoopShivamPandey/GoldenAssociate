package com.Raamsa.raamsa.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.Raamsa.raamsa.UI.WalletHistory;
import com.Raamsa.raamsa.Util.Api_Urls;
import com.Raamsa.raamsa.Util.Constants;
import com.Raamsa.raamsa.entity.Wallet;
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
 * Activities that contain this fragment must implement the
 * {@link WalletFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    CircleImageView civ_user;
    TextView tv_userName, tv_walletBal;
    String url = "general-setting";
    String profile = "view-profile";
    String FullName, Img_URL;
    Button btn_associates, btn_customers;
    EditText et_bankName,
    et_accno,
            et_ifsccode,
    et_referalcode;

    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        findViewByIds(view);
        view.findViewById(R.id.img_histroy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WalletHistory.class));
            }
        });
        btn_associates = view.findViewById(R.id.btn_associates);
        btn_customers = view.findViewById(R.id.btn_customers);
        Fragment fragment = new AssociateList();
        replaceFragment(fragment);
        greenAssociate();
        btn_associates.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                greenAssociate();
                Fragment fragment = new AssociateList();
                replaceFragment(fragment);
            }
        });

        btn_customers.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                greencutomer();
                Fragment fragment = new CustomersList();
                replaceFragment(fragment);
            }
        });
        getDetails();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                            Wallet wallet = Wallet.createobjectFromJson(data);
                            if (wallet != null && wallet.getWallet_amount() != null) {
                                tv_walletBal.setText(getActivity().getResources().getString(R.string.sym_rs) + " " + wallet.getWallet_amount());
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("agent_id", Constants.getSavedPreferences(getActivity(), LOGINKEY, ""));
                return params;
                // return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return view;
    }

    private void findViewByIds(View view) {
        civ_user = (CircleImageView) view.findViewById(R.id.civ_user);
        tv_userName = (TextView) view.findViewById(R.id.tv_userName);
        tv_walletBal = (TextView) view.findViewById(R.id.tv_walletBal);
            et_bankName =  view.findViewById(R.id.et_bankName);
            et_accno = view.findViewById(R.id.et_accno);
            et_ifsccode =  view.findViewById(R.id.et_ifsccode);
            et_referalcode = view.findViewById(R.id.et_referalcode);
    }

    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            //    Toast.makeText(getActivity(), jsonObject1.getString("profile_image"), Toast.LENGTH_SHORT).show();
                            tv_userName.setText(jsonObject1.getString("name_prefix") + jsonObject1.getString("first_name") + jsonObject1.getString("last_name"));
                            // Img_URL=jsonObject1.getString("profile_image");
                            Picasso.with(getActivity()).load(jsonObject1.getString("profile_image")).into(civ_user);
                          if(jsonObject1.getString("bank_name").equalsIgnoreCase("")|| jsonObject1.getString("bank_name")==null)
                              et_bankName.setText("N/A");
                              else
                            et_bankName.setText(jsonObject1.getString("bank_name"));

                            if(jsonObject1.getString("account_number").equalsIgnoreCase("")|| jsonObject1.getString("account_number")==null)
                                et_accno.setText("N/A");
                            else
                                et_accno.setText(jsonObject1.getString("account_number"));

                            if(jsonObject1.getString("ifsc_code").equalsIgnoreCase("")|| jsonObject1.getString("ifsc_code")==null)
                                et_ifsccode.setText("N/A");
                            else
                                et_ifsccode.setText(jsonObject1.getString("ifsc_code"));

                            if(jsonObject1.getString("referel_code").equalsIgnoreCase("")|| jsonObject1.getString("referel_code")==null)
                                et_referalcode.setText("N/A");
                            else
                                et_referalcode.setText(jsonObject1.getString("referel_code"));




                            //   Toast.makeText(getActivity(), jsonObject1+"", Toast.LENGTH_SHORT).show();
                        /*    et_F_Name.setText(jsonObject1.getString("father_name"));
                            tv_mob_No.setText(jsonObject1.getString("mobile"));
                            tv_Email.setText(jsonObject1.getString("email"));
                            tv_City.setText(jsonObject1.getString("city"));
                            tv_State.setText(jsonObject1.getString("state_name"));
                            tv_Gender.setText(jsonObject1.getString("gender"));
                            et_Aadhar.setText(jsonObject1.getString("aadhar_no"));
                            tv_Dob.setText(jsonObject1.getString("dateofbirth"));
                            et_Pincode.setText(jsonObject1.getString("pan_no"));*/

                        }
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
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction trasection = getChildFragmentManager().beginTransaction();

        if (!newFragment.isAdded()) {
            try {
                //FragmentTransaction trasection =
                getChildFragmentManager().beginTransaction();
                trasection.replace(R.id.ll_fragment, newFragment);
                trasection.addToBackStack(null);
                trasection.commit();
            } catch (Exception e) {
                // TODO: handle exception
                // AppConstants.printLog(e.getMessage());
            }
        } else {
            trasection.show(newFragment);
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

    void greenAssociate(){
        btn_associates.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_associates.setTextColor(getResources().getColor(R.color.white));
        btn_customers.setBackgroundColor(getResources().getColor(R.color.grey));
        btn_customers.setTextColor(getResources().getColor(R.color.black));
    }
    void greencutomer(){
        btn_customers.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_customers.setTextColor(getResources().getColor(R.color.white));
        btn_associates.setBackgroundColor(getResources().getColor(R.color.grey));
        btn_associates.setTextColor(getResources().getColor(R.color.black));
    }
}
