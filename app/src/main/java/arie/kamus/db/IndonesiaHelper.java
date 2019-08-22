package arie.kamus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static arie.kamus.db.DatabaseContract.IndonesiaColumns.I_KATA;
import static arie.kamus.db.DatabaseContract.IndonesiaColumns.I_KETERANGAN;
import static arie.kamus.db.DatabaseContract.TABLE_INDONESIA;

public class IndonesiaHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public IndonesiaHelper(Context context){
        this.context = context;
    }

    public IndonesiaHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<IndonesiaModel> getDataByName(String kata){
        Cursor cursor = database.query(TABLE_INDONESIA, null, I_KATA+" LIKE ? ", new String[]{"%"+kata+"%"},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        ArrayList<IndonesiaModel> arrayList = new ArrayList<>();
        IndonesiaModel indonesiaModel;
        if (cursor.getCount()>0) {
            do {
                indonesiaModel = new IndonesiaModel();
                indonesiaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                indonesiaModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(I_KATA)));
                indonesiaModel.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(I_KETERANGAN)));

                arrayList.add(indonesiaModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<IndonesiaModel> getAllData(){
        Cursor cursor = database.query(TABLE_INDONESIA,null,null,null,null,null,_ID+ " ASC",null);
        cursor.moveToFirst();
        ArrayList<IndonesiaModel> arrayList = new ArrayList<>();
        IndonesiaModel indonesiaModel;
        if (cursor.getCount()>0) {
            do {
                indonesiaModel = new IndonesiaModel();
                indonesiaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                indonesiaModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(I_KATA)));
                indonesiaModel.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(I_KETERANGAN)));


                arrayList.add(indonesiaModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(IndonesiaModel IndonesiaModel){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(I_KATA, IndonesiaModel.getKata());
        initialValues.put(I_KETERANGAN, IndonesiaModel.getKeterangan());
        return database.insert(TABLE_INDONESIA, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(IndonesiaModel IndonesiaModel){
        String sql = "INSERT INTO "+TABLE_INDONESIA+" ("+I_KATA+", "+I_KETERANGAN
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, IndonesiaModel.getKata());
        stmt.bindString(2, IndonesiaModel.getKeterangan());
        stmt.execute();
        stmt.clearBindings();

    }

    public int update(IndonesiaModel IndonesiaModel){
        ContentValues args = new ContentValues();
        args.put(I_KATA, IndonesiaModel.getKata());
        args.put(I_KETERANGAN, IndonesiaModel.getKeterangan());
        return database.update(TABLE_INDONESIA, args, _ID + "= '" + IndonesiaModel.getId() + "'", null);
    }


    public int delete(int id){
        return database.delete(TABLE_INDONESIA, _ID + " = '"+id+"'", null);
    }
}
