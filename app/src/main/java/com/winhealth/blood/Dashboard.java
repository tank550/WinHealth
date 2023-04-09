package com.winhealth.blood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends Fragment {
    private TextView textView;
    private TextView textView1;

    FragmentTransaction fragmentTransaction;
    private VideoView vd;
    private BottomNavigationView navigationView;
    //Date Datmaint = new Date();
    //SimpleDateFormat formatt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    //String Dat = formatt.format(Datmaint);
    RequestQueue requestQueue;
    ArrayList<JSONObject> objects= new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<JSONObject> mList;
    public static final String ok = null;
    //String Dateajt="?dateajt=".concat(Dat);
//    public Dashboard(FragmentTransaction fragmentTransaction){
//        this.fragmentTransaction=fragmentTransaction;
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dashboard, container, false);
        Context context = getContext();
        mRecyclerView=view.findViewById(R.id.recy);
        navigationView=view.findViewById(R.id.navigation_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        chatgpt();
        Intent intent =new Intent();
        Verifier_sang questionnair = new Verifier_sang();
        Questionnaire questionnaire = new Questionnaire(intent);
        Dashboard dashboard = new Dashboard();
        notif notification = new notif();
        Logout logout = new Logout();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        fragmentTransaction.replace(R.id.frag1, questionnair);
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
    //Uri builder = Uri.parse("http://192.168.1.100/api1/test.php");
    //Uri builder1 = Uri.withAppendedPath(builder, Dateajt);
    //String url = builder1.toString();
    public void chatgpt() {
        mList=new ArrayList<JSONObject>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, "http://192.168.43.238/api1/test.php",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                   public void onResponse(JSONArray response) {
                        objects = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject objectJSON = response.getJSONObject(i);
                                // Extraire les informations de l'objet JSON ici
                                objects.add(objectJSON);
                            } catch (JSONException e) {

                            }
                        }
                        mAdapter = new MyAdapter(getContext(), objects);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        // Utiliser la liste d'objets ici
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gérer les erreurs de requête ici
                        Log.e("Tota", "onErrorResponse "+ error.getMessage());
                        Toast.makeText(getContext(), "onErrorResponse"+ error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){};
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

    }

}



