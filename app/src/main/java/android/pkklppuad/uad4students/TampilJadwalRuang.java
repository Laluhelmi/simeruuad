package android.pkklppuad.uad4students;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ourFragment.FragmentRuang;
import pojo.Ruang;
import server.UrlKoneksi;


public class TampilJadwalRuang extends AppCompatActivity {

    private List<Fragment> fragments;
    private List<String>   haris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_jadwal_ruang);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff951b")));
        actionBar.setIcon(R.drawable.img_uad);
        String data = getIntent().getStringExtra("data");
        actionBar.setTitle("Ruang "+data);
        fragments = new ArrayList<>();
        haris     = new ArrayList<>();
        haris.add("Senin");haris.add("Selasa");haris.add("Rabu");haris.add("Kamis");
        haris.add("Jumat");haris.add("Sabtu");
        ambilData(data);
    }

    class Fragments extends FragmentPagerAdapter{
        private List<String>    namaData    = new ArrayList<>();
        private List<Fragment> fragments   = new ArrayList<>();

        Fragments(FragmentManager manager,List<String> namadata,List<Fragment> fragments){
            super(manager);
            this.namaData  = namadata;
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return namaData.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
    public void ambilData(String id){
        final StringRequest stringRequest = new StringRequest(UrlKoneksi.alamat + "/simeru/json/jadwalruang.php?ruang="+id,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object           = new JSONObject(response);
                    JSONObject hasil            = object.getJSONObject("hasil");
                    Iterator<String> iterator   = hasil.keys();
                    List<String> strings        = new ArrayList<>();

                    for(int i = 0;i < haris.size(); i++){
                        List<Ruang> ruangs      = new ArrayList<>();
                        if(hasil.has(haris.get(i))){
                            JSONArray jsonArrayhari = hasil.getJSONArray(haris.get(i));
                            for(int j = 0;j < jsonArrayhari.length(); j++){
                                JSONObject objec = jsonArrayhari.getJSONObject(j);
                                Ruang      ruang = new Ruang();
                                ruang.dosen     = objec.getString("namadosen");
                                ruang.jam       = objec.getString("jam");
                                ruang.kelas     = objec.getString("kelas");
                                ruang.matkul    = objec.getString("namakul");
                                ruang.namaruang = objec.getString("ruang_idruang");
                                ruang.semester  = objec.getString("semester");
                                ruang.sks       = objec.getString("sks");
                                ruangs.add(ruang);
                            }
                        }else{
                        }
                        Bundle bundle     = new Bundle();
                        bundle.putSerializable("data",(Serializable)ruangs);
                        Fragment fragment = new FragmentRuang();
                        fragment.setArguments(bundle);
                        fragments.add(fragment);
                    }
                    Fragments fragmentmanager = new Fragments(getSupportFragmentManager(),
                            haris,fragments);
                    ViewPager viewPager = (ViewPager)findViewById(R.id.pagerhari);
                    TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
                    viewPager.setAdapter(fragmentmanager);
                    tabLayout.setupWithViewPager(viewPager);
                }catch (Exception e){
                    Toast.makeText(TampilJadwalRuang.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TampilJadwalRuang.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
