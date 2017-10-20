package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Reza on 10/10/2016.
 */
public class SimeruActivity extends Activity {

    ImageView btn_jdwldosen, btn_notifkuliah, btn_programstudi, btn_jadwalruang,img_dinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simeru);

        btn_jdwldosen = (ImageView) findViewById(R.id.img_jdwldosen);
        btn_jdwldosen.setOnClickListener(aksi_jdwldosen);
        btn_notifkuliah = (ImageView) findViewById(R.id.img_notifkuliah);
        btn_notifkuliah.setOnClickListener(aksi_notifkuliah);
        btn_programstudi = (ImageView) findViewById(R.id.img_jadwalprogramstudi);
        btn_programstudi.setOnClickListener(aksi_programstudi);
        btn_jadwalruang = (ImageView) findViewById(R.id.img_jdwlruang);
        btn_jadwalruang.setOnClickListener(aksi_jadwalruang);
        img_dinding = (ImageView) findViewById(R.id.img_dinding);
        img_dinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Dindinginformasi.class);
                startActivity(i);

            }
        });
    }

    View.OnClickListener aksi_jdwldosen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SimeruActivity.this, SimeruJadwalDosen.class);
            startActivity(i);
        }
    };
    View.OnClickListener aksi_notifkuliah = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent i = new Intent(SimeruActivity.this, NotifKuliah.class);
            startActivity(i);
        }
    };
    View.OnClickListener aksi_programstudi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SimeruActivity.this, SimeruJadwalProgramStudi.class);
            startActivity(i);
        }
    };
    View.OnClickListener aksi_jadwalruang = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SimeruActivity.this, SimeruJadwalRuang.class);
            startActivity(i);
        }
    };

}

