package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.LoginActivity;
import com.bizmolimited.bizapp.MapsActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.RegisterActivity;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;
import com.bizmolimited.bizapp.utils.DialogCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bizmolimited.bizapp.R.id.pass;

/**
 * Created by guillermofuentesquijada on 08-10-17.
 */

public class FragmentLoginPass extends Fragment implements View.OnClickListener,TextView.OnEditorActionListener{

    private static final String TAG = FragmentLoginPass.class.getSimpleName();

    private View root;
    private TextView textIntro;
    private EditText textPass;
    private TextInputLayout ltextPass;

    private RequestQueue queue;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private CallbackFragments request;
    private Button btn_back;
    private Button btn_lost;
    private Button btn_create;
    private View waiting;

    private Pattern patternPass;
    private Matcher matcher;

    private LoginActivity login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login_pass, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        login = (LoginActivity) getActivity();
        login.getFab().setEnabled(false);
        login.getFab().setVisibility(View.VISIBLE);
        login.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSession();
            }
        });
        login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));

        String regex = getString(R.string.regexPass);

        patternPass = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        queue = Volley.newRequestQueue(getActivity());

        waiting = (View) root.findViewById(R.id.wainting);

        textPass = (EditText) root.findViewById(R.id.pass);
        ltextPass = (TextInputLayout) root.findViewById(R.id.lpass);
        btn_lost = (Button) root.findViewById(R.id.lost);
        btn_create = (Button) root.findViewById(R.id.newAccount);

        btn_back = (Button) root.findViewById(R.id.backLoginPhone);
        btn_back.setTypeface(font);
        btn_back.setText("\uf053");

        textIntro = (TextView) root.findViewById(R.id.textintro);


        try{
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        textPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ltextPass.setError(null);
                matcher = patternPass.matcher(textPass.getText().toString());
                boolean resp = matcher.matches();
                if(resp){
                    login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                    login.getFab().setEnabled(true);
                }else{
                    ltextPass.setError(getString(R.string.error_pass));
                    login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
                    login.getFab().setEnabled(false);
                }
            }
        });

        btn_back.setOnClickListener(this);
        btn_create.setOnClickListener(this);
        btn_lost.setOnClickListener(this);

        textPass.setOnEditorActionListener(this);

        textPass.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);

        return root;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backLoginPhone:
                request.onChangeFragment(2);
                break;
            case R.id.newAccount:
                verificarEmail();
                break;
            case R.id.lost:
                DialogCustom dialog = new DialogCustom();
                dialog.setText("Esta opción aun no se encuentra implementanda");
                dialog.setType(true);
                dialog.show(getFragmentManager(),TAG);
                break;
        }
    }

    public void verificar(){
        GeneralConstans gc = new GeneralConstans();

        final String email = sharedPref.getString(getString(R.string.email_key),"");
        email.replaceAll("\\s$", "");

        String URL_Request = gc.URL_BASE;
        URL_Request += gc.VERIFY_EMAIL;
        URL_Request += gc.REQUEST_EMAIL;
        URL_Request += email.trim();
        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            if(resp.getBoolean("success")){
                                requestCode(email);
                            }else{
                                waiting.setVisibility(View.INVISIBLE);
                                disableView(false);
                                DialogCustom dialog = new DialogCustom();
                                dialog.setText(resp.getString("info").toString());
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
                Log.d(TAG, error.toString());
                waiting.setVisibility(View.INVISIBLE);
                disableView(false);
                DialogCustom dialog = new DialogCustom();
                dialog.setType(true);
                dialog.setText(getString(R.string.error_verify));
                dialog.show(getFragmentManager(),TAG);
            }
        }
        );
        queue.add(stringRequest);
    }

    public void verificarEmail(){
        waiting.setVisibility(View.VISIBLE);
        disableView(true);
        verificar();
    }

    private void initSession(){
        waiting.setVisibility(View.VISIBLE);
        disableView(true);
        login();
    }

    private void disableView(boolean state){
        btn_create.setEnabled(!state);
        login.getFab().setEnabled(!state);
        btn_lost.setEnabled(!state);
        btn_back.setEnabled(!state);
        textPass.setEnabled(!state);
        if(!state){
            login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));

        }else {
            login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
        }
    }

    private void login(){
        GeneralConstans gc = new GeneralConstans();

        String email = sharedPref.getString(getString(R.string.email_key),"");
        String password = textPass.getText().toString();
        Country country = Country.getCountryFromSIM(getActivity());
        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }
        String codeCountry = country.getCode();
        String latitud = sharedPref.getString(getString(R.string.latitud_key),"0");
        String longitud = sharedPref.getString(getString(R.string.longitud_key), "0");
        email.replaceAll("\\s$", "");


        if(!longitud.equals("0") || ! latitud.equals("0")){
            String SV = sharedPref.getString(getString(R.string.setup_version_key), gc.SETUP_DEFAULT);
            String URL_Request = gc.URL_BASE;
            URL_Request += gc.REST_LOGIN;
            URL_Request += gc.REQUEST_EMAIL;
            URL_Request += email.trim();
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_PASSWORD;
            URL_Request += password.trim();
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_SETUP_VERSION;
            URL_Request += SV.trim();
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_LATITUD;
            URL_Request += latitud.trim();
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_LONGITUD;
            URL_Request += longitud.trim();
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_COUNTRY_CODE;
            URL_Request += codeCountry.trim();

            Log.d(TAG, URL_Request);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resp = new JSONObject(response);
                                if(resp.getBoolean("success")){
                                    /*
                                    Log.d(TAG,resp.getJSONObject("data").toString());

                                    editor.putInt(getString(R.string.id_user_key), resp.getJSONObject("data").getInt("iduser"));
                                    editor.putString(getString(R.string.name_user_key), resp.getJSONObject("data").getString("name"));
                                    editor.putString(getString(R.string.lastname_user_key), resp.getJSONObject("data").getString("lastname"));
                                    editor.putString(getString(R.string.email_key), resp.getJSONObject("data").getString("email"));
                                    editor.putString(getString(R.string.photo_user_key), resp.getJSONObject("data").getString("photo"));
                                    editor.putBoolean(getString(R.string.is_company_key), resp.getJSONObject("data").getBoolean("is_company"));
                                    editor.putBoolean(getString(R.string.is_offerer_key), resp.getJSONObject("data").getBoolean("is_offer"));
                                    if(resp.getJSONObject("data").getBoolean("is_offer") && resp.getJSONObject("data").has("offer_job")){
                                        editor.putString(getString(R.string.service_name_key), resp.getJSONObject("data").getJSONObject("offer_job").getString("service_name"));
                                        editor.putString(getString(R.string.description_job_key), resp.getJSONObject("data").getJSONObject("offer_job").getString("description"));
                                        editor.putString(getString(R.string.title_job_key), resp.getJSONObject("data").getJSONObject("offer_job").getString("title"));
                                        editor.putString(getString(R.string.service_id_key), resp.getJSONObject("data").getJSONObject("offer_job").getString("service_id"));
                                        editor.putString(getString(R.string.offer_job_id_key), resp.getJSONObject("data").getJSONObject("offer_job").getString("id"));
                                    }
                                    String geo = resp.getJSONObject("data").getJSONObject("setup").getString("geo_frecuency");
                                    editor.putString(getString(R.string.geo_frecuency_key), geo);
                                    editor.putString(getString(R.string.setup_version_key),resp.getJSONObject("data").getJSONObject("setup").getString("version_setup"));
                                    editor.putString(getString(R.string.offer_work_image), resp.getJSONObject("data").getJSONObject("setup").getString("offer_work_image"));
                                    editor.commit();

                                    */
                                    Log.d(TAG,resp.getJSONObject("user_data").toString());

                                    editor.putInt(getString(R.string.id_user_key), resp.getJSONObject("user_data").getInt("iduser"));
                                    editor.putString(getString(R.string.name_user_key), resp.getJSONObject("user_data").getString("name"));
                                    editor.putString(getString(R.string.lastname_user_key), resp.getJSONObject("user_data").getString("lastname"));
                                    editor.putString(getString(R.string.email_key), resp.getJSONObject("user_data").getString("email"));
                                    editor.putString(getString(R.string.photo_user_key), resp.getJSONObject("user_data").getString("photo"));
                                    editor.putBoolean(getString(R.string.is_company_key), resp.getJSONObject("user_data").getBoolean("is_company"));
                                    editor.putBoolean(getString(R.string.is_offerer_key), resp.getJSONObject("user_data").getBoolean("is_offer"));
                                    if(resp.getJSONObject("user_data").getBoolean("is_offer") && resp.getJSONObject("user_data").has("offer_job")){
                                        editor.putString(getString(R.string.service_name_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("service_name"));
                                        editor.putString(getString(R.string.description_job_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("description"));
                                        editor.putString(getString(R.string.title_job_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("title"));
                                        editor.putString(getString(R.string.service_id_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("service_id"));
                                        editor.putString(getString(R.string.offer_job_id_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("id"));
                                        editor.putString("offer_work_array", resp.getJSONObject("user_data").getJSONObject("offer_job").getJSONArray("offer_works").toString());
                                    }
                                    if(resp.getJSONObject("user_data").has("offer_work")){
                                        editor.putString("state_name_last",resp.getJSONObject("user_data").getJSONObject("offer_work").getString("state_name"));
                                        editor.putString("service_name_last",resp.getJSONObject("user_data").getJSONObject("offer_work").getString("service_name"));
                                        editor.putString("service_id_last",resp.getJSONObject("user_data").getJSONObject("offer_work").getString("state_name"));
                                        editor.putString("description_service_last",resp.getJSONObject("user_data").getJSONObject("offer_work").getString("state_name"));
                                        editor.putString("offer_work_id",resp.getJSONObject("user_data").getJSONObject("offer_work").getString("id"));
                                        editor.putString("state_id_last",resp.getJSONObject("user_data").getJSONObject("offer_work").getString("state_id"));
                                        editor.putString("proposals",resp.getJSONObject("user_data").getJSONObject("offer_work").getJSONArray("proposals").toString());
                                    }
                                    String geo = resp.getJSONObject("user_data").getJSONObject("setup").getString("geo_frecuency");
                                    editor.putString(getString(R.string.geo_frecuency_key), geo);
                                    editor.putString(getString(R.string.setup_version_key),resp.getJSONObject("user_data").getJSONObject("setup").getString("version_setup"));
                                    editor.putString(getString(R.string.offer_work_image), resp.getJSONObject("user_data").getJSONObject("setup").getString("offer_work_image"));
                                    editor.commit();
                                    Intent main = new Intent(getActivity(), MapsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    getActivity().finish();
                                    startActivity(main);

                                }else{
                                    waiting.setVisibility(View.INVISIBLE);
                                    disableView(false);
                                    DialogCustom dialog = new DialogCustom();
                                    dialog.setText(resp.getString("info").toString());
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
                    Log.d(TAG, error.toString());
                    waiting.setVisibility(View.INVISIBLE);
                    disableView(false);
                    DialogCustom dialog = new DialogCustom();
                    dialog.setType(true);
                    dialog.setText(getString(R.string.error_init_session));
                    dialog.show(getFragmentManager(),TAG);
                }
            }
            );
            queue.add(stringRequest);
        }else{
            waiting.setVisibility(View.INVISIBLE);
            disableView(false);
            DialogCustom dialog = new DialogCustom();
            dialog.setType(true);
            dialog.set_this(getActivity());
            dialog.setEnabledGPS(true);
            dialog.setText("No se ha logrado obtener su posición, BizmoBiz requiere que active su GPS.");
            dialog.show(getFragmentManager(),TAG);
        }
    }

    public void dialogNoCountry() {
        DialogCustom dialog = new DialogCustom();
        dialog.setType(true);
        dialog.setText(getString(R.string.noApp));
        dialog.show(getFragmentManager(), TAG);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handler = false;
        textPass.setError(null);
        matcher = patternPass.matcher(textPass.getText().toString());
        boolean resp = matcher.matches();
        if(i == EditorInfo.IME_ACTION_GO && resp){
            initSession();
            handler = true;
        }else {
            textPass.requestFocus();
            handler = true;
        }
        return handler;
    }

    private void requestCode(String email){
        GeneralConstans gc = new GeneralConstans();
        email.replaceAll("\\s$", "");
        waiting.setVisibility(View.VISIBLE);

        String URL_Request = gc.URL_BASE + gc.VERIFY_CODE + gc.REQUEST_EMAIL + email;
        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            waiting.setVisibility(View.INVISIBLE);
                            if(resp.getBoolean("result")){
                                String code = resp.getString(getString(R.string.verify_code));
                                editor.putString(getString(R.string.verify_code),code);
                                editor.commit();
                                Intent main = new Intent(getActivity(), RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                getActivity().startActivity(main);
                                getActivity().finish();
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
                Log.d(TAG, error.toString());

            }
        }
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.start();
        queue.add(stringRequest);
    }
}