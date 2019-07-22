package com.example.dublinbusapp;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BusStopPoints.db";
    public static final String TABLE_NAME = "BusStop_table";
    public static final String COL_1 = "SP_Modification";
    public static final String COL_2 = "SP_Status";
    public static final String COL_3 = "SP_CreationDateTime";
    public static final String COL_4 = "SP_RevisionNumber";
    public static final String COL_5 = "SP_ModificationDateTime";
    public static final String COL_6 = "AtcoCode";
    public static final String COL_7 = "PlateCode";
    public static final String COL_8 = "CommonName_lang_en";
    public static final String COL_9 = "ShortCommonName_lang_en";
    public static final String COL_10 = "Street_lang_en";
    public static final String COL_11 = "ShortCommonName_lang_ga";
    public static final String COL_12 = "LocalityCentre";
    public static final String COL_13 = "GridType";
    public static final String COL_14 = "Easting";
    public static final String COL_15 = "Northing";
    public static final String COL_16 = "Longitude";
    public static final String COL_17 = "Latitude";
    public static final String COL_18 = "StopType";
    public static final String COL_19 = "BusStopType";
    public static final String COL_20 = "TimingStatus";
    public static final String COL_21 = "CompassPoint";
    public static final String COL_22 = "StopAreaRef_Modification";
    public static final String COL_23 = "StopAreaRef";
    public static final String COL_24 = "StopAreaRef_Status";
    public static final String COL_25 = "StopAreaRef_CreationDateTime";
    public static final String COL_26 = "StopAreaRef_ModificationDateTime";
    public static final String COL_27 = "AdministrativeAreaRef";
    public static final String COL_28 = "Indicator_lang_en";
    public static final String COL_29 = "NptgLocalityRef";
    public static final String COL_30 = "NaptanCode";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,SP_ModificationSP_Status TEXT,SP_CreationDateTime TEXT,SP_RevisionNumber TEXT,SP_ModificationDateTime TEXT,AtcoCode TEXT,PlateCode TEXT,CommonName_lang_en TEXT,ShortCommonName_lang_en TEXT,Street_lang_en TEXT,ShortCommonName_lang_ga TEXT,LocalityCentre TEXT,GridType TEXT,Easting TEXT,Northing TEXT,Longitude TEXT,Latitude TEXT,StopType TEXT,BusStopType TEXT,TimingStatus TEXT,CompassPoint TEXT,StopAreaRef_Modification TEXT,StopAreaRef TEXT,StopAreaRef_Status TEXT,StopAreaRef_CreationDateTime TEXT,StopAreaRef_ModificationDateTime TEXT,AdministrativeAreaRef TEXT,Indicator_lang_en TEXT,NptgLocalityRef TEXT,NaptanCode)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
