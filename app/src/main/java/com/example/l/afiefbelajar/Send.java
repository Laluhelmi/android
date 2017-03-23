package com.example.l.afiefbelajar;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by L on 1/18/17.
 */

public class Send {
    public String kirim(String file, Context context,Uri uri){
        HttpURLConnection conn = null;
        String tipefile = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String respon = null;
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        try {

            File sourceFile = new File(Environment.getExternalStorageDirectory()+file);
            FileInputStream fileInputStream = (FileInputStream) context.getContentResolver().openInputStream(uri);

            MimeTypeMap map = MimeTypeMap.getSingleton();
            tipefile = map.getExtensionFromMimeType(context.getContentResolver().getType(uri));
            URL url = new URL("http://192.168.1.33/example2/upload/toupload.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            String param = "type="+tipefile;
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"filenya\";filename=\""
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
            //param baru
            dos.writeBytes("Content-Disposition: form-data; name=\"tipe\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(tipefile);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + twoHyphens);
            //close the streams //
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
            respon = e.getMessage();
        }
        return respon;
    }
    public String downloadFile(){
        int  MEGABYTE = 1024 * 1024;
        String hasil = null;
        try {
            String alamatUrl = "http://192.168.1.11/example2/upload/file/primary%253AWhatsApp%252FMedia%252FWhatsApp%2520Documents%252Fchapter8.pdf.pdf";
            URL url = new URL(alamatUrl);

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Belajar");
            folder.mkdir();
            String nama = alamatUrl.substring(alamatUrl.lastIndexOf("/")+1);
            nama = nama.replace("%"," ");
            File pdfFile = new File(folder, nama);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //urlConnection.setRequestMethod("GET");
            //urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
            hasil = nama;
        }catch (Exception e){
            hasil = e.getMessage();
        }
        return hasil;
    }
}
