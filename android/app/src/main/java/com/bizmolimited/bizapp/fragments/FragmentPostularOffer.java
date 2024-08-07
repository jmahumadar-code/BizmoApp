package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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
import com.bizmolimited.bizapp.utils.Country;
import com.bizmolimited.bizapp.utils.DialogCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guillermofuentesquijada on 06-11-17.
 */

public class FragmentPostularOffer extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener{

    private static final String TAG = FragmentPostularOffer.class.getSimpleName();

    private CallbackFragments request;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private View wainting;

    private EditText comentario;
    private TextInputLayout lcomentario;

    private EditText costo;
    private TextInputLayout lcosto;
    private EditText costo_min;
    private TextInputLayout lcosto_min;
    private EditText costo_max;
    private TextInputLayout lcosto_max;

    private Switch switch_costo;
    private View costo_min_max;

    private View cont_request;

    private RequestQueue queue;

    private String workId;

    private Country country;

    private MapsActivity mapsAc;

    private Pattern patternCurrency;
    private Matcher matcherCurrency;

    private boolean is_check;
    private DecimalFormat df;

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest_proposal, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        is_check = false;

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
                sendOfferProposal();
            }
        });

        country = Country.getCountryFromSIM(getActivity().getApplicationContext());

        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }

        df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(country.getSeparatorDecimal());
        symbols.setGroupingSeparator(country.getSeparatorMiles());
        df.setDecimalFormatSymbols(symbols);

        patternCurrency= Pattern.compile(country.getRegexCurrency(), Pattern.CASE_INSENSITIVE);

        Log.d(TAG, "pais: " + country.getCode() + " Signo: " + country.getSignMoney());

        queue = Volley.newRequestQueue(getActivity());

        wainting = view.findViewById(R.id.wainting);

        cont_request = view.findViewById(R.id.cont_request);

        comentario = (EditText) view.findViewById(R.id.comentario);
        lcomentario = (TextInputLayout) view.findViewById(R.id.lcomentario);

        costo = (EditText) view.findViewById(R.id.costo);
        lcosto = (TextInputLayout) view.findViewById(R.id.lcosto);
        costo_min = (EditText) view.findViewById(R.id.costo_min);
        lcosto_min = (TextInputLayout) view.findViewById(R.id.lcosto_min);
        costo_max = (EditText) view.findViewById(R.id.costo_max);
        lcosto_max = (TextInputLayout) view.findViewById(R.id.lcosto_max);

        switch_costo = (Switch) view.findViewById(R.id.switch_costo);
        costo_min_max = view.findViewById(R.id.min_max);

        costo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lcosto.setError(null);
                costo.removeTextChangedListener(this);
                String cost = costo.getText().toString();
                cost = cost.replace(country.getSignMoney(),"");
                Double inter = 0.0;
                if(!cost.trim().equals("")){
                    if(country.getSeparatorMiles() == '.'){
                        cost = cost.trim().replaceAll("\\.","");
                        cost = cost.trim().replaceAll(",",".");
                        inter = Double.parseDouble(cost);
                        cost = df.format(inter);
                    }else{
                        cost = cost.trim().replaceAll(",","");
                        inter = Double.parseDouble(cost);
                        cost = df.format(inter);
                    }
                }

                if(!cost.contains(country.getSignMoney())){
                    cost = country.getSignMoney() + " " + cost.trim();
                }

                costo.setText(cost);
                costo.setSelection(cost.length());
                costo.addTextChangedListener(this);
            }
        });

        costo_min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lcosto_min.setError(null);
                costo_min.removeTextChangedListener(this);
                String cost = costo_min.getText().toString();
                cost = cost.replace(country.getSignMoney(),"");
                Double inter = 0.0;
                if(!cost.trim().equals("")){
                    if(country.getSeparatorMiles() == '.'){
                        cost = cost.trim().replaceAll("\\.","");
                        cost = cost.trim().replaceAll(",",".");
                        inter = Double.parseDouble(cost);
                        cost = df.format(inter);
                    }else{
                        cost = cost.trim().replaceAll(",","");
                        inter = Double.parseDouble(cost);
                        cost = df.format(inter);
                    }
                }
                if(!cost.contains(country.getSignMoney())){
                    cost = country.getSignMoney() + " " + cost.trim();
                }

                costo_min.setText(cost);
                costo_min.setSelection(cost.length());
                costo_min.addTextChangedListener(this);
            }
        });

        costo_max.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lcosto_max.setError(null);
                costo_max.removeTextChangedListener(this);
                String cost = costo_max.getText().toString();
                cost = cost.replace(country.getSignMoney(),"");
                Double inter = 0.0;
                if(!cost.trim().equals("")){
                    if(country.getSeparatorMiles() == '.'){
                        cost = cost.trim().replaceAll("\\.","");
                        cost = cost.trim().replaceAll(",",".");
                        inter = Double.parseDouble(cost);
                        cost = df.format(inter);
                    }else{
                        cost = cost.trim().replaceAll(",","");
                        inter = Double.parseDouble(cost);
                        cost = df.format(inter);
                    }
                }
                if(!cost.contains(country.getSignMoney())){
                    cost = country.getSignMoney() + " " + cost.trim();
                }

                costo_max.setText(cost);
                costo_max.setSelection(cost.length());
                costo_max.addTextChangedListener(this);
            }
        });

        switch_costo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_check = isChecked;
                if(isChecked){
                    lcosto.setVisibility(View.GONE);
                    costo_min_max.setVisibility(View.VISIBLE);
                    costo_min.setText(country.getSignMoney() + " ");
                    costo_max.setText(country.getSignMoney() + " ");
                }else{
                    lcosto.setVisibility(View.VISIBLE);
                    costo_min_max.setVisibility(View.GONE);
                    costo.setText(country.getSignMoney() + " ");
                }
            }
        });

        costo.setText("");
        costo_max.setText("");
        costo_min.setText("");

        back.setTypeface(font);
        back.setText("\uf053");

        back.setOnClickListener(this);

        mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
        mapsAc.getFab1().setEnabled(true);

        try {
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        view.setOnClickListener(this);
        cont_request.setOnClickListener(this);

        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                hideKeyboard();
                costo.setText("");
                costo_max.setText("");
                costo_min.setText("");
                comentario.setText("");
                request.onChangeFragment(0);
                break;
            case R.id.cont_request:
                hideKeyboard();
                break;
        }

    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }

    private void sendOfferProposal(){
        final GeneralConstans gc = new GeneralConstans();
        String url_request = gc.URL_BASE;
        String cost = "";
        String cost_min = "";
        String cost_max = "";
        wainting.setVisibility(View.VISIBLE);
        if(!is_check){
            cost = costo.getText().toString();
            cost = cost.replace(country.getSignMoney(),"").trim();
            matcherCurrency = patternCurrency.matcher(cost);
            boolean pass = matcherCurrency.matches();
            if(!pass){
                wainting.setVisibility(View.GONE);
                lcosto.setError("Debe ingresar el costo de su trabajo");
                return;
            }else{
                if(country.getSeparatorMiles() == ','){
                    cost = cost.replaceAll(",","");
                }else{
                    cost = cost.replaceAll("\\.","");
                    cost = cost.replaceAll(",",".");
                }
            }
        }else{
            cost_min = costo_min.getText().toString();
            cost_min = cost_min.replace(country.getSignMoney(),"").trim();
            matcherCurrency = patternCurrency.matcher(cost_min);
            boolean pass = matcherCurrency.matches();
            if(!pass){
                wainting.setVisibility(View.GONE);
                lcosto_min.setError("Formato no aceptado");
                return;
            }else{
                if(country.getSeparatorMiles() == ','){
                    cost_min = cost_min.replaceAll(",","");
                }else{
                    cost_min = cost_min.replaceAll("\\.","");
                    cost_min = cost_min.replaceAll(",",".");
                }
            }

            cost_max = costo_max.getText().toString();
            cost_max = cost_max.replace(country.getSignMoney(),"").trim();
            matcherCurrency = patternCurrency.matcher(cost_max);
            pass = matcherCurrency.matches();
            if(!pass){
                wainting.setVisibility(View.GONE);
                lcosto_max.setError("Formato no aceptado");
                return;
            }else{
                if(country.getSeparatorMiles() == ','){
                    cost_max = cost_max.replaceAll(",","");
                }else{
                    cost_max = cost_max.replaceAll("\\.","");
                    cost_max = cost_max.replaceAll(",",".");
                }
            }

            double min = Double.parseDouble(cost_min);
            double max = Double.parseDouble(cost_max);
            if(min > max){
                wainting.setVisibility(View.GONE);
                lcosto_min.setError("Este valor debe ser menor al precio mayor");
                return;
            }else{
                lcosto_min.setError(null);
            }
        }

        final String comment = comentario.getText().toString();
        if(comment.trim().equals("") || comment.length() > 144){
            wainting.setVisibility(View.GONE);
            lcomentario.setError("Debe ingresar un comentario de su postulación.");
            return;
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();

        String date = fmtOut.format(now);

        String offer_id = sharedPref.getString(getString(R.string.offer_job_id_key), null);

        if(offer_id != null && workId != null){

            try {
                url_request += gc.PROPOSAL;
                url_request += gc.INSERT_PROPOSAL;
                if(is_check){
                    url_request += gc.REQUEST_TYPE_COST;
                    url_request += gc.COSTO_RANGO;
                    url_request += gc.REQUEST_AND;
                    url_request += gc.REQUEST_COST_MIN;
                    url_request += URLEncoder.encode(cost_min.trim(), "UTF-8").replaceAll("\\+", "%20");
                    url_request += gc.REQUEST_AND;
                    url_request += gc.REQUEST_COST_MAX;
                    url_request += URLEncoder.encode(cost_max.trim(), "UTF-8").replaceAll("\\+", "%20");
                }else{
                    url_request += gc.REQUEST_TYPE_COST;
                    url_request += gc.COSTO_UNICO;
                    url_request += gc.REQUEST_AND;
                    url_request += gc.REQUEST_COST;
                    url_request += URLEncoder.encode(cost.trim(), "UTF-8").replaceAll("\\+", "%20");
                }
                url_request += gc.REQUEST_AND;
                url_request += gc.DATE;
                url_request += URLEncoder.encode(date, "UTF-8").replaceAll("\\+", "%20");
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_COMMENTS;
                url_request += URLEncoder.encode(comment, "UTF-8").replaceAll("\\+", "%20");
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_OFFER_JOB_ID;
                url_request += offer_id;
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_OFFER_WORK_ID;
                url_request += workId;
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
                                if(r){
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
                                    request.onChangeFragment(0);
                                    DialogCustom dialog = new DialogCustom();
                                    dialog.setText("Se ha registrado exitosamente su postulación");
                                    dialog.setType(true);
                                    dialog.show(getFragmentManager(),TAG);
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
                    wainting.setVisibility(View.INVISIBLE);
                    DialogCustom dialog = new DialogCustom();
                    dialog.setType(true);
                    dialog.setText("Ha ocurrido un error, intente nuevamente.");
                    dialog.show(getFragmentManager(),TAG);
                }
            }
            );
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }else{
            wainting.setVisibility(View.INVISIBLE);
            Log.d(TAG, "error else");
            DialogCustom dialog = new DialogCustom();
            dialog.setText("Ha ocurrido un error, intente nuevamente");
            dialog.setType(true);
            dialog.show(getFragmentManager(),TAG);
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

}
