package com.winhealth.blood;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class fragxml extends Fragment {
    private TextView banque, sang, dispo, addresse ;
    private CardView cardView;
    String a, b, c, d;
    public fragxml(String a, String b, String c, String d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    public fragxml(){

    }

//    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//    SharedPreferences.Editor editor = sharedPreferences.edit();
//    String check = sharedPreferences.getString("type","");

    public String getA() {
        return a;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.resultrech, container, false);
        Context context = getContext();
        banque=view.findViewById(R.id.banque);
        banque.setText(a.toString());
        cardView=view.findViewById(R.id.result);
        sang=view.findViewById(R.id.ty);
        sang.setText(b.toString()+" :");
        dispo=view.findViewById(R.id.dispo);
        dispo.setText(c.toString());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String check = sharedPreferences.getString("type","");
        if (c.toString().equals("Disponible")){
            dispo.setTextColor(Color.parseColor("#096A09"));

        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.toString().equals("Disponible") && check.equals("Docteur")){
                    Toast.makeText(getContext(), "ça marche", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (c.toString().equals("Rupture")){
            dispo.setTextColor(Color.parseColor("#FF0000"));
        }
        addresse=view.findViewById(R.id.local);
        addresse.setText(d.toString());

        return view;

    }
}
