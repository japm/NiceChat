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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uyjj.nicechat.SQL.SQLiteDML;

public class ContactCRUDActivity extends Activity {

    int mUserId = 0;
    NiceChatProtos.Contact mContact;
    EditText mName;
    Button   mSave;
    SQLiteDML dbContact;
    ContactCRUDActivity thisApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_crud);
        thisApp = this;

        dbContact = new SQLiteDML<NiceChatProtos.Contact>(this, "Contact", NiceChatProtos.Contact.PARSER );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserId = extras.getInt(Utils.USER_ID);
        }
        if (mUserId > 0){
            mContact = (NiceChatProtos.Contact)dbContact.query(mUserId);
        }
        mName = (EditText) this.findViewById(R.id.txtName);

        if (mContact == null){
            NiceChatProtos.Contact.Builder b = NiceChatProtos.Contact.newBuilder();
            b.setName("");
            mContact = b.build();
        } else {
            mName.setText(mContact.getName());
        }
        mSave = (Button) this.findViewById(R.id.btnSave);

        mSave.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    NiceChatProtos.Contact.Builder b = NiceChatProtos.Contact.newBuilder();
                    b.setName(mName.getText().toString());
                    mContact = b.build();
                    if (mUserId == 0)
                        dbContact.insert(mContact);
                    else
                        dbContact.update(mUserId, mContact);

                    thisApp.finish();
                }
            }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_crud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
