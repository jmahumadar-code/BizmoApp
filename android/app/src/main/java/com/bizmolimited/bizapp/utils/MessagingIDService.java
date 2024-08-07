package com.bizmolimited.bizapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by guillermofuentesquijada on 04-11-17.
 */

public class MessagingIDService extends FirebaseInstanceIdService {


    private static final String TAG = MessagingIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        updateToken(refreshedToken);
    }

    public void updateToken(String token){
        final GeneralConstans gc = new GeneralConstans();
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        String mp = getText(R.string.bizapp_pref).toString();
        SharedPreferences sharedPreferences = getSharedPreferences(mp, Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt(getString(R.string.id_user_key), 0);
        String URL_Request = gc.URL_BASE;
        URL_Request += gc.UPDATE_TOKEN;
        URL_Request += gc.REQUEST_ID_USER;
        URL_Request += String.valueOf(idUser);
        URL_Request += gc.REQUEST_AND;
        URL_Request += gc.REQUEST_TOKEN;
        URL_Request += token;

        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "resp: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        }
        );
        queue.add(stringRequest);
    }
}
