package com.bizmolimited.bizapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.fragments.FragmentRegisterCode;
import com.bizmolimited.bizapp.fragments.FragmentRegisterEmail;
import com.bizmolimited.bizapp.fragments.FragmentRegisterName;
import com.bizmolimited.bizapp.fragments.FragmentRegisterPass;
import com.bizmolimited.bizapp.fragments.FragmentRegisterSpecial;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.DialogCustom;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class RegisterActivity extends BaseActivity implements CallbackFragments{

    public static final String TAG = RegisterActivity.class.getSimpleName();

    protected LocationManager locationManager;

    private RequestQueue queue;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private FusedLocationProviderClient mFusedLocationClient;

    private FragmentRegisterCode fragment1;
    private FragmentRegisterEmail fragment2;
    private FragmentRegisterPass fragment3;
    private FragmentRegisterName fragment4;
    private FragmentRegisterSpecial fragment5;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private LocationListener listenerLocation;

    private RegisterActivity _this = this;

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        queue = Volley.newRequestQueue(this);
        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        fragment1 = new FragmentRegisterCode();
        fragment2 = new FragmentRegisterEmail();
        fragment3 = new FragmentRegisterPass();
        fragment4 = new FragmentRegisterName();
        fragment5 = new FragmentRegisterSpecial();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initListenerLocation();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(sharedPref.getBoolean(getString(R.string.special_reg),false)){
            editor.remove(getString(R.string.special_reg));
            editor.commit();
            fragmentTransaction.replace(R.id.container2, fragment5);
        }else{
            fragmentTransaction.replace(R.id.container2, fragment1);
        }

        fragmentTransaction.commit();
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void initListenerLocation(){
        listenerLocation = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, location.toString());

                editor.putString(getString(R.string.latitud_key), String.valueOf(location.getLatitude()));
                editor.putString(getString(R.string.longitud_key), String.valueOf(location.getLongitude()));
                editor.commit();

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d(TAG,"disable");
                DialogCustom dialog = new DialogCustom();
                dialog.setType(true);
                dialog.setText(getString(R.string.gps_off));
                dialog.setEnabledGPS(true);
                dialog.set_this(_this);
                dialog.show(getFragmentManager(), TAG);
            }

            @Override
            public void onProviderEnabled(String provider) {
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
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Log.d(TAG, "location update");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listenerLocation);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listenerLocation);
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
    public void onChangeFragment(int fragment) {
        Fragment rest = null;
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(null);
        fragmentTransaction = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (fragment){
            case 1:
                rest = fragment1;
                break;
            case 2:
                rest = fragment2;
                break;
            case 3:
                rest = fragment3;
                break;
            case 4:
                rest = fragment4;
                break;
        }
        fragmentTransaction.replace(R.id.container2, rest);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "press");
        Fragment o = fragmentManager.findFragmentById(R.id.container2);
        if(o instanceof FragmentRegisterEmail){
            Intent main = new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            if(sharedPref.getBoolean(getString(R.string.special_reg),false)){
                editor.remove(getString(R.string.special_reg));
            }
            editor.putBoolean("cambiar",true);
            editor.commit();
            locationManager.removeUpdates(listenerLocation);
            startActivity(main);
            finish();
        }else if(o instanceof FragmentRegisterPass){
            onChangeFragment(2);
        }else if(o instanceof FragmentRegisterName){
            onChangeFragment(3);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        locationManager.removeUpdates(listenerLocation);

    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(listenerLocation);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listenerLocation);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listenerLocation);
    }

}
