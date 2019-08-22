package arie.kamus.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_ENGLISH = "table_english_indonesia";
    static String TABLE_INDONESIA = "table_indonesia_english";

    static final class EnglishColumns implements BaseColumns {
        static String E_KATA = "e_kata";
        static String E_KETERANGAN = "e_keterangan";
    }
    static final class IndonesiaColumns implements BaseColumns {
        static String I_KATA = "i_kata";
        static String I_KETERANGAN = "i_keterangan";
    }
}
