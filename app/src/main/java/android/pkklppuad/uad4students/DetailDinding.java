package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailDinding extends Activity{
	TextView niy, waktu, info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail_dinding);
		
		Intent i = getIntent();
		
		String niyy = i.getStringExtra("niy");
		String wakt = i.getStringExtra("waktu");
		String inf = i.getStringExtra("info");
		
		niy = (TextView)findViewById(R.id.tampil_dosen_wali);
		waktu = (TextView)findViewById(R.id.tampil_waktu);
		info = (TextView)findViewById(R.id.tampil_informasi);
		
		niy.setText(niyy);
		waktu.setText(wakt);
		info.setText(inf);
		
		
	}
	
	

}
