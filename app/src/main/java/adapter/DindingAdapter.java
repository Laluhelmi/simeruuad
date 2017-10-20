package adapter;

import android.content.Context;
import android.pkklppuad.uad4students.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import pojo.Informasidinding;

/**
 * Created by L on 8/5/17.
 */

public class DindingAdapter extends BaseAdapter {
    List<Informasidinding> informasidindings;
    private LayoutInflater layoutInflater;
    public DindingAdapter(List<Informasidinding> informasidindings, Context context){
        this.informasidindings = informasidindings;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int i) {
        return informasidindings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Informasidinding informasidinding = (Informasidinding) getItem(i);
                                    view  = layoutInflater.inflate(R.layout.iteminformasidinding,
                                            viewGroup,false);
        TextView infomatkul = (TextView)view.findViewById(R.id.itemmatakuliahinformasi);
        TextView tanggal    = (TextView)view.findViewById(R.id.itemhariinformasi);
        TextView kelas      = (TextView)view.findViewById(R.id.itemkelasinformasi);
        //
        infomatkul.setText(informasidinding.getDindingmatkul());
        tanggal.setText(informasidinding.getDindinghari());
        kelas.append(informasidinding.getDindingkelas().toUpperCase());


        return view;
    }

    @Override
    public int getCount() {
        return informasidindings.size();
    }
}
