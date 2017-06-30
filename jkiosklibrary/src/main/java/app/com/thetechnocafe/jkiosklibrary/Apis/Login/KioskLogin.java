package app.com.thetechnocafe.jkiosklibrary.Apis.Login;

import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.Consts.Constants;
import app.com.thetechnocafe.jkiosklibrary.Contracts.ResultCallbackContract;
import app.com.thetechnocafe.jkiosklibrary.Utilities.CookieUtility;
import app.com.thetechnocafe.jkiosklibrary.Contracts.KioskContract;


/**
 * Created by gurleensethi on 06/06/17.
 */

public class KioskLogin implements KioskContract<LoginResult> {
    private ResultCallbackContract<LoginResult> mCallback;
    private Handler mResultHandler;

    public KioskLogin() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/StudentPage.jsp url
    * to check if the credentials provided are right or not
    * */
    public KioskLogin login(final String enrollmentNumber, final String dateOfBirth, final String password,final String college) {
        //Execute in different thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    //Get the cookies from Webkiosk's website
                    Map<String, String> cookies = CookieUtility.getCookiesFor(enrollmentNumber, dateOfBirth, password,college);

                    //Login into webkiosk using the cookies
                    Document document = Jsoup.connect("https://webkiosk.jiit.ac.in/StudentFiles/StudentPage.jsp")
                            .cookies(cookies)
                            .userAgent(Constants.AGENT_MOZILLA)
                            .execute().parse();

                    //Create new login result
                    final LoginResult loginResult = new LoginResult();

                    //Check if the returned web page contains the string
                    //if yes then login was unsuccessful
                    if (document.toString().contains("FrameLeftStudent.jsp")) {
                        //Check for callbacks
                        loginResult.setValidCredentials(true);
                    } else {
                        loginResult.setValidCredentials(false);
                    }

                    //Check if user has provided a callback
                    mResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onResult(loginResult);
                            }
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();

                    //Check if callback is provided
                    mResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onError(e);
                            }
                        }
                    });
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
        login(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(), credentials.getCollege());
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    @Override
    public void addResultCallback(ResultCallbackContract<LoginResult> callback) {
        this.mCallback = callback;
    }

    /*
    * Remove the provided callback by the user if result is no longer required
    * */
    @Override
    public void removeCallback() {
        this.mCallback = null;
    }
}
