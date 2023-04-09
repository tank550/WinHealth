package com.winhealth.blood;


import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class SignUp extends AppCompatActivity {
    RequestQueue requestQueue;
    private EditText nom, prenom, mail, password, passwordvalid, name;
    private RadioButton sexe,sexe1;
    private String nom1;
    private String prenom1;
    private Button env;
    private TextView err;
    String result="";


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.signup);
        nom=(EditText)findViewById(R.id.nom);
        prenom=(EditText)findViewById(R.id.prenom);
        mail=(EditText)findViewById(R.id.mail);
        password=(EditText)findViewById(R.id.passe);
        passwordvalid=(EditText)findViewById(R.id.confpasse);
        name=(EditText)findViewById(R.id.user);
        sexe= (RadioButton) findViewById(R.id.f);
        sexe1=(RadioButton)findViewById(R.id.m);
        env=(Button)findViewById(R.id.bout);
        err=(TextView)findViewById(R.id.uterror);
        sexe.setOnCheckedChangeListener((buttonView, isChecked) -> nom1="F");
        sexe1.setOnCheckedChangeListener((buttonView, isChecked) -> nom1="M");
        env.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bout){
                    String chemin = "http://192.168.1.100/api1/post.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, chemin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String retour) {
                                    try {
                                        JSONObject gta = new JSONObject(retour);
                                        result=gta.getString("message").toString();
                                        Toast.makeText(SignUp.this, gta.getString("message"), LENGTH_SHORT).show();
                                        if (result=="succes"){
                                            startActivity(new Intent(SignUp.this, Login.class));
                                            finish();
                                        }
                                    } catch (Exception e) {
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    makeText(SignUp.this, error.getMessage(), LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<>();
                            params.put("name", name.getText().toString());
                            params.put("nom", nom.getText().toString());
                            params.put("prenom", prenom.getText().toString());
                            params.put("sexe", nom1.toString());
                            params.put("type","TY01");
                            params.put("mail",mail.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("passwordvalid", passwordvalid.getText().toString());
                            return params;
                        }
                    };
                    requestQueue = Volley.newRequestQueue(SignUp.this);
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
}
