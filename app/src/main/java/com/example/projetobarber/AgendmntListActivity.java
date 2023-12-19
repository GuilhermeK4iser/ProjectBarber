package com.example.projetobarber;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetobarber.usr_vars.AgendamentoData;
import com.example.projetobarber.usr_vars.Usuario;
import com.example.projetobarber.usr_vars.UsuarioData;

import java.io.ObjectInputStream;
import java.util.List;

public class AgendmntListActivity extends AppCompatActivity implements OnCreateContextMenuListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendmnt_list);
        AgendamentoData age_data = new AgendamentoData(this);
        UsuarioData usu_data = new UsuarioData(this);
        try {
            usu_data.getNome().equals("null");
        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Você precisa terminar seu perfil para acessar esta página.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AccountDataActivity.class);
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(openFileInput("usuario"));
                intent.putExtra("usu", ((Usuario) ois.readObject()).getNome());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            startActivity(intent);
            finish();
        }
        List<String> agendmnts = age_data.getAllAgendmntsArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_whitetext, agendmnts);
        ListView listView = (ListView) findViewById(R.id.agendmnts_list);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(this);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        AgendamentoData age_data = new AgendamentoData(this);
        SQLiteDatabase db = age_data.getWritableDatabase();
        String id = extractNumericPart(((TextView) info.targetView).getText().toString().substring(19));
        switch (item.getItemId()) {
            case R.id.rm_agendmnt_item: {
                age_data.deletarDados(Integer.parseInt(id));
                if (!age_data.getAllAgendmntsArray().contains("ID do agendamento: " + id))
                    Toast.makeText(this, "Agendamento removido com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
            case R.id.ch_agendmnt_item: {
                Intent intent = new Intent(this, AgendmntChActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        }
        return super.onContextItemSelected(item);
    }

    private String extractNumericPart(String input) {
        StringBuilder numericPart = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                numericPart.append(c);
            } else {
                break;
            }
        }
        return numericPart.toString();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        getMenuInflater().inflate(R.menu.agcmenu, menu);
    }
}