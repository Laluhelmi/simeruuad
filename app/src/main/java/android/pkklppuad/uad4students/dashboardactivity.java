package android.pkklppuad.uad4students;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import server.Account;

public class dashboardactivity<myhandler1> extends Activity {
	ImageView btn_perwalian, btn_jadwal_dosen, btn_tentang_apps, btn_keluar, btn_simeru;
	final Context context = this;
	private AlertDialog.Builder alertDialogBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		server.Account account = new server.Account();
		if(account.cekSession(this) == null){
			startActivity(new Intent(this,PortalAppsActivity.class));
			finish();
		}
		btn_simeru = (ImageView) findViewById(R.id.img_simeru);
		btn_simeru.setOnClickListener(aksi_simeru);
		btn_perwalian = (ImageView) findViewById(R.id.img_perwalian);
		btn_perwalian.setOnClickListener(aksi_perwalian);
		btn_tentang_apps = (ImageView) findViewById(R.id.img_tentang);
		btn_tentang_apps.setOnClickListener(aksi_tentang_apps);
		btn_keluar = (ImageView) findViewById(R.id.img_keluar);
		btn_keluar.setOnClickListener(aksi_keluar);
	}

	View.OnClickListener aksi_simeru = new  View.OnClickListener(){
		public void onClick(View v){
		Intent i = new Intent(dashboardactivity.this, SimeruActivity.class );
			startActivity(i);
		}
	};
	
	View.OnClickListener aksi_perwalian = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(dashboardactivity.this, PerwalianActivity.class);
			startActivity(i);
			
		}
	};

	View.OnClickListener aksi_jadwal_dosen = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		//	Intent i = new Intent(dashboardactivity.this, JadwalDosenActivity.class);
	//		startActivity(i);
			
		}
	};
	
	View.OnClickListener aksi_tentang_apps = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(dashboardactivity.this, InfoApps.class);
			startActivity(i);
		}
	};
	
	View.OnClickListener aksi_keluar = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setTitle("Yakin untuk Keluar ?");
				alertDialogBuilder
	                    .setMessage("Klik YA untuk keluar..")
	                    .setCancelable(false)
	                    .setPositiveButton("YA",
	                            new DialogInterface.OnClickListener() {
	                                public void onClick(DialogInterface dialog,
	                                        int id) {
										logout();
	                                }
	                            })
	                    .setNegativeButton("TIDAK",
	                            new DialogInterface.OnClickListener() {
	                                public void onClick(DialogInterface dialog,
	                                        int id) {
	                                    dialog.cancel();
	                                }
	                            }).create().show();
	 
	        }
	
	};
	
	@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // filter keyCode, apabila back button yang di click, maka berikan
        // action untuk menampikan alert dialog
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (alertDialogBuilder == null) {
                alertDialogBuilder = new AlertDialog.Builder(this);
            }
 
            alertDialogBuilder.setTitle("Yakin untuk Keluar ?");
            alertDialogBuilder
                    .setMessage("Klik YA untuk keluar..")
                    .setCancelable(false)
                    .setPositiveButton("YA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    // menghancurkan activity / keluar aplikasi
                                    finish();
                                }
                            })
                    .setNegativeButton("TIDAK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
 
                                    dialog.cancel();
                                }
                            }).create().show();
 
        }
        return false;
    }


	public void logout(){
		final String token = FirebaseInstanceId.getInstance().getToken();
		new AsyncTask<Void,Void,String>(){
			@Override
			protected String doInBackground(Void... voids) {
				server.Account account = new server.Account();
				return account.removeToken(token);
				}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				// menghancurkan activity / keluar aplikasi
				server.Account account = new server.Account();
				account.keluar(getApplicationContext());
				startActivity(new Intent(getApplicationContext()
						,PortalAppsActivity.class));
				finish();
			}
		}.execute();
	}
}
