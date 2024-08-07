package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by guillermofuentesquijada on 22-10-17.
 */

public class FragmentLoginRRSS extends Fragment implements View.OnClickListener{

    private static final String TAG = FragmentLoginRRSS.class.getSimpleName();
    private static final int RC_SIGN_IN = 132;

    private CallbackFragments request;
    private RequestQueue queue;

    private View root;
    private View btnFacebook;
    private View btnGoogle;
    private Button back;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    private View waiting;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login_rrss, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        btnFacebook = (View) root.findViewById(R.id.facebook_btn);
        btnGoogle = (View) root.findViewById(R.id.google_btn);
        back = (Button) root.findViewById(R.id.backRRSS);

        queue = Volley.newRequestQueue(getActivity());

        waiting = (View) root.findViewById(R.id.wainting);

        back.setTypeface(font);
        back.setText("\uf053");

        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        back.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) root.findViewById(R.id.login_button_fb);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        loginButton.setFragment(this);

        LoginActivity _this = ((LoginActivity) getActivity());

        mGoogleApiClient = _this.getmGoogleApiClient();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                Log.d(TAG,object.toString());
                                try {
                                    String url = "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large";
                                    userExists(object.getString("email"), object.getString("id"), GeneralConstans.FACEBOOK_TYPE, object.getString("first_name"), object.getString("last_name"), url);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG,"error");
            }
        });


        try{
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.facebook_btn:
                Log.d(TAG, "facebook click");
                loginButton.performClick();
                break;
            case R.id.google_btn:
                Log.d(TAG, "google click");
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.backRRSS:
                request.onChangeFragment(1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String url = "DEFAULT";
            if(acct.getPhotoUrl() != null){
                url = acct.getPhotoUrl().toString();
            }
            userExists(acct.getEmail(), acct.getId(), GeneralConstans.GOOGLE_TYPE, acct.getGivenName(), acct.getFamilyName(), url);
        } else {
            Log.d(TAG, "Cancel Google User Account");
        }
    }

    private void userExists(final String email, final String uid, final String type, final String name, final String lastname, final String url_picture){
        waiting.setVisibility(View.VISIBLE);
        disableView(true);
        final GeneralConstans gc = new GeneralConstans();

        String url_request = gc.URL_BASE;
        url_request += gc.USER_EXIST;
        url_request += gc.REQUEST_RRSS_EMAIL;
        url_request += email.trim();
        url_request += gc.REQUEST_AND;
        url_request += gc.REQUEST_RRSS_UID;
        url_request += uid.trim();
        url_request += gc.REQUEST_AND;
        url_request += gc.REQUEST_RRSS_TYPE;
        url_request += type;
        Log.d(TAG, url_request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            Log.d(TAG, resp.toString());
                            if(resp.getBoolean("success")){
                                editor.putBoolean(getString(R.string.special_log),true);
                                editor.putString(getString(R.string.type_special),type);
                                editor.putString(getString(R.string.photo_user_key), url_picture);
                                editor.commit();
                                loginRRSS(email, uid, type);
                            }else{
                                editor.putString(getString(R.string.name_special), name);
                                editor.putString(getString(R.string.lastname_special), lastname);
                                editor.putString(getString(R.string.email_special), email);
                                editor.putString(getString(R.string.iduser_special), uid);
                                if(type.equals(gc.GOOGLE_TYPE)){
                                    editor.putString(getString(R.string.type_special), getString(R.string.googlelabel));
                                }else{
                                    editor.putString(getString(R.string.type_special), getString(R.string.facebooklabel));
                                }
                                editor.putString(getString(R.string.photo_user_key), url_picture);
                                editor.putBoolean(getString(R.string.special_reg), true);
                                editor.commit();
                                waiting.setVisibility(View.INVISIBLE);
                                disableView(false);
                                Intent init_reg = new Intent(getActivity(), RegisterActivity.class);
                                startActivity(init_reg);
                                getActivity().finish();
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
                dialog.setText("Ha ocurrido un error, intente nuevamente.");
                dialog.show(getFragmentManager(),TAG);
            }
        }
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void loginRRSS(String email, String uid, String type){
        GeneralConstans gc = new GeneralConstans();
        Country country = Country.getCountryFromSIM(getActivity());
        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }
        String latitud = sharedPref.getString(getString(R.string.latitud_key),"0");
        String longitud = sharedPref.getString(getString(R.string.longitud_key), "0");

        if(!latitud.equals("0") || !longitud.equals("0")){
            String url_request = gc.URL_BASE;
            url_request += gc.LOGIN_RRSS;
            url_request += gc.REQUEST_RRSS_EMAIL;
            url_request += email.trim();
            url_request += gc.REQUEST_AND;
            url_request += gc.REQUEST_RRSS_UID;
            url_request += uid.trim();
            url_request += gc.REQUEST_AND;
            url_request += gc.REQUEST_RRSS_TYPE;
            url_request += type.trim();
            url_request += gc.REQUEST_AND;
            url_request += gc.REQUEST_LATITUD;
            url_request += latitud;
            url_request += gc.REQUEST_AND;
            url_request += gc.REQUEST_LONGITUD;
            url_request += longitud;
            url_request += gc.REQUEST_AND;
            url_request += gc.REQUEST_COUNTRY_CODE;
            url_request += country.getCode();
            url_request += gc.REQUEST_AND;
            url_request += gc.REQUEST_SETUP_VERSION;
            url_request += sharedPref.getString(getString(R.string.setup_version_key), gc.SETUP_DEFAULT);
            Log.d(TAG, url_request);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_request,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resp = new JSONObject(response);
                                if(resp.getBoolean("success")){
                                    Log.d(TAG,resp.getJSONObject("user_data").toString());

                                    editor.putInt(getString(R.string.id_user_key), resp.getJSONObject("user_data").getInt("iduser"));
                                    editor.putString(getString(R.string.name_user_key), resp.getJSONObject("user_data").getString("name"));
                                    editor.putString(getString(R.string.lastname_user_key), resp.getJSONObject("user_data").getString("lastname"));
                                    editor.putString(getString(R.string.email_key), resp.getJSONObject("user_data").getString("email"));
                                    if(!sharedPref.getBoolean(getString(R.string.special_log),false)){
                                        editor.putString(getString(R.string.photo_user_key), resp.getJSONObject("user_data").getString("photo"));
                                    }
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
                                    /*
                                    Log.d(TAG,resp.getJSONObject("user_data").toString());

                                    editor.putInt(getString(R.string.id_user_key), resp.getJSONObject("user_data").getInt("iduser"));
                                    editor.putString(getString(R.string.name_user_key), resp.getJSONObject("user_data").getString("name"));
                                    editor.putString(getString(R.string.lastname_user_key), resp.getJSONObject("user_data").getString("lastname"));
                                    editor.putString(getString(R.string.email_key), resp.getJSONObject("user_data").getString("email"));

                                    editor.putBoolean(getString(R.string.is_company_key), resp.getJSONObject("user_data").getBoolean("is_company"));
                                    editor.putBoolean(getString(R.string.is_offerer_key), resp.getJSONObject("user_data").getBoolean("is_offer"));
                                    if(resp.getJSONObject("user_data").getBoolean("is_offer") && resp.getJSONObject("user_data").getJSONObject("offer_job") != null){
                                        editor.putString(getString(R.string.service_name_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("service_name"));
                                        editor.putString(getString(R.string.description_job_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("description"));
                                        editor.putString(getString(R.string.title_job_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("title"));
                                        editor.putString(getString(R.string.service_id_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("service_id"));
                                        editor.putString(getString(R.string.offer_job_id_key), resp.getJSONObject("user_data").getJSONObject("offer_job").getString("id"));
                                    }
                                    String geo = resp.getJSONObject("user_data").getJSONObject("setup").getString("geo_frecuency");
                                    editor.putString(getString(R.string.geo_frecuency_key), geo);
                                    editor.putString(getString(R.string.setup_version_key),resp.getJSONObject("user_data").getJSONObject("setup").getString("version_setup"));
                                    editor.commit();
                                    */
                                    waiting.setVisibility(View.INVISIBLE);
                                    disableView(false);
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

                }
            }
            );
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);

        }else{
            waiting.setVisibility(View.INVISIBLE);
            if(type.equals(GeneralConstans.FACEBOOK_TYPE)){
                LoginManager.getInstance().logOut();
            }else{
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            }
            disableView(false);
            DialogCustom dialog = new DialogCustom();
            dialog.setType(true);
            dialog.set_this(getActivity());
            dialog.setEnabledGPS(true);
            dialog.setText("No se ha logrado obtener su posición, BizmoBiz requiere que active su GPS.");
            dialog.show(getFragmentManager(),TAG);
        }
    }

    private void disableView(boolean state){
        back.setEnabled(!state);
        btnGoogle.setEnabled(!state);
        btnFacebook.setEnabled(!state);
    }

}
