package com.bizmolimited.bizapp;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.bizmolimited.bizapp.fragments.FragmentDummy;
import com.bizmolimited.bizapp.fragments.FragmentMapsASearch;
import com.bizmolimited.bizapp.fragments.FragmentMapsAddOfferJob;
import com.bizmolimited.bizapp.fragments.FragmentMapsAddOfferJob2;
import com.bizmolimited.bizapp.fragments.FragmentMapsRequest;
import com.bizmolimited.bizapp.fragments.FragmentMapsSearch;
import com.bizmolimited.bizapp.fragments.FragmentPostularOffer;
import com.bizmolimited.bizapp.fragments.FragmentProposals;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.DialogCustom;
import com.bizmolimited.bizapp.utils.DialogPostule;
import com.bizmolimited.bizapp.utils.DialogSelect;
import com.bizmolimited.bizapp.utils.DialogShowService;
import com.bizmolimited.bizapp.utils.DialogWork;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bizmolimited.bizapp.utils.AppHelper.convertDpToPixel;


public class MapsActivity extends BaseActivity implements OnMapReadyCallback, DialogCustom.CloseSession, LocationSource,
        CallbackFragments, GoogleMap.OnCameraIdleListener, DialogSelect.CallbackDialogSelect, View.OnClickListener,
        GoogleMap.OnInfoWindowClickListener, DialogPostule.CallbackPostular, DialogWork.CallbackWorkOffer,
        FragmentMapsAddOfferJob2.CallbackChangeMenu, FragmentMapsRequest.CallbackFAB {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private static final int REQUEST_ACCESS_NETWORK_STATE = 2;
    private static final int REQUEST_INTERNET = 3;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 5;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 6;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 4;
    private static final int REQUEST_CAMERA = 7;
    private List<String> permissionsArray = new ArrayList<>();

    private static final int ZOOM_MAPS = 16;
    private float last_zoom = 0;

    private boolean infoMarker = false;

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static long UPDATE_INTERVAL_IN_MILLISECONDS;
    private static long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;

    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private OnLocationChangedListener listener;
    private Boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;
    private GoogleMap mMap;

    private DrawerLayout mDrawerLayout;
    private TextView menu;
    private TextView usernameView;
    private TextView exit;
    private TextView addOffer;
    private TextView showOffer;
    private TextView changeUser;
    private CircleImageView profile_img;
    private View wainting;

    private SupportMapFragment mapFragment;

    private MapsActivity _this = this;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private View mapView;
    private LocationManager mLocationManager;
    private android.location.LocationListener listenerStatusGPS;

    private RequestQueue queue;

    private FragmentMapsSearch search;
    private FragmentMapsASearch search_av;
    private FragmentMapsRequest request;
    private FragmentMapsAddOfferJob add_offer_job;
    private FragmentMapsAddOfferJob2 add_offer_job2;
    private FragmentPostularOffer postular;
    private FragmentProposals showProposals;
    private FragmentDummy dummy;

    private View container_map;

    private DialogSelect select;
    private DialogShowService showOfferdlog;
    private DialogPostule dlogPostule;
    private DialogWork dlogwork;

    private FloatingActionButton fab;
    private FloatingActionButton fab1;

    private View content_mode;
    private TextView text_mode;

    private boolean activate_fab;

    private View text_search;

    private int idUser;
    private boolean is_offer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        activate_fab = false;
        updateValuesFromBundle(savedInstanceState);
        queue = Volley.newRequestQueue(_this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapView = mapFragment.getView();

        content_mode = findViewById(R.id.content_mode_user);
        text_mode = (TextView) findViewById(R.id.text_mode_user);

        search = new FragmentMapsSearch();
        search_av = new FragmentMapsASearch();
        request = new FragmentMapsRequest();
        add_offer_job = new FragmentMapsAddOfferJob();
        add_offer_job2 = new FragmentMapsAddOfferJob2();
        postular = new FragmentPostularOffer();
        showProposals = new FragmentProposals();
        dummy = new FragmentDummy();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        menu = (TextView) findViewById(R.id.lateral_menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                String propo = sharedPref.getString("proposals","[]");
                try {
                    JSONArray array = new JSONArray(propo);
                    if(array.length() > 0 &&
                            sharedPref.getString("state_id_last","0").equals("3") &&
                            text_search.getVisibility() == View.VISIBLE && container_map.getVisibility() == View.GONE){
                        fab.setVisibility(View.VISIBLE);
                        activate_fab = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        text_search = findViewById(R.id.text_search);
        container_map = findViewById(R.id.container_map);

        text_search.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setVisibility(View.GONE);

        fab.setOnClickListener(this);

        select = new DialogSelect();
        select.setCallback(this);

        showOfferdlog = new DialogShowService();
        dlogPostule = new DialogPostule();
        dlogPostule.setPostulacion(this);

        dlogwork = new DialogWork();
        dlogwork.setCallback(this);

        wainting = findViewById(R.id.wainting);

        View legal_exit = findViewById(R.id.view_legal_exit);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) legal_exit.getLayoutParams();

        if(hasNavBar(getApplicationContext())){
            params.setMargins(0, 0, 0, convertDpToPixel(10, getApplicationContext()));
            legal_exit.setLayoutParams(params);
        }else{
            params.setMargins(0, 0, 0, convertDpToPixel(50, getApplicationContext()));
            legal_exit.setLayoutParams(params);
        }

        exit = (TextView) findViewById(R.id.exit_map);
        addOffer = (TextView) findViewById(R.id.add_offer_job);
        showOffer = (TextView) findViewById(R.id.show_offer_job);
        changeUser = (TextView) findViewById(R.id.change_user);

        exit.setOnClickListener(this);
        addOffer.setOnClickListener(this);
        showOffer.setOnClickListener(this);
        changeUser.setOnClickListener(this);

        NavigationView nav = (NavigationView) findViewById(R.id.navview);
        View header = nav.getHeaderView(0);

        usernameView = (TextView) header.findViewById(R.id.username);
        profile_img = (CircleImageView) header.findViewById(R.id.profile_image);
        TextView star = (TextView) header.findViewById(R.id.staryellow);

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        is_offer = sharedPref.getBoolean(getString(R.string.is_offerer_key),false);

        Log.d(TAG,"is_offer: " + is_offer);

        if(sharedPref.getBoolean(getString(R.string.special_log), false) && !sharedPref.getString(getString(R.string.photo_user_key),"DEFAULT").equals("DEFAULT")){
            Log.d(TAG,"uri: " + sharedPref.getString(getString(R.string.photo_user_key),""));
            Picasso.with(getApplicationContext()).load(sharedPref.getString(getString(R.string.photo_user_key),"")).into(profile_img);
        }

        int geo_frecuency = Integer.parseInt(sharedPref.getString(getString(R.string.geo_frecuency_key), GeneralConstans.GEO_FRECUENCY));

        UPDATE_INTERVAL_IN_MILLISECONDS = geo_frecuency * 2;
        FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS;

        idUser = sharedPref.getInt(getString(R.string.id_user_key), 0);
        String name = sharedPref.getString(getString(R.string.name_user_key), " ") + " " + sharedPref.getString(getString(R.string.lastname_user_key), " ");
        usernameView.setText(name);

        mRequestingLocationUpdates = true;

        mLastUpdateTime = "";

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.font));

        Button btn_schuled = (Button) findViewById(R.id.time);
        btn_schuled.setTypeface(font);
        btn_schuled.setText("\uf017");
        btn_schuled.setTextColor(getResources().getColor(R.color.GrayC));

        star.setTypeface(font);
        star.setText("\uf005");
        menu.setTypeface(font);
        menu.setText("\uf0c9");

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        checkPermissions();
    }

    boolean hasNavBar(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            return resources.getBoolean(id);
        } else {    // Check for keys
            boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !hasMenuKey && !hasBackKey;
        }
    }

    public FloatingActionButton getFab1(){
        return this.fab1;
    }

    public void getInitialOffer(){
        mMap.clear();
        showOffer.setVisibility(View.GONE);
        addOffer.setVisibility(View.GONE);
        final GeneralConstans gc = new GeneralConstans();
        String lat = sharedPref.getString(getString(R.string.latitud_key),"0");
        String lgn = sharedPref.getString(getString(R.string.longitud_key),"0");
        Log.d(TAG, "zoom "+mMap.getCameraPosition().zoom);
        int zoom_level = (int) mMap.getCameraPosition().zoom;
        int value = (int) Math.pow(2, zoom_level);
        int km = (((40000/ value) * 2) >= 1)? (40000/ value) * 2 : 1;
        String radius = String.valueOf(km);

        if(!lat.equals("0") && !lgn.equals("0")){
            String URL_Request = gc.URL_BASE;
            URL_Request += gc.OFFER_JOB;
            URL_Request += gc.INITIAL_OFFER;
            URL_Request += gc.REQUEST_USER_ID;
            URL_Request += String.valueOf(idUser);
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_LATITUD;
            URL_Request += lat;
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_LONGITUD;
            URL_Request += lgn;
            URL_Request += gc.REQUEST_AND;
            URL_Request += gc.REQUEST_RADIUS;
            URL_Request += radius;

            Log.d(TAG, URL_Request);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonOffer = new JSONObject(response);
                                JSONArray offers = jsonOffer.getJSONArray("offer_jobs");
                                for (int i = 0; i < offers.length(); i++) {
                                    JSONObject offer = offers.getJSONObject(i);
                                    String title = offer.getString("title");
                                    String description = offer.getString("description");
                                    Double lat = offer.getDouble("latitud");
                                    Double lgn = offer.getDouble("longitud");
                                    addOfferToMap(title, description, lat, lgn);
                                }
                                String work_id = sharedPref.getString("offer_work_id", null);
                                Log.d(TAG, "worj id: " + work_id);
                                if(work_id != null){
                                    seekProposal(work_id);
                                }else{
                                    activate_fab = false;
                                    fab.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            wainting.setVisibility(View.INVISIBLE);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            }
            );
            queue.add(stringRequest);
        }else{
            Log.d(TAG, "error no position");
            wainting.setVisibility(View.INVISIBLE);
        }

    }

    public void getCategorias(){
        wainting.setVisibility(View.VISIBLE);
        final GeneralConstans gc = new GeneralConstans();
        String URL_Request = gc.URL_BASE;
        URL_Request += gc.CATEGORY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor.putString(getString(R.string.category_key),response);
                        editor.commit();
                        wainting.setVisibility(View.INVISIBLE);
                        initSelect();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wainting.setVisibility(View.INVISIBLE);
                Log.d(TAG, error.toString());
            }
        }
        );
        queue.add(stringRequest);
    }

    public void updateToken(String token){
        final GeneralConstans gc = new GeneralConstans();
        String URL_Request = gc.URL_BASE;
        URL_Request += gc.UPDATE_TOKEN;
        URL_Request += gc.REQUEST_USER_ID;
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

    private void addOfferToMap(String title, String description, Double lat, Double lgn){
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lgn))
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .snippet(description));
    }

    private void listenerGPS(){
        listenerStatusGPS = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d(TAG, provider + " enabled");
                if(!mRequestingLocationUpdates){
                    startLocationUpdates();
                }
                mLocationManager.removeUpdates(listenerStatusGPS);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d(TAG, provider + " disabled");
            }
        };
    }

    public void closeSession() {
        DialogCustom dialog = new DialogCustom();
        dialog.setText(getString(R.string.close_session));
        dialog.setType(false);
        dialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token: " + token);
        updateToken(token);

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setLocationSource(this);
        mMap.setOnInfoWindowClickListener(this);

        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        if(hasNavBar(getApplicationContext())){
            layoutParams.setMargins(0, 0, convertDpToPixel(20,getApplicationContext()), convertDpToPixel(60,getApplicationContext()));
        }else{
            layoutParams.setMargins(0, 0, convertDpToPixel(20,getApplicationContext()), convertDpToPixel(80,getApplicationContext()));
        }

        try {
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        if (mCurrentLocation != null) {
            LatLng thisLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(thisLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MAPS));
        } else {
            String lgn = sharedPref.getString(getString(R.string.longitud_key), "0.0");
            String lat = sharedPref.getString(getString(R.string.latitud_key), "0.0");

            if (!lgn.equals("0.0") && !lat.equals("0.0")) {
                LatLng thisLocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lgn));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(thisLocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MAPS));
            }
        }
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View myContentView = getLayoutInflater().inflate(
                        R.layout.info_marker, null);
                TextView tvTitle = ((TextView) myContentView
                        .findViewById(R.id.title));
                tvTitle.setText(marker.getTitle());
                TextView tvSnippet = ((TextView) myContentView
                        .findViewById(R.id.snippet));
                tvSnippet.setText(marker.getSnippet());
                return myContentView;
            }
        });
        mMap.setOnCameraIdleListener(this);
        getCategorias();
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            updateUI();
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                listener.onLocationChanged(mCurrentLocation);
                //LatLng thisLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                editor.putString(getString(R.string.latitud_key), String.valueOf(mCurrentLocation.getLatitude()));
                editor.putString(getString(R.string.longitud_key), String.valueOf(mCurrentLocation.getLongitude()));
                editor.commit();

                GeneralConstans gc = new GeneralConstans();
                String URL_Request = gc.URL_BASE;
                URL_Request += gc.REST_TRACKING;
                URL_Request += gc.REQUEST_ID_USER;
                URL_Request += idUser;
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_LATITUD;
                URL_Request += mCurrentLocation.getLatitude();
                URL_Request += gc.REQUEST_AND;
                URL_Request += gc.REQUEST_LONGITUD;
                URL_Request += mCurrentLocation.getLongitude();

                RequestQueue queue = Volley.newRequestQueue(_this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject resp = new JSONObject(response);

                                    if(resp.getBoolean("result")){
                                        Log.d(TAG,resp.toString());

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
                                            if(resp.getJSONObject("user_data").getJSONObject("offer_work").getJSONArray("proposals").length() > 0 &&
                                                    resp.getJSONObject("user_data").getJSONObject("offer_work").getString("state_id").equals("3") &&
                                                    text_search.getVisibility() == View.VISIBLE && container_map.getVisibility() == View.GONE){
                                                fab.setVisibility(View.VISIBLE);
                                                activate_fab = true;
                                            }
                                        }
                                        String geo = resp.getJSONObject("user_data").getJSONObject("setup").getString("geo_frecuency");
                                        editor.putString(getString(R.string.geo_frecuency_key), geo);
                                        editor.putString(getString(R.string.setup_version_key),resp.getJSONObject("user_data").getJSONObject("setup").getString("version_setup"));
                                        editor.putString(getString(R.string.offer_work_image), resp.getJSONObject("user_data").getJSONObject("setup").getString("offer_work_image"));
                                        editor.commit();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error");
                    }
                });
                queue.add(stringRequest);

                //mMap.moveCamera(CameraUpdateFactory.newLatLng(thisLocation));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MAPS));
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            }

            @Override
            public void onLocationAvailability(LocationAvailability status) {
                if (!status.isLocationAvailable()) {
                    startLocationUpdates();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        mRequestingLocationUpdates = false;
                        updateUI();
                        DialogCustom dialog = new DialogCustom();
                        dialog.setType(true);
                        dialog.setText(getString(R.string.gps_off));
                        dialog.setEnabledGPS(true);
                        dialog.set_this(this);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listenerStatusGPS);
                        dialog.show(getFragmentManager(), TAG);
                        break;
                }
                break;
        }
    }

    private void startLocationUpdates() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        if (ActivityCompat.checkSelfPermission(_this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(_this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                mCurrentLocation = task.getResult();
                                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                                //LatLng thisLocation = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
                                //mMap.moveCamera(CameraUpdateFactory.newLatLng(thisLocation));
                                //mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MAPS));
                                if(mCurrentLocation != null){
                                    listener.onLocationChanged(mCurrentLocation);
                                    updateUI();
                                }
                            }
                        });

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {
                                    Log.d(TAG, "petición");
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(MapsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        updateUI();
                    }
                });
    }

    private void updateUI() {
        if(mCurrentLocation != null){
            //LatLng thisLocation = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(thisLocation));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MAPS));
            listener.onLocationChanged(mCurrentLocation);
        }
    }

    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }

        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void checkPermissions() {
        permissionsArray.clear();
        int isWrite = ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isWrite != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

                return;
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        } else {
            permissionsArray.add((REQUEST_WRITE_EXTERNAL_STORAGE - 1), String.valueOf(REQUEST_WRITE_EXTERNAL_STORAGE));
        }
        int isAccessNetworkState = ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_NETWORK_STATE);
        if (isAccessNetworkState != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.ACCESS_NETWORK_STATE)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);

                return;
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_NETWORK_STATE - 1), String.valueOf(REQUEST_ACCESS_NETWORK_STATE));
        }
        int isInternet = ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.INTERNET);
        if (isInternet != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.INTERNET}, REQUEST_INTERNET);

                return;
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.INTERNET}, REQUEST_INTERNET);
            return;
        } else {
            permissionsArray.add((REQUEST_INTERNET - 1), String.valueOf(REQUEST_INTERNET));
        }
        int isRead = ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isRead != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);

                return;
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        } else {
            permissionsArray.add((REQUEST_READ_EXTERNAL_STORAGE - 1), String.valueOf(REQUEST_READ_EXTERNAL_STORAGE));
        }
        int isLocation = ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (isLocation != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_COARSE_LOCATION - 1), String.valueOf(REQUEST_ACCESS_COARSE_LOCATION));
        }

        int isLocationF = ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (isLocationF != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
            return;
        } else {
            permissionsArray.add((REQUEST_ACCESS_FINE_LOCATION - 1), String.valueOf(REQUEST_ACCESS_FINE_LOCATION));
        }
        int isCamera = ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.CAMERA);
        if (isCamera != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                return;
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
            return;
        } else {
            permissionsArray.add((REQUEST_CAMERA - 1), String.valueOf(REQUEST_CAMERA));
        }

        if (permissionsArray.size() == 7) {
            createLocationCallback();
            createLocationRequest();
            buildLocationSettingsRequest();
            listenerGPS();
            mapFragment.getMapAsync(this);

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
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(sharedPref.getBoolean(getString(R.string.special_log), false)){
            if(sharedPref.getString(getString(R.string.type_special),"0").equals(GeneralConstans.GOOGLE_TYPE)){
                Auth.GoogleSignInApi.signOut(getmGoogleApiClient());
            }else{
                LoginManager.getInstance().logOut();
            }
        }
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.clear();
        editor.commit();
        mLocationManager.removeUpdates(listenerStatusGPS);
        Intent login = new Intent(_this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(login);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.START)){
            mDrawerLayout.closeDrawer(Gravity.START);
        }else if(container_map.getVisibility() == View.VISIBLE){
            onChangeFragment(0);
        }else{
            closeSession();
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.listener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onChangeFragment(int fragment) {
        fab1.setVisibility(View.GONE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (fragment){
            case 0:
                //Home
                String work_id = sharedPref.getString("offer_work_id", null);
                if(work_id != null && text_search.getVisibility() == View.VISIBLE && activate_fab){
                    fab.setVisibility(View.VISIBLE);
                }
                transaction.replace(R.id.container_map, dummy)
                        .disallowAddToBackStack().commit();
                container_map.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                mapView.setVisibility(View.VISIBLE);
                break;
            case 1:
                //Search
                fab.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                container_map.setVisibility(View.VISIBLE);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container_map, search)
                        .disallowAddToBackStack().commit();
                break;
            case 2:
                //Search Av
                fab.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                container_map.setVisibility(View.VISIBLE);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container_map, search_av)
                        .disallowAddToBackStack().commit();
                break;
            case 3:
                //Request
                fab.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                container_map.setVisibility(View.VISIBLE);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container_map, request)
                        .disallowAddToBackStack().commit();
                break;
            case 4:
                fab.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                container_map.setVisibility(View.VISIBLE);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container_map, add_offer_job)
                        .disallowAddToBackStack().commit();
                break;
            case 5:
                fab.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                container_map.setVisibility(View.VISIBLE);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container_map, add_offer_job2)
                        .disallowAddToBackStack().commit();
                break;
            case 6:
                fab.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                container_map.setVisibility(View.VISIBLE);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container_map, postular)
                        .disallowAddToBackStack().commit();
                break;
            case 7:
                fab.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                container_map.setVisibility(View.VISIBLE);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container_map, showProposals)
                        .disallowAddToBackStack().commit();
                break;
        }
    }

    @Override
    public void onCameraIdle() {
        if(last_zoom != mMap.getCameraPosition().zoom){
            Log.d(TAG,"zoom changed");
            last_zoom = mMap.getCameraPosition().zoom;
            if(!is_offer){
                getInitialOffer();
            }else{
                String type = sharedPref.getString("type_user_init",null);
                if(type != null && type.equals("offer")){
                    listWork();
                }else{
                    getInitialOffer();
                }
            }
        }
    }

    @Override
    public void responseDialogSelect(boolean userOrOffer) {
        infoMarker = false;

        wainting.setVisibility(View.VISIBLE);
        if(!is_offer){
            if(userOrOffer){
                content_mode.setVisibility(View.GONE);
                text_mode.setText("Modo Oferente");
                text_search.setVisibility(View.GONE);
                initOfferUser();
            }else{
                content_mode.setVisibility(View.GONE);
                text_mode.setText("Modo Usuario");
                text_search.setVisibility(View.VISIBLE);
                getInitialOffer();
            }
        }else{
            if(userOrOffer){
                content_mode.setVisibility(View.VISIBLE);
                text_mode.setText("Modo Oferente");
                text_search.setVisibility(View.GONE);
                initOfferUser();
            }else{
                content_mode.setVisibility(View.VISIBLE);
                text_mode.setText("Modo Usuario");
                text_search.setVisibility(View.VISIBLE);
                getInitialOffer();
            }
        }

    }

    private void initOfferUser(){
        String servId = sharedPref.getString(getString(R.string.service_id_key),null);
        mMap.clear();
        fab.setVisibility(View.GONE);
        if(servId == null){
            addOffer.setVisibility(View.VISIBLE);
            showOffer.setVisibility(View.GONE);
        }else{
            infoMarker = true;
            showOffer.setVisibility(View.VISIBLE);
            addOffer.setVisibility(View.GONE);
            listWork();
        }
        wainting.setVisibility(View.INVISIBLE);
    }

    public void listWork(){
        mMap.clear();
        final GeneralConstans gc = new GeneralConstans();
        String URL_Request = gc.URL_BASE;
        URL_Request += gc.OFFER_LIST_WORK;
        URL_Request += gc.REQUEST_USER_ID;
        URL_Request += String.valueOf(idUser);
        URL_Request += gc.REQUEST_AND;
        URL_Request += gc.REQUEST_LATITUD;
        URL_Request += String.valueOf(sharedPref.getString(getString(R.string.latitud_key),"0"));
        URL_Request += gc.REQUEST_AND;
        URL_Request += gc.REQUEST_LONGITUD;
        URL_Request += String.valueOf(sharedPref.getString(getString(R.string.longitud_key),"0"));

        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "resp: " + response);
                        try{
                            JSONObject resp = new JSONObject(response);

                            boolean r = resp.getBoolean("success");

                            if(r){
                                JSONArray offers = resp.getJSONArray("offer_jobs");
                                editor.putString("jsonOffer", offers.toString());
                                editor.commit();
                                for (int i = 0; i < offers.length(); i++) {
                                    JSONObject offer = offers.getJSONObject(i);
                                    String title = offer.getString("title");
                                    String description = offer.getString("description");
                                    Double lat = offer.getDouble("latitud");
                                    Double lgn = offer.getDouble("longitud");
                                    addOfferToMap(title, description, lat, lgn);
                                }
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
        queue.add(stringRequest);
    }



    private void initSelect(){
        showOffer.setVisibility(View.GONE);
        addOffer.setVisibility(View.GONE);
        changeUser.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        String type_user = sharedPref.getString("type_user_init", null);

        if(is_offer){
            changeUser.setVisibility(View.VISIBLE);
            if(type_user != null){
                if(type_user.equals("user")){
                    responseDialogSelect(false);
                }else{
                    responseDialogSelect(true);
                }
            }else{
                select.show(getFragmentManager(), "DialogSelect");
            }
        }else{
            responseDialogSelect(false);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exit_map:
                mDrawerLayout.closeDrawer(Gravity.START);
                closeSession();
                break;
            case R.id.change_user:
                mDrawerLayout.closeDrawer(Gravity.START);
                select.show(getFragmentManager(), "DialogSelect");
                break;
            case R.id.add_offer_job:
                mDrawerLayout.closeDrawer(Gravity.START);
                onChangeFragment(4);
                break;
            case R.id.show_offer_job:
                mDrawerLayout.closeDrawer(Gravity.START);
                String cat = sharedPref.getString(getString(R.string.service_name_key)," ");
                String title = sharedPref.getString(getString(R.string.title_job_key), " ");
                String descrip = sharedPref.getString(getString(R.string.description_job_key), " ");
                showOfferdlog.setNameCat(cat);
                showOfferdlog.setTitleServ(title);
                showOfferdlog.setDescServ(descrip);
                showOfferdlog.show(getFragmentManager(),"ShowOfferDialog");
                break;
            case R.id.text_search:
                mDrawerLayout.closeDrawer(Gravity.START);
                onChangeFragment(1);
                break;
            case R.id.fab:
                showDlogResultWork();
                break;
        }
    }

    public void showDlogResultWork(){
        dlogwork.show(getFragmentManager(), "ShowDlogOfferWork");
    }

    public void seekProposal(String offerWorkId){
        final GeneralConstans gc = new GeneralConstans();
        String URL_Request = gc.URL_BASE;
        URL_Request += gc.GET_PROPOSAL;
        URL_Request += gc.REQUEST_OFFER_WORK_ID;
        URL_Request += offerWorkId.trim();

        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "resp: " + response);
                        try{
                            JSONObject resp = new JSONObject(response);

                            boolean r = resp.getBoolean("success");

                            if(r){
                                editor.putString("proposals",resp.getJSONArray("proposals").toString());
                                editor.commit();
                                if(resp.getJSONArray("proposals").length() > 0
                                        && sharedPref.getString("state_id_last","0").equals("3") &&
                                        text_search.getVisibility() == View.VISIBLE && container_map.getVisibility() == View.GONE){
                                    fab.setVisibility(View.VISIBLE);
                                    activate_fab = true;
                                }else{
                                    activate_fab = false;
                                    fab.setVisibility(View.GONE);
                                }
                                Log.d(TAG, "commit: "+ resp.getString("proposals"));
                            }else{
                                fab.setVisibility(View.GONE);
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
        queue.add(stringRequest);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG,"click marker.");
        if(infoMarker){
            Log.d(TAG, "buscar job");
            buscarWork(marker.getPosition().latitude, marker.getPosition().longitude);
        }
    }

    private void buscarWork(Double lat, Double lgn){
        String offer = sharedPref.getString("jsonOffer", "");
        Log.d(TAG, "entre a buscar");
        try {
            Log.d(TAG, "offer: "+ offer);
            JSONArray json = new JSONArray(offer);
            for (int i = 0; i < json.length(); i++){
                if(String.valueOf(lat).equals(json.getJSONObject(i).getString("latitud")) && String.valueOf(lgn).equals(json.getJSONObject(i).getString("longitud"))){
                    String id = json.getJSONObject(i).getString("id");
                    String arr = sharedPref.getString("offer_work_array","[]");
                    JSONArray array = new JSONArray(arr);

                    boolean postular = true;

                    for (int e = 0; e < array.length(); e++){
                        if(id.equals(array.getString(e))){
                            postular = false;
                            break;
                        }
                    }

                    if(postular){
                        dlogPostule.setClientServ(json.getJSONObject(i).getString("clientName"));
                        dlogPostule.setDescServ(json.getJSONObject(i).getString("description"));
                        dlogPostule.setNameCat(json.getJSONObject(i).getString("serviceName"));
                        dlogPostule.setTitleServ(json.getJSONObject(i).getString("title"));
                        dlogPostule.setDistServ(json.getJSONObject(i).getString("distanceAt"));
                        dlogPostule.setId(id);
                        dlogPostule.setJsonImage(json.getJSONObject(i).getString("images"));
                        Log.d(TAG, json.getJSONObject(i).getString("images"));
                        Log.d(TAG," encontrado y abriendo dlog");
                        dlogPostule.show(getFragmentManager(),"DlogPostule");

                    }else{
                        DialogCustom dialog = new DialogCustom();
                        dialog.setText("Ya ha postulado a este trabajo.");
                        dialog.setType(true);
                        dialog.setEnabledGPS(false);
                        dialog.show(getFragmentManager(), TAG);
                    }
                    return;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postulacion(String id) {
        Log.d(TAG, "id postulacion: " + id);
        postular.setWorkId(id);
        onChangeFragment(6);
    }

    @Override
    public void showOfferWork(boolean show) {
        if(show){
            onChangeFragment(7);
        }
    }

    @Override
    public void changeMenu(boolean change) {
        if(change){
            showOffer.setVisibility(View.VISIBLE);
            addOffer.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideFab(boolean hide) {
        if(hide){
            fab.setVisibility(View.GONE);
        }
    }
}
