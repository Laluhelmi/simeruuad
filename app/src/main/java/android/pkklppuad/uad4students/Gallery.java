package android.pkklppuad.uad4students;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import server.UrlKoneksi;


public class Gallery extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME ,
                MediaStore.Images.ImageColumns.DATE_TAKEN};

        cursor = getContentResolver().query(uri, projection, null,
                null, orderBy);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int tanggal = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        ImageView imageView = (ImageView)findViewById(R.id.gambar);
        File file = new File(listOfAllImages.get(0));
        Bitmap bitmap = BitmapFactory.decodeFile(listOfAllImages.get(0));
        imageView.setImageBitmap(bitmap);
        Uri uri1 = Uri.parse("file://"+listOfAllImages.get(0));
        new Asyn(listOfAllImages.get(0),uri1).execute();
    }


    public static String uploadKeServer(String file, Context context, Uri uri, String idpesan){
        HttpURLConnection conn = null;
        String tipefile = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String respon = null;
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 8024 * 8024;
        try {
            URL url = new URL(UrlKoneksi.alamat+"simeru/simpanfoto.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());
            FileInputStream fileInputStream=null;
            fileInputStream = (FileInputStream)
                    context.getContentResolver().openInputStream(uri);

            MimeTypeMap map = MimeTypeMap.getSingleton();
            tipefile = map.getExtensionFromMimeType(context.getContentResolver().getType(uri));
            String param = "type="+tipefile;
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"foto\";filename=\""
                    +file+"\""+lineEnd+"");

            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"tipe\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes("fd");
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"idpesan\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes("dfd");
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens);
            fileInputStream.close();
            dos.flush();
            dos.close();
            BufferedReader buf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            buf.close();;
            respon = sb.toString();

        }catch (Exception e){
            respon = "error "+e.getMessage();
        }
        return respon;
    }
    class Asyn extends AsyncTask<String,Void,String>{
        private Uri uri;
        private String file;
        public Asyn(String file,Uri uri){
            this.uri = uri;
            this.file = file;

        }
        @Override
        protected String doInBackground(String... param) {
            return uploadKeServer(file,getApplicationContext(),uri,null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Gallery.this, s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
