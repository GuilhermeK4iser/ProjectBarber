package com.example.projetobarber.usr_vars;

import android.database.Cursor;
import android.database.sqlite.*;

public class UsuarioData extends SQLiteOpenHelper {
    public UsuarioData(android.content.Context context) {
        super(context, "usuario.db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario (_id integer primary key autoincrement, nome_usuario text, nome_legal text, email text, senha text, telefone text, cep text)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
        onCreate(db);
    }

    public void inserirDados(String nome_usuario, String nome_legal, String email, String senha, String telefone, String cep) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into usuario (nome_usuario, nome_legal, email, senha, telefone, cep) values ('" + nome_usuario + "', '" + nome_legal + "', '" + email + "', '" + senha + "', '" + telefone + "', '" + cep + "')");
    }

    public void alterarDados(String nome_usuario, String email, String senha, String telefone, String cep) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update usuario set nome_usuario = '" + nome_usuario + "', email = '" + email + "', senha = '" + senha + "', telefone = '" + telefone + "', cep = '" + cep + "'");
    }

    public void deletarDados() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from usuario");
    }

    public String getNome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select nome_legal from usuario", null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getString(0);
    }

    public String getEmail() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select email from usuario", null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getString(0);
    }

    public String getPostal() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select cep from usuario", null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getString(0);
    }

    public String getTelefone() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select telefone from usuario", null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getString(0);
    }

    public String getSenha() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select senha from usuario", null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getString(0);
    }

    public String getUsuario() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select nome_usuario from usuario", null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getString(0);
    }

    public int getIdentifier() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select _id from usuario", null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getInt(0);
    }
}
