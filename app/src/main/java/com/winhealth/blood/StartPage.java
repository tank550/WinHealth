package com.winhealth.blood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity {
    RequestQueue requestQueue;
    ArrayList<JSONObject> objects;
    ArrayList<String> object;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<JSONObject> mList;
    String ok;
    JSONArray ko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);

    Runnable ano = new Runnable() {
        @Override
        public void run() {
//            startActivity(new Intent(StartPage.this,Login.class));
            checkbox();
            finish();
        }
    };

        new Handler().postDelayed(ano, 3000);
    }



    private void checkbox() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(StartPage.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String check = sharedPreferences.getString("name","");
        if(check!=""){
            Intent pageprincipale = new Intent(StartPage.this, fragvide.class);
            startActivity(pageprincipale);
            finish();
        }else {
            startActivity(new Intent(StartPage.this,Login.class));
            finish();
        }
    }

}