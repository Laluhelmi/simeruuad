package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.pkklppuad.uad4students.Detailjadwaldose;
import android.pkklppuad.uad4students.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pojo.Dosen;

/**
 * Created by lupa on 16/07/2017.
 */
public class Adapterdosen extends BaseAdapter {

    private Context context;
    private List<Dosen> datadosen = null;
    private ArrayList<Dosen> arraylist;
    private LayoutInflater layoutInflater;

    public Adapterdosen(List<Dosen> datadosen, Context context){
        this.datadosen = datadosen;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.arraylist = new ArrayList<Dosen>();
        this.arraylist.addAll(datadosen);
    }

    @Override
    public int getCount() {
        return datadosen.size();
    }

    @Override
    public Object getItem(int position) {
        return datadosen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        convertView = layoutInflater.inflate(R.layout.item_dosen, parent, false);
        TextView namamatkul,namadosen;
        namamatkul = (TextView)convertView.findViewById(R.id.namamatkul);
        namadosen = (TextView)convertView.findViewById(R.id.namadosen);
        namamatkul.setTextColor(Color.parseColor("#FF020202"));
        namadosen.setTextColor(Color.parseColor("#FF020202"));

        Dosen dosen = (Dosen) getItem(position);
        namamatkul.setText(dosen.getMatkul());
        namadosen.setText(dosen.getNama());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                i = new Intent(context, Detailjadwaldose.class);
                Bundle b = new Bundle();
                i.putExtra("id_dosen", String.valueOf(datadosen.get(position).getIddosen()));
                i.putExtra("nama_dosen", String.valueOf(datadosen.get(position).getNama()));
                i.putExtra("matkul", String.valueOf(datadosen.get(position).getMatkul()));
                i.putExtras(b);
                context.startActivity(i);
            }
        });
//        convertView.setOnClickListener();
        return convertView;
    }



    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        datadosen.clear();
        if (charText.length() == 0) {
            datadosen.addAll(arraylist);
        } else {
            for (Dosen wp : arraylist) {
                if (wp.getNama().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    datadosen.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
