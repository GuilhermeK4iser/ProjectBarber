package com.example.projetobarber;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.ObjectInputStream;

import com.example.projetobarber.usr_vars.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectInputStream ois;
        Usuario usr;
        try {
            ois = new ObjectInputStream(openFileInput("usuario"));
            usr = (Usuario) ois.readObject();
            Intent intent = new Intent(this, UserHomeActivity.class);
            intent.putExtra("usu", usr.getNome());
            intent.putExtra("sen", usr.getSenha());
            startActivity(intent);
            finish();
        } catch (Exception e) {
            setContentView(R.layout.registro_conta);
        }
    }

    public void abrirLogin(View v) {
        Intent intent = new Intent(this, SignInUIActivity.class);
        startActivity(intent);
    }

    public void abrirCriacao(View v) {
        Intent intent = new Intent(this, UserCreationActivity.class);
        startActivity(intent);
    }
}