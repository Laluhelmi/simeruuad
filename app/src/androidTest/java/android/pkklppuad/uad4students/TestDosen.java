package android.pkklppuad.uad4students;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.SettableFuture;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 10/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class TestDosen {

    String datanya ="{\"hasil\":{\"Senin\":[{\"idajar\":\"4\",\"dosen_niynipnidn\":\"0014107301\",\"matakuliah_idmatakuliah\":\"1815530\",\"ruang_idruang\":\"Ruang.8\",\"kelas\":\"A\",\"jam\":\"08.45-10.45\",\"hari\":\"Senin\",\"niynipnidn\":\"0014107301\",\"namadosen\":\"Ali Tarmuji, S.T., M.Cs\",\"program_studi_idprogram_studi\":\"tif00\",\"password\":\"da532bf806defa26fdbeee5dd2e0d68f\",\"idmatakuliah\":\"1815530\",\"namakul\":\"Dasar Sistem Komputer\",\"semester\":\"1\",\"sks\":\"3\"}],\"Rabu\":[{\"idajar\":\"56\",\"dosen_niynipnidn\":\"0014107301\",\"matakuliah_idmatakuliah\":\"1815530\",\"ruang_idruang\":\"Ruang.8\",\"kelas\":\"B\",\"jam\":\"10.30-12.10\",\"hari\":\"Rabu\",\"niynipnidn\":\"0014107301\",\"namadosen\":\"Ali Tarmuji, S.T., M.Cs\",\"program_studi_idprogram_studi\":\"tif00\",\"password\":\"da532bf806defa26fdbeee5dd2e0d68f\",\"idmatakuliah\":\"1815530\",\"namakul\":\"Dasar Sistem Komputer\",\"semester\":\"1\",\"sks\":\"3\"}],\"Kamis\":[{\"idajar\":\"90\",\"dosen_niynipnidn\":\"0014107301\",\"matakuliah_idmatakuliah\":\"1876031\",\"ruang_idruang\":\"3.1.313\",\"kelas\":\"A\",\"jam\":\"14.15-16.05\",\"hari\":\"Kamis\",\"niynipnidn\":\"0014107301\",\"namadosen\":\"Ali Tarmuji, S.T., M.Cs\",\"program_studi_idprogram_studi\":\"tif00\",\"password\":\"da532bf806defa26fdbeee5dd2e0d68f\",\"idmatakuliah\":\"1876031\",\"namakul\":\"Penjaminan Kualitas Perangkat Lunak\",\"semester\":\"7\",\"sks\":\"3\"}]}}";
    Context context;
    @Before
    public void Test(){
        this.context = InstrumentationRegistry.getContext();
    }
    public TestDosen(){
    }

    @Test
    public void useTestDosen() throws Exception{

        JadwalDosen jadwalDosen = new JadwalDosen(context);
        final SettableFuture<String> ff = SettableFuture.create();

        jadwalDosen.JadwalDosen("0014107301", new JadwalDosen.DsnJadwal() {
            @Override
            public void Hasil(String hasil) {
                ff.set(hasil);
            }
        });

        String isi = ff.get(5, TimeUnit.SECONDS);
        assertEquals(isi,datanya);
    }
}
