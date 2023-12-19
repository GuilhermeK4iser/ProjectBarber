package com.example.projetobarber.usr_vars;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "agendamento.db";
    public static final String TABLE_NAME = "agendamento";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "barbeiro";
    public static final String COL_3 = "data";
    public static final String COL_4 = "hora";
    public static final String COL_5 = "servicos";
    public static final String COL_6 = "preco";
    public static final String COL_7 = "nome_lg";

    public AgendamentoData(android.content.Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " (" + COL_1 + " integer primary key autoincrement, " + COL_2 + " text, " + COL_3 + " text, " + COL_4 + " text, " + COL_5 + " text, " + COL_6 + " integer, " + COL_7 + " text)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    public void inserirDados(String barbeiro, String data, String hora, String servicos, int preco, String nome_lg) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into " + TABLE_NAME + " (" + COL_2 + ", " + COL_3 + ", " + COL_4 + ", " + COL_5 + ", " + COL_6 + ", " + COL_7 + ") values ('" + barbeiro + "', '" + data + "', '" + hora + "', '" + servicos + "', '" + preco + "', '" + nome_lg + "')");
    }

    public void deletarDados(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where " + COL_1 + " = " + id);
    }

    public void alterarDados(int id, String barbeiro, String data, String hora, String servicos, int preco) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update " + TABLE_NAME + " set " + COL_2 + " = '" + barbeiro + "', " + COL_3 + " = '" + data + "', " + COL_4 + " = '" + hora + "', " + COL_5 + " = '" + servicos + "', " + COL_6 + " = '" + preco + "' where " + COL_1 + " = " + id);
    }

    public void deletarTodosOsDados() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }

    public String getTotalAgendmnts() {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("select count(*) from " + TABLE_NAME, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getAllAgendmntsData() {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        StringBuilder result = new StringBuilder();
        while (!cursor.isAfterLast()) {
            result.append(cursor.getString(0)).append(" ").append(cursor.getString(1)).append(" ").append(cursor.getString(2)).append(" ").append(cursor.getString(3)).append(" ").append(cursor.getString(4)).append(" ").append(cursor.getString(5)).append(" ").append(cursor.getString(6)).append("\n");
            cursor.moveToNext();
        }
        return result.toString();
    }

    public List<String> getAllAgendmntsArray() {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        List<String> result = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            result.add("ID do agendamento: " + cursor.getString(0) + " | Barbeiro: " + cursor.getString(1) + " | Data: " + cursor.getString(2) + " | Hora: " + cursor.getString(3) + " | Serviço(s): " + cursor.getString(4) + " | Preço (R$): " + cursor.getString(5) + ",00 | Nome legal do cliente: " + cursor.getString(6));
            cursor.moveToNext();
        }
        return result;
    }

    public String describeTable() {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("PRAGMA table_info(" + TABLE_NAME + ")", null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getDateAndTime() {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("select " + COL_3 + ", " + COL_4 + " from " + TABLE_NAME, null);
        cursor.moveToFirst();
        StringBuilder result = new StringBuilder();
        while (!cursor.isAfterLast()) {
            result.append(cursor.getString(0)).append(" ").append(cursor.getString(1)).append("\n");
            cursor.moveToNext();
        }
        return result.toString();
    }

    public String[] getCurrAgendmntData(int id) {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("select " + COL_2 + ", " + COL_4 + ", " + COL_5 + ", " + COL_6 + ", " + COL_7 + ", " + COL_3 + " from " + TABLE_NAME + " where " + COL_1 + " = " + id, null);
        cursor.moveToFirst();
        String[] result = new String[7];
        result[0] = cursor.getString(0);
        result[1] = cursor.getString(1);
        result[2] = cursor.getString(2);
        result[3] = cursor.getString(3);
        result[4] = cursor.getString(4);
        result[5] = cursor.getString(5);
        return result;
    }
}
