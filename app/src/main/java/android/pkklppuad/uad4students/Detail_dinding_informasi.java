package android.pkklppuad.uad4students;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class Detail_dinding_informasi extends Activity {
    private TextView namadosen,kelas,jam,detailinformasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dinding_informasi);

        namadosen       = (TextView)findViewById(R.id.namadosen);
        kelas           = (TextView)findViewById(R.id.kelas);
        jam             = (TextView)findViewById(R.id.jam);
        detailinformasi = (TextView)findViewById(R.id.detailinformasi);

        String nama_dosen = getIntent().getStringExtra("namadosen");
        String kelas_     = getIntent().getStringExtra("kelas");
        String jam_       = getIntent().getStringExtra("jam");
        String deskripsi_ = getIntent().getStringExtra("deskripsi");

        namadosen.setText(nama_dosen);
        kelas.setText(kelas_);
        jam.setText(jam_);
        detailinformasi.setText(deskripsi_);

    }
}
