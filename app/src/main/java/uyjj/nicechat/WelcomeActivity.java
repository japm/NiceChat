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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;


public class WelcomeActivity extends Activity {

    ProgressBar mProgress;
    TextView    mTextProgress;
    ThisApp     mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mProgress = (ProgressBar)this.findViewById(R.id.progressBar);
        mTextProgress = (TextView)this.findViewById(R.id.textProgress);
        mApp = (ThisApp)getApplication();

        AsyncTask initTask = new AsyncTask() {

            WelcomeActivity thisAct;

            @Override
            protected Object doInBackground(Object[] objects) {
                thisAct = (WelcomeActivity)objects[0];
                thisAct.mApp.setSession(new Session());
                this.publishProgress(getString(R.string.progress_init_session));
                thisAct.mApp.getSession().start();
                this.publishProgress(getString(R.string.progress_session_started));
                try {Thread.sleep(1000);} catch (InterruptedException e) {Utils.Dummy();}
                return null;
            }
            @Override
            protected void onProgressUpdate(Object... values){
                mTextProgress.setText((String)values[0]);
            }

            @Override
            protected void onPostExecute(Object Result){
                Intent intent = new Intent(thisAct, LoginActivity.class);
                thisAct.startActivity(intent);
            }
        };

        initTask.execute(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_close){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        //Session s = mApp.getSession();
        //if (s == null)
        //   this.finish(); //No session? Something went wrong, finish the application
        //else if (!s.isLogedIn())
        //   this.finish(); //Failed to login? Finish the application

        super.onResume();
        Session s = mApp.getSession();
        if (s != null && s.isLogedIn()){
            Intent intent = new Intent(this, ContactsActivity.class);
            this.startActivity(intent);
            this.finish();
        }


    }
}
