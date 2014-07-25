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

package uyjj.nicechat;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import uyjj.nicechat.Adapters.ContactAdapter;
import uyjj.nicechat.SQL.SQLiteLoader;

public class ContactsActivity extends ListActivity
        implements LoaderManager.LoaderCallbacks<List<NiceChatProtos.Contact>>{

    ContactAdapter mCa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCa = new ContactAdapter(this,
                                 android.R.layout.simple_list_item_1,
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
            transitionToCreateContract();
            return true;
        } else if (id == R.id.action_update) {
            return true;
        }else if (id == R.id.action_delete) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void transitionToCreateContract() {
        Intent intent = new Intent(this, ContactCRUDActivity.class);
        intent.putExtra(Utils.USER_ID, 0);
        this.startActivity(intent);
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
