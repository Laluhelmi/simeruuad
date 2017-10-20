package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import java.util.Map;

import adapter.CustomAdapter;
import adapter.DindingAdapter;
import pojo.Informasidinding;
import server.Account;
import server.UrlKoneksi;

public class Dindinginformasi extends Activity {

    private ListView listView;
    private DindingAdapter dindingAdapter;
    private List<Informasidinding>  informasidindings;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dindinginformasi);
        requestQueue = Volley.newRequestQueue(this);
        informasidindings = new ArrayList<>();

        listView   = (ListView) findViewById(R.id.listinformasidinding);
        String niy = new Account().getNiy(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Detail_dinding_informasi.class);
                intent.putExtra("jam"       ,informasidindings.get(i).getDindinghari());
                intent.putExtra("kelas"     ,informasidindings.get(i).getDindingkelas());
                intent.putExtra("deskripsi" ,informasidindings.get(i).getDindingdeskripsi());
                intent.putExtra("namadosen" ,informasidindings.get(i).getDosen());
                startActivity(intent);
            }
        });
        getInformasi(niy);
    }
    public void getInformasi(String niy){
        StringRequest stringRequest = new StringRequest(UrlKoneksi.alamat+"simeru/tampildinding.php?niy="+niy,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("hasil").equals("sukses")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Informasidinding informasidinding = new Informasidinding();
                            informasidinding.setDindinghari(jsonObject1.getString("jam"));
                            informasidinding.setDindingkelas(jsonObject1.getString("kelas"));
                            informasidinding.setDindingdeskripsi(jsonObject1.getString("keterangan"));
                            if(jsonObject1.getString("keterangan").equals("Lain-Lain")){
                              informasidinding.setDindingdeskripsi(jsonObject1.getString("lainlain"));
                            }
                            informasidinding.setDindingmatkul(jsonObject1.getString("namakul"));
                            informasidinding.setIddinding(jsonObject1.getString("id_dinding"));
                            informasidinding.setDosen(jsonObject1.getString("namadosen"));
                            informasidindings.add(informasidinding);
                        }
                            dindingAdapter = new DindingAdapter(informasidindings,getApplicationContext());
                            listView.setAdapter(dindingAdapter);
                    }

                }catch (Exception e){
                    Toast.makeText(Dindinginformasi.this, "error json "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dindinginformasi.this, "error respon "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

}
