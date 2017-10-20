package perwalian;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.pkklppuad.uad4students.R;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.TampilTopik;
import pojo.TampilPerwalin;
import server.Account;
import server.UrlKoneksi;

public class ChatActivity extends Activity{

	private RequestQueue requestQueue;
	public static boolean isTopikActive = false;
	private ListView topikListview;
	private List<TampilPerwalin> tampilPerwalins = new ArrayList<>();
	//adapter Listview
	private TampilTopik tampilTopik;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mengetahui kalau activity sedang dibuka
		isTopikActive = true;

		setContentView(R.layout.layout_chat);
		requestQueue 	= Volley.newRequestQueue(this);
		((Button)findViewById(R.id.tamabahtopik)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Dialog dialog = new Dialog(ChatActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dilaog_add_topik);
				final EditText topikInput  = (EditText)dialog.findViewById(R.id.judultopik);
				Button   simpantopik = (Button)dialog.findViewById(R.id.simpantopik);
				simpantopik.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(topikInput.getText().toString().isEmpty() == false)
							simpanTopik(topikInput.getText().toString());
							dialog.dismiss();
					}
				});
				dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,500);
				dialog.show();
			}
		});
		topikListview = (ListView)findViewById(R.id.topik);
		getData();;
		LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("Topik"));
	}

	public void simpanTopik(final String topik){

		String url 					= UrlKoneksi.alamat+"simeru/perwalian/posttopik.php";
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM0dd HH:mm:ss");
				Date date = new Date();
				TampilPerwalin tampilPerwalin = new TampilPerwalin();
				tampilPerwalin.topik = topik;
				tampilPerwalin.jam   = dateFormat.format(date);
				tampilPerwalins.add(0,tampilPerwalin);
				tampilTopik.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(ChatActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
				}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> param = new HashMap<>();
				String niy 	= new Account().getNiy(getApplicationContext());
				String nama = new Account().getNama(getApplicationContext());
				param.put("iduser"		,niy);
				param.put("topik" 		,topik);
				param.put("idperwalian"	,niy);
				param.put("role"		,"dosen");
				param.put("username"	,nama);
				return param;
			}
		};
		requestQueue.add(stringRequest);
	}
	public void getData(){

		String idperwalian 			= new Account().getNiy(this);
		String url 					= UrlKoneksi.alamat+"simeru/perwalian/gettopik.php?idperwalian="+idperwalian;
		StringRequest stringRequest = new StringRequest( url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
			try {
				JSONArray jsonArray = new JSONArray(response);

				for (int i = 0; i< jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					TampilPerwalin tampilPerwalin = new TampilPerwalin();
					tampilPerwalin.idtopik = jsonObject.getString("idtopik");
					tampilPerwalin.iduser  = jsonObject.getString("iduser");
					tampilPerwalin.jam	   = jsonObject.getString("jam");
					tampilPerwalin.topik   = jsonObject.getString("topik");
					tampilPerwalins.add(tampilPerwalin);
				}
				tampilTopik = new TampilTopik(getApplicationContext(),tampilPerwalins);
				topikListview.setAdapter(tampilTopik);
				topikListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						Intent intent = new Intent(getApplicationContext(),CommentTopik.class);
						intent.putExtra("idcomment"		,tampilPerwalins.get(i).idtopik);
						intent.putExtra("judulcomment"	,tampilPerwalins.get(i).topik);
						startActivity(intent);

					}
				});
			}catch (Exception e){
				Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(ChatActivity.this, "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
		requestQueue.add(stringRequest);
	}
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String topik = intent.getStringExtra("topik");
			String jam   = intent.getStringExtra("jam");
			TampilPerwalin tampilPerwalin = new TampilPerwalin();
			tampilPerwalin.idtopik = "";
			tampilPerwalin.iduser  = "";
			tampilPerwalin.jam	   = jam;
			tampilPerwalin.topik   = topik;
			tampilPerwalins.add(0,tampilPerwalin);
			tampilTopik.notifyDataSetChanged();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		isTopikActive = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isTopikActive = false;
	}
}
