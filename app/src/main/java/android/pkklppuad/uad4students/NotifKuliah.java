package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import server.Account;
import server.GetDAta;
import server.UrlKoneksi;

/**
 * Created by Yohani on 10/11/2016.
 */
public class NotifKuliah extends Activity {
    private EditText deskripsi;
    List<String> matkul = new ArrayList<>();
    List<String> kelases  = new ArrayList<>();
    List<String> haris   = new ArrayList<>();
    Spinner hari,matkulspinner,kelas,keterangan;
    private RequestQueue request;
    private Map<String,Matakuliah> stringMatakuliahMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_kuliah);
        request = Volley.newRequestQueue(this);
        stringMatakuliahMap = new HashMap<>();
        new getData().execute();
        matkul.add("Pilih Mata Kuliah");
        kelases.add("Pilih Kelas");
        haris.add("Pilih Hari");
        Button kirim            = (Button) findViewById(R.id.Kirim);
        matkulspinner           = (Spinner)findViewById(R.id.matkul);
        hari                    = (Spinner)findViewById(R.id.harinya);
        kelas                   = (Spinner)findViewById(R.id.kelasnya);
        keterangan              = (Spinner)findViewById(R.id.keterandosen);
        final List<String> ket        = new ArrayList<>();
        ket.add("Diganti Elearning");ket.add("Diganti Tugas");ket.add("Diganti Hari Lain");
        ket.add("Lain-Lain");
        ArrayAdapter<String> arrayAdapterKeterangan = new ArrayAdapter<String>(NotifKuliah.this,
                android.R.layout.simple_spinner_item,ket);
        keterangan.setAdapter(arrayAdapterKeterangan);
        keterangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(ket.get(i).equals("Lain-Lain")){
                    ((EditText)findViewById(R.id.deskripsiinformasi)).setVisibility(View.VISIBLE);
                }else{
                    ((EditText)findViewById(R.id.deskripsiinformasi)).setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        deskripsi = (EditText) findViewById(R.id.deskripsiinformasi);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInformasi();
                onBackPressed();
            }
        });
    }

    public void gantiHari(String kelas1,String matkul){
        List<String> strings = new ArrayList<>();

        strings.add(stringMatakuliahMap.get(matkul).kelasdanhari.get(kelas1));
        ArrayAdapter<String> arrayAdapterhari = new ArrayAdapter<String>(NotifKuliah.this,
                android.R.layout.simple_spinner_item,strings);
        hari.setAdapter(arrayAdapterhari);
    }

    public  void itemselected(){
        matkulspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i != 0){
                        final String matakuliah = matkul.get(i);
                        final List<String> kelasmatkul = new ArrayList<String>();
                        for(String key : stringMatakuliahMap.get(matkul.get(i)).kelasdanhari.keySet()){
                            kelasmatkul.add(key);
                        }
                        ArrayAdapter<String> arrayAdapterkelas = new ArrayAdapter<String>(NotifKuliah.this,
                                android.R.layout.simple_spinner_item,kelasmatkul);
                        kelas.setAdapter(arrayAdapterkelas);
                        kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long l) {
                                gantiHari(kelasmatkul.get(i2),matakuliah);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sendInformasi(){
        final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        final Date hariIni = Calendar.getInstance().getTime();
        final String informasi = ((EditText)findViewById(R.id.deskripsiinformasi)).getText().toString();
        String[] datetime = dateFormat.format(hariIni).split(" ");
        final String date   = datetime[0];
        final String time   = datetime[0];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlKoneksi.alamat+"simeru/postinfo.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NotifKuliah.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NotifKuliah.this, "gagal "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
               // stringMap.put(""    ,new Account().getNiy(getApplicationContext()));
                stringMap.put("hari"       ,hari.getSelectedItem().toString());
                stringMap.put("kelas"      ,kelas.getSelectedItem().toString());
                stringMap.put("matakuliah" ,matkulspinner.getSelectedItem().toString());
                stringMap.put("keterangan" ,keterangan.getSelectedItem().toString());
                stringMap.put("tanggal"    ,dateFormat.format(hariIni));
                stringMap.put("lainlain"  ,((EditText)findViewById(R.id.deskripsiinformasi)).
                                            getText().toString());
                return stringMap;
            }
        };
        request.add(stringRequest);
    }
    class getData extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Account account = new Account();
            return new GetDAta().getDosenJadwal(account.getNiy(getApplicationContext()));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i =0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    if(stringMatakuliahMap.containsKey(object.getString("matakuliah")) == false){
                        Matakuliah matakuliah = new Matakuliah();
                        matakuliah.nama = object.getString("matakuliah");
                        matakuliah.kelasdanhari.put(object.getString("kelas"),object.getString("hari"));
                        stringMatakuliahMap.put(object.getString("matakuliah"),matakuliah);
                        matkul.add(object.getString("matakuliah"));
                    }else{
                        stringMatakuliahMap.get(object.getString("matakuliah"))
                        .kelasdanhari.put(object.getString("kelas"),object.getString("hari"));
                    }

                }
                ArrayAdapter<String> arrayAdapterhari = new ArrayAdapter<String>(NotifKuliah.this,
                        android.R.layout.simple_spinner_item,matkul);
                matkulspinner.setAdapter(arrayAdapterhari);
                itemselected();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Matakuliah{
        String nama;
        Map<String,String>kelasdanhari = new HashMap<>();

    }
}
