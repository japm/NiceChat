package uyjj.nicechat;

import android.app.Application;

/**
 * Created by juan on 7/5/14.
 */
public class ThisApp extends Application {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
