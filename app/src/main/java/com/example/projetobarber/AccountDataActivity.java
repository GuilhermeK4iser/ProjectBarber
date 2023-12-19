package com.example.projetobarber;

import android.app.Dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.*;
import android.widget.Toast;

import com.example.projetobarber.usr_vars.*;

import java.io.IOException;
import java.io.ObjectInputStream;

import androidx.appcompat.app.AppCompatActivity;

public class AccountDataActivity extends AppCompatActivity {
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_usuario);
        TextView usu_name = findViewById(R.id.paginausuario_usrtexto);
        String usu_texto = getIntent().getStringExtra("usu");
        usu_name.setText(usu_texto);
    }

    public void abrirDadosDialogo(View v) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dados_usuario_dialog);
        UsuarioData udhelper = new UsuarioData(this);
        TextView emailread = dialog.findViewById(R.id.emailread_view), foneread = dialog.findViewById(R.id.fnread_view), cepread = dialog.findViewById(R.id.cepread_view), nomelegread = dialog.findViewById(R.id.nomelegread_view);
        try {
            emailread.setText(String.format("%s %s", emailread.getText(), udhelper.getEmail()));
            foneread.setText(String.format("%s %s", foneread.getText(), udhelper.getTelefone()));
            cepread.setText(String.format("%s %s", cepread.getText(), udhelper.getPostal()));
            nomelegread.setText(String.format("%s %s", nomelegread.getText(), udhelper.getNome()));
            dialog.show();
        } catch (Exception e) {
            if (e.toString().contains("CursorIndexOutOfBoundsException"))
                Toast.makeText(this, "Você precisa concluir o perfil, faça isso clicando no botão ao lado", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Erro desconhecido.", Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirEdicaoDadosDialogo(View v) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alterar_pdata_dialogo);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        UsuarioData udhelper = new UsuarioData(this);
        try {
            if (!udhelper.getNome().equals("null")) {
                ((EditText) dialog.findViewById(R.id.insemail_db_campo)).setText(udhelper.getEmail());
                ((EditText) dialog.findViewById(R.id.insfone_db_campo)).setText(udhelper.getTelefone());
                ((EditText) dialog.findViewById(R.id.inscep_db_campo)).setText(udhelper.getPostal());
                ((EditText) dialog.findViewById(R.id.insnomeleg_db_campo)).setText(udhelper.getNome());
                dialog.findViewById(R.id.insnomeleg_db_campo).setFocusable(false);
                dialog.findViewById(R.id.insnomeleg_db_campo).setClickable(false);
            } else {
                ((EditText) dialog.findViewById(R.id.insemail_db_campo)).setText(udhelper.getEmail());
                ((EditText) dialog.findViewById(R.id.insfone_db_campo)).setText(udhelper.getTelefone());
                ((EditText) dialog.findViewById(R.id.inscep_db_campo)).setText(udhelper.getPostal());
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        Button btn = dialog.findViewById(R.id.confirmar_alteracoespdata_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirmarEdicaoDadosDialogo(dialog);
            }
        });
        dialog.show();
    }

    public void confirmarEdicaoDadosDialogo(Dialog d) {
        String email = ((EditText) d.findViewById(R.id.insemail_db_campo)).getText().toString(), fone = ((EditText) d.findViewById(R.id.insfone_db_campo)).getText().toString(), cep = ((EditText) d.findViewById(R.id.inscep_db_campo)).getText().toString(), nomelegal = ((EditText) d.findViewById(R.id.insnomeleg_db_campo)).getText().toString();
        UsuarioData ud = new UsuarioData(this);
        SQLiteDatabase db = ud.getWritableDatabase();
        Usuario usr = new Usuario();
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput("usuario"));
            usr = (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (!usr.getNome().equals(ud.getNome())) {
                ud.deletarDados();
                ud.inserirDados(usr.getNome(), nomelegal, email, usr.getSenha(), fone, cep);
            } else ud.alterarDados(usr.getNome(), email, usr.getSenha(), fone, cep);
            ud.close();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            ud.inserirDados(usr.getNome(), nomelegal, email, usr.getSenha(), fone, cep);
            ud.close();
        }
        d.dismiss();
    }

    public void abrirAgendmntsView(View v) {
        startActivity(new android.content.Intent(this, AgendmntListActivity.class));
    }
}
