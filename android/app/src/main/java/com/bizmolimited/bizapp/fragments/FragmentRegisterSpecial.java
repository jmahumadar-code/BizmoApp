package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.LoginActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.RegisterActivity;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;
import com.bizmolimited.bizapp.utils.DialogCustom;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guillermofuentesquijada on 09-10-17.
 */

public class FragmentRegisterSpecial extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = FragmentRegisterSpecial.class.getSimpleName();

    private CallbackFragments request;

    private View root;
    private Button back;
    private EditText name;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private ImageView flag;
    private TextView codeCountry;

    private Pattern patternPhone;
    private Matcher matcher4;

    private View waiting;

    private RequestQueue queue;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private GoogleApiClient mGoogleApiClient;

    private RegisterActivity register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register_special, container, false);

        Country country = Country.getCountryFromSIM(getActivity().getApplicationContext());
        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }

        String regexPhone = country.getRegexPhone();

        patternPhone = Pattern.compile(regexPhone, Pattern.CASE_INSENSITIVE);

        register = (RegisterActivity) getActivity();
        register.getFab().setVisibility(View.VISIBLE);
        register.getFab().setEnabled(false);
        register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
        register.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCode(email.getText().toString());
            }
        });

        queue = Volley.newRequestQueue(getActivity());
        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        name = (EditText) root.findViewById(R.id.nameregspe);
        lastname = (EditText) root.findViewById(R.id.lastnameregspe);
        phone = (EditText) root.findViewById(R.id.numspe);
        email = (EditText) root.findViewById(R.id.emailregspe);
        flag = (ImageView) root.findViewById(R.id.flagcountry);
        codeCountry = (TextView) root.findViewById(R.id.codePhone);

        Drawable flagIMG = getResources().getDrawable(country.getFlag());

        flag.setImageDrawable(flagIMG);
        codeCountry.setText(country.getDialCode());

        name.setText(sharedPref.getString(getString(R.string.name_special),""));
        lastname.setText(sharedPref.getString(getString(R.string.lastname_special),""));
        email.setText(sharedPref.getString(getString(R.string.email_special),""));

        back = (Button) root.findViewById(R.id.backInit);
        waiting = (View) root.findViewById(R.id.wainting1);


        RegisterActivity _this = ((RegisterActivity) getActivity());

        mGoogleApiClient = _this.getmGoogleApiClient();

        back.setTypeface(font);
        back.setText("\uf053");

        back.setOnClickListener(this);

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                matcher4 = patternPhone.matcher(phone.getText().toString());
                boolean n4 = matcher4.matches();
                email.setError(null);
                if(n4){
                    register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                    register.getFab().setEnabled(true);
                }else{
                    register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
                    register.getFab().setEnabled(false);
                }
            }
        });

        try {
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        phone.setOnEditorActionListener(this);

        name.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backInit:
                String type = sharedPref.getString(getString(R.string.type_special),"nada");
                if(type.equals(getString(R.string.googlelabel))){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    Log.d(TAG, "desconectado: " + status.toString());
                                }
                            });
                }else{
                    //Facebook
                    LoginManager.getInstance().logOut();
                }
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
                getActivity().finish();
                break;
        }
    }

    private void disableView(boolean state){
        register.getFab().setEnabled(!state);
        back.setEnabled(!state);
        name.setEnabled(!state);
        lastname.setEnabled(!state);
        phone.setEnabled(!state);
        email.setEnabled(!state);
        if(!state){
            register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));

        }else {
            register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handler = false;
        matcher4 = patternPhone.matcher(phone.getText().toString());
        boolean n4 = matcher4.matches();

        email.setError(null);
        if(i == EditorInfo.IME_ACTION_DONE && n4){
            register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
            register.getFab().setEnabled(true);
            requestCode(email.getText().toString().trim());
            handler = true;
        }else{
            register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
            register.getFab().setEnabled(false);
            handler = true;
        }

        return handler;
    }

    private void requestCode(String email){
        GeneralConstans gc = new GeneralConstans();
        waiting.setVisibility(View.VISIBLE);
        disableView(true);
        String URL_Request = gc.URL_BASE;
        URL_Request += gc.VERIFY_CODE;
        URL_Request += gc.REQUEST_EMAIL;
        try {
            URL_Request += URLEncoder.encode(email, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        disableView(false);
                        waiting.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject resp = new JSONObject(response);
                            if(resp.getBoolean("result")){
                                String code = resp.getString(getString(R.string.verify_code));
                                editor.putString(getString(R.string.verify_code),code);
                                editor.putBoolean(getString(R.string.special_reg),true);
                                editor.putString(getString(R.string.phone_key),phone.getText().toString());
                                editor.commit();
                                request.onChangeFragment(1);
                            }else{
                                String info = resp.getString("info");
                                DialogCustom dialog = new DialogCustom();
                                dialog.setText(info);
                                dialog.setType(true);
                                dialog.show(getFragmentManager(),TAG);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                disableView(false);
                waiting.setVisibility(View.INVISIBLE);
                Log.d(TAG, error.toString());
                String info = "Se ha producido un error de conexión, intente nuevamente";
                DialogCustom dialog = new DialogCustom();
                dialog.setText(info);
                dialog.setType(true);
                dialog.show(getFragmentManager(),TAG);
            }
        }
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.start();
        queue.add(stringRequest);
    }

}