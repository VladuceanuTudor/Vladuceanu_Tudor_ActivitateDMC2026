package com.example.lab4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TVAdapter extends ArrayAdapter<TV> {

    private final Context context;
    private final ArrayList<TV> lista;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public TVAdapter(Context context, ArrayList<TV> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_tv, parent, false);
        }

        TV tv = lista.get(position);

        TextView tvMarca = convertView.findViewById(R.id.tvItemMarca);
        TextView tvDetalii = convertView.findViewById(R.id.tvItemDetalii);
        TextView tvData = convertView.findViewById(R.id.tvItemData);
        TextView tvBadge = convertView.findViewById(R.id.tvBadge);

        tvMarca.setText(tv.getMarca());
        tvDetalii.setText(tv.getDiagonala() + "\" • " + tv.getTipPanel() + " • " + tv.getPret() + " RON");
        tvData.setText("Adăugat: " + sdf.format(tv.getDataAdaugarii()));

        if (tv.isEsteSmartTV()) {
            tvBadge.setText("SMART");
            tvBadge.setBackgroundColor(Color.parseColor("#3949AB"));
        } else {
            tvBadge.setText("BASIC");
            tvBadge.setBackgroundColor(Color.parseColor("#90A4AE"));
        }

        return convertView;
    }
}