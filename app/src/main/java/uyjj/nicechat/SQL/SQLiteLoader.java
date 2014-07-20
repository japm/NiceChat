package uyjj.nicechat.SQL;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 7/19/14.
 */
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
