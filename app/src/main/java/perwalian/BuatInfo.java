package perwalian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.pkklppuad.uad4students.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pojo.Refresh;
import server.Account;
import server.UrlKoneksi;


public class BuatInfo{
    private Context context;

    public BuatInfo(Context context){
        this.context = context;
    }
    public void kirimNotif(final String dosenniy, final String info, final Refresh refresh){
        RequestQueue requestQueue   = Volley.newRequestQueue(this.context);
        String url = UrlKoneksi.alamat+"/simeru/perwalian/postinfo.php";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "sudah disimpan", Toast.LENGTH_SHORT).show();
                refresh.refreshPage();
                ;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("dosenniy",dosenniy);
                data.put("info"    ,info);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
