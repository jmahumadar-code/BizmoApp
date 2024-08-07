package com.bizmolimited.bizapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bizmolimited.bizapp.fragments.FragmentLoginInit;
import com.bizmolimited.bizapp.fragments.FragmentLoginPass;
import com.bizmolimited.bizapp.fragments.FragmentLoginPhone;
import com.bizmolimited.bizapp.fragments.FragmentLoginRRSS;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.DialogCustom;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements CallbackFragments {

    private static final String TAG = LoginActivity.class.getSimpleName();

    protected LocationManager locationManager;

    private static final int REQUEST_ACCESS_NETWORK_STATE = 2;
    private static final int REQUEST_INTERNET = 3;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 5;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 6;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 4;
    private static final int REQUEST_CAMERA = 7;
    private List<String> permissionsArray = new ArrayList<>();

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private FragmentLoginInit fragment1;
    private FragmentLoginPhone fragment2;
    private FragmentLoginPass fragment3;
    private FragmentLoginRRSS fragment4;

    private LoginActivity _this = this;
    private android.location.LocationListener listenerStatusGPS;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragment1 = new FragmentLoginInit();
        fragment2 = new FragmentLoginPhone();
        fragment3 = new FragmentLoginPass();
        fragment4 = new FragmentLoginRRSS();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        boolean op = sharedPref.getBoolean("cambiar", false);

        if (op) {
            fragmentTransaction.replace(R.id.container_login, fragment3);
            editor.remove("cambiar");
            editor.commit();
        } else {
            fragmentTransaction.replace(R.id.container_login, fragment1);
        }

        fragmentTransaction.commit();

        checkPermission();

    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void checkPermission() {
        permissionsArray.clear();
        int isWrite = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isWrite != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        } else {
            permissionsArray.add((REQUEST_WRITE_EXTERNAL_STORAGE - 1), String.valueOf(REQUEST_WRITE_EXTERNAL_STORAGE));
        }
        int isAccessNetworkState = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_NETWORK_STATE);
        if (isAccessNetworkState != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.ACCESS_NETWORK_STATE)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);

                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_NETWORK_STATE - 1), String.valueOf(REQUEST_ACCESS_NETWORK_STATE));
        }
        int isInternet = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.INTERNET);
        if (isInternet != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.INTERNET}, REQUEST_INTERNET);

                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.INTERNET}, REQUEST_INTERNET);
            return;
        } else {
            permissionsArray.add((REQUEST_INTERNET - 1), String.valueOf(REQUEST_INTERNET));
        }
        int isRead = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isRead != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);

                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        } else {
            permissionsArray.add((REQUEST_READ_EXTERNAL_STORAGE - 1), String.valueOf(REQUEST_READ_EXTERNAL_STORAGE));
        }
        int isLocation = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (isLocation != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_COARSE_LOCATION - 1), String.valueOf(REQUEST_ACCESS_COARSE_LOCATION));
        }

        int isLocationF = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (isLocationF != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_FINE_LOCATION - 1), String.valueOf(REQUEST_ACCESS_FINE_LOCATION));
        }
        int isCamera = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.CAMERA);
        if (isCamera != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                return;
            }
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
            return;
        } else {
            permissionsArray.add((REQUEST_CAMERA - 1), String.valueOf(REQUEST_CAMERA));
        }
        Log.d(TAG, "size: " + permissionsArray.size());
        if (permissionsArray.size() == 7) {
            Log.d(TAG, "permisos ok...");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            listerGPS();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_NETWORK_STATE:
                checkPermission();
                break;
            case REQUEST_INTERNET:
                checkPermission();
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                checkPermission();
                break;
            case REQUEST_READ_EXTERNAL_STORAGE:
                checkPermission();
                break;
            case REQUEST_ACCESS_COARSE_LOCATION:
                checkPermission();
                break;
            case REQUEST_ACCESS_FINE_LOCATION:
                checkPermission();
                break;
            case REQUEST_CAMERA:
                checkPermission();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onChangeFragment(int fragment) {
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(null);
        Fragment result = null;
        fragmentTransaction = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (fragment) {
            case 1:
                result = fragment1;
                hideKeyboard();
                fragmentTransaction.replace(R.id.container_login, result);
                break;
            case 2:
                result = fragment2;
                fragmentTransaction.replace(R.id.container_login, result);
                break;
            case 3:
                result = fragment3;
                fragmentTransaction.replace(R.id.container_login, result);
                break;
            case 4:
                result = fragment4;
                fragmentTransaction.replace(R.id.container_login, result);
                break;
            default:
                Log.d(TAG, "fragment: " + fragment);
                break;
        }

        fragmentTransaction.commit();

    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(),0);
    }

    @Override
    public void onBackPressed() {
        Fragment o = fragmentManager.findFragmentById(R.id.container_login);
        if (o instanceof FragmentLoginInit) {
            super.onBackPressed();
        } else if (o instanceof FragmentLoginPhone) {
            onChangeFragment(1);
        } else if (o instanceof FragmentLoginPass) {
            onChangeFragment(2);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(listenerStatusGPS != null){
            locationManager.removeUpdates(listenerStatusGPS);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(listenerStatusGPS != null){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listenerStatusGPS);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listenerStatusGPS);
        }
    }

    private void listerGPS() {
        listenerStatusGPS = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d(TAG, location.toString());
                editor.putString(getString(R.string.latitud_key), String.valueOf(location.getLatitude()));
                editor.putString(getString(R.string.longitud_key), String.valueOf(location.getLongitude()));
                editor.commit();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d(TAG, "enable");
                if (ActivityCompat.checkSelfPermission(_this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(_this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null){
                    Log.d(TAG, "GPS: " + location.toString());
                    editor.putString(getString(R.string.latitud_key), String.valueOf(location.getLatitude()));
                    editor.putString(getString(R.string.longitud_key), String.valueOf(location.getLongitude()));
                    editor.commit();
                }
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d(TAG,"disable");
                DialogCustom dialog = new DialogCustom();
                dialog.setType(true);
                dialog.setText(getString(R.string.gps_off));
                dialog.setEnabledGPS(true);
                dialog.set_this(_this);
                dialog.show(getFragmentManager(), TAG);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.d(TAG, "location update");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listenerStatusGPS);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listenerStatusGPS);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
            Log.d(TAG, location.toString());
            editor.putString(getString(R.string.latitud_key), String.valueOf(location.getLatitude()));
            editor.putString(getString(R.string.longitud_key), String.valueOf(location.getLongitude()));
            editor.commit();
        }else{
            Log.d(TAG, "no position");
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                Log.d(TAG, location.toString());
                editor.putString(getString(R.string.latitud_key), String.valueOf(location.getLatitude()));
                editor.putString(getString(R.string.longitud_key), String.valueOf(location.getLongitude()));
                editor.commit();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        locationManager.removeUpdates(listenerStatusGPS);
    }

}

