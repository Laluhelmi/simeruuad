package server;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by L on 8/1/17.
 */

public class Account {
    private String niy,password,nama,prodi;
    private SharedPreferences sharedPreferences;

    public Account(){

    }
    public Account(String niy,String password){
        this.niy        = niy;
        this.password   = password;
    }
    public String removeToken(String token){
        try {
            String url = UrlKoneksi.alamat+"/simeru/keluar.php";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("POST");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("token="+token);
            con.setDoOutput(true);
            DataOutputStream dataout = new DataOutputStream(con.getOutputStream());
            dataout.writeBytes(stringBuilder.toString());
            dataout.flush();
            dataout.close();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            buffer.close();
            return sb.toString();
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public String Login(String token){
        try {
            String url = UrlKoneksi.alamat+"/simeru/index.php";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("POST");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("niy="+this.niy);
            stringBuilder.append("&password="+this.password);
            stringBuilder.append("&token="+token);
            con.setDoOutput(true);
            DataOutputStream dataout = new DataOutputStream(con.getOutputStream());
            dataout.writeBytes(stringBuilder.toString());
            dataout.flush();
            dataout.close();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            buffer.close();
            return sb.toString();
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public void buatSession(Context context,String niy,String password,String nama,String prodi){
        sharedPreferences               = context.getSharedPreferences("Akun",Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("niy",         niy);
        editor.putString("password",    password);
        editor.putString("namadosen",   nama);
        editor.putString("prodi"    ,   prodi);
        editor.commit();
    }
    public String getNama(Context context){
        sharedPreferences = context.getSharedPreferences("Akun",Context.MODE_APPEND);
        return sharedPreferences.getString("namadosen",null);
    }
    public String getDosenProdi(Context context){
        sharedPreferences               = context.getSharedPreferences("Akun",Context.MODE_APPEND);
        return sharedPreferences.getString("prodi",null);
    }
    public String    cekSession(Context context){
        sharedPreferences = context.getSharedPreferences("Akun",Context.MODE_APPEND);
        return sharedPreferences.getString("niy",null);
    }
    public void keluar(Context context){
        sharedPreferences = context.getSharedPreferences("Akun",Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public String getNiy(Context context){
        sharedPreferences = context.getSharedPreferences("Akun",Context.MODE_APPEND);
        return sharedPreferences.getString("niy",null);
    }
}
