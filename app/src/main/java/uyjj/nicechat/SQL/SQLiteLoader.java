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

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.google.protobuf.MessageLite;

import java.util.List;

public class SQLiteLoader<T extends MessageLite> extends AsyncTaskLoader<List<T>> {

    private final SQLiteDML mDML;
    private List<T>         mResult;


    public  SQLiteLoader(Context context,
                         String table,
                         com.google.protobuf.Parser<T> parser){
        super(context);

        this.mDML = new SQLiteDML<T>(context, table, parser);
        this.mResult = null;
    }


    @Override
    public List<T> loadInBackground() {
        return this.mDML.query();
    }



    @Override
    public void deliverResult(List<T> list) {
        if (isReset()) {
            mResult = null;
            return;
        }

        mResult=list;

        if (isStarted()) {
            super.deliverResult(mResult);
        }
    }


    @Override
    protected void onStartLoading() {
        if (mResult!=null) {
            deliverResult(mResult);
        }

        if (takeContentChanged() || mResult==null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
       cancelLoad();
    }

    @Override
    public void onCanceled(List<T> cursor) {
        mResult = null;
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        mResult =null;
    }
}
