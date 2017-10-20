package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CatatanPerwalianActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_catatan_perwalian);
	
		ArrayList<ItemDaftar> list = new ArrayList<ItemDaftar>();
		list.add(new ItemDaftar("Perwalian 1", "07/08/2015"));
		list.add(new ItemDaftar("Perwalian 2", "29/08/2015"));
		list.add(new ItemDaftar("Perwalian 3", "12/10/2015"));
	
		CatatanPerwalianAdapter adapter = new CatatanPerwalianAdapter(this, list);
	
		ListView listView = (ListView) findViewById(R.id.list_catatan);
		listView.setAdapter(adapter);
	
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Intent i = new Intent(CatatanPerwalianActivity.this, CatatanPerwalianAdapter.class );
				startActivity(i);
			}
		});
	}
	
	

}
