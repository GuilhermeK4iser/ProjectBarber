package com.example.projetobarber;

import android.view.LayoutInflater;
import android.view.View;

public class AgendmntViewChangeListener {

    public boolean onClick(View v) {
        View infView = LayoutInflater.from(v.getContext()).inflate(R.layout.agendmntpt1, null);
        return v != infView;
    }
}