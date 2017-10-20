package adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lupa on 13/09/2016.
 */
public class CustomAdapter extends BaseAdapter {

    private List<View> items;


    public CustomAdapter(List<View> items){
        this.items=items;
    }



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return items.get(position);
    }


}
