package com.example.l.afiefbelajar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText text1, text2;
    private Button tombol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (EditText)findViewById(R.id.editText);
        text2 = (EditText)findViewById(R.id.editText2);
        tombol = (Button)findViewById(R.id.button);
        final ProgressDialog dialog = new ProgressDialog(getApplicationContext());
        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("loading");
                String url = "http://192.168.2.236/example2/afif/cek.php";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> objek = new HashMap<String, String>();
                        objek.put("username", text1.getText().toString());
                        objek.put("password", text2.getText().toString());
                        return objek;
                    }
                };
                queue.add(request);
            }
        });
    }
}
