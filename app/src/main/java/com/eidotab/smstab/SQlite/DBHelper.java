package com.eidotab.smstab.SQlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.eidotab.smstab.Model.Historico;
import com.eidotab.smstab.Model.platoStats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "basesms01.db";
    private static final int DATABASE_VERSION = 3;
    private String DB_PATH = null;

    public static DBHelper GetDBHelper(Context context)
    {
        DBHelper dbHelper = new DBHelper(context.getApplicationContext());

        if (!dbHelper.isDataBaseExist())
        {
            dbHelper.deleteAllHistorico();

            dbHelper.deleteAllPlatoStats();

            dbHelper.createDataBase();

        }
        return dbHelper;
    }



    private DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    private void createDataBase()
    {
        boolean isExist = isDataBaseExist();

        if (!isExist)
        {
            this.getReadableDatabase();

            onCreate(this.getWritableDatabase());
        }
    }

    private boolean isDataBaseExist()
    {
        File file = new File(DB_PATH + DATABASE_NAME);

        return file.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Escribir la estructura de la bd: Tablas, ...
        db.execSQL(" CREATE TABLE historico (_id TEXT primary key, jhistorico  TEXT); ");
        db.execSQL(" CREATE TABLE platostats (_id INTEGER primary key, jplatostats  TEXT); ");

        // ....
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Escribir las modificaciones en la bd.
        db.execSQL(" DROP TABLE IF EXISTS historico; ");
        db.execSQL(" DROP TABLE IF EXISTS platostats; ");
        onCreate(db);
    }


    /* IMPLEMENTACIÓN: MÉTODOS CRUD */

    /* TABLA: historico */

    private static final String TABLE_NAME_HISTORICO = "historico";
    private static final String TABLE_NAME_PLATOSTATS = "platostats";


    public boolean addHistorico(Historico historico)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(historico);
        contentValues.put("_id", historico.get_id());
        contentValues.put("jhistorico", json);
        db.insert(TABLE_NAME_HISTORICO, null, contentValues);

        return true;
    }

    public boolean addPlatostats(ArrayList<ArrayList<platoStats>> datas)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(datas);
        contentValues.put("jplatostats", json);
        db.insert(TABLE_NAME_PLATOSTATS, null, contentValues);

        return true;
    }



    public boolean updateHistorico(Historico historico)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(historico);
        contentValues.put("_id", historico.get_id());
        contentValues.put("jhistorico", json);
        db.update(TABLE_NAME_HISTORICO, contentValues, "_id = ?",
                new String[]{ historico.get_id() });

        return true;
    }


/*
    public boolean updateTablet(Tablet tablet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(tablet);
        contentValues.put("nrtab", tablet.getNro_tablet());
        contentValues.put("jtablet", json);
        db.update(TABLE_NAME_TABLET, contentValues, "nrtab = ?",
                new String[]{ tablet.getNro_tablet() });

        return true;
    }*/


    public boolean deleteHistorico(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_HISTORICO, "_id = ?",
                new String[]{ id });

        return true;
    }


/*
    public boolean deleteTablet(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_TABLET, "nrtab = ?",
                new String[]{ id });

        return true;
    }
*/

    private boolean deleteAllHistorico()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_HISTORICO, "",
                new String[]{  });

        return true;
    }

    private boolean deleteAllPlatoStats()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_PLATOSTATS, "",
                new String[]{  });

        return true;
    }



/*    public Historico getHistorico(String id)
    {
        Historico historico = new Historico();

        SQLiteDatabase db = this.getReadableDatabase();

        Gson gson = new Gson();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_HISTORICO + " WHERE _id = '" + id + "'", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jhistorico")));

            historico  = gson.fromJson(sacadata, Historico.class);

            cursor.moveToNext();
        }
        cursor.close();
        return historico;
    }*/


   /* public Tablet getTablet(String nutab)
    {
        Tablet tablet = new Tablet();

        SQLiteDatabase db = this.getReadableDatabase();

        Gson gson = new Gson();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_TABLET + " WHERE nrtab= '" + nutab + "'", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jtablet")));

            tablet = gson.fromJson(sacadata, Tablet.class);

            cursor.moveToNext();
        }
        cursor.close();
        return tablet;
    }*/

    public ArrayList<Historico> listHistoricos()
    {
        ArrayList<Historico> lista = new ArrayList<>();

        //Historico historico;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_HISTORICO, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Historico historicos;

            String sacadata = (cursor.getString(cursor.getColumnIndex("jhistorico")));

            historicos  = gson.fromJson(sacadata, Historico.class);

            lista.add(historicos);

            cursor.moveToNext();
        }

        cursor.close();
        return lista;
    }

    public ArrayList<ArrayList<platoStats>> listplatostats()
    {
        ArrayList<ArrayList<platoStats>> lista = new ArrayList<>();

        //Historico historico;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_PLATOSTATS, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jplatostats")));

            Type listType = new TypeToken<ArrayList<ArrayList<platoStats>>>(){}.getType();

            lista= gson.fromJson(sacadata, listType);

            cursor.moveToNext();
        }

        cursor.close();
        return lista;
    }








}
