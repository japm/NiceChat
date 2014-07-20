/**
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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
