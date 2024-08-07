package com.bizmolimited.bizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.bizmolimited.bizapp.utils.DialogCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private static final int REQUEST_ACCESS_NETWORK_STATE = 2;
    private static final int REQUEST_INTERNET = 3;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 5;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 6;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 4;
    private static final int REQUEST_CAMERA = 7;
    private List<String> permissionsArray = new ArrayList<>();

    private int WAIT = 4000;
    private MainActivity _this = this;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getSharedPreferences(mp,Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        if(sharedPref.getInt(getString(R.string.id_user_key), 0) > 0){
            WAIT = 2000;
        }else{
            WAIT = 4000;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent iniciar;
                int id = sharedPref.getInt(getString(R.string.id_user_key), 0);
                if(id == 0){
                    iniciar = new Intent(_this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(iniciar);
                }else{
                    setup(id);
                }

            }
        }, WAIT);

    }

    public void setup(int idUser){
        final GeneralConstans gc = new GeneralConstans();
        String URL_Request = gc.URL_BASE + gc.REST_SETUP + gc.REQUEST_ID_USER + Integer.toString(idUser);
        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            String geo = resp.getString(getString(R.string.geo_frecuency_key));
                            editor.putString(getString(R.string.geo_frecuency_key), geo);
                            editor.putString(getString(R.string.setup_version_key), resp.getString("version_setup"));
                            editor.putString(getString(R.string.offer_work_image), resp.getString("offer_work_image"));
                            editor.commit();

                            Intent main = new Intent(_this, MapsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(main);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                DialogCustom dialog = new DialogCustom();
                dialog.setType(true);
                dialog.setText("No existe una conexión a internet en el teléfono.");
                dialog.show(getFragmentManager(),TAG);
            }
        }
        );
        queue.add(stringRequest);
    }

    public void checkPermissions() {
        int isWrite = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isWrite != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        } else {
            permissionsArray.add((REQUEST_WRITE_EXTERNAL_STORAGE - 1), String.valueOf(REQUEST_WRITE_EXTERNAL_STORAGE));
        }
        int isAccessNetworkState = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_NETWORK_STATE);
        if (isAccessNetworkState != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_NETWORK_STATE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);

                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_NETWORK_STATE - 1), String.valueOf(REQUEST_ACCESS_NETWORK_STATE));
        }
        int isInternet = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.INTERNET);
        if (isInternet != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.INTERNET}, REQUEST_INTERNET);

                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.INTERNET}, REQUEST_INTERNET);
            return;
        } else {
            permissionsArray.add((REQUEST_INTERNET - 1), String.valueOf(REQUEST_INTERNET));
        }
        int isRead = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isRead != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);

                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        } else {
            permissionsArray.add((REQUEST_READ_EXTERNAL_STORAGE - 1), String.valueOf(REQUEST_READ_EXTERNAL_STORAGE));
        }
        int isLocation = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (isLocation != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_COARSE_LOCATION - 1), String.valueOf(REQUEST_ACCESS_COARSE_LOCATION));
        }

        int isLocationF = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (isLocationF != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_FINE_LOCATION - 1), String.valueOf(REQUEST_ACCESS_FINE_LOCATION));
        }
        int isCamera = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
        if (isCamera != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
            return;
        } else {
            permissionsArray.add((REQUEST_CAMERA - 1), String.valueOf(REQUEST_CAMERA));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_NETWORK_STATE:
                checkPermissions();
                break;
            case REQUEST_INTERNET:
                checkPermissions();
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                checkPermissions();
                break;
            case REQUEST_READ_EXTERNAL_STORAGE:
                checkPermissions();
                break;
            case REQUEST_ACCESS_COARSE_LOCATION:
                checkPermissions();
                break;
            case REQUEST_ACCESS_FINE_LOCATION:
                checkPermissions();
                break;
            case REQUEST_CAMERA:
                checkPermissions();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
