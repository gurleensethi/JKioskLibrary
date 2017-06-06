package app.com.tedconsulting.jkiosklibrary;

import app.com.tedconsulting.jkiosklibrary.Apis.KioskLogin;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class JKiosk {

    //Returns a variable of kiosk login
    public static KioskLogin getLoginApi() {
        return new KioskLogin();
    }
}
