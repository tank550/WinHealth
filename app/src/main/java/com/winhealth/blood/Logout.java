package com.winhealth.blood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Logout extends Fragment {
    private Button button;
    private BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;
    //Context context=getContext();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_logout, container, false);
        button=view.findViewById(R.id.deconn);

        Intent intent = new Intent();
        Dashboard dashboard = new Dashboard();
        Questionnaire questionnaire = new Questionnaire(intent);
        Verifier_sang verifier_sang = new Verifier_sang();
        notif notification = new notif();
        bottomNavigationView = view.findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        //Pour changer la couleur du titre qui est sélectionné
                        fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag1, dashboard);
                        fragmentTransaction.commit();
                        // Mettre à jour le fragment affiché pour le fragment Home
                        return true;
                    case R.id.navigation_map:
                        //Pour changer la couleur du titre qui est sélectionné
                        fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag1, questionnaire);
                        fragmentTransaction.commit();

                        // Mettre à jour le fragment affiché pour le fragment Dashboard

                        return true;
                    case R.id.navigation_search:
                        fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag1, verifier_sang);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_notification:
                        //Pour changer la couleur du titre qui est sélectionné
                        fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag1, notification);
                        fragmentTransaction.commit();
                        // Mettre à jour le fragment affiché pour le fragment Notifications
                        return true;
                    case R.id.navigation_account:
                        //Pour changer la couleur du titre qui est sélectionné

                        // Mettre à jour le fragment affiché pour le fragment Notifications
                        return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accueil = new Intent(getContext(), Login.class);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", "");
                editor.putString("nom", "");
                editor.putString("prenom", "");
                editor.putString("type", "");
                editor.apply();
                startActivity(accueil);
            }
        });

        return view;
    }
}
