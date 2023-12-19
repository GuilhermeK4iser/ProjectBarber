package com.example.projetobarber;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetobarber.usr_vars.AgendamentoData;
import com.example.projetobarber.usr_vars.UsuarioData;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Agendmntpt3Activity extends AppCompatActivity {
    public void onCreate(android.os.Bundle savedInstanceState) {
        Intent intent = getIntent();
        String barbeiro = intent.getStringExtra("barbeiro");
        int preco = intent.getIntExtra("totalpg", 0);
        boolean[] servicos = new boolean[3];
        servicos[0] = intent.getBooleanExtra("serv1", false);
        servicos[1] = intent.getBooleanExtra("serv2", false);
        servicos[2] = intent.getBooleanExtra("serv3", false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agendmntpt3);
        Spinner timeSpinner = findViewById(R.id.timespinner);
        List<String> timeList = generateTimeList();
        MaterialCalendarView calendarView = findViewById(R.id.calendarView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_style_inherited, timeList);
        adapter.setDropDownViewResource(R.layout.dropdownspinner_style_inherited);
        timeSpinner.setAdapter(adapter);
        findViewById(R.id.ag_final_btn).setOnClickListener((view) -> {
            if (barbeiro != null && preco != 0 && servicos != null && timeSpinner.getSelectedItem() != null && calendarView.getSelectedDate() != null) {
                String servicos_str = "";
                if (servicos[0]) servicos_str += "Corte de cabelo";
                if (servicos[1]) {
                    if (servicos_str.length() > 0) servicos_str += ", ";
                    servicos_str += "Pé";
                }
                if (servicos[2]) {
                    if (servicos_str.length() > 0) servicos_str += ", ";
                    servicos_str += "Barba";
                }
                String data = calendarView.getSelectedDate().getDay() + "/" + calendarView.getSelectedDate().getMonth() + "/" + calendarView.getSelectedDate().getYear();
                String hora = timeSpinner.getSelectedItem().toString();
                AgendamentoData ag_data = new AgendamentoData(this);
                SQLiteDatabase db = ag_data.getWritableDatabase();
                String data_e_horario = data + " " + hora;
                String datas_e_horarios = ag_data.getDateAndTime();
                if (datas_e_horarios.contains(data_e_horario))
                    Toast.makeText(this, "Já existe um agendamento para este horário e data.", Toast.LENGTH_SHORT).show();
                else {
                    ag_data.inserirDados(barbeiro, data, hora, servicos_str, preco, new UsuarioData(this).getNome());
                    Toast.makeText(this, "Agendamento realizado com sucesso! Voltando\n ao menu principal...", Toast.LENGTH_SHORT).show();
                    Intent menu = new Intent(this, MainActivity.class);
                    System.out.printf("%s", ag_data.getAllAgendmntsData());
                    ag_data.close();
                    startActivity(menu);
                }
            } else {
                System.out.printf("barbeiro: %s\npreco: %d\nservicos: %s\ntimeSpinner: %s\ncalendarView: %s\n", barbeiro, preco, Arrays.toString(servicos), timeSpinner.getSelectedItem(), calendarView.getSelectedDate());
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
