package app.com.thetechnocafe.jkiosklibrary.Utilities;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

import app.com.thetechnocafe.jkiosklibrary.Constants;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class CookieUtility {

    /*
    * Connect to webkiosk.jiit.ac.in
    * Login with the provided credentials and return the cookies received
    * from the host.
    * These cookies can be used to further crawl other Webkiosk Urls
    * */
    public static Map<String, String> getCookiesFor(String enrollmentNumber, String dateOfBirth, String password) throws IOException {
        Connection.Response loginForm = Jsoup.connect("https://webkiosk.jiit.ac.in/")
                .method(Connection.Method.GET)
                .userAgent(Constants.AGENT_MOZILLA)
                .execute();

        Connection.Response mainPage = Jsoup.connect("https://webkiosk.jiit.ac.in/CommonFiles/UserActionn.jsp")
                .data("txtInst", "Institute")
                .data("InstCode", "JIIT")
                .data("txtuType", "Member Type")
                .data("UserType", "S")
                .data("txtCode", "Enrollment No")
                .data("MemberCode", enrollmentNumber)
                .data("DOB", "DOB")
                .data("DATE1", dateOfBirth)
                .data("txtPin", "Password/Pin")
                .data("Password", password)
                .data("BTNSubmit", "Submit")
                .cookies(loginForm.cookies())
                .execute();

        return mainPage.cookies();
    }
}
