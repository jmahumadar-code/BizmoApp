package com.bizmolimited.bizapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bizmolimited.bizapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by guillermofuentesquijada on 05-11-17.
 */

public class DialogImage extends DialogFragment{

    private Bitmap image;
    private String path;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));


        View view = inflater.inflate(R.layout.dialog_img, null);

        Button close = (Button) view.findViewById(R.id.close);
        close.setTypeface(font);
        close.setText("\uf057");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImage.this.getDialog().cancel();
            }
        });

        ImageView img = (ImageView) view.findViewById(R.id.image_dlog);
        if(image != null){
            img.setImageBitmap(image);
        }else{
            Picasso.with(getActivity().getApplicationContext()).load(path).into(img);
        }

        builder.setView(view);

        return builder.create();
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
