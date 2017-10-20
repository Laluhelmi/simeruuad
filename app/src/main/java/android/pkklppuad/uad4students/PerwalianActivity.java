package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import perwalian.Catatan_Perwalian;
import perwalian.ChatActivity;

public class PerwalianActivity extends Activity implements View.OnClickListener{
	
	ImageView btn_mahasiswa, btn_catatan, btn_dinding, btn_chatting;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_perwalian);
		btn_catatan = (ImageView)findViewById(R.id.img_catatanp);
		btn_catatan.setOnClickListener(this);
		((ImageView)findViewById(R.id.img_mhs)).setOnClickListener(this);
		((ImageView)findViewById(R.id.img_chat)).setOnClickListener(this);

	};

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.img_catatanp){
			startActivity(new Intent(getApplicationContext(), Catatan_Perwalian.class));
		}else if(view.getId() == R.id.img_mhs){
			Intent i = new Intent(PerwalianActivity.this, MahasiswaActivity.class);
			startActivity(i);
		}
		else if(view.getId() == R.id.img_chat){
			startActivity(new Intent(getApplicationContext(), ChatActivity.class));
		}

	}

}
