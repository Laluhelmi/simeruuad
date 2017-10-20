package perwalian;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import server.UrlKoneksi;

/**
 * Created by L on 9/23/17.
 */

public class KirimInfo {
    private Context context;
    public KirimInfo(Context context){
        this.context = context;
    }
    public void kirimNotif(final String dosenniy, final String info, final Activity activity){
        RequestQueue requestQueue   = Volley.newRequestQueue(context);
        String url = UrlKoneksi.alamat+"/simeru/perwalian/postinfo.php";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
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
