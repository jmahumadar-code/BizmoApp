package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bizmolimited.bizapp.MapsActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.utils.AppHelper;
import com.bizmolimited.bizapp.utils.CallbackFragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guillermofuentesquijada on 06-11-17.
 */

public class FragmentMapsAddOfferJob extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = FragmentMapsAddOfferJob.class.getSimpleName();

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String jsonText;

    private View wainting;
    private RadioGroup groups;

    private LinkedHashMap<String, String> list;

    private CallbackFragments request;

    private MapsActivity mapsAc;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offer_job_1, container, false);
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
                request.onChangeFragment(5);
            }
        });


        Button back = (Button) view.findViewById(R.id.back);

        back.setTypeface(font);
        back.setText("\uf053");

        wainting = view.findViewById(R.id.wainting);
        groups = (RadioGroup) view.findViewById(R.id.radio_groups);
        groups.removeAllViews();

        back.setOnClickListener(this);

        list = new LinkedHashMap<>();
        getFullSpinner();

        groups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rd = groups.findViewById(i);
                String name = rd.getText().toString();
                Log.d(TAG, "name: "+ name);
                mapsAc.getFab1().setEnabled(true);
                mapsAc.getFab1().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                registerIdCat(name);
            }
        });

        try {
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFullSpinner();
    }

    @Override
    public void onPause(){
        super.onPause();
        groups.removeAllViews();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                request.onChangeFragment(0);
                break;
        }
    }

    private void registerIdCat(String name){
        String id = list.get(name);
        Log.d(TAG, "id: "+ id);
        editor.putString("id_cat",id);
        editor.putString("name_cat",name);
        editor.commit();
    }

    public void getFullSpinner(){
        list.clear();
        groups.removeAllViews();
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONArray array = json.getJSONArray("categories");

            for (int i = 0; i < array.length(); i++){
                list.put(array.getJSONObject(i).getString("name"), String.valueOf(array.getJSONObject(i).getInt("id")));
                RadioButton rbtn1 = new RadioButton(getActivity());
                rbtn1.setText(array.getJSONObject(i).getString("name"));
                groups.addView(rbtn1);
                JSONArray arrayChild = array.getJSONObject(i).getJSONArray("childrens");
                for (int e = 0; e < arrayChild.length(); e++){
                    list.put(arrayChild.getJSONObject(e).getString("name"), String.valueOf(arrayChild.getJSONObject(e).getInt("id")));
                    RadioButton rbtn2 = new RadioButton(getActivity());
                    rbtn2.setText(arrayChild.getJSONObject(e).getString("name"));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(AppHelper.convertDpToPixel(30,getActivity()),0,0,0);
                    rbtn2.setLayoutParams(params);
                    groups.addView(rbtn2);
                    JSONArray arrayChild1 = arrayChild.getJSONObject(e).getJSONArray("childrens");
                    for (int f = 0; f < arrayChild1.length(); f++){
                        list.put(arrayChild1.getJSONObject(f).getString("name"), String.valueOf(arrayChild1.getJSONObject(f).getInt("id")));
                        RadioButton rbtn3 = new RadioButton(getActivity());
                        rbtn3.setText(arrayChild1.getJSONObject(f).getString("name"));
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        params1.setMargins(AppHelper.convertDpToPixel(60,getActivity()),0,0,0);
                        rbtn3.setLayoutParams(params1);
                        groups.addView(rbtn3);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}
