package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.LoginActivity;
import com.bizmolimited.bizapp.MapsActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.constants.GeneralConstans;
import com.bizmolimited.bizapp.utils.AppHelper;
import com.bizmolimited.bizapp.utils.CallbackDateTime;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.DatePickerFragment;
import com.bizmolimited.bizapp.utils.DialogCustom;
import com.bizmolimited.bizapp.utils.DialogImage;
import com.bizmolimited.bizapp.utils.TimePickerFragment;
import com.bizmolimited.bizapp.utils.VolleyMultipartRequest;
import com.bizmolimited.bizapp.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by guillermofuentesquijada on 06-11-17.
 */

public class FragmentMapsRequest extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener, CallbackDateTime {

    private static final String TAG = FragmentMapsRequest.class.getSimpleName();

    private CallbackFragments request;
    private CallbackFAB hideFAB;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private int cantidad_foto;
    private int cantidad_fotos_actual = 0;
    private Button add_photo;
    private EditText descrip_search;

    private ArrayList<ImageView> listaIMG;
    private ArrayList<String> listPathIMG;
    private LinearLayout container_photo;
    private View wainting;

    private EditText text;
    private TextInputLayout txtdesc;

    private EditText title;
    private TextInputLayout ltitle;

    private View cont_request;
    private View datetime;
    private RadioGroup group;

    private EditText date;
    private EditText time;
    private TextInputLayout ldate;
    private TextInputLayout ltime;

    private RequestQueue queue;
    private String idCat;

    private TimePickerFragment pickerTime;
    private DatePickerFragment pickerDate;

    private MapsActivity mapsAc;
    private boolean detach;

    private String mCurrentPhotoPath;

