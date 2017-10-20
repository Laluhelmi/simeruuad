package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.pkklppuad.uad4students.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import pojo.TampilPerwalin;

/**
 * Created by L on 10/5/17.
 */

public class TampilTopik extends BaseAdapter {
    private Context context;
    private List<TampilPerwalin> tampilPerwalins;
    private LayoutInflater layoutInflater;
    public TampilTopik(Context context,List<TampilPerwalin> tampilPerwalin){
        this.context         = context;
        layoutInflater       = LayoutInflater.from(context);
        this.tampilPerwalins = tampilPerwalin;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view                = layoutInflater.inflate(R.layout.item_tampil_topik,viewGroup,false);
        TextView judul      = (TextView)view.findViewById(R.id.judul);
        TextView by         = (TextView)view.findViewById(R.id.by);
        TampilPerwalin item = (TampilPerwalin)getItem(i);
        judul.setText(item.topik);
        by.setText(item.username);
        ((TextView)view.findViewById(R.id.jam)).setText(item.jam);
        ((TextView)view.findViewById(R.id.jam)).setTypeface(null, Typeface.ITALIC);

        return view;
    }

    @Override
    public Object getItem(int i) {
        return tampilPerwalins.get(i);
    }

    @Override
    public int getCount() {
        return this.tampilPerwalins.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
