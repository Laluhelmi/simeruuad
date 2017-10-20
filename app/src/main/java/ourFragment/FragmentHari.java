package ourFragment;

import android.content.Context;
import android.pkklppuad.uad4students.R;
import android.pkklppuad.uad4students.SimeruJadwalProgramStudi;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pojo.Ruang;


public class FragmentHari extends Fragment {

    private ListView listView;
    private List<Ruang> ruangs = new ArrayList<>();
    private List<String> cek = new ArrayList<>();
    private Context context;

    public static FragmentHari newInstance(List<Ruang> ruangs){
        FragmentHari fragmentHari = new FragmentHari();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",(Serializable) ruangs);
        fragmentHari.setArguments(bundle);
        return fragmentHari;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v          = inflater.inflate(R.layout.activity_fragment_hari,container,false);
        this.ruangs     = (List<Ruang>) getArguments().getSerializable("data");
        this.listView   = (ListView)v.findViewById(R.id.listruang);
        urutkan();
        Base base = new Base(ruangs, getActivity().getApplicationContext());
        listView.setAdapter(base);

        return v;
    }
    public void urutkan(){
        String ruang = null;
        for(int i=0;i<ruangs.size();i++){
            if(ruangs.get(i).namaruang.equals(ruang)){
                cek.add("tidak");
            }else{
                cek.add(ruangs.get(i).namaruang);
                ruang = ruangs.get(i).namaruang;
            }
        }
    }
    class Base extends BaseAdapter{
        List<Ruang> ruangs;
        LayoutInflater layoutInflater;
        public Base(List<Ruang> ruangs, Context context){
            layoutInflater = LayoutInflater.from(context);
            this.ruangs    = ruangs;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getCount() {
            return ruangs.size();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = layoutInflater.inflate(R.layout.item_list_ruang,viewGroup,false);
            TextView matakuliah = (TextView)view.findViewById(R.id.matakuliah);
            TextView kelas = (TextView)view.findViewById(R.id.kelas);
            TextView ruang = (TextView)view.findViewById(R.id.ruang_name);
            TextView jam = (TextView)view.findViewById(R.id.jam);
            TextView semester = (TextView)view.findViewById(R.id.semester);
            TextView dosen = (TextView)view.findViewById(R.id.dosen);

            Ruang r =(Ruang) getItem(i);
            matakuliah.setText(r.matkul);
            ruang.setText(r.namaruang);
            kelas.setText(r.kelas);
            jam.setText(r.jam);
            semester.setText(r.semester);
            dosen.setText(r.dosen);

            if(cek.get(i).equals("tidak")){
                ruang.setVisibility(View.GONE);
            }

            return view;
        }

        @Override
        public Object getItem(int i) {
            return ruangs.get(i);
        }
    }
}
