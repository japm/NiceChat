package uyjj.nicechat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.protobuf.ByteString;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import uyjj.nicechat.SQL.SQLiteDDL;
import uyjj.nicechat.SQL.SQLiteDML;
import uyjj.nicechat.SQL.SQLiteRow;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ThisApp mApp;
    private SQLiteDDL mDb;
    private Button mRegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLiteDDL(this);

        setContentView(R.layout.activity_login);


        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                mRegisterButton.setVisibility(View.GONE);
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin(true);
                    return true;
                }
                return false;
            }
        });
        mPasswordView.setOnKeyListener(
            new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (mRegisterButton.getVisibility() != View.GONE)
                        mRegisterButton.setVisibility(View.GONE);
                    return false;
                }

        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(true);
            }
        });

        mRegisterButton = (Button) findViewById(R.id.email_register_button);
        mRegisterButton.setVisibility(View.GONE);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(false);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mApp = (ThisApp)getApplication();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(boolean login) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(this, email, password, login);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private boolean mLogin;
        private final LoginActivity mLogAct;

        UserLoginTask(LoginActivity LogAct, String email, String password, boolean login) {
            mEmail = email;
            mPassword = password;
            mLogin = login;
            mLogAct = LogAct;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Session        ses = mApp.getSession();

            if (mLogin) {
                Login(ses);
            } else {
                Register(ses);
            }

            return  ses.isLogedIn();
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                mApp.getSession().setLogedIn(true);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                mRegisterButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
            mApp.getSession().setLogedIn(false);
        }

        private void Login(Session  ses){

            byte[] pw512;
            try {
                pw512 = Utils.SHA512(mPassword);
            } catch (NoSuchAlgorithmException e) {
                pw512 = null;
            }
            if (pw512 == null)
                return; //Utf-8 not valid nearly impossible

            SQLiteDML<NiceChatProtos.User> db =
                    new SQLiteDML<NiceChatProtos.User>(mLogAct, "User", NiceChatProtos.User.PARSER);
            List<SQLiteRow<NiceChatProtos.User>> users = db.query();
            byte[] pwDb512 = new byte[64];
            for(SQLiteRow<NiceChatProtos.User> usrRow : users){
                NiceChatProtos.User usr = usrRow.getData();
                usr.getPasswordSHA().copyTo(pwDb512, 0);
                if (Arrays.equals(pwDb512, pw512)) {
                    ses.setUserId(usr.getUserID());
                    ses.setUserName(usr.getName());
                    ses.setLogedIn(true);
                    break;
                }
            }
        }

        private void Register(Session        ses){
            SQLiteDatabase db  = mDb.getWritableDatabase();
            try {
                byte[] pw512;
                try {
                    pw512 = Utils.SHA512(mPassword);
                } catch (NoSuchAlgorithmException e) {
                    pw512 = null;
                }

                NiceChatProtos.User.Builder b = NiceChatProtos.User.newBuilder();
                b.setName(this.mEmail);
                b.setPasswordSHA(ByteString.copyFrom(pw512));
                b.setUserID(this.mEmail);

                NiceChatProtos.User usr = b.build();

                ContentValues values = new ContentValues();
                values.put("data", usr.toByteArray());

                final long user = db.insert("User", null, values);

                ses.setUserId(this.mEmail);
                ses.setUserName(this.mEmail);
                ses.setLogedIn(true);
            } finally {
                try{ db.close();}catch(Exception e) {}
            }
        }
    }
}



