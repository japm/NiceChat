package uyjj.nicechat.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juan on 7/6/14.
 */
public class SQLiteDDL extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "nicechat";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_USER = "create table User( " +
                                                   "id integer primary key autoincrement, "+
                                                   "data BLOB )";

    private static final String TABLE_CONTACT = "create table Contact( " +
                                                    "id integer primary key autoincrement, "+
                                                    "data BLOB )";

    private static final String TABLE_USER_DROP = "DROP TABLE IF EXISTS User";
    private static final String TABLE_CONTACT_DROP = "DROP TABLE IF EXISTS Contact";



    public  SQLiteDDL(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_USER);
        sqLiteDatabase.execSQL(TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        //TODO: improve database upgrade
        sqLiteDatabase.execSQL(TABLE_USER_DROP);
        sqLiteDatabase.execSQL(TABLE_CONTACT_DROP);
        this.onCreate(sqLiteDatabase);
    }
}
