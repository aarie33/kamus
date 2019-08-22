package arie.kamus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static arie.kamus.db.DatabaseContract.EnglishColumns.E_KATA;
import static arie.kamus.db.DatabaseContract.EnglishColumns.E_KETERANGAN;
import static arie.kamus.db.DatabaseContract.TABLE_ENGLISH;

public class EnglishHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public EnglishHelper(Context context) {
        this.context = context;
    }

    public EnglishHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<EnglishModel> getDataByName(String kata) {
        String result = "";
        Cursor cursor = database.query(TABLE_ENGLISH, null, E_KATA + " LIKE ?", new String[]{"%"+kata+"%"}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<EnglishModel> arrayList = new ArrayList<>();
        EnglishModel EnglishModel;
        if (cursor.getCount() > 0) {
            do {
                EnglishModel = new EnglishModel();
                EnglishModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                EnglishModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(E_KATA)));
                EnglishModel.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(E_KETERANGAN)));

                arrayList.add(EnglishModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<EnglishModel> getAllData() {
        Cursor cursor = database.query(TABLE_ENGLISH, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<EnglishModel> arrayList = new ArrayList<>();
        EnglishModel EnglishModel;
        if (cursor.getCount() > 0) {
            do {
                EnglishModel = new EnglishModel();
                EnglishModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                EnglishModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(E_KATA)));
                EnglishModel.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(E_KETERANGAN)));


                arrayList.add(EnglishModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(EnglishModel EnglishModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(E_KATA, EnglishModel.getKata());
        initialValues.put(E_KETERANGAN, EnglishModel.getKeterangan());
        return database.insert(TABLE_ENGLISH, null, initialValues);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(EnglishModel EnglishModel) {
        String sql = "INSERT INTO " + TABLE_ENGLISH + " (" + E_KATA + ", " + E_KETERANGAN
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, EnglishModel.getKata());
        stmt.bindString(2, EnglishModel.getKeterangan());
        stmt.execute();
        stmt.clearBindings();

    }

    public int update(EnglishModel EnglishModel) {
        ContentValues args = new ContentValues();
        args.put(E_KATA, EnglishModel.getKata());
        args.put(E_KETERANGAN, EnglishModel.getKeterangan());
        return database.update(TABLE_ENGLISH, args, _ID + "= '" + EnglishModel.getId() + "'", null);
    }


    public int delete(int id) {
        return database.delete(TABLE_ENGLISH, _ID + " = '" + id + "'", null);
    }
}
