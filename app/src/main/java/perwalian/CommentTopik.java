package perwalian;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.pkklppuad.uad4students.R;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.CommentAdapter;
import pojo.CommentPojo;
import pojo.TampilPerwalin;
import server.Account;
import server.UrlKoneksi;

public class CommentTopik extends Activity {
    private TextView judul;
    private Button   kirim;
    private EditText inputComment;
    private ListView comments;
    public static boolean isCommentActive = false;
    public static String idCommentActive = null;
    private List<CommentPojo> commentPojos = new ArrayList<>();
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_topik);
        final String idTopik      = getIntent().getStringExtra("idcomment");
        idCommentActive     = idTopik;
        String judulTopik   = getIntent().getStringExtra("judulcomment");
        getComment(idTopik);
        judul               = (TextView)findViewById(R.id.judultopik);
        comments            = (ListView)findViewById(R.id.isicomment);
        judul.setText(judulTopik);
        kirim               = (Button)findViewById(R.id.kirim);
        inputComment        = (EditText) findViewById(R.id.inputkomment);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment(inputComment.getText().toString(),idTopik);
                inputComment.setText("");
            }
        });
        //inisialisasi acitivity comment active
        isCommentActive = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("Comment"));
    }
    public void postComment(final String comment, final String idTopik){
        RequestQueue requestQueue   = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                UrlKoneksi.alamat+"simeru/perwalian/postComment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CommentTopik.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(CommentTopik.this, "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                String idUser           = new Account().getNiy(getApplicationContext());
                String username         = new Account().getNama(getApplicationContext());
                data.put("comment" ,comment);
                data.put("id_topik",idTopik);
                data.put("role"    ,"dosen");
                data.put("id_user"  ,idUser);
                data.put("username" ,username);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getComment(final String idTopik){
        RequestQueue  requestQueue  = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(UrlKoneksi.alamat+"simeru/perwalian/getComment.php?idtopik="+idTopik,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0;i < jsonArray.length(); i ++){
                        CommentPojo commentPojo = new CommentPojo();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        commentPojo.comment   = jsonObject.getString("comment");
                        commentPojo.id_topik  = idTopik;
                        commentPojo.id_user   = jsonObject.getString("id_user");
                        commentPojo.idComment = jsonObject.getString("id");
                        commentPojo.role      = jsonObject.getString("role");
                        commentPojo.username  = jsonObject.getString("username");
                        commentPojos.add(commentPojo);
                    }
                    commentAdapter = new CommentAdapter(getApplicationContext(),commentPojos);
                    comments.setAdapter(commentAdapter);

                }catch (Exception e){
                    Toast.makeText(CommentTopik.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CommentTopik.this, "Error di comment"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCommentActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isCommentActive = false;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CommentPojo commentPojo = new CommentPojo();
            commentPojo.username    = intent.getStringExtra("username");
            commentPojo.comment     = intent.getStringExtra("comment");
            commentPojos.add(commentPojo);
            commentAdapter.notifyDataSetChanged();
            comments.smoothScrollToPosition(commentPojos.size()-1);
        }
    };
}
