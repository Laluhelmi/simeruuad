package android.pkklppuad.uad4students;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pojo.Mahasiswa;
import server.Account;
import server.UrlKoneksi;

public class MahasiswaActivity extends Activity {
	private ListView listView;
	private List<String> daftarMahasiswa;
	private RequestQueue requestQueue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mahasiswa);
		daftarMahasiswa = new ArrayList<>();
		requestQueue 	= Volley.newRequestQueue(this);
		listView		= (ListView)findViewById(R.id.list_mahasiswa);
		ambilData(new Account().getNiy(this));
	}

	public void ambilData(String niy){
		String nidn = new Account().getNiy(this);
		StringRequest stringRequest = new StringRequest(UrlKoneksi.alamat + "/simeru/tampilmahasiswabimbing.php?niy="
									+niy,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try{
							List<Mahasiswa> mahasiswas = new ArrayList<>();
							JSONArray jsonArray = new JSONArray(response);
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								Mahasiswa mahasiswa   = new Mahasiswa();
								mahasiswa.nama 		  = jsonObject.getString("nama");
								mahasiswa.nim		  = jsonObject.getString("nim");
								mahasiswas.add(mahasiswa);
							}

							CostumAdapter costumAdapter = new CostumAdapter(mahasiswas,MahasiswaActivity.this);
							((ListView)findViewById(R.id.list_mahasiswa)).setAdapter(costumAdapter);
							listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
									String nidn 	 = ((TextView)view.findViewById(R.id.item_keterangan)).getText().toString();
									Intent intent	 = new Intent(MahasiswaActivity.this,transkrip.class);
									intent.putExtra("nidn",nidn);
									startActivity(intent);
								}
							});
						}catch (Exception e){

						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		requestQueue.add(stringRequest);
	}
	class CostumAdapter extends BaseAdapter{

		private List<Mahasiswa> mahasiswas;
		private LayoutInflater layoutInflater;

		public CostumAdapter(List<Mahasiswa> mahasiswas, Context context){
			this.mahasiswas 	= mahasiswas;
			this.layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return this.mahasiswas.size();
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			Mahasiswa mahasiswa = (Mahasiswa)getItem(i);
			view = layoutInflater.inflate(R.layout.item_list_data,viewGroup,false);
			((TextView)view.findViewById(R.id.item_nama)).setText(mahasiswa.nama);
			((TextView)view.findViewById(R.id.item_keterangan)).setText(mahasiswa.nim);
			return view;
		}

		@Override
		public Object getItem(int i) {
			return mahasiswas.get(i);
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}
	}
}
	