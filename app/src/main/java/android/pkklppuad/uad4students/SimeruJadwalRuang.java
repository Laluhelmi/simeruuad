package android.pkklppuad.uad4students;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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

/**
 * Created by Reza on 11/10/2016.
 */
public class SimeruJadwalRuang extends Activity implements AdapterView.OnItemClickListener{
    Spinner spinerkampus,spinerlantai, spinerhari;
    Button bcari;
    private RequestQueue requestQueue;
    private String[] ruang;
    private List<String> stringsruang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jadwal_ruangan);
        requestQueue = Volley.newRequestQueue(this);
        String[] kampus = new String[]{
                                "kampus 1","kampus 2","kampus 3","kampus 4",
                                "kampus 5", "kampus 6"
                                };
         final String[] idkampus = new String[]{
                                 "kmp01","kmp02","kmp03","kmp04",
                                 "kmp05", "kmp06"

                                 };
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.listkampus,R.id.listviwe,kampus);
        ListView listView = (ListView)findViewById(R.id.listkampus);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stringsruang = new ArrayList<>();
                ambilRuang(idkampus[i]);
            }
        });
    }


    public void ambilRuang(String id){
         StringRequest stringRequest = new StringRequest(UrlKoneksi.alamat+"/simeru/json/tampilruang.php?id="+id
                 , new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try{
                     JSONObject object = new JSONObject(response);
                     JSONArray  hasil  = object.getJSONArray("hasil");
                     for(int i=0; i < hasil.length(); i++){
                         JSONObject data= hasil.getJSONObject(i);
                         stringsruang.add(data.getString("idruang"));
                     }
                     ArrayAdapter<String> adapter = new ArrayAdapter<String>(SimeruJadwalRuang.this,
                             R.layout.poplistview,R.id.listviwe,stringsruang);
                     Dialog dialog = new Dialog(SimeruJadwalRuang.this);
                     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                     dialog.setContentView(R.layout.popup);
                     Window window = dialog.getWindow();
                     WindowManager.LayoutParams layoutParams = window.getAttributes();
                     ListView listruang = (ListView)dialog.findViewById(R.id.listruang);
                     listruang.setAdapter(adapter);
                     listruang.setOnItemClickListener(SimeruJadwalRuang.this);
                     if(stringsruang.size() < 1){
                         ((TextView)dialog.findViewById(R.id.tidakadainfo)).setVisibility(View.VISIBLE);
                     }else{
                         ((TextView)dialog.findViewById(R.id.tidakadainfo)).setVisibility(View.GONE);
                     }
                     dialog.show();
                 }catch (Exception e){
                     Toast.makeText(SimeruJadwalRuang.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

             }
         });
        requestQueue.add(stringRequest);
     };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(),TampilJadwalRuang.class);
        intent.putExtra("data",stringsruang.get(i));
        startActivity(intent);

    }
}
