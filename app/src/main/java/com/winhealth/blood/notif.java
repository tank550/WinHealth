package com.winhealth.blood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class notif extends Fragment {
    private BottomNavigationView bottomNavigationView;
    TextView textView;
    FragmentTransaction fragmentTransaction;
    RequestQueue requestQueue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notif, container, false);
        Intent intent = new Intent();
        textView=view.findViewById(R.id.chech);
        notifs();
        Dashboard dashboard = new Dashboard();
        Questionnaire questionnaire = new Questionnaire(intent);
        Verifier_sang verifier_sang = new Verifier_sang();
        notif notification = new notif();
        Logout logout = new Logout();

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
                        fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag1, logout);
                        fragmentTransaction.commit();
                        // Mettre à jour le fragment affiché pour le fragment Notifications
                        return true;
                }
                return false;
            }
        });
        return view;
    }


    public void notifs(){
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String check = sharedPreferences.getString("name","");
        textView.setText(check);
        Toast.makeText(getContext(),check,Toast.LENGTH_LONG);
        String api ="http://192.168.43.238/api1/notifr.php?name="+check.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {

                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(String response) {
                        //Convertir la réponse string en tableau de Json.Chaque compartiment de la réponse compose une ligne du tableau
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response);
                        int jsonArraySize = jsonArray.length();

                        String centre, dater, coder, heure, etat;

                        JSONObject jsonObject;
                        //if (jsonArraySize > 0){



                        fragmnotif[] Fragmnotifs = new fragmnotif[jsonArraySize];
                            for (int i = 0; i < jsonArraySize; i++){
                            jsonObject = jsonArray.getJSONObject(i);
                            //Récupérer de l'objet Json chaque information avec sa clé
                            centre = jsonObject.getString("centre");
                            dater = jsonObject.getString("Dater");
                            coder = jsonObject.getString("coder");
                            heure = jsonObject.getString("heure");
                            etat = jsonObject.getString("Etat");
                            Toast.makeText(getContext(), etat.toString(), Toast.LENGTH_LONG);
                            Fragmnotifs[i]=new fragmnotif(centre,dater,heure,etat,coder);
                            fragmentTransaction.add(R.id.notif, Fragmnotifs[i]);
                            //fragmentTransaction.commit();
                        }
                            //notiffrag frag = new notiffrag();
                            //fragmentTransaction.add(R.id.notif, frag);
                        //fragmentTransaction.commit();
                            fragmentTransaction.commit();
                    //}
                        } catch (JSONException e) {
                        //throw new RuntimeException(e);
                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAUVAIS", "onResponse: " + error.getMessage());
                Toast.makeText(getContext(), "Une erreur est survenue, veuillez vérifier votre connexion au réseau et réessayer.", Toast.LENGTH_LONG).show();
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
