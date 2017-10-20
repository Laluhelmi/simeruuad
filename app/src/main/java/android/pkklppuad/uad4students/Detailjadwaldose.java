package android.pkklppuad.uad4students;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import java.util.Map;

import adapter.CustomAdapter;
import pojo.DetailDosen;

public class Detailjadwaldose extends Activity {

    List<DetailDosen> detaildosen;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_jadwal_dosen);

        listView = (ListView) findViewById(R.id.list_detail_dosen);
        detaildosen = new ArrayList<>();
        detaildosen();
    }


    private void detaildosen(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://192.168.43.202/simeru/detaildosen.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Boolean status = object.getBoolean("status");

                    if (status) {

                        JSONArray data = object.getJSONArray("data");
                        View view;
                        List<View> views = new ArrayList<>();
                        LayoutInflater inflater = LayoutInflater.from(Detailjadwaldose.this);
                        TextView hari,matkul,ruang,kelas,jam;
                        if (data.length()> 0){

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject item = data.getJSONObject(i);

                                DetailDosen getnama = new DetailDosen();
                                getnama.setMatkul(item.getString("matkul"));
                                getnama.setRuang(item.getString("ruang"));
                                getnama.setHari(item.getString("hari"));
                                getnama.setKelas(item.getString("kelas"));
                                getnama.setJam(item.getString("jam"));


                                detaildosen.add(getnama);

                                view = inflater.inflate(R.layout.itemlengkapjadwaldosen,listView, false);
                                hari = (TextView)view.findViewById(R.id.Hari);
                                matkul = (TextView)view.findViewById(R.id.Matakuliah);
                                ruang = (TextView)view.findViewById(R.id.Ruang);
                                kelas = (TextView)view.findViewById(R.id.Kelas);
                                jam = (TextView)view.findViewById(R.id.jam);


                                hari.setText(item.getString("hari"));
                                matkul.setText(item.getString("matkul"));
                                kelas.setText(item.getString("kelas"));
                                ruang.setText(item.getString("ruang"));
                                jam.setText(item.getString("jam"));


                                views.add(view);
                            }

                            listView.setAdapter(new CustomAdapter(views));

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "status tidak ada", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected void onFinish() {
                super.onFinish();
                //progressDialog.dismiss();
            }
            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
                //progressDialog.show();
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                Bundle b = getIntent().getExtras();
                String iddosen = b.getString("id_dosen");
                String matkul = b.getString("matkul");

                params.put("iddosen", iddosen);
                params.put("matakuliah", matkul);


                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
}
