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

public class DialogShowService extends DialogFragment {

    private String nameCat;
    private String titleServ;
    private String descServ;

    public void setDescServ(String descServ) {
        this.descServ = descServ;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public void setTitleServ(String titleServ) {
        this.titleServ = titleServ;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_show_offer, null);

        TextView cat = (TextView) view.findViewById(R.id.catServ);
        TextView title = (TextView) view.findViewById(R.id.titleServ);
        TextView desc = (TextView) view.findViewById(R.id.descServ);

        cat.setText(nameCat);
        title.setText(titleServ);
        desc.setText(descServ);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));
        Button close = (Button) view.findViewById(R.id.close);
        close.setTypeface(font);
        close.setText("\uf057");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogShowService.this.getDialog().cancel();
            }
        });

        builder.setView(view);


        return builder.create();
    }


}