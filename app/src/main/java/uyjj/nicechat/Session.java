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
            Utils.Dummy();
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
