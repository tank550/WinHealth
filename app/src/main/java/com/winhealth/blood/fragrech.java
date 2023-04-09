package com.winhealth.blood;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragrech extends Fragment {
    private TextView libbanque;
    private Button btnvalid;
    String lbanq;
    public fragrech(String lbanq){
        this.lbanq=lbanq;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question, container, false);
        Context context= getContext();
        libbanque=view.findViewById(R.id.libbanque);
        btnvalid=view.findViewById(R.id.banqvalid);

        btnvalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
