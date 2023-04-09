package com.winhealth.blood;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class fragmnotif extends Fragment {
    private TextView centre, dater, heure, etat, coder ;
    String centren, datern, heuren, etatn, codern;
    //FragmentTransaction fragmentTransaction;

    public fragmnotif(String centr, String datr, String heur, String eta, String code){
        this.centren=centr;
        this.datern=datr;
        this.heuren=heur;
        this.etatn=eta;
        this.codern=code;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifvue, container, false);
        centre=view.findViewById(R.id.dispon);
        centre.setText(centren.toString());
        dater=view.findViewById(R.id.localn);
        dater.setText(datern.toString());
        heure=view.findViewById(R.id.heurn);
        heure.setText(heuren.toString());
        etat=view.findViewById(R.id.pas);
        etat.setText(etatn.toString());
        if (etat.toString().equals("Passer")){
            etat.setTextColor(Color.parseColor("#FF0000"));

        }
        if (etat.toString().equals("A venir")){
            etat.setTextColor(Color.parseColor("#096A09"));

        }
        coder=view.findViewById(R.id.banquen);
        coder.setText(codern.toString());
        //cardView=view.findViewById(R.id.resulta);


        return view;
    }
}
