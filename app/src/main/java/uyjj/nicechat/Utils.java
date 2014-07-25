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

import java.io.UnsupportedEncodingException;

public class Utils {

    public static final String USER_ID = "USER_ID";

    public static byte[] SHA512(String toEncode) throws java.security.NoSuchAlgorithmException{

        if (toEncode == null)
            return null;
        try {
            return SHA512(toEncode.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e){
            return null;
        }

    }

    public static byte[] SHA512(byte[] toEncode) throws java.security.NoSuchAlgorithmException{

        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");

        return digest.digest(toEncode);
    }

    public static void Dummy(){}
}
