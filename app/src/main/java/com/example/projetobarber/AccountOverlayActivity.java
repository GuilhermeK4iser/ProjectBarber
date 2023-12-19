package com.example.projetobarber;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.projetobarber.usr_vars.*;


public class AccountOverlayActivity extends Dialog {

    public AccountOverlayActivity(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.opcsconta_overlay);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        Button excluirconta_btn = findViewById(R.id.excluirconta_btn), sair_btn = findViewById(R.id.sair_btn);
        excluirconta_btn.setOnClickListener(v -> {
            dismiss();
            Dialog excluir = new Dialog(context);
            excluir.requestWindowFeature(Window.FEATURE_NO_TITLE);
            excluir.setContentView(R.layout.exconta_overlay);
            excluir.getWindow().setBackgroundDrawableResource(android.R.color.white);
            excluir.show();
            Button confirmar_btn = excluir.findViewById(R.id.confirmar_btn), cancelar_btn = excluir.findViewById(R.id.cancelar_btn);
            confirmar_btn.setVisibility(Button.VISIBLE);
            cancelar_btn.setVisibility(Button.VISIBLE);
            confirmar_btn.setOnClickListener(v1 -> {
                try {
                    context.deleteFile("usuario");
                    excluir.dismiss();
                    dismiss();
                    Toast.makeText(context, "Conta excluída com sucesso.", Toast.LENGTH_SHORT).show();
                    ((UserHomeActivity) context).finish();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    new UsuarioData(getContext()).deletarDados();
                    new AgendamentoData(getContext()).deletarTodosOsDados();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            cancelar_btn.setOnClickListener(v1 -> excluir.dismiss());
        });
        sair_btn.setOnClickListener(v -> {
            try {
                FileInputStream fis = context.openFileInput("usuario");
                ObjectInputStream ois = new ObjectInputStream(fis);
                Usuario usr = (Usuario) ois.readObject();
                ois.close();
                fis.close();
                FileOutputStream fos = context.openFileOutput("usuario_deslogado", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(usr);
                oos.close();
                fos.close();
                dismiss();
                Toast.makeText(context, "Saindo...", Toast.LENGTH_SHORT).show();
                ((UserHomeActivity) context).finish();
                context.deleteFile("usuario");
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
