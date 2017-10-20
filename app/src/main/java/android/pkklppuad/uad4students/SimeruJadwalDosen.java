package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import adapter.Adapterdosen;
import pojo.Dosen;
import server.Account;
import server.UrlKoneksi;

/**
 * Created by Reza on 10/11/2016.
 */
public class SimeruJadwalDosen extends Activity {

    List<Dosen> dosen;
    private ListView listView;
    Adapterdosen adapter;
    private RequestQueue requestQueue;
    private List<Jadwal> jadwalskuliah = new ArrayList<>();
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jadwaldosen);
        requestQueue = Volley.newRequestQueue(this);
        listView = (ListView) findViewById(R.id.list_jadwal_dosen);
        dosen = new ArrayList<>();
        context = SimeruJadwalDosen.this;

        /**
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScaleAnimation scaleAnimation = new ScaleAnimation((float)0.0, (float)1.0, (float)0.0, (float)1.5);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setDuration(3000);
                ((LinearLayout)findViewById(R.id.searchlayout)).animate().y(listView.getY()).setDuration(4000).start();
            }
        });
         **/
        String niy = new Account().getNiy(this);
        ambildata(niy);
       // Toast.makeText(this, niy, Toast.LENGTH_SHORT).show();
    }
    public void ambildata(String niy){

        JadwalDosen jadwalDosen = new JadwalDosen(context);
        jadwalDosen.JadwalDosen(niy, new JadwalDosen.DsnJadwal() {
            @Override
            public void Hasil(String hasil) {

                List<String> namahari = new ArrayList<>();
                List<Jadwal> jadwales = new ArrayList<>();
                try{
                    JSONObject jsonObject = new JSONObject(hasil);
                    JSONObject hasil2  = jsonObject.getJSONObject("hasil");
                    if(hasil2.has("Senin")){
                        JSONArray jsonArray = hasil2.getJSONArray("Senin");
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Jadwal jadwal = new Jadwal();
                            jadwal.hari = object1.getString("hari");
                            jadwal.ruang = object1.getString("ruang_idruang");
                            jadwal.matkul = object1.getString("namakul");
                            jadwal.jam = object1.getString("jam");
                            jadwal.kelas = object1.getString("kelas");
                            jadwalskuliah.add(jadwal);
                        }
                    }
                    if(hasil2.has("Selasa")){
                        JSONArray jsonArray = hasil2.getJSONArray("Selasa");
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Jadwal jadwal = new Jadwal();
                            jadwal.hari = object1.getString("hari");
                            jadwal.ruang = object1.getString("ruang_idruang");
                            jadwal.matkul = object1.getString("namakul");
                            jadwal.jam = object1.getString("jam");
                            jadwal.kelas = object1.getString("kelas");
                            jadwalskuliah.add(jadwal);
                        }
                    }
                    if(hasil2.has("Rabu")){
                        JSONArray jsonArray = hasil2.getJSONArray("Rabu");
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Jadwal jadwal = new Jadwal();
                            jadwal.hari = object1.getString("hari");
                            jadwal.ruang = object1.getString("ruang_idruang");
                            jadwal.matkul = object1.getString("namakul");
                            jadwal.jam = object1.getString("jam");
                            jadwal.kelas = object1.getString("kelas");
                            jadwalskuliah.add(jadwal);
                        }
                    }
                    if(hasil2.has("Kamis")){
                        JSONArray jsonArray = hasil2.getJSONArray("Kamis");
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Jadwal jadwal = new Jadwal();
                            jadwal.hari = object1.getString("hari");
                            jadwal.ruang = object1.getString("ruang_idruang");
                            jadwal.matkul = object1.getString("namakul");
                            jadwal.jam = object1.getString("jam");
                            jadwal.kelas = object1.getString("kelas");
                            jadwalskuliah.add(jadwal);
                        }
                    }
                    if(hasil2.has("Jumat")){
                        JSONArray jsonArray = hasil2.getJSONArray("Jumat");
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Jadwal jadwal = new Jadwal();
                            jadwal.hari = object1.getString("hari");
                            jadwal.ruang = object1.getString("ruang_idruang");
                            jadwal.matkul = object1.getString("namakul");
                            jadwal.jam = object1.getString("jam");
                            jadwal.kelas = object1.getString("kelas");
                            jadwalskuliah.add(jadwal);
                        }
                    }
                    if(hasil2.has("Sabtu")){
                        JSONArray jsonArray = hasil2.getJSONArray("Sabtu");
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Jadwal jadwal = new Jadwal();
                            jadwal.hari = object1.getString("hari");
                            jadwal.ruang = object1.getString("ruang_idruang");
                            jadwal.matkul = object1.getString("namakul");
                            jadwal.jam = object1.getString("jam");
                            jadwal.kelas = object1.getString("kelas");
                            jadwalskuliah.add(jadwal);
                        }
                    }
                    urutkan();
                    Adapter adapter = new Adapter(jadwalskuliah,SimeruJadwalDosen.this);
                    listView.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(SimeruJadwalDosen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private List<String> haris = new ArrayList<>();
    public void urutkan(){
        String hari = null;
        for(int i=0;i<jadwalskuliah.size();i++){
            if(jadwalskuliah.get(i).hari.equals(hari)){
                haris.add("tidak");
            }else{
                haris.add(jadwalskuliah.get(i).hari);
                hari = jadwalskuliah.get(i).hari;
            }
        }
    }
    public void ambilJadwalhari(String hari,JSONObject object){
        try {
            JSONArray jsonArray = object.getJSONArray(hari);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject object1 = jsonArray.getJSONObject(i);
                Jadwal jadwal = new Jadwal();
                jadwal.hari = object1.getString("hari");
                jadwal.ruang = object1.getString("ruang");
                jadwal.matkul = object1.getString("matakuliah_idmatakuliah");
                jadwal.jam = object1.getString("jam");
                jadwal.kelas = object1.getString("kelas");
                jadwalskuliah.add(jadwal);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class Jadwal{
        String matkul,kelas,ruang,jam,hari;
    }
    class Adapter extends BaseAdapter{
        List<Jadwal> map = new ArrayList<>();
        LayoutInflater inflater;
        private String hari = null;
        Adapter(List<Jadwal> map, Context context){
            this.map      = map;
            this.inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return this.map.size();
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.itemjadwaldosen,viewGroup,false);
            Jadwal jadwal = (Jadwal)getItem(i);
            TextView h      = (TextView)view.findViewById(R.id.hari);
            TextView matkul = (TextView)view.findViewById(R.id.matakuliah);
            TextView ruang  = (TextView)view.findViewById(R.id.ruang);
            TextView kelas  = (TextView)view.findViewById(R.id.kelas);
            TextView jam    = (TextView)view.findViewById(R.id.jam);
            h     .setText(jadwal.hari);
            matkul.setText(jadwal.matkul);
            ruang .setText(jadwal.ruang);
            kelas .setText(jadwal.kelas);
            jam   .setText(jadwal.jam);
            if(haris.get(i).equals("tidak")){
                h.setVisibility(View.GONE);
            }
           // Toast.makeText(SimeruJadwalDosen.this, haris.get(i), Toast.LENGTH_SHORT).show();
            return view;
        }

        @Override
        public Object getItem(int i) {
            return this.map.get(i);
        }
    }

}
