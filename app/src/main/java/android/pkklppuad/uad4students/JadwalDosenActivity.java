package android.pkklppuad.uad4students;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class JadwalDosenActivity extends Activity{
	
//	TextView  nama, keterangan;
//	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//
//	//String link_url = "http://192.168.43.152/perwalianuad/dosen_api.php";
//	String link_url = "http://perwalian.esy.es/dosen_api.php";
//
//	private static final String GET_ARRAY = "info";
//	private static final String GET_NIYNIP = "niynip";
//	private static final String GET_NAMA = "nama";
//	private static final String GET_GELAR = "gelar";
//	private static final String GET_PRODI = "prodi";
//
//	JSONArray info = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout_jadwal_dosen);
//
//		list = new ArrayList<HashMap<String, String>>();
//		nama = (TextView)findViewById(R.id.item_nama);
//		keterangan = (TextView)findViewById(R.id.item_keterangan);
//        new JSONParse().execute();
//
//	}
//
//	private class JSONParse extends AsyncTask<String, String, JSONObject> {
//   	 private ProgressDialog pDialog;
//   	@Override
//       protected void onPreExecute() {
//           super.onPreExecute();
//           pDialog = new ProgressDialog(JadwalDosenActivity.this);
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
//   				String keterangan = c.getString(GET_NIYNIP);
//   				String nama = c.getString(GET_NAMA);
//   				String gelar = c.getString(GET_GELAR);
//   				String prodi = c.getString(GET_PRODI);
//
//   				// Adding value HashMap key => value
//   				HashMap<String, String> map = new HashMap<String, String>();
//
//   				map.put(GET_NIYNIP, keterangan);
//   				map.put(GET_NAMA, nama);
//   				map.put(GET_GELAR, gelar);
//   				map.put(GET_PRODI, prodi);
//
//   				list.add(map);
//   				ListView listView = (ListView) findViewById(R.id.list_dosen);
//
//   				ListAdapter adapter = new SimpleAdapter(JadwalDosenActivity.this, list,
//   						R.layout.item_list_data,
//   						new String[] {GET_NAMA, GET_NIYNIP }, new int[] {
//   								R.id.item_nama, R.id.item_keterangan});
//
//   				listView.setAdapter(adapter);
//   				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//   							@Override
//   							public void onItemClick(AdapterView<?> arg0, View arg1,
//   									int position, long arg3) {
//
//   							}
//   						});
//
//   				}
//   		} catch (JSONException e) {
//   			e.printStackTrace();
//   		}
//
//
//   	 }
//   }
}