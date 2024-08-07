package com.bizmolimited.bizapp.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bizmolimited.bizapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;


public class AdapterItem extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Proposal> items;

    public AdapterItem (Activity activity, ArrayList<Proposal> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Proposal> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_proposal, null);
        }
        Country country = Country.getCountryFromSIM(activity.getApplicationContext());
        if(country.getCode().equals("US")){
            country = Country.getCountryByName("Chile");
        }

        Proposal dir = items.get(position);

        TextView nameOffer = (TextView) v.findViewById(R.id.nameOffer);
        nameOffer.setText(dir.getOfferName());

        TextView nameServ = (TextView) v.findViewById(R.id.nameServ);
        nameServ.setText(dir.getOfferJobName());

        TextView fecha = (TextView) v.findViewById(R.id.fecha);
        fecha.setText(dir.getFecha());

        TextView cost = (TextView) v.findViewById(R.id.cost);
        String price = "";

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(country.getSeparatorDecimal());
        symbols.setGroupingSeparator(country.getSeparatorMiles());
        df.setDecimalFormatSymbols(symbols);

        if(dir.getTypeCost()){
            price = country.getSignMoney() + " " + df.format(dir.getCost_min()) + " - " + df.format(dir.getCost_max());
        }else {
            price = country.getSignMoney() + " " + df.format(dir.getCost());
        }

        cost.setText(price);

        TextView dist = (TextView) v.findViewById(R.id.dist);
        dist.setText(dir.getDistanceAt().substring(0,4) + " KM");

        TextView coment = (TextView) v.findViewById(R.id.coment);
        coment.setText(dir.getCommnets());

        return v;
    }
}
