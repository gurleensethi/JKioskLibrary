package app.com.thetechnocafe.jkiosklibrary;


import app.com.thetechnocafe.jkiosklibrary.Apis.Login.KioskLogin;
import app.com.thetechnocafe.jkiosklibrary.Apis.Semesters.KioskSemesters;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class JKiosk {

    //Returns an object of kiosk login
    public static KioskLogin getLoginApi() {
        return new KioskLogin();
    }

    //Returns an object Kiosk Semester
    public static KioskSemesters getSemestersApi() {
        return new KioskSemesters();
    }
}
