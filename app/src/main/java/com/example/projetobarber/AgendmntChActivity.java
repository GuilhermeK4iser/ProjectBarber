package com.example.projetobarber;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.threeten.bp.LocalDate;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetobarber.usr_vars.AgendamentoData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AgendmntChActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendmnt_edit);
        TextView agendmnts_ch_header = findViewById(R.id.agendmnts_ch_header);
        String id = getIntent().getStringExtra("id");
        final int[] preco = {0};
        MaterialCalendarView calendarchv = findViewById(R.id.calendarChangeView);
        agendmnts_ch_header.setText(String.format("%s %s", agendmnts_ch_header.getText(), id));
        List<String> barbeiros = new ArrayList<>(), servicos = new ArrayList<>();
        barbeiros.add("Roberto Firmino Souza");
        barbeiros.add("Erick de Oliveira");
        barbeiros.add("Carlos Mendel");
        servicos.add("Corte de cabelo");
        servicos.add("Pé");
        servicos.add("Barba");
        servicos.add("Corte de cabelo e pé");
        servicos.add("Corte de cabelo e barba");
        servicos.add("Pé e barba");
        servicos.add("Corte de cabelo, pé e barba");
        List<String> timeList = generateTimeList();
        ArrayAdapter<String> timeadapter = new ArrayAdapter<>(this, R.layout.spinner_style_inherited, timeList), barbadapter = new ArrayAdapter<>(this, R.layout.spinner_style_inherited, barbeiros), servadapter = new ArrayAdapter<>(this, R.layout.spinner_style_inherited, servicos);
        timeadapter.setDropDownViewResource(R.layout.dropdownspinner_style_inherited);
        barbadapter.setDropDownViewResource(R.layout.dropdownspinner_style_inherited);
        servadapter.setDropDownViewResource(R.layout.dropdownspinner_style_inherited);
        Spinner barb_spinner = findViewById(R.id.barbeiro_spinner), serv_spinner = findViewById(R.id.servs_ch_spinner), time_spinner = findViewById(R.id.ch_timespinner);
        barb_spinner.setAdapter(barbadapter);
        serv_spinner.setAdapter(servadapter);
        time_spinner.setAdapter(timeadapter);
        AgendamentoData agendamentoch = new AgendamentoData(this);
        SQLiteDatabase db = agendamentoch.getReadableDatabase();
        String[] dadosch = new String[6];
        try {
            dadosch = agendamentoch.getCurrAgendmntData(Integer.parseInt(id));
            String[] data = dadosch[5].split("/");
            CalendarDay calendarday = CalendarDay.from(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
            calendarchv.setSelectedDate(calendarday);
            calendarchv.setCurrentDate(calendarday, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        barb_spinner.setSelection(barbadapter.getPosition(dadosch[0]));
        time_spinner.setSelection(timeadapter.getPosition(dadosch[1]));
        try {
            dadosch[2] = dadosch[2].toLowerCase();
            if (dadosch[2].contains("cabelo") && dadosch[2].contains("pé") && dadosch[2].contains("barba")) {
                serv_spinner.setSelection(servadapter.getPosition("Corte de cabelo, pé e barba"));
            } else if (dadosch[2].contains("cabelo") && dadosch[2].contains("pé")) {
                serv_spinner.setSelection(servadapter.getPosition("Corte de cabelo e pé"));
            } else if (dadosch[2].contains("cabelo") && dadosch[2].contains("barba")) {
                serv_spinner.setSelection(servadapter.getPosition("Corte de cabelo e barba"));
            } else if (dadosch[2].contains("pé") && dadosch[2].contains("barba")) {
                serv_spinner.setSelection(servadapter.getPosition("Pé e barba"));
            } else if (dadosch[2].contains("cabelo")) {
                serv_spinner.setSelection(servadapter.getPosition("Corte de cabelo"));
            } else if (dadosch[2].contains("pé")) {
                serv_spinner.setSelection(servadapter.getPosition("Pé"));
            } else if (dadosch[2].contains("barba")) {
                serv_spinner.setSelection(servadapter.getPosition("Barba"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView aviso = findViewById(R.id.agendmnts_ch_disclaimer);
        aviso.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        String[] finalDadosch = dadosch;
        serv_spinner.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String serv = serv_spinner.getSelectedItem().toString().toLowerCase();
                preco[0] = 0;
                if (serv.contains("corte de cabelo")) preco[0] += 35;
                if (serv.contains("pé")) preco[0] += 10;
                if (serv.contains("barba")) preco[0] += 20;
                aviso.setText(String.format("%s%s", "Esses itens não são diretamente editáveis por você:\nPreço: R$ " + preco[0] + "\n", "Cliente: " + finalDadosch[4]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }));
        Button confch_agendmnt = findViewById(R.id.ch_final_btn);
        confch_agendmnt.setOnClickListener((view) -> {
            String serv = serv_spinner.getSelectedItem().toString();
            String dia = String.valueOf(calendarchv.getSelectedDate().getDay()), mes = String.valueOf(calendarchv.getSelectedDate().getMonth()), ano = String.valueOf(calendarchv.getSelectedDate().getYear());
            String data = dia + "/" + mes + "/" + ano;
            String hora = time_spinner.getSelectedItem().toString();
            String data_e_horario = data + " " + hora;
            String datas_e_horarios = agendamentoch.getDateAndTime();
            if (datas_e_horarios.contains(data_e_horario))
                Toast.makeText(this, "Já existe um agendamento para este horário e data.", Toast.LENGTH_SHORT).show();
            else {
                db.close();
                SQLiteDatabase dbw = agendamentoch.getWritableDatabase();
                agendamentoch.alterarDados(Integer.parseInt(id), barb_spinner.getSelectedItem().toString(), data, time_spinner.getSelectedItem().toString(), serv, preco[0]);
                Toast.makeText(this, "Agendamento editado com sucesso! Voltando ao menu principal...", Toast.LENGTH_SHORT).show();
                Intent princi = new Intent(this, MainActivity.class);
                System.out.printf("%s", agendamentoch.getAllAgendmntsData());
                agendamentoch.close();
                dbw.close();
                startActivity(princi);
            }
        });
    }

    private List<String> generateTimeList() {
        List<String> timeList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, 20);
        endTime.set(Calendar.MINUTE, 20);

        while (calendar.before(endTime)) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            String formattedTime = String.format("%02dh%02d", hour, minute);
            timeList.add(formattedTime);

            calendar.add(Calendar.MINUTE, 20);
        }

        return timeList;
    }
}