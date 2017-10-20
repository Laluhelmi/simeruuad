package android.pkklppuad.uad4students;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ourFragment.FragmentHari;
import pojo.Ruang;
import server.Account;
import server.UrlKoneksi;

/**
 * Created by Reza on 11/10/2016.
 */
 public  class SimeruJadwalProgramStudi extends AppCompatActivity {

    private ViewPager       haripger;
    private TabLayout       tabLayout;
    private RequestQueue    requestQueue;
    private List<Fragment>  fragments = new ArrayList<>();
    private List<String>    namahari;
    public static Context context;
    private HashMap<String,List<Ruang>> stringListHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jadwal_programstudi);

        context = SimeruJadwalProgramStudi.this;
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff951b")));
        actionBar.setIcon(R.drawable.img_uad);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        haripger  = (ViewPager)findViewById(R   .id.pagerhari);

        requestQueue = Volley.newRequestQueue(this);
        ambilData();

    }

    public void ambilData(){
        Account account = new Account();
        StringRequest stringRequest = new StringRequest(UrlKoneksi.alamat+"/simeru/json/prodi.php?idprodi="+
                account.getDosenProdi(getApplicationContext())
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject hasil      = jsonObject.getJSONObject("hasil");
                    Iterator<String> keysHasil = hasil.keys();
                    namahari               = new ArrayList<>();
                    while (keysHasil.hasNext()){
                        namahari.add(keysHasil.next());
                    }
                    for(int i = 0;i < namahari.size();i++){
                        List<Ruang> ruangs = new ArrayList<>();
                        JSONArray array = hasil.getJSONArray(namahari.get(i));
                        for(int j=0;j<array.length();j++){
                            JSONObject object = array.getJSONObject(j);
                            Ruang ruang     = new Ruang();
                            ruang.dosen     = object.getString("namadosen");
                            ruang.jam       = object.getString("jam");
                            ruang.kelas     = object.getString("kelas");
                            ruang.matkul    = object.getString("namakul");
                            ruang.namaruang = object.getString("ruang_idruang");
                            ruang.semester  = object.getString("semester");
                            ruangs.add(ruang);
                        }
                        Fragment fragment = FragmentHari.newInstance(ruangs);
                        fragments.add(fragment);
                    }
                    Fragmentstate fragmentstate = new Fragmentstate(getSupportFragmentManager(),
                                                                    namahari,fragments);
                    haripger.setAdapter(fragmentstate);
                    tabLayout.setupWithViewPager(haripger);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SimeruJadwalProgramStudi.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }



    class Fragmentstate extends FragmentPagerAdapter{
        List<String> hari;
        List<Fragment> frag = new ArrayList<>();

        public Fragmentstate(FragmentManager manager,List<String> hari,List<Fragment> list){
            super(manager);
            this.hari = hari;
            this.frag = list;
        }
        @Override
        public int getCount() {
            return frag.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return hari.get(position);
        }

        @Override
        public Fragment getItem(int position) {
           return frag.get(position);

        }
    }




}
