package android.pkklppuad.uad4students;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JadwalDosenAdapter extends ArrayAdapter<ItemDaftar>{
	
	private ArrayList<ItemDaftar> list;
	private Activity act;

	public JadwalDosenAdapter(Activity context, ArrayList<ItemDaftar> objects) {
		super(context, R.layout.item_list_data, objects);
		this.list = objects;
		this.act = context;
	}

	static class ViewHolder {
		protected ImageView icon;
		protected TextView nama;
		protected TextView keterangan;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = act.getLayoutInflater();
			view = inflater.inflate(R.layout.item_list_data, null);

			ViewHolder holder = new ViewHolder();
			holder.icon = (ImageView) view.findViewById(R.id.item_icon);
			holder.nama = (TextView) view.findViewById(R.id.item_nama);
			holder.keterangan = (TextView) view
					.findViewById(R.id.item_keterangan);
			view.setTag(holder);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		ItemDaftar mhs= list.get(position);

		holder.icon.setImageResource(R.drawable.img_user);
		holder.nama.setText(mhs.getName());
		holder.keterangan.setText(mhs.getBrand());

		return view;
	}

}
