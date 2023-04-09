package com.winhealth.blood;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private TextView ins;
    private EditText mot;

    private EditText nom;
    private ImageView eye;
    private boolean etatvisible;
    private Button connection;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);
        ins = (TextView) findViewById(R.id.ins);
        eye = findViewById(R.id.eye);
        mot = findViewById(R.id.mot);
        connection = findViewById(R.id.bout);
        nom = findViewById(R.id.mail);
        etatvisible = false;
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etatvisible == false) {
                    eye.setImageResource(R.mipmap.eye);//Visible
                    mot.setInputType(R.string.pass);
                    etatvisible = true;
                } else {
                    eye.setImageResource(R.mipmap.blindpx);//Invisible
                    mot.setInputType(R.string.passe);
                    etatvisible = false;
                }

            }
        });
        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectUser();
            }
        });
    }
    public void onApiResponse(String response){
        Boolean success = null;
        String error = "";

        try{
            JSONObject gta = new JSONObject(response);
            success = gta.getBoolean("success");

            if(success == true) {
                Intent accueil = new Intent(getApplicationContext(), Acceuil.class);
                startActivity(accueil);
                finish();
            }else {
                error =gta.getString("error");
                Log.e("Tota", error);
                makeText(this, error, LENGTH_SHORT).show();
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.e("Toto", "onApiResponse: ", e);
            makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void connectUser() {
        String url = "http://192.168.1.100/api1/connection.php";
       StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onApiResponse(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Tota","onErrorResponse "+ error.getMessage());
                makeText(Login.this, "onErrorResponse" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError{
               Map<String, String> params = new HashMap<>();
               String username = params.put("username", nom.getText().toString());
               params.put("password", mot.getText().toString());
               return params;
           }
       };

        requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(jsonObjectRequest);
    }
}


