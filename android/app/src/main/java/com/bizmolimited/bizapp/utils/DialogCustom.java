package com.bizmolimited.bizapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bizmolimited.bizapp.R;

/**
 * Created by guillermofuentesquijada on 08-10-17.
 */

public class DialogCustom extends DialogFragment {

    public static int GPS_ENABLE_REQUEST = 133;

    private String tex;
    private boolean type;
    private boolean enabledGPS;
    private Activity _this;
    private CloseSession mListener;
    private static final String icon_logout = "\uf08b";
    private static final String icon_gps = "\uf14e";

    public interface CloseSession {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    public DialogCustom(){
        type = false;
        enabledGPS = false;
        _this = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));


        View view = inflater.inflate(R.layout.dialog_custom, null);

        View action = view.findViewById(R.id.btn_action);
        TextView icon = (TextView) view.findViewById(R.id.btn_icon);
        TextView text_btn = (TextView) view.findViewById(R.id.btn_text_action);

        TextView text = (TextView) view.findViewById(R.id.text_dialog);

        Button close = (Button) view.findViewById(R.id.close);
        close.setTypeface(font);
        close.setText("\uf057");

        icon.setTypeface(font);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCustom.this.getDialog().cancel();
            }
        });

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type){
                    _this.startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_ENABLE_REQUEST);
                }else {
                    mListener.onDialogPositiveClick(DialogCustom.this);
                }
            }
        });

        text.setText(tex);
        if(type){
            if(enabledGPS){
                icon.setText(icon_gps);
                text_btn.setText("Activar GPS");
            }else{
                action.setVisibility(View.GONE);
            }
            builder.setView(view);
        }else{
            try {
                mListener = (CloseSession) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString()
                        + " must implement NoticeDialogListener");
            }
            icon.setText(icon_logout);
            text_btn.setText("Cerrar Sesión");
            builder.setView(view);

        }

        return builder.create();
    }

    public void setText(String msg){
        tex = msg;
    }

    public void setType(boolean type){
        this.type = type;
    }

    public void setEnabledGPS(boolean enabledGPS) {
        this.enabledGPS = enabledGPS;
    }

    public void set_this(Activity _this) {
        this._this = _this;
    }
}

