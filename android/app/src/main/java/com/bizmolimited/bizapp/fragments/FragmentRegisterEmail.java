package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bizmolimited.bizapp.LoginActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.RegisterActivity;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guillermofuentesquijada on 09-10-17.
 */

public class FragmentRegisterEmail extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener{

    private static final String TAG = FragmentRegisterEmail.class.getSimpleName();

    private CallbackFragments request;

    private Pattern pattern;
    private Matcher matcher;

    private View root;
    private Button back;
    private EditText phone;
    private TextInputLayout lphone;
    private ImageView flag;
    private TextView codeCountry;

    private View waiting;

    private RequestQueue queue;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private RegisterActivity register;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register_email, container, false);

        Country country = Country.getCountryFromSIM(getActivity());
        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }
        String regex = country.getRegexPhone();

        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        register = (RegisterActivity) getActivity();
        register.getFab().setVisibility(View.VISIBLE);
        register.getFab().setEnabled(false);
        register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
        register.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(getString(R.string.phone_key), phone.getText().toString());
                editor.commit();
                request.onChangeFragment(3);
            }
        });

        waiting = (View) root.findViewById(R.id.wainting);
        flag = (ImageView) root.findViewById(R.id.flagcountry);
        codeCountry = (TextView) root.findViewById(R.id.codePhone);

        queue = Volley.newRequestQueue(getActivity());

        editor.remove("cambiar");
        editor.commit();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        Drawable flagIMG = getResources().getDrawable(country.getFlag());

        flag.setImageDrawable(flagIMG);
        codeCountry.setText(country.getDialCode());

        phone = (EditText) root.findViewById(R.id.num);
        lphone = (TextInputLayout) root.findViewById(R.id.lnum);
        back = (Button) root.findViewById(R.id.backLogin1);

        back.setTypeface(font);
        back.setText("\uf053");


        back.setOnClickListener(this);

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                matcher = pattern.matcher(phone.getText().toString());
                lphone.setError(null);
                if(matcher.matches()){
                    register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                    register.getFab().setEnabled(true);
                }else{
                    register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));
                    register.getFab().setEnabled(false);
                }
            }
        });

        try {
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        phone.setOnEditorActionListener(this);

        phone.requestFocus();
        
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backLogin1:
                Intent main = new Intent(getActivity(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                editor.putBoolean("cambiar",true);
                editor.commit();
                getActivity().startActivity(main);
                getActivity().finish();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handler = false;
        matcher = pattern.matcher(phone.getText().toString());
        phone.setError(null);
        if(i == EditorInfo.IME_ACTION_GO && matcher.matches()){
            editor.putString(getString(R.string.phone_key), phone.getText().toString());
            editor.commit();
            request.onChangeFragment(3);
            handler = true;
        }else{
            phone.requestFocus();
            handler = true;
        }
        return handler;
    }

}