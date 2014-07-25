/*
 * Copyright (C) 2014  Juan Pascual
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package uyjj.nicechat.SQL;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

import java.util.ArrayList;
import java.util.List;

import uyjj.nicechat.Utils;

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
            cr = db.rawQuery("select data from " + mTable , null);
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
                    Utils.Dummy();
                }
                cr.moveToNext();
            }
        } finally {
            try{if (cr != null) cr.close();} catch (Exception e){Utils.Dummy();}
            try{if (db != null) db.close();} catch (Exception e){Utils.Dummy();}
        }

        return retVal;
    }

    public T query(int id) {
        SQLiteDatabase db = null;
        Cursor cr = null;
        T      retVal = null;
        try{
            db = mDb.getReadableDatabase();
            cr = db.rawQuery("select data from " + mTable +" where id = ?", new String[]{ Integer.toString(id)});
            if (cr == null)
                return retVal;
            cr.moveToFirst();
            if (!cr.isAfterLast())
                return retVal;

            byte[] d = cr.getBlob(0);
            if (d == null)
                return retVal;

            try {
                retVal = this.mParser.parseFrom(d);
            } catch (InvalidProtocolBufferException e){
                Utils.Dummy();
            }

        } finally {
            try{if (cr != null) cr.close();} catch (Exception e){Utils.Dummy();}
            try{if (db != null) db.close();} catch (Exception e){Utils.Dummy();}
        }

        return retVal;
    }

    public void insert(T obj) {
        if (obj == null)
            return ;

        SQLiteDatabase db = null;
        try{
            db = mDb.getReadableDatabase();
            db.execSQL("insert into " + mTable + " (Data) values(?)", new Object[]{ obj.toByteArray() });
        } finally {
            try{if (db != null) db.close();} catch (Exception e){Utils.Dummy();}
        }
    }

    public void update(int id, T obj) {
        if (obj == null)
            return;

        SQLiteDatabase db = null;
        try{
            db = mDb.getReadableDatabase();
            db.execSQL("update " + mTable + " set Data = ? where id =  ?", new Object[]{ obj.toByteArray(), id });
        } finally {
            try{if (db != null) db.close();} catch (Exception e){Utils.Dummy();}
        }
    }
}