    public FragmentMapsRequest(){
        detach = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps_request, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        listaIMG = new ArrayList<>();
        listPathIMG = new ArrayList<>();

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
                sendJob();
            }
        });


        text = (EditText) view.findViewById(R.id.search_text);
        String nameCat = sharedPref.getString("name_cat","");

        text.setText(nameCat);

        queue = Volley.newRequestQueue(getActivity());

        String cant = sharedPref.getString(getString(R.string.offer_work_image), GeneralConstans.MAX_IMG_SEARCH);

        cantidad_foto = Integer.parseInt(cant);

        pickerDate = new DatePickerFragment();
        pickerTime = new TimePickerFragment();

        pickerTime.setCallaback(this);
        pickerDate.setCallaback(this);

        add_photo = (Button) view.findViewById(R.id.add_photo);
        wainting = view.findViewById(R.id.wainting);
        container_photo = (LinearLayout) view.findViewById(R.id.container_photo);
        descrip_search = (EditText) view.findViewById(R.id.description_text);
        txtdesc = (TextInputLayout) view.findViewById(R.id.txt_des);
        cont_request = view.findViewById(R.id.cont_request);
        datetime = view.findViewById(R.id.datetime);
        group = (RadioGroup) view.findViewById(R.id.group_solicitud);

        title = (EditText) view.findViewById(R.id.title);
        ltitle = (TextInputLayout) view.findViewById(R.id.ltitle);

        date = (EditText) view.findViewById(R.id.datetxt);
        time = (EditText) view.findViewById(R.id.timetxt);
        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);
        ldate = (TextInputLayout) view.findViewById(R.id.ldatetxt);
        ltime = (TextInputLayout) view.findViewById(R.id.ltimetxt);

        date.setOnClickListener(this);
        time.setOnClickListener(this);

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickerDate.show(getFragmentManager(),"PickerDate");
                }
            }
        });

        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickerTime.show(getFragmentManager(),"PickerTime");
                }
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);
                switch (rb.getId()){
                    case R.id.programar:
                        datetime.setVisibility(View.VISIBLE);
                        break;
                    case R.id.inmediato:
                        datetime.setVisibility(View.GONE);
                        break;
                }
            }
        });

        back.setTypeface(font);
        back.setText("\uf053");
        add_photo.setTypeface(font);
        add_photo.setText("\uf030");

        back.setOnClickListener(this);
        add_photo.setOnClickListener(this);

        descrip_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                text.setError(null);
                int cant_char = descrip_search.getText().length();
                int queda = 144 - cant_char;
                if(queda < 0){
                    txtdesc.setError("Máximo 144 caracteres");
                }

            }
        });



        try {
            request = (CallbackFragments) getActivity();
            hideFAB = (CallbackFAB) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        view.setOnClickListener(this);
        cont_request.setOnClickListener(this);

        text.setText("");
        date.setText("");
        time.setText("");
        descrip_search.setText("");
        title.setText("");
        idCat = sharedPref.getString("id_cat","");
        text.setText(nameCat);
        Log.d(TAG, "create con cat: "+ nameCat);

        mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
        mapsAc.getFab1().setEnabled(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String nameCat = sharedPref.getString("name_cat","");
        idCat = sharedPref.getString("id_cat","");
        text.setText(nameCat);
        if(detach){
            Log.d(TAG,"detach");
            date.setText("");
            time.setText("");
            descrip_search.setText("");
            title.setText("");
            cantidad_fotos_actual = 0;
            detach = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        detach = true;
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = AppHelper.customPhotoSize(mCurrentPhotoPath, (float)0.3);
            galleryAddPic(mCurrentPhotoPath);
            Log.d(TAG,"foto ok");
            cantidad_fotos_actual++;
            createImageView(imageBitmap);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void createImageView(Bitmap bitmap){
        ImageView image = new ImageView(getActivity());
        image.setLayoutParams(new android.view.ViewGroup.LayoutParams(AppHelper.convertDpToPixel(70, getActivity()),AppHelper.convertDpToPixel(70, getActivity())));
        image.setPadding(10,10,10,10);
        image.setImageBitmap(bitmap);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView _this = (ImageView) view;
                DialogImage dlog = new DialogImage();
                String path = mCurrentPhotoPath;
                dlog.setImage(AppHelper.customPhotoSize(path));
                dlog.show(getFragmentManager(),TAG);
            }
        });
        listaIMG.add(image);
        listPathIMG.add(mCurrentPhotoPath);
        container_photo.addView(image,0);
        if (cantidad_fotos_actual == cantidad_foto){
            add_photo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_photo:
                hideKeyboard();
                dispatchTakePictureIntent();
                break;
            case R.id.back:
                hideKeyboard();
                request.onChangeFragment(1);
                text.setText("");
                date.setText("");
                time.setText("");
                descrip_search.setText("");
                title.setText("");
                cantidad_fotos_actual = 0;
                break;
            case R.id.cont_request:
                hideKeyboard();
                break;
            case R.id.datetxt:
                Log.d(TAG, "click pickerdate");
                pickerDate.show(getFragmentManager(),"PickerDate");
                break;
            case R.id.timetxt:
                Log.d(TAG, "click pickertime");
                pickerTime.show(getFragmentManager(), "PickerTime");
                break;
        }

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG,"Error create file");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.bizmolimited.bizapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void galleryAddPic(String path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }

    private void sendJob(){
        wainting.setVisibility(View.VISIBLE);
        String titulo = title.getText().toString();
        if(titulo.trim().equals("")){
            wainting.setVisibility(View.GONE);
            ltitle.setError("Debe ingresar un título para el trabajo");
            return;
        }
        String descripcion = descrip_search.getText().toString();
        if(descripcion.trim().equals("")){
            wainting.setVisibility(View.GONE);
            txtdesc.setError("Debe ingresar una descripción para el trabajo");
            return;
        }
        boolean now = false, progra = false;
        if(group.getCheckedRadioButtonId() == R.id.programar){
            progra = true;
        }else{
            now = true;
        }
        String fecha = date.getText().toString();
        if(fecha.trim().equals("") && progra){
            wainting.setVisibility(View.GONE);
            ldate.setError("Debe ingresar la fecha en la cual requiere el trabajo");
            return;
        }else{
            if(!fecha.trim().equals("") && progra){
                Log.d(TAG, "fecha = " + fecha);
                fecha = fecha.split("-")[2] + "-" + fecha.split("-")[1] + "-" + fecha.split("-")[0];
            }
        }
        String hora = time.getText().toString();
        if(hora.trim().equals("") && progra){
            wainting.setVisibility(View.GONE);
            ltime.setError("Debe ingresar la hora en la cual requiere el trabajo");
            return;
        }else{
            if(!hora.trim().equals("") && progra){
                hora = hora + ":00";
                Log.d(TAG, "hora = " + hora);
            }
        }

        String tipo = (now) ? "1" : "2";
        String latitud = sharedPref.getString(getString(R.string.latitud_key),"0");
        String longitud = sharedPref.getString(getString(R.string.longitud_key), "0");
        String user_id = String.valueOf(sharedPref.getInt(getString(R.string.id_user_key), 0));

        if(!latitud.equals("0") && !longitud.equals("0")){
            final GeneralConstans gc = new GeneralConstans();
            /*

            String url_request = gc.URL_BASE;
            try {

                url_request += gc.OFFER_WORK;
                url_request += gc.INSERT_OFFER_WORK;
                url_request += gc.REQUEST_USER_ID;
                url_request += user_id.trim();
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_TITLE;
                url_request += URLEncoder.encode(titulo, "UTF-8").replaceAll("\\+", "%20");
                url_request += gc.REQUEST_AND;
                url_request += gc.REQUEST_DESCRIPTION;
                url_request += URLEncoder.encode(descripcion, "UTF-8").replaceAll("\\+", "%20");
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
                url_request += gc.REQUEST_TYPE;
                url_request += tipo.trim();
                if (progra) {
                    url_request += gc.REQUEST_AND;
                    url_request += gc.REQUEST_WORK_DATE;
                    url_request += fecha.trim() + "%20" + hora.trim();
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            */
            String fullURL = gc.URL_BASE + gc.OFFER_WORK + gc.INSERT_OFFER_JOB2;
            if(progra){
                String fulldate = fecha.trim() + " " + hora.trim();
                sendImageJob(fullURL, user_id.trim(), titulo,
                        descripcion,latitud,longitud,idCat.trim(),
                        tipo.trim(), fulldate);
            }else{
                sendImageJob(fullURL, user_id.trim(), titulo,
                        descripcion,latitud,longitud,idCat.trim(),
                        tipo.trim(), null);
            }

            /*
            Log.d(TAG, url_request);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_request,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resp = new JSONObject(response);
                                Log.d(TAG, resp.toString());
                                String info = "";
                                String idwork = "";
                                boolean r = resp.getBoolean("success");
                                wainting.setVisibility(View.INVISIBLE);
                                if(r){
                                    editor.putString("offer_work_id", resp.getString("offer_work_id"));
                                    editor.commit();
                                    request.onChangeFragment(0);
                                    hideFAB.hideFab(true);
                                    DialogCustom dialog = new DialogCustom();
                                    dialog.setText("Se ha enviado su petición exitosamente, en 10 minutos se le notificará vía email los oferentes que han aceptado su solicitud.");
                                    dialog.setType(true);
                                    dialog.show(getFragmentManager(),TAG);
                                }else{
                                    info = resp.getString("info");
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
            */
        }else{
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

    @Override
    public void newDateTime(String result, int dateOrTime) {
        switch (dateOrTime){
            case 0:
                //Date
                date.setText(result);
                break;
            case 1:
                //Time
                time.setText(result);
                break;
        }
    }

    public interface CallbackFAB{
        public void hideFab(boolean hide);
    }

    private void sendImageJob(String FullURL, final String user_id, final String titulo, final String descripcion, final String latitud, final String longitud, final String id_cat, final String tipo, final String fecha) {
        String url = FullURL;
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject resp = new JSONObject(resultResponse);
                    Log.d(TAG, resp.toString());
                    String info = "";
                    String idwork = "";
                    boolean r = resp.getBoolean("success");
                    wainting.setVisibility(View.INVISIBLE);
                    if(r){
                        editor.putString("offer_work_id", resp.getString("offer_work_id"));
                        editor.commit();
                        request.onChangeFragment(0);
                        hideFAB.hideFab(true);
                        DialogCustom dialog = new DialogCustom();
                        dialog.setText("Se ha enviado su petición exitosamente, en 10 minutos se le notificará vía email los oferentes que han aceptado su solicitud.");
                        dialog.setType(true);
                        dialog.show(getFragmentManager(),TAG);
                    }else{
                        info = resp.getString("info");
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
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Log.i("Error", errorMessage);
                wainting.setVisibility(View.INVISIBLE);
                DialogCustom dialog = new DialogCustom();
                dialog.setType(true);
                dialog.setText("Ha ocurrido un error, intente nuevamente. Error: " + errorMessage);
                dialog.show(getFragmentManager(),TAG);
                //error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("title", titulo);
                params.put("description", descripcion);
                params.put("latitud", latitud);
                params.put("longitud", longitud);
                params.put("service_id", id_cat);
                params.put("type", tipo);
                if(tipo.equals("2")){
                    params.put("work_date", fecha);
                }
                Log.d(TAG, params.toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                String name, mime, filename;

                for (int i = 0; i < listPathIMG.size(); i++){
                    name = "image" + (i+1);
                    filename = "image" + (i+1) + ".jpg";
                    mime = "image/jpeg";
                    params.put(name, new DataPart(filename, AppHelper.getFileDataFromDrawable(getActivity().getBaseContext(), AppHelper.customPhotoSize(listPathIMG.get(i)),40), mime));
                    Log.d(TAG,filename);
                }
                return params;
            }
        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity().getBaseContext()).addToRequestQueue(multipartRequest);
    }
}
