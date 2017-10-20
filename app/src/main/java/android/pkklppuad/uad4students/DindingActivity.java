package android.pkklppuad.uad4students;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DindingActivity extends Activity{
	
	TextView  niy, informasi;
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	
	//String link_url = "http://192.168.43.152/perwalianuad/dinding_api.php";
	String link_url = "http://perwalian.esy.es/dinding_api.php";

	public static final String GET_ARRAY = "info";
	public static final String GET_WAKTU = "waktu";
	public static final String GET_NIY = "niynip";
	public static final String GET_INFORMASI = "informasi";
	
	JSONArray info = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dinding);



		ArrayList<ItemDaftar> list = new ArrayList<ItemDaftar>();
		list.add(new ItemDaftar("j", "11018032"));
		list.add(new ItemDaftar("Aswindra Aji Kurniawan", "11018034"));
		list.add(new ItemDaftar("Gilang Pamungkas", "11018035"));
		list.add(new ItemDaftar("Aminudin Rais", "11018036"));
		list.add(new ItemDaftar("Ayu Laksmi Pandhita", "11018037"));
		list.add(new ItemDaftar("Wayu Miftahul Huda", "11018038"));
		list.add(new ItemDaftar("Tatang Jaya Diguna", "11018039"));
		list.add(new ItemDaftar("Yogi Surya Nugraha", "11018040"));
		list.add(new ItemDaftar("Yohani", "1300018059"));

		final MahasiswaAdapter adapter = new MahasiswaAdapter(this, list);

		ListView listView = (ListView) findViewById(R.id.list_dinding);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
				ListView listView = (ListView) findViewById(R.id.list_dinding);
				listView.getAdapter();
				registerForContextMenu(listView);

			}
		});
		
//		list = new ArrayList<HashMap<String, String>>();
//		niy = (TextView)findViewById(R.id.item_dinding_nama);
//		informasi = (TextView)findViewById(R.id.item_info);
//        new JSONParse().execute();

	}
	
//	private class JSONParse extends AsyncTask<String, String, JSONObject> {
//   	 private ProgressDialog pDialog;
//   	@Override
//       protected void onPreExecute() {
//           super.onPreExecute();
//           pDialog = new ProgressDialog(DindingActivity.this);
//           pDialog.setMessage("Menghubungkan ke server...");
//           pDialog.setIndeterminate(false);
//           pDialog.setCancelable(true);
//           pDialog.show();
//
//   	}
//
//   	@Override
//       protected JSONObject doInBackground(String... args) {
//
//   		JSONParser jParser = new JSONParser();
//
//   		// Getting JSON from URL
//   		JSONObject json = jParser.AmbilJson(link_url);
//   		return json;
//   	}
//   	 @Override
//        protected void onPostExecute(JSONObject json) {
//   		 pDialog.dismiss();
//   		 try {
//   				// Getting JSON Array from URL
//   				info = json.getJSONArray(GET_ARRAY);
//   				for(int i = 0; i < info.length(); i++){
//   				JSONObject c = info.getJSONObject(i);
//
//   				// Storing  JSON item in a Variable
//   				String waktu = c.getString(GET_WAKTU);
//   				String niy = c.getString(GET_NIY);
//   				String informasi = c.getString(GET_INFORMASI);
//
//   				// Adding value HashMap key => value
//   				final HashMap<String, String> map = new HashMap<String, String>();
//
//   				map.put(GET_WAKTU, waktu);
//   				map.put(GET_NIY, niy);
//   				map.put(GET_INFORMASI, informasi);
//
//   				list.add(map);
//   				ListView listView = (ListView) findViewById(R.id.list_dinding);
//
//   				ListAdapter adapter = new SimpleAdapter(DindingActivity.this, list,
//   						R.layout.item_dinding,
//   						new String[] {GET_NIY, GET_WAKTU }, new int[] {
//   								R.id.item_dinding_nama, R.id.item_info});
//
//   				listView.setAdapter(adapter);
//   				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//
//						Intent i = new Intent(DindingActivity.this, DetailDinding.class);
//						i.putExtra("waktu", list.get(+position).get(GET_WAKTU));
//						i.putExtra("niy", list.get(+position).get(GET_NIY));
//						i.putExtra("info", list.get(+position).get(GET_INFORMASI));
//						startActivity(i);
//		            }
//		        });
//
//				}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//
//	 }
//}
	
	
}
