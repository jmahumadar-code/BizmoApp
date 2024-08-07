package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guillermofuentesquijada on 05-11-17.
 */

public class FragmentMapsSearch extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener{

    public static final String TAG = FragmentMapsSearch.class.getSimpleName();

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String jsonText;

    private EditText text_search;
    private View search_pro;
    private CallbackFragments request;
    private LinkedHashMap<String, String> list;
    private View wainting;
    private RadioGroup groups;
    private TextView no_result;
    private View view;

    private RequestQueue queue;

    private Pattern pattern;
    private Matcher matcher;

    private MapsActivity mapsAc;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps_search, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        jsonText = sharedPref.getString(getString(R.string.category_key),"");

        mapsAc = (MapsActivity) getActivity();
        mapsAc.getFab1().setEnabled(false);
        mapsAc.getFab1().setVisibility(View.VISIBLE);
        mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
        mapsAc.getFab1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                request.onChangeFragment(3);
            }
        });

        queue = Volley.newRequestQueue(getActivity());

        Button back = (Button) view.findViewById(R.id.back);
        text_search = (EditText) view.findViewById(R.id.search_text);
        search_pro = view.findViewById(R.id.init_search_pro);
        wainting = view.findViewById(R.id.wainting);
        groups = (RadioGroup) view.findViewById(R.id.radio_groups);
        no_result = (TextView) view.findViewById(R.id.no_result);

        search_pro.setOnClickListener(this);

        String regex = getString(R.string.regexTextSearch);

        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        list = new LinkedHashMap<>();

        try {
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        back.setTypeface(font);
        back.setText("\uf053");

        back.setOnClickListener(this);




        groups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rd = groups.findViewById(i);
                String name = rd.getText().toString();
                Log.d(TAG, "name: "+ name);
                registerIdCat(name);
                mapsAc.getFab1().setEnabled(true);
                mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));

            }
        });
        text_search.setOnEditorActionListener(this);
        text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = text_search.getText().toString();
                matcher = pattern.matcher(text);
                if(matcher.matches()){
                    searchByTagCategories();
                }else{
                    if(text.length() == 0){
                        groups.removeAllViews();
                        no_result.setVisibility(View.GONE);
                        mapsAc.getFab1().setEnabled(false);
                        mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
                    }
                }

            }
        });

        view.setOnClickListener(this);

        return view;
    }

    private void registerIdCat(String name){
        String id = list.get(name);
        Log.d(TAG, "id: "+ id);
        editor.putString("id_cat",id);
        editor.putString("name_cat",name);
        editor.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "resumed");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "pause");
        text_search.setText("");
        groups.removeAllViews();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                hideKeyboard();
                request.onChangeFragment(0);
                break;
            case R.id.init_search_pro:
                hideKeyboard();
                request.onChangeFragment(2);
                break;
            default:
                hideKeyboard();
                break;
        }
    }

    public void searchByTagCategories(){
        wainting.setVisibility(View.VISIBLE);
        String text = text_search.getText().toString();
        GeneralConstans gc = new GeneralConstans();
        String URL_Request = gc.URL_BASE;
        URL_Request += gc.CAT;
        URL_Request += gc.FIND_BY_TAG;
        URL_Request += gc.REQUEST_TAG;
        URL_Request += text.replaceAll("\\s$", "").replace(" ","%20");
        Log.d(TAG, URL_Request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "resp: " + response);
                        try {
                            JSONObject jsonOffer = new JSONObject(response);
                            JSONArray array = jsonOffer.getJSONArray("categories");

                            list.clear();
                            groups.removeAllViews();
                            no_result.setVisibility(View.GONE);
                            mapsAc.getFab1().setEnabled(false);
                            mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));

                            for (int i = 0; i < array.length(); i++){
                                list.put(array.getJSONObject(i).getString("name"), String.valueOf(array.getJSONObject(i).getInt("id")));
                                RadioButton rbutton = new RadioButton(getActivity());
                                rbutton.setText(array.getJSONObject(i).getString("name"));

                                groups.addView(rbutton);
                                if(array.length() == 1){
                                    rbutton.setChecked(true);
                                }
                            }

                            if (array.length() < 1){
                                no_result.setVisibility(View.VISIBLE);
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
                wainting.setVisibility(View.INVISIBLE);
                DialogCustom dialog = new DialogCustom();
                dialog.setType(true);
                dialog.setText("Ha ocurrido un error error al realizar la búsqueda, intente nuevamente.");
                dialog.show(getFragmentManager(),TAG);
            }
        }
        );
        queue.add(stringRequest);

    }



    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handler = false;
        String text = text_search.getText().toString();
        matcher = pattern.matcher(text);
        if(matcher.matches() && i == EditorInfo.IME_ACTION_GO){
            handler = true;
            hideKeyboard();
            searchByTagCategories();
        }
        return handler;
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }
}
