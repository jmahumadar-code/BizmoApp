package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.LoginActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guillermofuentesquijada on 04-10-17.
 */

public class FragmentLoginPhone extends Fragment implements View.OnClickListener,TextView.OnEditorActionListener{

    private static final String TAG = FragmentLoginPhone.class.getSimpleName();
    private View root;
    private ImageView flag;
    //private TextView codeCountry;
    private EditText emailText;
    private TextView textIntro;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private Pattern patternPhone;
    private Matcher matcher;

    private CallbackFragments request;
    private Button btn_back;

    private LoginActivity login;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login_phone, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        Country country = Country.getCountryFromSIM(getActivity().getApplicationContext());
        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }

        login = (LoginActivity) getActivity();
        login.getFab().setEnabled(false);
        login.getFab().setVisibility(View.VISIBLE);
        login.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click fab in login");
                editor.putString(getString(R.string.email_key), emailText.getText().toString());
                editor.commit();
                request.onChangeFragment(3);
            }
        });

        //login.getFab().setBackgroundColor(getResources().getColor(R.color.DimGray));
        login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));

        String regexEmail = getString(R.string.regexEmail);

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        patternPhone = Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE);

        btn_back = (Button) root.findViewById(R.id.backLoginInit);
        btn_back.setTypeface(font);
        btn_back.setText("\uf053");

        flag = (ImageView) root.findViewById(R.id.flagcountry);
        emailText = (EditText) root.findViewById(R.id.email_login);
        textIntro = (TextView) root.findViewById(R.id.textintro);


        textIntro.setText(R.string.label_login_phone);

        emailText.setTextColor(getResources().getColor(R.color.DimGray));

        Drawable flagIMG = getResources().getDrawable(country.getFlag());

        flag.setImageDrawable(flagIMG);

        try{
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        btn_back.setOnClickListener(this);


        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                matcher = patternPhone.matcher(emailText.getText().toString());
                if(matcher.matches()){
                    login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                    login.getFab().setEnabled(true);
                }else {
                    login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
                    login.getFab().setEnabled(false);
                }
            }
        });

        if(sharedPref.getBoolean("cambiar",false)){
            emailText.setText(sharedPref.getString(getString(R.string.phone_key), " "));
            login.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
            login.getFab().setEnabled(true);
            editor.remove("cambiar");
            editor.commit();
        }

        emailText.setOnEditorActionListener(this);
        emailText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);

        return root;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backLoginInit:
                request.onChangeFragment(1);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handler = false;
        matcher = patternPhone.matcher(emailText.getText().toString());
        if(i == EditorInfo.IME_ACTION_GO && matcher.matches()){
            editor.putString(getString(R.string.email_key), emailText.getText().toString());
            editor.commit();
            request.onChangeFragment(3);
            handler = true;
        }else{
            emailText.requestFocus();
            handler = true;
        }
        return handler;
    }
}
