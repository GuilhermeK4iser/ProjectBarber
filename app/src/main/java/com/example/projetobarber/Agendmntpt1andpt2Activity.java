package com.example.projetobarber;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Agendmntpt1andpt2Activity extends AppCompatActivity {
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        final View[] cur_agendmnt = {inflater.inflate(R.layout.agendmntpt1, null)};
        setContentView(cur_agendmnt[0]);
        View b1 = findViewById(R.id.barb1_view), b2 = findViewById(R.id.barb2_view), b3 = findViewById(R.id.barb3_view);
        final String[] barbeiro = {""};
        b1.setOnClickListener(v -> {
            b1.setBackgroundResource(R.drawable.buttontapped);
            b2.setBackgroundResource(R.drawable.buttoncrud);
            b3.setBackgroundResource(R.drawable.buttoncrud);
            b1.setSelected(true);
            b2.setSelected(false);
            b3.setSelected(false);
        });
        b2.setOnClickListener(v -> {
            b1.setBackgroundResource(R.drawable.buttoncrud);
            b2.setBackgroundResource(R.drawable.buttontapped);
            b3.setBackgroundResource(R.drawable.buttoncrud);
            b2.setSelected(true);
            b1.setSelected(false);
            b3.setSelected(false);
        });
        b3.setOnClickListener(v -> {
            b1.setBackgroundResource(R.drawable.buttoncrud);
            b2.setBackgroundResource(R.drawable.buttoncrud);
            b3.setBackgroundResource(R.drawable.buttontapped);
            b3.setSelected(true);
            b2.setSelected(false);
            b1.setSelected(false);
        });
        findViewById(R.id.proxi_btn).setOnClickListener(v -> {
            if (b1.isSelected()) {
                barbeiro[0] = "Roberto Firmino Souza";
                cur_agendmnt[0] = inflater.inflate(R.layout.agendmntpt2, null);
                setContentView(cur_agendmnt[0]);
            } else if (b2.isSelected()) {
                barbeiro[0] = "Erick de Oliveira";
                cur_agendmnt[0] = inflater.inflate(R.layout.agendmntpt2, null);
                setContentView(cur_agendmnt[0]);
            } else if (b3.isSelected()) {
                barbeiro[0] = "Carlos Mendel";
                cur_agendmnt[0] = inflater.inflate(R.layout.agendmntpt2, null);
                setContentView(cur_agendmnt[0]);
            } else {
                Toast.makeText(this, "Para continuar, por favor, selecione um barbeiro.", Toast.LENGTH_SHORT).show();
            }
            boolean isPt2 = new AgendmntViewChangeListener().onClick(v);
            if (isPt2) {
                final int[] totalpg = {0};
                View s1 = findViewById(R.id.serv1_view), s2 = findViewById(R.id.serv2_view), s3 = findViewById(R.id.serv3_view), ir_agpt3 = findViewById(R.id.para_agpt3_btn);
                try {
                    ir_agpt3.setOnClickListener(v1 -> {
                        if (s1.isSelected() || s2.isSelected() || s3.isSelected()) {
                            Intent intent = new Intent(this, Agendmntpt3Activity.class);
                            intent.putExtra("barbeiro", barbeiro[0]);
                            intent.putExtra("serv1", s1.isSelected());
                            intent.putExtra("serv2", s2.isSelected());
                            intent.putExtra("serv3", s3.isSelected());
                            intent.putExtra("totalpg", totalpg[0]);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Para continuar, por favor, selecione ao menos um serviço.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    s1.setOnClickListener(v1 -> {
                        if (s1.isSelected()) {
                            s1.setBackgroundResource(R.drawable.buttoncrud);
                            s1.setSelected(false);
                            if (totalpg[0] > 0) totalpg[0] -= 35;
                        } else {
                            s1.setBackgroundResource(R.drawable.buttontapped);
                            s1.setSelected(true);
                            totalpg[0] += 35;
                        }
                        System.out.printf("Você irá pagar: R$ %d\n", totalpg[0]);
                    });
                    s2.setOnClickListener(v1 -> {
                        if (s2.isSelected()) {
                            s2.setBackgroundResource(R.drawable.buttoncrud);
                            s2.setSelected(false);
                            if (totalpg[0] > 0) totalpg[0] -= 10;
                        } else {
                            s2.setBackgroundResource(R.drawable.buttontapped);
                            s2.setSelected(true);
                            totalpg[0] += 10;
                        }
                        System.out.printf("Você irá pagar: R$ %d\n", totalpg[0]);
                    });
                    s3.setOnClickListener(v1 -> {
                        if (s3.isSelected()) {
                            s3.setBackgroundResource(R.drawable.buttoncrud);
                            s3.setSelected(false);
                            if (totalpg[0] > 0) totalpg[0] -= 20;
                        } else {
                            s3.setBackgroundResource(R.drawable.buttontapped);
                            s3.setSelected(true);
                            totalpg[0] += 20;
                        }
                        System.out.printf("Você irá pagar: R$ %d\n", totalpg[0]);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
