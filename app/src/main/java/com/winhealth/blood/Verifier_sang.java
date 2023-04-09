package com.winhealth.blood;

import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class Verifier_sang extends Fragment {
    private BottomNavigationView navigationView;
    private SearchView recherche;
    RequestQueue requestQueue;
    FragmentTransaction fragmentTransaction;
    private TextView ok;
    String a, b, c, d, e, f;
    int compteur=0;




    String api ="http://192.168.43.238/api1/index1.php?rech=";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_verifier_sang, container, false);
        Context context = getContext();
        navigationView=view.findViewById(R.id.navigation_view);
        //ScrollView scrollView = view.findViewById(R.id.containerScroolView);
        //afficherQuestion(scrollView, context);
        recherche=view.findViewById(R.id.rech);
        //ok=view.findViewById(R.id.ok);
        Intent intent =new Intent();
        Questionnaire questionnair = new Questionnaire(intent);
        Dashboard questionnaire = new Dashboard();
        Verifier_sang verifier_sang = new Verifier_sang();
        notif notification =new notif();
        Logout logout = new Logout();


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        //Pour changer la couleur du titre qui est sélectionné
                        fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag1, questionnaire);
                        fragmentTransaction.commit();

                        // Mettre à jour le fragment affiché pour le fragment Home
                        return true;
                    case R.id.navigation_map:
                        //Pour changer la couleur du titre qui est sélectionné
                        fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag1, questionnair);
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

        recherche.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recher(query);
            return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //recher(newText);
                return false;
            }
        });

        return view;
    }

    public void recher(String sang){
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        e=sang;
//        FragmentManager ragmentManager = getParentFragmentManager();

//        switch (sang){
//            case "A+":sang="A"; break;
//            case "B+":sang="B"; break;
//            case "O+":sang="O"; break;
//            case "AB+":sang="AB"; break;
//        }
        //api=api+sang;
        if (sang.equals("A") || sang.equals("A-") || sang.equals("AB-") || sang.equals("AB") || sang.equals("B-") || sang.equals("B") || sang.equals("O-") || sang.equals("O")){
            /*JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, api, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            fragxml[] result= new fragxml[response.length()];

                            for (int i=0; i < response.length(); i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    a=jsonObject.getString("banque");
                                    //ok.setText(a);
                                    b=e;
                                    c=jsonObject.getString("observ");
                                    if (c.equals("Rupture")){
                                        continue;
                                    }
                                    d=jsonObject.getString("addresse");
                                    result[i]=new fragxml(a,b,c,d);
                                    //ok.setText(result[i].getA());
                                    fragmentTransaction.add(R.id.sousfrag, result[i]);
                                    //fragmentTransaction.commit();
                                }catch (JSONException e){

                                }
                            }
                            fragmentTransaction.commit();
                            }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Tota", "onErrorResponse "+ error.getMessage());
                            Toast.makeText(getContext(), "onErrorResponse"+error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });*///{
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("rech", sang.toString());
//                    return params;
//                }
            //}

            String api ="http://192.168.43.238/api1/index1.php?rech="+sang;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {

                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(String response) {

                    try {

                        //Convertir la réponse string en tableau de Json.Chaque compartiment de la réponse compose une ligne du tableau
                        JSONArray jsonArray = new JSONArray(response);
                        int jsonArraySize = jsonArray.length();

                        String banque, adresse, observation;

                        JSONObject jsonObject;
                        fragxml[] result= new fragxml[jsonArraySize];

                        for (int i = 0; i < jsonArraySize; i++){
                            jsonObject = jsonArray.getJSONObject(i);

                            //Récupérer de l'objet Json chaque information avec sa clé
                            banque = jsonObject.getString("banque");
                            adresse = jsonObject.getString("addresse");
                            observation = jsonObject.getString("observ");
                            result[i]=new fragxml(banque,e,observation,adresse);
                            fragmentTransaction.add(R.id.sousfrag, result[i]);
                            //fragmentTransaction.commit();
                        }
                        //fragmentTransaction.commit();
                        fragmentTransaction.commit();
                        //Toast.makeText(getContext(),"AB+",Toast.LENGTH_LONG);
                    } catch (JSONException ex) {
                        throw new RuntimeException(ex);
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
        else {
            makeText(getContext(), "Groupe sanguin non valide", Toast.LENGTH_LONG).show();
        }

    }

}