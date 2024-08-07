package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bizmolimited.bizapp.MapsActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.utils.AdapterItem;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;
import com.bizmolimited.bizapp.utils.Proposal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

/**
 * Created by guillermofuentesquijada on 19-11-17.
 */

public class FragmentProposals extends Fragment implements View.OnClickListener{

    private static final String TAG = FragmentLoginPhone.class.getSimpleName();
    private View root;
    private Button btn_back;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private ListView list;
    private Country country;

    private CallbackFragments request;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_proposals, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        btn_back = (Button) root.findViewById(R.id.back);
        btn_back.setTypeface(font);
        btn_back.setText("\uf053");
        btn_back.setOnClickListener(this);


        country = Country.getCountryFromSIM(getActivity().getApplicationContext());

        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }

        Log.d(TAG, "pais: " + country.getCode() + " Signo: " + country.getSignMoney());

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        list = (ListView) root.findViewById(R.id.listProposal);
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();

        Log.d(TAG,"pro comm: "+ sharedPref.getString("proposals",""));

        try {
            JSONArray array = new JSONArray(sharedPref.getString("proposals",""));

            for(int i = 0; i < array.length(); i++){
                JSONObject json = array.getJSONObject(i);
                if(json.getBoolean("costType")){
                    proposals.add(new Proposal(json.getString("id"), json.getBoolean("costType"), json.getString("costMin"), json.getString("costMax"),
                            json.getString("comments"), json.getString("fecha"), json.getString("offerWorkId"),
                            json.getString("offerWorkLatitud"), json.getString("offerWorkLongitud"), json.getString("offerJobId"),
                            json.getString("offerJobName"), json.getString("offerName"), json.getString("offerJobLatitud"),
                            json.getString("offerJobLongitud"), json.getString("distanceAt")));
                }else{
                    proposals.add(new Proposal(json.getString("id"), json.getBoolean("costType"), json.getString("cost"),
                            json.getString("comments"), json.getString("fecha"), json.getString("offerWorkId"),
                            json.getString("offerWorkLatitud"), json.getString("offerWorkLongitud"), json.getString("offerJobId"),
                            json.getString("offerJobName"), json.getString("offerName"), json.getString("offerJobLatitud"),
                            json.getString("offerJobLongitud"), json.getString("distanceAt")));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        AdapterItem adapter = new AdapterItem(getActivity(), proposals);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Log.d(TAG, " click en: " + pos);

            }
        });

        try {
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        list = (ListView) root.findViewById(R.id.listProposal);
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();

        Log.d(TAG,"pro comm: "+ sharedPref.getString("proposals",""));

        try {
            JSONArray array = new JSONArray(sharedPref.getString("proposals",""));

            for(int i = 0; i < array.length(); i++){
                JSONObject json = array.getJSONObject(i);
                if(json.getBoolean("costType")){
                    proposals.add(new Proposal(json.getString("id"), json.getBoolean("costType"), json.getString("costMin"), json.getString("costMax"),
                            json.getString("comments"), json.getString("fecha"), json.getString("offerWorkId"),
                            json.getString("offerWorkLatitud"), json.getString("offerWorkLongitud"), json.getString("offerJobId"),
                            json.getString("offerJobName"), json.getString("offerName"), json.getString("offerJobLatitud"),
                            json.getString("offerJobLongitud"), json.getString("distanceAt")));
                }else{
                    proposals.add(new Proposal(json.getString("id"), json.getBoolean("costType"), json.getString("cost"),
                            json.getString("comments"), json.getString("fecha"), json.getString("offerWorkId"),
                            json.getString("offerWorkLatitud"), json.getString("offerWorkLongitud"), json.getString("offerJobId"),
                            json.getString("offerJobName"), json.getString("offerName"), json.getString("offerJobLatitud"),
                            json.getString("offerJobLongitud"), json.getString("distanceAt")));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        AdapterItem adapter = new AdapterItem(getActivity(), proposals);

        list.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                request.onChangeFragment(0);
                break;
        }
    }

}
