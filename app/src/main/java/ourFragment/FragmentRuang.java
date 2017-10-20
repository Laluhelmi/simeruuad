package ourFragment;

import android.content.Context;
import android.pkklppuad.uad4students.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import pojo.Ruang;


public class FragmentRuang extends Fragment{
    private ListView listView;
    private List<Ruang> ruangList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_ruang,container,false);
        listView  = (ListView)view.findViewById(R.id.listview);
        ruangList = (List<Ruang>) getArguments().getSerializable("data");
        BaseAdaptersaya
                baseAdaptersaya = new BaseAdaptersaya(ruangList,getActivity().getApplicationContext());
        listView.setAdapter(baseAdaptersaya);
        if(ruangList.size() < 1){
            ((TextView)view.findViewById(R.id.tidakadainfo)).setVisibility(View.VISIBLE);
        }
        return view;
    }

    class BaseAdaptersaya extends BaseAdapter{
        private List<Ruang> ruangList;
        LayoutInflater layoutInflater;

        public BaseAdaptersaya(List<Ruang> ruangs, Context context){
            this.ruangList = ruangs;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public Object getItem(int i) {
            return ruangList.get(i);
        }

        @Override
        public int getCount() {
            return ruangList.size();
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView jam,matakuliah,kelas,semester,sks,dosen;
            Ruang ruang     = (Ruang) getItem(i);
            view            = layoutInflater.inflate(R.layout.item_ruang_list_jam,viewGroup,false);
            ((TextView)view.findViewById(R.id.jam)).setText("Jam "+ruang.jam);
            ((TextView)view.findViewById(R.id.matakuliah)).setText(ruang.matkul);
            ((TextView)view.findViewById(R.id.kelas)).setText(ruang.kelas);
            ((TextView)view.findViewById(R.id.semester)).setText(ruang.semester);
            ((TextView)view.findViewById(R.id.sks)).setText(ruang.sks);
            ((TextView)view.findViewById(R.id.dosen)).setText(ruang.dosen);

            return view;
        }
    }

}
