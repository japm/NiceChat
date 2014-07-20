package uyjj.nicechat;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uyjj.nicechat.Adapters.ContactAdapter;
import uyjj.nicechat.R;
import uyjj.nicechat.SQL.SQLiteDDL;
import uyjj.nicechat.SQL.SQLiteLoader;

public class ContactsActivity extends ListActivity
        implements LoaderManager.LoaderCallbacks<List<NiceChatProtos.Contact>>{

    ContactAdapter mCa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCa = new ContactAdapter(this,
                                 getListView().getId(),
                                 new ArrayList<NiceChatProtos.Contact>());

        setListAdapter(mCa);

        getLoaderManager().initLoader(0, null, this);

        setContentView(R.layout.activity_contacts);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_create) {
            return true;
        } else if (id == R.id.action_update) {
            return true;
        }else if (id == R.id.action_delete) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<NiceChatProtos.Contact>> onCreateLoader(int i, Bundle bundle) {
        return new SQLiteLoader<NiceChatProtos.Contact>(this, "Contact", NiceChatProtos.Contact.PARSER);
    }

    @Override
    public void onLoadFinished(Loader<List<NiceChatProtos.Contact>> listLoader, List<NiceChatProtos.Contact> contacts) {
        mCa.setContacts(contacts);
    }

    @Override
    public void onLoaderReset(Loader<List<NiceChatProtos.Contact>> listLoader) {
        mCa.setContacts(new ArrayList<NiceChatProtos.Contact>());
    }
}
