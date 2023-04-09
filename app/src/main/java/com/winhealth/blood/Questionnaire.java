package com.winhealth.blood;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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

import java.util.Random;

public class Questionnaire extends Fragment {
    private RadioGroup[] radioGroups;
    private Button button;
    private String nomuser;
    FragmentTransaction fragmentTransaction;
    private BottomNavigationView bottomNavigationView;
    Rdv rdv = new Rdv();
    Dashboard dashboard = new Dashboard();
    public Questionnaire(String nomuser, Intent intent) {
        this.nomuser = nomuser;
        this.intent = intent;
    }

    public Questionnaire(Intent intent) {
        this.intent = intent;
    }

    public Intent intent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.questionnaire, container, false);
        Context context = getContext();
        ScrollView scrollView = view.findViewById(R.id.containerScroolView);
        afficherQuestion(scrollView, context);

        Intent intent = new Intent();
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
    public void afficherQuestion(ScrollView scrollViewContainer, Context context) {
        String url = "http://192.168.43.238/api1/recupQuestion.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                try {
                    //Convertir la réponse string en tableau de Json.Chaque compartiment de la réponse compose une ligne du tableau
                    JSONArray jsonArray = new JSONArray(response);

                    int jsonArraySize = jsonArray.length();
                    radioGroups = new RadioGroup[jsonArraySize];

                    String question, resultatCorrect, resultatIncorrect, id;

                    JSONObject jsonObject;

                    LinearLayout textContainer = new LinearLayout(context);
                    textContainer.setOrientation(LinearLayout.VERTICAL);

                    Random random = new Random();
                    int index;

                    for (int i = 0; i < jsonArraySize; i++) {

                        //Récupérer dans un objet Json chaque ligne du table de Json
                        jsonObject = jsonArray.getJSONObject(i);


                        //Récupérer de l'objet Json chaque information avec sa clé
                        question = jsonObject.getString("question");

                        //Récupérer de l'objet Json chaque réponse avec sa clé
                        resultatCorrect = jsonObject.getString("resultat");

                        //Récupérer de l'objet Json chaque mauvaise réponse avec sa clé
                        resultatIncorrect = jsonObject.getString("mauvais");

                        TextView textView = new TextView(context);
                        textView.setId(View.generateViewId());
                        textView.setText(question);
                        textView.setTextColor(Color.parseColor("#777777"));
                        textView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6, getResources().getDisplayMetrics()));
                        textView.setElegantTextHeight(true);
                        textView.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()),0,0,0);
                        textView.setMaxLines(4);
                        textView.setEllipsize(TextUtils.TruncateAt.END);
                        Typeface textViewTypeface = Typeface.createFromAsset(context.getAssets(),"OpenSans-Regular.ttf");
                        textView.setTypeface(textViewTypeface);

                        CardView cardView = new CardView(context);
                        cardView.setId(View.generateViewId());
                        cardView.setCardElevation((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                        cardView.setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                        cardView.setPadding(0,0,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                        cardView.setUseCompatPadding(true);
                        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        cardView.setLayoutParams(layoutParams);

                        RelativeLayout relativeLayout = new RelativeLayout(context);
                        relativeLayout.setPadding(0,0,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                        relativeLayout.setId(View.generateViewId());
                        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        relativeLayout.setLayoutParams(relativeLayoutParams);

                        TextView titre = new TextView(context);
                        titre.setText("Question");
                        titre.setTextColor(Color.parseColor("#00008B"));
                        Typeface titreTypeface = Typeface.createFromAsset(context.getAssets(),"Montserrat-MediumItalic.ttf");
                        textView.setTypeface(titreTypeface);
                        titre.setId(View.generateViewId());
                        titre.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
                        RelativeLayout.LayoutParams titreParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        titreParams.setMargins(
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()),
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()),
                                0,
                                0

                        );
                        titreParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        titreParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        titre.setLayoutParams(titreParams);



                        RelativeLayout relativeLayoutContainer = new RelativeLayout(context);
                        RelativeLayout.LayoutParams relativeLayoutContainerParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        relativeLayoutContainerParams.setMargins(
                                0,
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()),
                                0
                        );
                        relativeLayoutContainerParams.addRule(RelativeLayout.BELOW, titre.getId());
                        relativeLayoutContainer.setLayoutParams(relativeLayoutContainerParams);
                        relativeLayoutContainer.setId(View.generateViewId());



                        RadioGroup radioGroup = new RadioGroup(context);
                        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                        radioGroup.setPadding(0, 0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                        RelativeLayout.LayoutParams radiogroupParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        radiogroupParams.setMargins(
                                0,
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()),
                                0,
                                0
                        );
                        radiogroupParams.addRule(RelativeLayout.BELOW, textView.getId());
                        radioGroup.setLayoutParams(radiogroupParams);



                        index = random.nextInt(2)+1;

                        if(index==1){
                            RadioButton radioButton1 = new RadioButton(context);
                            radioButton1.setText(resultatCorrect);
                            radioButton1.setId(View.generateViewId());

                            RadioButton radioButton2 = new RadioButton(context);
                            radioButton2.setText(resultatIncorrect);
                            radioButton2.setId(View.generateViewId());
                            radioGroup.addView(radioButton1);
                            radioGroup.addView(radioButton2);
                            addRadioGroupListeners(radioGroup, radioButton2, context);

                        }else {
                            RadioButton radioButton1 = new RadioButton(context);
                            radioButton1.setText(resultatIncorrect);
                            radioButton1.setId(View.generateViewId());

                            RadioButton radioButton2 = new RadioButton(context);
                            radioButton2.setText(resultatCorrect);
                            radioButton2.setId(View.generateViewId());

                            radioGroup.addView(radioButton1);
                            radioGroup.addView(radioButton2);
                            addRadioGroupListeners(radioGroup, radioButton1, context);


                        }
                        relativeLayoutContainer.addView(textView);
                        relativeLayoutContainer.addView(radioGroup);
                        relativeLayout.addView(titre);
                        relativeLayout.addView(relativeLayoutContainer);
                        cardView.addView(relativeLayout);
                        radioGroups[i] = radioGroup;



                        textContainer.addView(cardView);


                    }

                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    linearLayout.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 195, getResources().getDisplayMetrics()),0,0,0);
                    linearLayout.setLayoutParams(linearLayoutParams);

                    button = new Button(context);
                    button.setText("Prendre rendez-vous");
                    button.setBackgroundResource(R.drawable.arrondi_button);
                    button.setTextAppearance(context,R.style.buttonStyle);
                    button.setPadding(10,0,10,0);
                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    button.setLayoutParams(buttonParams);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean checkSelectionResult;
                            checkSelectionResult = checkselection(context);
                            if(checkSelectionResult==false){
                                new AlertDialog.Builder(context).setTitle("Alerte").setMessage("Vous n'avez pas répondu à toutes les questions").setPositiveButton("Ok", null).show();
                            }else {

                                //((Dashboard) getActivity()).replaceFragment(rdv, "RDV");
                                fragmentTransaction = getParentFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frag1, rdv);
                                fragmentTransaction.commit();
                            }

                            //new AlertDialog.Builder(context).setTitle("Test d'aptitude au don").setMessage("Vous n'etes pas éligible pour un don").setPositiveButton("Ok", null).show();
                        }
                    });
                    linearLayout.addView(button);
                    textContainer.addView(linearLayout);
                    scrollViewContainer.addView(textContainer);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAUVAIS", "onResponse: " + error.getMessage());
                Toast.makeText(context, "Une erreur est survenue, veuillez vérifier votre connexion au réseau et réessayer.", Toast.LENGTH_LONG).show();
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }
    private boolean checkselection(Context context) {
        boolean selection = true;
        for (int i = 0; i < radioGroups.length; i++) {
            if( radioGroups[i].getCheckedRadioButtonId()==-1 ){
                selection = false;
            }
        }
        return selection;
    }
    public void addRadioGroupListeners(RadioGroup radioGroupe, RadioButton radioButton, Context context) {
        radioGroupe.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selecctedButton = group.findViewById(checkedId);
            if (selecctedButton != null) {
                selecctedButton.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton button = (RadioButton) group.getChildAt(i);
                    if (button == selecctedButton) {
                        button.setTextColor(ContextCompat.getColor(context, R.color.bottom_nav_item_color_selected));
                        button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
                    }else {
                        button.setTextColor(ContextCompat.getColor(context, R.color.black));
                        button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
                    }
                }
                if (selecctedButton.getId() == radioButton.getId()) {
                    new AlertDialog.Builder(context).setTitle("Test d'aptitude au don").setMessage("Vous n'etes pas éligible pour un don").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frag1, dashboard);
                            fragmentTransaction.commit();
                        }
                    }).show();
                }

            }
        });
    }
}
