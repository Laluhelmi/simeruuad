package perwalian;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.pkklppuad.uad4students.R;
import android.pkklppuad.uad4students.SimeruJadwalRuang;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import pojo.Refresh;
import server.Account;
import server.UrlKoneksi;

public class Catatan_Perwalian extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan__perwalian);
        ((Button)findViewById(R.id.buatInfo)).setOnClickListener(this);
        getData();

    }

    public List<Informasi> getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String niy = new Account().getNiy(this);
        final List<Informasi> informasis = new ArrayList<>();
        String url = UrlKoneksi.alamat+"/simeru/perwalian/getInfo.php?dosenniy="+niy;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0;i<jsonArray.length();i++){
                        Informasi informasi = new Informasi();
                        informasi.info = jsonArray.getJSONObject(i).getString("info");
                        informasi.jam  = jsonArray.getJSONObject(i).getString("jam");
                        informasis.add(informasi);
                    }
                    ((ListView)findViewById(R.id.isidindingperwalian)).setAdapter(
                            new ListViewAdapter(getApplicationContext(),informasis)
                    );

                }
                catch (Exception e){
                    Toast.makeText(Catatan_Perwalian.this, e.getMessage(), Toast.LENGTH_SHORT).show();;};
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Catatan_Perwalian.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
        return informasis;
    }

    @Override
    public void onClick(View view) {
            final Dialog dialog = new Dialog(Catatan_Perwalian.this);
            EditText isiinfo = null;
            if(view.getId() == R.id.buatInfo){
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_buat_info);
                final Button   kirimInfo = (Button)dialog.findViewById(R.id.kirim);
                isiinfo            = (EditText)dialog.findViewById(R.id.isiinfo);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,500);
                kirimInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BuatInfo buatInfo = new BuatInfo(getApplicationContext());
                        String niy        = new Account().getNiy(getApplicationContext());
                        String info       = ((EditText)dialog.findViewById(R.id.isiinfo)).getText().toString();
                        buatInfo.kirimNotif(niy,info,new RefreshImp());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    class ListViewAdapter extends BaseAdapter{


        LayoutInflater layoutInflater;
        List<Informasi> informasis = new ArrayList<>();
        public ListViewAdapter(Context context,List<Informasi> informasis){
         layoutInflater  = LayoutInflater.from(context);
            this.informasis = informasis;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getCount() {
            return this.informasis.size();
        }

        @Override
        public Object getItem(int i) {
            return informasis.get(i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = layoutInflater.inflate(R.layout.item_list_catata_perwalian,viewGroup,false);
            Informasi informasi = (Informasi)getItem(i);
            ((TextView)view.findViewById(R.id.jamnya)).setText(informasi.jam);
            ((TextView)view.findViewById(R.id.infonya)).setText(informasi.info);

            return view;
        }

    }
    class Informasi{
        public String info,jam;
    }
    class RefreshImp implements pojo.Refresh{
        @Override
        public void refreshPage() {
            getData();
        }
    }
}
