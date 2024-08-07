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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.MapsActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.RegisterActivity;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;
import com.bizmolimited.bizapp.utils.DialogCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guillermofuentesquijada on 09-10-17.
 */

public class FragmentRegisterPass extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener{

    private static final String TAG = FragmentRegisterPass.class.getSimpleName();

    private CallbackFragments request;

    private View root;
    private Button back;
    private EditText pass;
    private TextInputLayout lpass;

    private View waiting;

    private RequestQueue queue;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private boolean special;

    private Pattern patternPass;
    private Matcher matcher;

    private RegisterActivity register;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register_pass, container, false);

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        queue = Volley.newRequestQueue(getActivity());
        waiting = (View) root.findViewById(R.id.wainting1);


        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        register = (RegisterActivity) getActivity();
        register.getFab().setVisibility(View.VISIBLE);
        register.getFab().setEnabled(false);
        register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
        register.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(getString(R.string.pass_key), pass.getText().toString());
                editor.commit();
                if(special){
                    registerUserRRSS();
                }else{
                    request.onChangeFragment(4);
                }
            }
        });

        String regex = getString(R.string.regexPass);

        special = sharedPref.getBoolean(getString(R.string.special_reg),false);

        Log.d(TAG, "special: " + special);

        patternPass = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        pass = (EditText) root.findViewById(R.id.passreg);
        lpass = (TextInputLayout) root.findViewById(R.id.lpassreg);

        back = (Button) root.findViewById(R.id.backEmail);

        back.setTypeface(font);
        back.setText("\uf053");

        back.setOnClickListener(this);

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pass.setError(null);
                lpass.setError(null);
                matcher = patternPass.matcher(pass.getText().toString());
                boolean resp = matcher.matches();
                if(resp){
                    register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                    register.getFab().setEnabled(true);
                }else{
                    lpass.setError("La contraseña ingresada no es válida, recuerda debe terner más de 6 caracteres.");
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
        pass.setOnEditorActionListener(this);
        pass.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backEmail:
                request.onChangeFragment(2);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handler = false;
        pass.setError(null);
        lpass.setError(null);
        matcher = patternPass.matcher(pass.getText().toString());
        boolean resp = matcher.matches();
        if(i == EditorInfo.IME_ACTION_GO && resp){
            editor.putString(getString(R.string.pass_key), pass.getText().toString());
            editor.commit();
            if(special){
                registerUserRRSS();
            }else{
                request.onChangeFragment(4);
            }

            handler = true;
        }else{
            if(!resp){
                pass.setError(getString(R.string.error_pass));
            }
            pass.requestFocus();
            handler = true;
        }
        return handler;
    }

    public void registerUserRRSS(){
        waiting.setVisibility(View.VISIBLE);
        GeneralConstans gc = new GeneralConstans();
        String phone = sharedPref.getString(getString(R.string.phone_key),"99999999");
        String email = sharedPref.getString(getString(R.string.email_special),"");
        String pass = sharedPref.getString(getString(R.string.pass_key),"123456");
        String lgn = sharedPref.getString(getString(R.string.longitud_key),"0");
        String lat = sharedPref.getString(getString(R.string.latitud_key),"0");
        String uid = sharedPref.getString(getString(R.string.iduser_special),null);
        String type = sharedPref.getString(getString(R.string.type_special),getString(R.string.facebooklabel));
        if(type.equals(getString(R.string.facebooklabel))){
            type = gc.FACEBOOK_TYPE;
        }else{
            type = gc.GOOGLE_TYPE;
        }
        Country country = Country.getCountryFromSIM(getActivity());
        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }
        String code = country.getCode();
        String names = sharedPref.getString(getString(R.string.name_special),"");
        String lastnames = sharedPref.getString(getString(R.string.lastname_special),"");

        if(!lgn.equals("0") || !lat.equals("0") || uid != null){
            String URL_Request = gc.URL_BASE ;
            try {
                URL_Request += gc.REST_REGISTER;
                URL_Request += gc.REQUEST_NAME;
                URL_Request += URLEncoder.encode(names, "UTF-8").replaceAll("\\+", "%20");
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_LASTNAME;
                URL_Request += URLEncoder.encode(lastnames, "UTF-8").replaceAll("\\+", "%20");
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_COUNTRY_CODE;
                URL_Request += code.trim();
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_EMAIL;
                URL_Request += URLEncoder.encode(email, "UTF-8").replaceAll("\\+", "%20");
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_PHONE;
                URL_Request += phone.trim();
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_PASSWORD;
                URL_Request += pass.trim();
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_LATITUD;
                URL_Request += lat.trim();
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_LONGITUD;
                URL_Request += lgn.trim();
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_RRSS_UID;
                URL_Request += uid.trim();
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_RRSS_UID;
                URL_Request += type.trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


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
                                    //String geo = resp.getJSONObject("data").getJSONObject("setup").getString("geo_frecuency");
                                    //editor.putString(getString(R.string.geo_frecuency_key), geo);
                                    //editor.putString(getString(R.string.setup_version_key),resp.getJSONObject("data").getJSONObject("setup").getString("version_setup"));
                                    editor.putString(getString(R.string.setup_version_key),resp.getJSONObject("data").getString("version_setup"));
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

                                    waiting.setVisibility(View.INVISIBLE);
                                    Intent main = new Intent(getActivity(), MapsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    getActivity().startActivity(main);
                                    getActivity().finish();
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
                    dialog.setText(getString(R.string.error_create_account));
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

    public void disableView(boolean state){
        back.setEnabled(!state);
        register.getFab().setEnabled(!state);
    }
}