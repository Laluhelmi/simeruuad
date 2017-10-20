package android.pkklppuad.uad4students;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import server.UrlKoneksi;

/**
 * Created by L on 10/4/17.
 */

public class JadwalDosen {

    RequestQueue requestQueue;
    String Url = UrlKoneksi.alamat+"/simeru/json/jadwaldosen.php?niy=";

    public JadwalDosen(Context context){

        requestQueue = Volley.newRequestQueue(context);
    }

    public void JadwalDosen(String niy, final DsnJadwal dsnJadwal){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url + niy, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("data", response.toString());
                dsnJadwal.Hasil(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public interface DsnJadwal{
        void Hasil (String hasil);
    }
}
