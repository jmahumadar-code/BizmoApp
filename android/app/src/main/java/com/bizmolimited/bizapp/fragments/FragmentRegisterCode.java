package com.bizmolimited.bizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.bizmolimited.bizapp.LoginActivity;
import com.bizmolimited.bizapp.R;
import com.bizmolimited.bizapp.RegisterActivity;
import com.bizmolimited.bizapp.utils.CallbackFragments;
import com.bizmolimited.bizapp.utils.Country;

/**
 * Created by guillermofuentesquijada on 08-10-17.
 */

public class FragmentRegisterCode extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = FragmentRegisterCode.class.getSimpleName();

    private CallbackFragments request;

    private View root;
    private EditText cod1;
    private EditText cod2;
    private EditText cod3;
    private EditText cod4;
    private TextView intro;
    private Button back;

    private boolean special;

    private String codeEm;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private RegisterActivity register;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register_code, container, false);

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        intro = (TextView) root.findViewById(R.id.textintroregcod);

        special = sharedPref.getBoolean(getString(R.string.special_reg),false);

        register = (RegisterActivity) getActivity();
        register.getFab().setVisibility(View.VISIBLE);
        register.getFab().setEnabled(false);
        register.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = cod1.getText().toString()+cod2.getText().toString()+cod3.getText().toString()+cod4.getText().toString();
                cod1.setError(null);
                cod2.setError(null);
                cod3.setError(null);
                cod4.setError(null);
                if(code.equals(codeEm)){
                    register.getFab().setEnabled(true);
                    register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                    if(special){
                        request.onChangeFragment(3);

                    }else{
                        request.onChangeFragment(2);

                    }
                    request.onChangeFragment(3);
                }else{
                    cod1.setError(getString(R.string.error_code));
                    cod2.setError(getString(R.string.error_code));
                    cod3.setError(getString(R.string.error_code));
                    cod4.setError(getString(R.string.error_code));
                    cod4.requestFocus();
                }
            }
        });

        register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DimGray)));

        String emaillabel = "";

        if(special){
            emaillabel = sharedPref.getString(getString(R.string.email_special),"example1@bizmobiz.com");
        }else{
            emaillabel = sharedPref.getString(getString(R.string.email_key),"example@bizmobiz.com");
        }

        intro.setText(getString(R.string.label_code)+" "+emaillabel);

        cod1 = (EditText) root.findViewById(R.id.cod1);
        cod2 = (EditText) root.findViewById(R.id.cod2);
        cod3 = (EditText) root.findViewById(R.id.cod3);
        cod4 = (EditText) root.findViewById(R.id.cod4);

        back = (Button) root.findViewById(R.id.backLogin);
        back.setOnClickListener(this);

        back.setTypeface(font);
        back.setText("\uf053");


        codeEm = sharedPref.getString(getString(R.string.verify_code),"1234");



        cod1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cod1.setError(null);
                cod2.setError(null);
                cod3.setError(null);
                cod4.setError(null);
                if(cod1.getText().length() == 1){
                    cod2.requestFocus();
                }
            }
        });

        cod2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cod1.setError(null);
                cod2.setError(null);
                cod3.setError(null);
                cod4.setError(null);
                if(cod2.getText().length() == 1){
                    cod3.requestFocus();
                }
            }
        });

        cod3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cod1.setError(null);
                cod2.setError(null);
                cod3.setError(null);
                cod4.setError(null);
                if(cod3.getText().length() == 1){
                    cod4.requestFocus();
                }
            }
        });

        cod4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String code = cod1.getText().toString()+cod2.getText().toString()+cod3.getText().toString()+cod4.getText().toString();
                cod1.setError(null);
                cod2.setError(null);
                cod3.setError(null);
                cod4.setError(null);
                if(code.equals(codeEm)){
                    register.getFab().setEnabled(true);
                    register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                    if(special){
                        request.onChangeFragment(3);
                    }else{
                        request.onChangeFragment(2);
                    }

                }else{
                    cod1.setError(getString(R.string.error_code));
                    cod2.setError(getString(R.string.error_code));
                    cod3.setError(getString(R.string.error_code));
                    cod4.setError(getString(R.string.error_code));
                    cod4.requestFocus();
                }
            }
        });


        try {
            request = (CallbackFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnRequestChangeFragment");
        }

        cod4.setOnEditorActionListener(this);


        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        cod1.requestFocus();

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backLogin:
                if(sharedPref.getBoolean(getString(R.string.special_reg),false)){
                    editor.remove(getString(R.string.special_reg));
                    editor.commit();
                }
                Intent main = new Intent(getActivity(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getActivity().startActivity(main);
                getActivity().finish();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handled = false;
        if (i == EditorInfo.IME_ACTION_GO) {
            String code = cod1.getText().toString()+cod2.getText().toString()+cod3.getText().toString()+cod4.getText().toString();
            cod1.setError(null);
            cod2.setError(null);
            cod3.setError(null);
            cod4.setError(null);
            if(code.equals(codeEm)){
                register.getFab().setEnabled(true);
                register.getFab().setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Black)));
                request.onChangeFragment(2);
            }else{
                cod1.setError(getString(R.string.error_code));
                cod2.setError(getString(R.string.error_code));
                cod3.setError(getString(R.string.error_code));
                cod4.setError(getString(R.string.error_code));
                cod4.requestFocus();
            }
        }
        return handled;
    }
}