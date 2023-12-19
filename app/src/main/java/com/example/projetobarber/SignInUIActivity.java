package com.example.projetobarber;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.projetobarber.usr_vars.Usuario;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class SignInUIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_senha);
    }

    public void validarLogin(View v) {
        EditText usuario = findViewById(R.id.usuario_login_campo), senha = findViewById(R.id.senha_login_campo);
        if (!usuario.getText().toString().equals("") && !senha.getText().toString().equals("")) {
            try {
                FileInputStream fis = openFileInput("usuario_deslogado");
                ObjectInputStream ois = new ObjectInputStream(fis);
                Usuario usr = (Usuario) ois.readObject();
                ois.close();
                fis.close();
                if (usr.getNome().equals(usuario.getText().toString()) && usr.getSenha().equals(senha.getText().toString())) {
                    FileOutputStream fos = openFileOutput("usuario", MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(usr);
                    oos.close();
                    fos.close();
                    getApplicationContext().deleteFile("usuario_deslogado");
                    android.content.Intent intent = new android.content.Intent(this, UserHomeActivity.class);
                    intent.putExtra("usu", usr.getNome());
                    intent.putExtra("sen", usr.getSenha());
                    startActivity(intent);
                    finish();
                } else if (!usr.getNome().equals(usuario.getText().toString())) {
                    usuario.setError("Usuário não encontrado.");
                }
                if (!usr.getSenha().equals(senha.getText().toString())) {
                    senha.setError("Senha incorreta.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString();
                if (sStackTrace.contains("usuario_deslogado") && sStackTrace.contains("java.io.FileNotFoundException")) {
                    usuario.setError("Usuário não encontrado.");
                } else if (sStackTrace.contains("java.io.EOFException")) {
                    senha.setError("Senha incorreta.");
                } else {
                    usuario.setError("Erro desconhecido.");
                }
            }
        } else if (usuario.getText().toString().equals("")) {
            usuario.setError("Você deve inserir um nome de usuário para sua conta.");
        } else if (senha.getText().toString().equals("")) {
            senha.setError("Você deve inserir uma senha para o usuário.");
        }
    }
}
