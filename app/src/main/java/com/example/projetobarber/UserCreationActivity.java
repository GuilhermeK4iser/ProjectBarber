package com.example.projetobarber;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.Context;
import android.widget.VideoView;
import android.media.MediaPlayer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.example.projetobarber.usr_vars.Usuario;

public class UserCreationActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abertura_conta);
    }

    public void validarCriacao(View v) {
        CheckBox cc = findViewById(R.id.termos_check);
        EditText usuario = findViewById(R.id.criacaonomeusuario_campo), senha = findViewById(R.id.criacaosenha_campo);
        if (cc.isChecked() && !usuario.getText().toString().equals("") && !senha.getText().toString().equals("")) {
            if (usuario.getText().toString().toLowerCase().contains("everton") || usuario.getText().toString().toLowerCase().contains("lor") || usuario.getText().toString().toLowerCase().contains("gui")) {
                VideoView video = findViewById(R.id.videoView);
                String video_p = "android.resource://" + getPackageName() + "/" + R.raw.ee_video;
                Uri uri = Uri.parse(video_p);
                video.setVideoURI(uri);
                video.setVisibility(View.VISIBLE);
                video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        video.setVisibility(View.INVISIBLE);
                        goToHome(v);
                    }
                });
                video.start();
            } else goToHome(v);
            /*try {
                FileOutputStream fos = openFileOutput("usuario", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                Usuario novo = new Usuario(usuario.getText().toString(), senha.getText().toString());
                oos.writeObject(novo);
                oos.close();
                fos.close();
                Intent intent = new Intent(this, UserHomeActivity.class);
                intent.putExtra("usu", novo.getNome());
                intent.putExtra("sen", novo.getSenha());
                startActivity(intent);
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } else if (!cc.isChecked()) {
            cc.setError("Você deve aceitar os termos de uso para demonstrar conhecimento.");
        } else if (usuario.getText().toString().equals("")) {
            usuario.setError("Você deve inserir um nome de usuário para sua conta.");
        } else if (senha.getText().toString().equals("")) {
            senha.setError("Você deve inserir uma senha para o usuário.");
        }
    }

    public void goToHome(View v) {
        EditText usuario = findViewById(R.id.criacaonomeusuario_campo), senha = findViewById(R.id.criacaosenha_campo);
        try {
            FileOutputStream fos = openFileOutput("usuario", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Usuario novo = new Usuario(usuario.getText().toString(), senha.getText().toString());
            oos.writeObject(novo);
            oos.close();
            fos.close();
            Intent intent = new Intent(this, UserHomeActivity.class);
            intent.putExtra("usu", novo.getNome());
            intent.putExtra("sen", novo.getSenha());
            startActivity(intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
