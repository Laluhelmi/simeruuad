package android.pkklppuad.uad4students;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
		import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import server.Account;

public class PortalAppsActivity extends Activity implements OnClickListener{
	
	private EditText user, pass;

	// private EditText urlserver;
	private Button mSubmit;
	private Account account;
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// php login script location:
	//private static final String LOGIN_URL = "http://192.168.43.152/perwalianuad/login.php";
	private static final String LOGIN_URL = "http://perwalian.esy.es/index.php/mhs/servicelogin";
	// JSON element ids from repsonse of php script:
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		// setup input fields
		user = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.password);

		// setup buttons
		mSubmit = (Button) findViewById(R.id.msubmit);

		// register listeners
		mSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
//		new AttemptLogin().execute();
			//Intent a = new Intent(this, dashboardactivity.class);
			//startActivity(a);
		if(v.getId() == R.id.msubmit){
			account = new Account(user.getText().toString(),pass.getText().toString());
			new AttemptLogin().execute();
		}
	}

	class AttemptLogin extends AsyncTask<String, String, String> {
		boolean failure = true;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PortalAppsActivity.this);
			pDialog.setMessage("Masuk Aplikasi...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			String token = FirebaseInstanceId.getInstance().getToken();
			return account.Login(token);
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				try{
					JSONObject jsonObject = new JSONObject(file_url);
					String hasil  = jsonObject.getString("hasil");
					if(hasil.equals("sukses")){
					JSONObject data = jsonObject.getJSONObject("data");
						account.buatSession(getApplicationContext(),
								data.getString("niynipnidn"),
								data.getString("password") ,
								data.getString("namadosen"),
								data.getString("program_studi_idprogram_studi"));
						startActivity(new Intent(getApplicationContext(),dashboardactivity.class));
						finish();

					}else{
						Toast.makeText(PortalAppsActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
