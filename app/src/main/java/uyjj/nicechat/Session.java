package uyjj.nicechat;

/**
 * Created by juan on 7/5/14.
 */
public class Session {

    private boolean logedIn;
    private byte[]  cryptoKey;
    private String  userName;
    private String  userId;

    public Session(){
        logedIn = false;
    }

    public void start(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }

    public boolean isLogedIn() {
        return logedIn;
    }

    public void setLogedIn(boolean logedIn) {
        this.logedIn = logedIn;
    }

    byte[] getCryptoKey() {
        return cryptoKey;
    }

    void setCryptoKey(byte[] cryptoKey) {
        this.cryptoKey = cryptoKey;
    }

    String getUserName() {
        return userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserId() {
        return userId;
    }

    void setUserId(String userId) {
        this.userId = userId;
    }
}
