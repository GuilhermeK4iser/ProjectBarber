package com.example.projetobarber;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import com.bumptech.glide.Glide;
import com.example.projetobarber.usr_vars.Usuario;
import com.example.projetobarber.usr_vars.UsuarioData;

public class UserHomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        abreIntent(getIntent().getStringExtra("usu"), getIntent().getStringExtra("sen"));
        View accopts = findViewById(R.id.accopts_view);
        accopts.setOnClickListener(v -> {
            AccountOverlayActivity acc = new AccountOverlayActivity(this, true, null);
            acc.show();
        });
        View accodata = findViewById(R.id.accodata_view);
        accodata.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccountDataActivity.class);
            intent.putExtra("usu", getIntent().getStringExtra("usu"));
            startActivity(intent);
        });
        ImageView ic_agendmnt = findViewById(R.id.icone_agendmnt);
        Glide.with(this).load(R.drawable.icschedule_dark).into(ic_agendmnt);
        ic_agendmnt.setOnClickListener(v -> {
            UsuarioData usu_data = new UsuarioData(this);
            try {
                usu_data.getNome().equals("null");
                Intent intent = new Intent(this, Agendmntpt1andpt2Activity.class);
                startActivity(intent);
            } catch (Exception e) {
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
            }
        });
    }

    public void abreIntent(String usu, String sen) {
        Usuario u = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput("usuario"));
            u = (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (!Objects.equals(sen, u.getSenha())) {
            Toast.makeText(this, "Credenciais não correspondem. Saindo...", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        } else {
            TextView ut = findViewById(R.id.usuario_texto);
            ut.setText(String.format("%s, %s", ut.getText(), usu));
        }
    }

    public void abrirAgendmntsViewHome(View view) {
        Intent intent = new Intent(this, AgendmntListActivity.class);
        startActivity(intent);
    }
}
