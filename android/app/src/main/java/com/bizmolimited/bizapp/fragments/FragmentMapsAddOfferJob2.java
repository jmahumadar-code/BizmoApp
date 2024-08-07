package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bizmolimited.bizapp.MapsActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.DialogCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by guillermofuentesquijada on 06-11-17.
 */

public class FragmentMapsAddOfferJob2 extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = FragmentMapsAddOfferJob2.class.getSimpleName();

    private CallbackFragments request;
    private CallbackChangeMenu changeMenu;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private EditText descrip_search;

    private View wainting;

    private EditText text;
    private TextInputLayout txtdesc;

    private EditText title;
    private TextInputLayout ltitle;

    private View cont_request;

    private RequestQueue queue;
    private String idCat;
    private String nameCat;

    private MapsActivity mapsAc;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offer_job_2, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));


        Button back = (Button) view.findViewById(R.id.back);
        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        mapsAc = (MapsActivity) getActivity();
        mapsAc.getFab1().setEnabled(false);
        mapsAc.getFab1().setVisibility(View.VISIBLE);
        mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
        mapsAc.getFab1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                sendOfferJob();
            }
        });

        text = (EditText) view.findViewById(R.id.search_text);
        nameCat = sharedPref.getString("name_cat", "");
        idCat = sharedPref.getString("id_cat", "");

        text.setText(nameCat);

        queue = Volley.newRequestQueue(getActivity());

        wainting = view.findViewById(R.id.wainting);
        descrip_search = (EditText) view.findViewById(R.id.description_text);
        txtdesc = (TextInputLayout) view.findViewById(R.id.txt_des);
        cont_request = view.findViewById(R.id.cont_request);

        title = (EditText) view.findViewById(R.id.title);
        ltitle = (TextInputLayout) view.findViewById(R.id.ltitle);


        mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
        mapsAc.getFab1().setEnabled(true);

        back.setTypeface(font);
        back.setText("\uf053");

        back.setOnClickListener(this);

        try {
            request = (CallbackFragments) getActivity();
            changeMenu = (CallbackChangeMenu) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        view.setOnClickListener(this);
        cont_request.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String nameCat = sharedPref.getString("name_cat", "");
        idCat = sharedPref.getString("id_cat", "");
        text.setText(nameCat);
    }

    @Override
    public void onPause() {
        super.onPause();
        text.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                hideKeyboard();
                request.onChangeFragment(4);
                break;
            case R.id.cont_request:
                hideKeyboard();
                break;
        }

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void sendOfferJob() {
        wainting.setVisibility(View.VISIBLE);
        final String titulo = title.getText().toString();
        if (titulo.trim().equals("")) {
            wainting.setVisibility(View.GONE);
            ltitle.setError("Debe ingresar un título para el trabajo");
            return;
        }
        final String descripcion = descrip_search.getText().toString();
        if (descripcion.trim().equals("")) {
            wainting.setVisibility(View.GONE);
            txtdesc.setError("Debe ingresar una descripción para el trabajo");
            return;
        }

        String latitud = sharedPref.getString(getString(R.string.latitud_key), "0");
        String longitud = sharedPref.getString(getString(R.string.longitud_key), "0");
        String id = String.valueOf(sharedPref.getString(getString(R.string.offer_job_id_key), "0"));

        if(id.equals("0")){
            id = String.valueOf(sharedPref.getInt(getString(R.string.id_user_key),0));
        }

        Log.d(TAG, "lat: " + latitud + " lgn: " + longitud);

        if (!latitud.equals("0") && !longitud.equals("0")) {
            final GeneralConstans gc = new GeneralConstans();
            String url_request = gc.URL_BASE;
            try {
                url_request += gc.OFFER_JOB;
                url_request += gc.INSERT_OFFER_JOB;
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_TITLE;
                url_request += URLEncoder.encode(titulo, "UTF-8").replaceAll("\\+","%20");
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_DESCRIPTION;
                url_request += URLEncoder.encode(descripcion, "UTF-8").replaceAll("\\+","%20");
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_LATITUD;
                url_request += latitud.trim();
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_LONGITUD;
                url_request += longitud.trim();
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_SERVICE_ID;
                url_request += idCat.trim();
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_OFFER_ID;
                url_request += id.trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d(TAG, url_request);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_request,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resp = new JSONObject(response);
                                Log.d(TAG, resp.toString());
                                boolean r = resp.getBoolean("success");
                                wainting.setVisibility(View.INVISIBLE);
                                if (r) {
                                    String id = resp.getString("offer_job_id");
                                    editor.putString(getString(R.string.offer_job_id_key),id);
                                    editor.putString(getString(R.string.service_name_key), nameCat);
                                    editor.putString(getString(R.string.description_job_key), descripcion);
                                    editor.putString(getString(R.string.title_job_key), titulo);
                                    editor.putString(getString(R.string.service_id_key), idCat);
                                    editor.commit();
                                    request.onChangeFragment(0);
                                    changeMenu.changeMenu(true);
                                    DialogCustom dialog = new DialogCustom();
                                    dialog.setText("Se ha registrado exitosamente su trabajo");
                                    dialog.setType(true);
                                    dialog.show(getFragmentManager(), TAG);
                                } else {
                                    String info = resp.getString("info");
                                    DialogCustom dialog = new DialogCustom();
                                    dialog.setText(info);
                                    dialog.setType(true);
                                    dialog.show(getFragmentManager(), TAG);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                    wainting.setVisibility(View.INVISIBLE);
                    DialogCustom dialog = new DialogCustom();
                    dialog.setType(true);
                    dialog.setText("Ha ocurrido un error, intente nuevamente.");
                    dialog.show(getFragmentManager(), TAG);
                }
            }
            );
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        } else {
            wainting.setVisibility(View.INVISIBLE);
            Log.d(TAG, "error else");
            DialogCustom dialog = new DialogCustom();
            dialog.setText("Ha ocurrido un error, intente nuevamente");
            dialog.setType(true);
            dialog.show(getFragmentManager(), TAG);
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    public interface CallbackChangeMenu{
        public void changeMenu(boolean change);
    }

}
