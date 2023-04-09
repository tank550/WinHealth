package com.winhealth.blood;


import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private EditText nom, prenom, mail, password, passwordvalid, name, matricule, addresse, phone;
    private RadioButton sexe, sexe1, donneur, docteur;
    private String nom1, type;
    private String prenom1;
    private Button env;
    private TextView err;
    private boolean etat = false,matriculeEtat=true;
    private ImageView oeuil, oeuil1;
    int ko=0;
    String result = null;
    Login ok = new Login();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.signup);
        oeuil = (ImageView) findViewById(R.id.eye);

        oeuil1 = (ImageView) findViewById(R.id.eye1);

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        mail = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.passe);
        passwordvalid = (EditText) findViewById(R.id.confpasse);
        name = (EditText) findViewById(R.id.user);
        matricule = findViewById(R.id.matricule);
        int ko1= matricule.getVisibility();
        sexe = (RadioButton) findViewById(R.id.f);
        sexe1 = (RadioButton) findViewById(R.id.m);
        docteur = (RadioButton) findViewById(R.id.docteur);
        donneur = (RadioButton) findViewById(R.id.donneur);
        addresse = (EditText) findViewById(R.id.addresse);
        phone = (EditText) findViewById(R.id.tel);

        env = (Button) findViewById(R.id.bout);

        err = (TextView) findViewById(R.id.uterror);

        sexe.setOnCheckedChangeListener((buttonView, isChecked) -> nom1 = "F");
        sexe1.setOnCheckedChangeListener((buttonView, isChecked) -> nom1 = "M");
        docteur.setOnCheckedChangeListener((buttonView, isChecked) -> {
            type="Docteur";
            if (matriculeEtat==true){
                matricule.setVisibility(View.VISIBLE);
                matriculeEtat=true;
            }else{
                matricule.setVisibility(View.GONE);
                matriculeEtat=false;
            }
        });
        donneur.setOnCheckedChangeListener((buttonView, isChecked) -> {
            type="Donneur";
            if (matriculeEtat==false){
                matricule.setVisibility(View.VISIBLE);
                matriculeEtat=true;
            }else{
                matricule.setVisibility(View.GONE);
                matriculeEtat=false;
            }


        });

        env.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ko<=3){
                    startActivity(new Intent(SignUp.this, Login.class));
                    finish();
                }
                if (v.getId() == R.id.bout) {
                    String chemin = "http://192.168.43.238/api1/post.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, chemin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String retour) {
                                    try {
                                        JSONObject gta = new JSONObject(retour);
                                        result = gta.getString("message").toString();
                                        if (gta.getBoolean("verif")) {
                                            startActivity(new Intent(SignUp.this, Login.class));
                                            finish();
                                        }else{
                                            Toast.makeText(SignUp.this, gta.getString("message"), LENGTH_SHORT).show();
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
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("name", name.getText().toString());
                            params.put("nom", nom.getText().toString());
                            params.put("prenom", prenom.getText().toString());
                            params.put("sexe", nom1.toString());
                            params.put("mail", mail.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("addr", addresse.getText().toString());
                            params.put("tel", phone.getText().toString());
                            params.put("passwordvalid", passwordvalid.getText().toString());
                            if (type.equals("Donneur")){
                                params.put("type", type.toString());
                            }

                            if (type.equals("Docteur")){
                                params.put("type", type.toString());
                                params.put("matr", matricule.getText().toString());
                            }

                            return params;
                        }
                    };
                    requestQueue = Volley.newRequestQueue(SignUp.this);
                    requestQueue.add(stringRequest);
                }
                ko=ko+1;
            }
        });

        oeuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etat == true) {
                    ok.visible(etat, password, oeuil);
                    etat = false;
                } else {
                    ok.visible(etat, password, oeuil);
                    etat = true;
                }

            }
        });
        oeuil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etat == true) {
                    ok.visible(etat, passwordvalid, oeuil1);
                    etat = false;
                } else {
                    ok.visible(etat, passwordvalid, oeuil1);
                    etat = true;
                }

            }
        });

    }
    public String matriculeChecked(boolean matriculeEtat){
        String visibility;
        if (matriculeEtat == true){
            visibility = "visible";
        }else {
            visibility = "gone";
        }
        return visibility;
    }
}
