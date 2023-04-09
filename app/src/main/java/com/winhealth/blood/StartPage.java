package com.winhealth.blood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);

        //checkbox();
    Runnable ano = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(StartPage.this,Login.class));
            finish();
        }
    };

        new Handler().postDelayed(ano, 3000);
    }

    /*public void checkbox(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("name","");
        if (check.equals("true")){
            startActivity(new Intent(StartPage.this,Acceuil.class));
            finish();
        }
    }*/
}