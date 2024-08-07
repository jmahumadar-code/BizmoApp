package com.bizmolimited.bizapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

public class DialogWork extends DialogFragment {

    public interface CallbackWorkOffer {
        public void showOfferWork(boolean show);
    }

    private CallbackWorkOffer callback;

    public void setCallback(CallbackWorkOffer callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_work, null);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        Button close = (Button) view.findViewById(R.id.close);
        close.setTypeface(font);
        close.setText("\uf057");

        TextView icon = (TextView) view.findViewById(R.id.icon);
        View btn = view.findViewById(R.id.btn);

        icon.setTypeface(font);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogWork.this.getDialog().cancel();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogWork.this.getDialog().cancel();
                callback.showOfferWork(true);
            }
        });

        builder.setView(view);

        return builder.create();
    }


}