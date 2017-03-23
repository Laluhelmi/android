package com.example.l.afiefbelajar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;

public class UploadFile extends AppCompatActivity {
    private Button upload;
    private Button ambilFile,download;
    private String file;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        upload = (Button)findViewById(R.id.upload);
        ambilFile = (Button)findViewById(R.id.ambilfile);
        download = (Button)findViewById(R.id.download);

        ambilFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("application/pdf;image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void,Void,String>(){
                    @Override
                    protected String doInBackground(Void... voids) {

                        return new Send().kirim(file,UploadFile.this,uri);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        Toast.makeText(UploadFile.this, s, Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void,Void,String>(){
                    @Override
                    protected String doInBackground(Void... voids) {
                        return new Send().downloadFile();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        notif(s);
                        Toast.makeText(UploadFile.this, s, Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }
        });
    }

    public String ambilNama(Uri uri){

        String[] projection = { MediaStore.Files.FileColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public String namanya(Uri uri){
        Cursor cursor = null;
        String displayName = null;
        try {
            cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                displayName = cursor.getString(idx);
                cursor.close();
            }
        } finally {
            cursor.close();
        }
        return displayName;
    }
    public String nama(Uri uri){
        String path= null;
        String[] projection = {MediaStore.MediaColumns.DATA};

        ContentResolver cr = getApplicationContext().getContentResolver();
        Cursor metaCursor = cr.query(uri, projection, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    path = metaCursor.getString(0);
                }
            } finally {
                metaCursor.close();
            }
        }
        return path;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            file = data.getData().toString();
            uri = data.getData();
            Toast.makeText(this, "sudah "+file, Toast.LENGTH_SHORT).show();
        }
    }

    public void notif(String nama){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Belajar/"+nama);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        int koderekuest = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, koderekuest, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Download selesai")
                .setContentTitle("selesai Bro")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        long[] pattern = {500,500};
        noBuilder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        noBuilder.setSound(alarmSound);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }
}
