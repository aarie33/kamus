package arie.kamus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static arie.kamus.db.DatabaseContract.EnglishColumns.E_KATA;
import static arie.kamus.db.DatabaseContract.EnglishColumns.E_KETERANGAN;
import static arie.kamus.db.DatabaseContract.IndonesiaColumns.I_KATA;
import static arie.kamus.db.DatabaseContract.IndonesiaColumns.I_KETERANGAN;
import static arie.kamus.db.DatabaseContract.TABLE_ENGLISH;
import static arie.kamus.db.DatabaseContract.TABLE_INDONESIA;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENGLISH = "create table "+TABLE_ENGLISH+
            " ("+_ID+" integer primary key autoincrement, " +
            E_KATA+" text not null, " +
            E_KETERANGAN+" text not null);";
    public static String CREATE_TABLE_INDONESIA = "create table "+TABLE_INDONESIA+
            " ("+_ID+" integer primary key autoincrement, " +
            I_KATA+" text not null, " +
            I_KETERANGAN+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISH);
        db.execSQL(CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_INDONESIA);
        onCreate(db);
    }
}
