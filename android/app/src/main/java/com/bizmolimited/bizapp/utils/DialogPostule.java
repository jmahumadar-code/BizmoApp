package com.bizmolimited.bizapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizmolimited.bizapp.R;
import com.google.android.gms.vision.text.Line;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by guillermofuentesquijada on 08-10-17.
 */

public class DialogPostule extends DialogFragment {

    private static final String TAG = DialogPostule.class.getSimpleName();

    public interface CallbackPostular {
        public void postulacion(String id);
    }

    private String nameCat;
    private String titleServ;
    private String descServ;
    private String distServ;
    private String clientServ;
    private String jsonImage;
    private String id;

    private CallbackPostular postulacion;


    public void setPostulacion(CallbackPostular postulacion) {
        this.postulacion = postulacion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescServ(String descServ) {
        this.descServ = descServ;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public void setTitleServ(String titleServ) {
        this.titleServ = titleServ;
    }

    public void setClientServ(String clientServ) {
        this.clientServ = clientServ;
    }

    public void setDistServ(String distServ) {
        this.distServ = distServ;
    }

    public void setJsonImage(String jsonImage) {
        this.jsonImage = jsonImage;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_postule, null);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font));

        Button close = (Button) view.findViewById(R.id.close);
        close.setTypeface(font);
        close.setText("\uf057");

        View btn = view.findViewById(R.id.btn);
        TextView text_btn = (TextView) view.findViewById(R.id.btn_text);
        TextView icon_btn = (TextView) view.findViewById(R.id.icon);

        icon_btn.setTypeface(font);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPostule.this.getDialog().cancel();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPostule.this.getDialog().cancel();
                postulacion.postulacion(id);
            }
        });

        TextView cat = (TextView) view.findViewById(R.id.catServ);
        TextView title = (TextView) view.findViewById(R.id.titleServ);
        TextView desc = (TextView) view.findViewById(R.id.descServ);
        TextView dist = (TextView) view.findViewById(R.id.distServ);
        TextView client = (TextView) view.findViewById(R.id.clientServ);
        TextView image = (TextView) view.findViewById(R.id.images);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.container_images);
        container.removeAllViews();
        try{
            JSONObject json = new JSONObject(jsonImage);
            if(json.has("0")){
                Log.d(TAG, "hay datos");
                Iterator<String> iter = json.keys();
                while (iter.hasNext()){
                    String key = iter.next();
                    ImageView imageView = new ImageView(getActivity());
                    imageView .setLayoutParams(new android.view.ViewGroup.LayoutParams(AppHelper.convertDpToPixel(50, getActivity()),AppHelper.convertDpToPixel(50, getActivity())));
                    imageView .setPadding(10,10,10,10);
                    final String path = json.getString(key);
                    Picasso.with(getActivity().getApplicationContext()).load(path).into(imageView);
                    imageView .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogImage dlog = new DialogImage();
                            dlog.setImage(null);
                            dlog.setPath(path);
                            dlog.show(getFragmentManager(),TAG);
                        }
                    });
                    container.addView(imageView);
                }
            }else{
                image.setVisibility(View.GONE);
                container.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cat.setText(nameCat);
        title.setText(titleServ);
        desc.setText(descServ);
        dist.setText(distServ.substring(0,4) + " KM");
        client.setText(clientServ);

        builder.setView(view);

        return builder.create();
    }


}