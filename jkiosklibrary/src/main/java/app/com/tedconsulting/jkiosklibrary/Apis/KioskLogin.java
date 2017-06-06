package app.com.tedconsulting.jkiosklibrary.Apis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

import app.com.tedconsulting.jkiosklibrary.ResultCallbackContract;
import app.com.tedconsulting.jkiosklibrary.Utilities.CookieUtility;


/**
 * Created by gurleensethi on 06/06/17.
 */

public class KioskLogin {
    private ResultCallbackContract<LoginResult> mCallback;

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/StudentPage.jsp url
    * to check if the credentials provided are right or not
    * */
    public KioskLogin login(final String enrollmentNumber, final String dateOfBirth, final String password) {
        //Execute in different thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    //Get the cookies from Webkiosk's website
                    Map<String, String> cookies = CookieUtility.getCookiesFor(enrollmentNumber, dateOfBirth, password);

                    //Login into webkiosk using the cookies
                    Document document = Jsoup.connect("https://webkiosk.jiit.ac.in/CommonFiles/UserActionn.jsp")
                            .cookies(cookies)
                            .execute().parse();

                    //Create new login result
                    LoginResult loginResult = new LoginResult();

                    //Check if the returned web page contains the string "Signin Action"
                    //if yes then login was unsuccessful
                    if (document.body().toString().toLowerCase().contains("signin action")) {
                        //Check for callbacks
                        loginResult.setValidCredentials(false);
                    } else {
                        loginResult.setValidCredentials(true);
                    }

                    //Check if user has provided a callback
                    if (mCallback != null) {
                        mCallback.onResult(loginResult);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    //Check if callback is provided
                    if (mCallback != null) {
                        mCallback.onError(e);
                    }
                }
            }
        };
        thread.start();

        return this;
    }

    /*
    * Overloaded login method that takes WebkioskCredentials object
    * */
    public KioskLogin login(WebkioskCredentials credentials) {
        login(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword());
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    public void addResultCallback(ResultCallbackContract<LoginResult> callback) {
        this.mCallback = callback;
    }

    /*
    * Remove the provided callback by the user if result is no longer required
    * */
    public void removeCallback() {
        this.mCallback = null;
    }
}
