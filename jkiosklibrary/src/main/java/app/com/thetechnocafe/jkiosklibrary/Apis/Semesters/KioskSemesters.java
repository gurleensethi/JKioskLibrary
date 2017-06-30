package app.com.thetechnocafe.jkiosklibrary.Apis.Semesters;

import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.Consts.Constants;
import app.com.thetechnocafe.jkiosklibrary.Exceptions.InvalidCredentialsException;
import app.com.thetechnocafe.jkiosklibrary.Contracts.ResultCallbackContract;
import app.com.thetechnocafe.jkiosklibrary.Utilities.CookieUtility;
import app.com.thetechnocafe.jkiosklibrary.Contracts.KioskContract;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class KioskSemesters implements KioskContract<SemestersResult> {
    private ResultCallbackContract<SemestersResult> mCallback;
    private Handler mResultHandler;

    public KioskSemesters() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudSubjectFaculty.jsp url
    * to fetch the list of all semesters
    * */
    private KioskSemesters getSemesters(final String enrollmentNumber, final String dateOfBirth, final String password,final String college) {
        //Execute in different thread
        final Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    //Get the cookies from Webkiosk's website
                    Map<String, String> cookies = CookieUtility.getCookiesFor(enrollmentNumber, dateOfBirth, password,college);

                    //Login into webkiosk using the cookies
                    Document document = Jsoup.connect("https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudSubjectFaculty.jsp")
                            .cookies(cookies)
                            .userAgent(Constants.AGENT_MOZILLA)
                            .execute().parse();

                    //Create new login result
                    final SemestersResult semestersResult = new SemestersResult();

                    //Check if the returned web page contains the string "Session Timeout"
                    //if yes then login was unsuccessful
                    if (document.body().toString().toLowerCase().contains("session timeout")) {
                        //Throw invalid credentials exception
                        throw new InvalidCredentialsException();
                    } else {
                        //Traverse the html data to get the list of semesters
                        Elements elements = document.body().getElementsByAttributeValue("name", "frm")
                                .get(0).getElementsByTag("table")
                                .get(0).getElementsByTag("tbody")
                                .get(0).getElementsByTag("tr")
                                .get(1).getElementsByTag("td")
                                .get(0).getElementsByTag("select")
                                .get(0).children();

                        for (Element element : elements) {
                            semestersResult.getSemesters().add(element.attr("value"));
                        }
                    }

                    //Check if user has provided a callback
                    if (mCallback != null) {
                        mResultHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onResult(semestersResult);
                            }
                        });
                    }

                } catch (final Exception e) {
                    e.printStackTrace();

                    //Check if callback is provided
                    if (mCallback != null) {
                        mResultHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onError(e);
                            }
                        });
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
    public KioskSemesters getSemesters(WebkioskCredentials credentials) {
        getSemesters(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(), credentials.getCollege());
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    @Override
    public void addResultCallback(ResultCallbackContract<SemestersResult> callback) {
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
