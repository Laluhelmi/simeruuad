package android.pkklppuad.uad4students;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Tampildetailinformasi extends Activity {

    TextView deskripsi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_detail_informasi);

        deskripsi = (TextView) findViewById(R.id.tv_deskripsi);

        Bundle bundle=getIntent().getExtras();
        String deskripsii=bundle.get("deskripsi").toString();

        deskripsi.setText(deskripsii);


    }
}
