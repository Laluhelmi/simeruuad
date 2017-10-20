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
import pojo.Detailprodi;

public class Tampilprogramstudi extends Activity {

    ListView listView;
    List<Detailprodi> detailprodi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilprogramstudi);

        Bundle bundle=getIntent().getExtras();
        String hari=bundle.get("parsehari").toString();
        String lantai=bundle.get("parselantai").toString();
        listView = (ListView) findViewById(R.id.listjadwalprodi);
        detailprodi = new ArrayList<>();
        tampilstudi();

    }


    private void tampilstudi(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://192.168.43.202/simeru/tampilprodi.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Boolean status = object.getBoolean("status");

                    if (status) {

                        JSONArray data = object.getJSONArray("data");
                        View view;
                        List<View> views = new ArrayList<>();
                        LayoutInflater inflater = LayoutInflater.from(Tampilprogramstudi.this);
                        TextView dosen,matkul,ruang,kelas,jam;
                        if (data.length()> 0){

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject item = data.getJSONObject(i);

                                Detailprodi getnama = new Detailprodi();
                                getnama.setMatkulprodi(item.getString("matkul"));
                                getnama.setRuang(item.getString("ruang"));
                                getnama.setDosen(item.getString("dosen"));
                                getnama.setKelas(item.getString("kelas"));
                                getnama.setJam(item.getString("jam"));


                                detailprodi.add(getnama);

                                view = inflater.inflate(R.layout.itemlistjadwalprodi,listView, false);
                                dosen = (TextView)view.findViewById(R.id.namadosen);
                                matkul = (TextView)view.findViewById(R.id.namamatakuliah);
                                ruang = (TextView)view.findViewById(R.id.namaruang);
                                kelas = (TextView)view.findViewById(R.id.namakelas);
                                jam = (TextView)view.findViewById(R.id.namajam);


                                dosen.setText(item.getString("dosen"));
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
                Bundle bundle=getIntent().getExtras();
                String prodi=bundle.get("parseprodi").toString();
                String hari=bundle.get("parsehari").toString();
                String lantai=bundle.get("parselantai").toString();

                params.put("prodi", prodi);
                params.put("hari", hari);
                params.put("lantai", lantai);


                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
}
