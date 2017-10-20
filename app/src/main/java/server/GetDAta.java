package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by L on 8/2/17.
 */

public class GetDAta {
    public String getDosenJadwal(String niy){
        try {
            String url = UrlKoneksi.alamat+"/simeru/dosenmatkul.php?niy="+niy;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("GET");
            con.setDoOutput(true);
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
}
