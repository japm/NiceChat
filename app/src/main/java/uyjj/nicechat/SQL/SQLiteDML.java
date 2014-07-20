package uyjj.nicechat.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 7/20/14.
 */
public class SQLiteDML<T extends MessageLite>{

    private final SQLiteOpenHelper mDb;
    private final String           mTable;
    private final com.google.protobuf.Parser<T> mParser;

    public  SQLiteDML(Context context,
                     String table,
                     com.google.protobuf.Parser<T> parser){
        this.mDb = new SQLiteDDL(context);
        this.mTable = table;
        this.mParser = parser;
    }

    public List<T> query() {
        SQLiteDatabase db = null;
        Cursor cr = null;
        List<T>        retVal = new ArrayList<T>();
        try{
            db = mDb.getReadableDatabase();
            cr = db.rawQuery("select data from " + mTable, null);
            if (cr == null)
                return retVal;
            cr.moveToFirst();
            while(!cr.isAfterLast()){
                byte[] d = cr.getBlob(0);
                if (d == null) {
                    cr.moveToNext();
                    continue;
                }
                try {
                    T obj = this.mParser.parseFrom(d);
                    retVal.add(obj);
                } catch (InvalidProtocolBufferException e){

                }
                cr.moveToNext();
            }
        } finally {
            try{if (cr != null) cr.close();} catch (Exception e){}
            try{if (db != null) db.close();} catch (Exception e){}
        }

        return retVal;
    }

}
