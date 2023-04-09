package com.winhealth.blood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class fragvide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragvide);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Dashboard questionnaire = new Dashboard();
        //Verifier_sang questionnaire = new Verifier_sang();
        fragmentTransaction.add(R.id.frag1, questionnaire);
        fragmentTransaction.commit();
    }
}