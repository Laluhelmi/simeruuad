package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import server.UrlKoneksi;


public class transkrip extends Activity {

    private ListView listView;
    private TextView nama,nim;
    private List<Transkip> transkips;
    private float ipk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transkrip);

        listView    = (ListView) findViewById(R.id.listranskip);
        nama        = (TextView) findViewById(R.id.nama);
        nim         = (TextView) findViewById(R.id.nim);
        String nims  = getIntent().getStringExtra("nidn");
        transkips   = new ArrayList<>();
        nim.setText(":"+nims);
        ambilData(nims);
    }

    public void ambilData(String nim){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(UrlKoneksi.alamat + "/simeru/transkip.php?nim=" + nim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            int totalNilaiAngka = 0;
                            int totalSks        = 0;
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                Transkip transkip = new Transkip();
                                nama.setText(":"+object.getString("nama"));
                                transkip.kode     = object.getString("matakuliah_idmatakuliah");
                                transkip.namakul  = object.getString("namakul");
                                transkip.sks      = object.getString("sks");
                                transkip.nilai    = object.getString("nilai");
                                float nilaiangka    = 0;

                                if(transkip.nilai.equals("A")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 4.00);
                                    transkip.bobot= "4.00";
                                }else if(transkip.nilai.equals("A-")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 3.67);
                                    transkip.bobot= "3.67";
                                }
                                else if(transkip.nilai.equals("B")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 3.00);
                                    transkip.bobot= "3.00";
                                }
                                else if(transkip.nilai.equals("B+")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 3.33);
                                    transkip.bobot= "3.33";
                                }
                                else if (transkip.nilai.equals("B-")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 2.67);
                                }
                                else if(transkip.nilai.equals("C")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 2.00);
                                    transkip.bobot= "2.00";
                                }
                                else if(transkip.nilai.equals("C+")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 2.33);
                                    transkip.bobot= "2.33";
                                }
                                else if (transkip.nilai.equals("C-")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 1.67);
                                    transkip.bobot= "1.67";
                                }
                                else if(transkip.nilai.equals("D")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 1.00);
                                    transkip.bobot= "1.00";
                                }
                                else if(transkip.nilai.equals("D+")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 1.33);
                                    transkip.bobot= "1.33";
                                }
                                else if(transkip.nilai.equals("E")){
                                    nilaiangka    = (float) (Float.parseFloat(transkip.sks) * 0.00);
                                    transkip.bobot= "0.0";
                                }
                                totalSks         += Integer.parseInt(transkip.sks);
                                totalNilaiAngka  += nilaiangka;
                                transkips.add(transkip);
                            }
                            ipk = ((float)totalNilaiAngka) / ((float)totalSks);
                            BaseAdapterSaya baseAdapterSaya = new BaseAdapterSaya(transkips,transkrip.this);

                            ipk =(float) Math.floor(ipk * 100) / 100;

                            ((TextView)findViewById(R.id.ipk)).setText("IPK : "+String.valueOf(ipk));
                            listView.setAdapter(baseAdapterSaya);
                        }catch (Exception e){
                            Toast.makeText(transkrip.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(transkrip.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    class BaseAdapterSaya extends BaseAdapter{
        List<Transkip> transkips;
        LayoutInflater layoutInflater;
        public BaseAdapterSaya(List<Transkip> transkips, Context context){
            this.transkips = transkips;
            layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return transkips.get(i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view              = layoutInflater.inflate(R.layout.item_transkip,viewGroup,false);
            Transkip transkip = (Transkip)getItem(i);
            ((TextView)view.findViewById(R.id.matakuliah)).setText(transkip.namakul);
            ((TextView)view.findViewById(R.id.kode)).setText(transkip.bobot);
            ((TextView)view.findViewById(R.id.sks)).setText(transkip.sks);
            ((TextView)view.findViewById(R.id.nilai)).setText(transkip.nilai);
            return view;
        }

        @Override
        public int getCount() {
            return transkips.size();
        }
    }
    class Transkip{
        String namakul,kode,sks,nilai,bobot;
    }
}
