package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;
import com.bizmolimited.bizapp.utils.DialogCustom;

/**
 * Created by guillermofuentesquijada on 04-10-17.
 */

public class FragmentLoginInit extends Fragment implements View.OnClickListener {

    private static final String TAG = FragmentLoginInit.class.getSimpleName();

    private CallbackFragments request;

    private View root;
    private ImageView flag;
    //private TextView codeCountry;
    private EditText phoneNumber;
    private TextView labelRRSS;
    private Country country;
    private View error;
    private View success;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login_init, container, false);
        error = root.findViewById(R.id.error_container);
        success = root.findViewById(R.id.success_container);

        country = Country.getCountryFromSIM(getActivity().getApplicationContext());
        if(country != null) {
            success.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
            if (country.getCode().equals("US")) {
                country = Country.getCountryByName("Chile");
            }

            flag = (ImageView) root.findViewById(R.id.flagcountry);
            //codeCountry = (TextView) root.findViewById(R.id.codePhone);
            phoneNumber = (EditText) root.findViewById(R.id.num);

            labelRRSS = (TextView) root.findViewById(R.id.btn_rrss);
            labelRRSS.setOnClickListener(this);

            phoneNumber.setInputType(InputType.TYPE_NULL);
            phoneNumber.setOnClickListener(this);
            phoneNumber.requestFocus();

            Drawable flagIMG = getResources().getDrawable(country.getFlag());

            flag.setImageDrawable(flagIMG);
            //codeCountry.setText(country.getDialCode());

            try {
                request = (CallbackFragments) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
            }
            Log.d(TAG, "hide");
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
        }else{
            success.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
        }
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.num:
                request.onChangeFragment(2);
                break;
            case R.id.btn_rrss:
                request.onChangeFragment(4);
                break;
        }
    }

}
