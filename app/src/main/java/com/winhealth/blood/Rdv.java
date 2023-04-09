package com.winhealth.blood;

import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Rdv extends Fragment {

    private ImageView imageView, imageViewHeure;
    private EditText editText, editTextHeure;
    private Spinner spinner;
    private RequestQueue requestQueue, requestQueue1;
    private String ville;
    private LinearLayout layout;
    private SimpleDateFormat simpleDateFormat;
    private DatePicker date;
    private Time time;
    private Calendar reminderDateTime;
    FragmentTransaction fragmentTransaction;
    Dashboard dashboard = new Dashboard();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choix_ville, container, false);
        Context context = getContext();
        reminderDateTime = Calendar.getInstance();
        imageView = view.findViewById(R.id.calendar);
        editText = view.findViewById(R.id.date);
        spinner = view.findViewById(R.id.mot);
        layout = view.findViewById(R.id.container);
        editTextHeure = view.findViewById(R.id.heure);
        imageViewHeure = view.findViewById(R.id.hours);
        imageViewHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Récupérer la date courante
                Calendar calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Mise à jour du champ de texte
                        String selectedTime = hourOfDay + ":" + minute;
                        editTextHeure.setText(selectedTime);
                        reminderDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        reminderDateTime.set(Calendar.MINUTE, minute);
                        time = new Time(hourOfDay, minute, 0);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Récupérer la date courante
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                reminderDateTime.set(Calendar.YEAR, year);
                reminderDateTime.set(Calendar.MONTH, month);
                reminderDateTime.set(Calendar.DAY_OF_MONTH, day);

                //Création de la boite de dialogue DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int yearof, int monthof, int dayOfMonth) {

                                //Mise à jour du champ de texte
                                String selectedDate = yearof + "-" + (monthof + 1) + "-" + dayOfMonth;
                                editText.setText(selectedDate);
                            }
                        }, year, month, day);

                //Définition de la date minimale comme étant la date système
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //Affichage de la boite de dialogue
                datePickerDialog.show();
                date = datePickerDialog.getDatePicker();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ville = parent.getItemAtPosition(position).toString().trim();
                Toast.makeText(getContext(), ville, Toast.LENGTH_LONG).show();
                afficherCentre(ville, layout);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ScrollView scrollView = view.findViewById(R.id.containerScrool);
        return view;
    }

    private void afficherCentre(String ville, LinearLayout linearLayout) {
        String url = "http://192.168.43.238/api1/recupCentre.php?ville=" + ville;
        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        //Convertir la réponse string en tableau de Json.Chaque compartiment de la réponse compose une ligne du tableau
                        JSONArray jsonArray = new JSONArray(response);
                        int jsonArraySize = jsonArray.length();
                        JSONObject jsonObject;
                        String libelle;
                        for (int i = 0; i < jsonArraySize; i++) {
                            //Récupérer dans un objet Json chaque ligne du table de Json
                            jsonObject = jsonArray.getJSONObject(i);

                            //Récupérer de l'objet Json chaque information avec sa clé
                            libelle = jsonObject.getString("libelle");

                            CardView cardView = new CardView(getContext());
                            cardView.setId(View.generateViewId());
                            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );
                            cardView.setLayoutParams(layoutParams);

                            TextView textView = new TextView(getContext());
                            textView.setId(View.generateViewId());
                            textView.setText(libelle);
                            textView.setTextColor(Color.parseColor("#000000"));
                            textView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6, getResources().getDisplayMetrics()));
                            textView.setElegantTextHeight(true);
                            textView.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0, 0, 0);
                            textView.setMaxLines(10);

                            Button button = new Button(getContext());
                            button.setText("Valider");
                            button.setId(View.generateViewId());
                            button.setBackgroundResource(R.drawable.arrondi_button);
                            button.setTextAppearance(getContext(), R.style.buttonStyle);
                            String finalLibelle = libelle;
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Activity mActivity = getActivity();
                                    //SharedPreferences mPrefs = mActivity.getSharedPreferences("my_preference", MODE_PRIVATE);
                                    //String nom = mPrefs.getString("username", "");
                                    /*Bundle bundle = getArguments();
                                    String nom = bundle.getString("data");
                                    makeText(getContext(), nom, Toast.LENGTH_LONG).show();
                                    if (getArguments() != null) {
                                        String string = (String) textView.getText();
                                        //insertRdv(string, date, time);
                                    }*/
                                    insertRdv(finalLibelle,editText.getText().toString(), editTextHeure.getText().toString(), getContext(), reminderDateTime);

                                }
                            });
                            //button.setPadding(10,0,10,0);
                            RelativeLayout relativeLayoutContainer = new RelativeLayout(getContext());
                            RelativeLayout.LayoutParams relativeLayoutContainerParams = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            relativeLayoutContainerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                            button.setLayoutParams(relativeLayoutContainerParams);
                            relativeLayoutContainer.setId(View.generateViewId());


                            relativeLayoutContainer.addView(textView);
                            relativeLayoutContainer.addView(button);
                            cardView.addView(relativeLayoutContainer);
                            linearLayout.addView(cardView);
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    TextView textView = new TextView(getContext());
                    textView.setId(View.generateViewId());
                    textView.setText(R.string.nodisponible);
                    textView.setTextColor(Color.parseColor("#000000"));
                    textView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    textView.setElegantTextHeight(true);
                    textView.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0, 0, 0);
                    textView.setMaxLines(10);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Tota", "onErrorResponse " + error.getMessage());
                makeText(getContext(), "onErrorResponse" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String Ville = params.put("ville", ville);
                return params;
            }
        }*/;
        requestQueue.add(stringRequest);
    }

    private void insertRdv(String centre, String date, String time, Context context, Calendar reminderDateTime) {
        requestQueue1 = Volley.newRequestQueue(getContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String check = sharedPreferences.getString("name","");
        String url = "http://192.168.43.238/api1/insertRdv.php?centre=" + centre + "&datee=" + date + "&timee=" + time + "&name="+check;
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response contient le code du rdv
                if (!(response == null)) {
                    new AlertDialog.Builder(getContext()).setTitle("Code du rendez-vous").setMessage("Félication vous venez de prendre rendez-vous au centre "+centre+" pour un don de sang le "+date+"à "+time+" un geste remarquable votre code de rendez-vous est le suivant :" + response+" "+url).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frag1, dashboard);
                            fragmentTransaction.commit();
                        }
                    }).show();
                    // Planifier les rappels
                    ReminderScheduler.scheduleReminder(context, reminderDateTime);
                } else {
                    new AlertDialog.Builder(getContext()).setTitle("Alert").setMessage("Erreur lors de l'enregistrement du rendez-vous").setPositiveButton("Ok", null).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(getContext()).setTitle("Alert").setMessage("Erreur de connexion au réseau").setPositiveButton("Ok", null).show();
            }
        });
        requestQueue1.add(stringRequest1);
    }

    public void setData(String Data) {
        Toast.makeText(getContext(), Data, Toast.LENGTH_LONG);
    }

}
