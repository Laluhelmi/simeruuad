package android.pkklppuad.uad4students;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class PemberitahuanTerkirim extends Activity{
	
	TextView set_pesan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_chat);
		set_pesan = (TextView) findViewById(R.id.text_hasil);
		Bundle b = getIntent().getExtras();
		set_pesan.setText(b.getString("pesan"));
	}

}
