package com.bizmolimited.bizapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bizmolimited.bizapp.R;

import org.w3c.dom.Text;

/**
 * Created by guillermofuentesquijada on 08-10-17.
 */

public class DialogSelect extends DialogFragment {

    private CallbackDialogSelect callback;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String type_user;
    private static final String icon_user = "\uf007";
    private static final String icon_offer = "\uf0c0";

    public void setCallback(CallbackDialogSelect callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        String mp = getText(R.string.bizapp_pref).toString();
        sharedPref = getActivity().getSharedPreferences(mp, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        type_user = sharedPref.getString("type_user_init", null);

        View view = inflater.inflate(R.layout.dialog_select, null);

        View btn1 = view.findViewById(R.id.btn1);
        View btn2 = view.findViewById(R.id.btn2);

        TextView icon1 = (TextView) view.findViewById(R.id.btn_icon_act1);
        TextView icon2 = (TextView) view.findViewById(R.id.btn_icon_act2);

        TextView text1 = (TextView) view.findViewById(R.id.btn_text_act1);
        TextView text2 = (TextView) view.findViewById(R.id.btn_text_act2);

        TextView text = (TextView) view.findViewById(R.id.text_select);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        icon1.setTypeface(font);
        icon2.setTypeface(font);

        Button close = (Button) view.findViewById(R.id.close);
        close.setTypeface(font);
        close.setText("\uf057");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSelect.this.getDialog().cancel();
            }
        });

        if(type_user == null){
            close.setVisibility(View.GONE);
            text.setText("Con que modalidad desea iniciar:");
            text1.setText("Modo Usuario");
            text2.setText("Modo Oferente");
            icon1.setText(icon_user);
            icon2.setText(icon_offer);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogSelect.this.getDialog().cancel();
                    editor.putString("type_user_init","user");
                    editor.commit();
                    callback.responseDialogSelect(false);
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogSelect.this.getDialog().cancel();
                    editor.putString("type_user_init","offer");
                    editor.commit();
                    callback.responseDialogSelect(true);
                }
            });
            builder.setView(view);
        }else{
            close.setVisibility(View.VISIBLE);
            btn1.setVisibility(View.GONE);
            text.setText("Desea cambiar a modo:");
            if(type_user.equals("user")){
                text2.setText("Modo Oferente");
                icon2.setText(icon_offer);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogSelect.this.getDialog().cancel();
                        editor.putString("type_user_init","offer");
                        editor.commit();
                        callback.responseDialogSelect(true);
                    }
                });
                builder.setView(view);
            }else{
                text2.setText("Modo Usuario");
                icon2.setText(icon_user);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogSelect.this.getDialog().cancel();
                        editor.putString("type_user_init","user");
                        editor.commit();
                        callback.responseDialogSelect(false);
                    }
                });

                builder.setView(view);
            }
        }


        return builder.create();
    }

    public interface CallbackDialogSelect{
        public void responseDialogSelect(boolean userOrOffer);
    }

}