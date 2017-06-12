package app.com.thetechnocafe.jkiosklibrary.Apis.DetailAttendance;

import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.Constants;
import app.com.thetechnocafe.jkiosklibrary.Exceptions.InvalidCredentialsException;
import app.com.thetechnocafe.jkiosklibrary.Contracts.ResultCallbackContract;
import app.com.thetechnocafe.jkiosklibrary.Utilities.CookieUtility;
import app.com.thetechnocafe.jkiosklibrary.Contracts.KioskContract;
import app.com.thetechnocafe.jkiosklibrary.Utilities.StringUtility;

/**
 * Created by gurleen on 10/6/17.
 */

public class KioskDetailAttendance implements KioskContract<DetailAttendanceResult> {
    private ResultCallbackContract<DetailAttendanceResult> mCallback;
    private Handler mResultHandler;

    public KioskDetailAttendance() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the url provided
    * to fetch the list of detailed attendance
    * */
    private KioskDetailAttendance getAttendance(final String enrollmentNumber, final String dateOfBirth, final String password, final String url) {
        //Execute in different thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    //Get the cookies from Webkiosk's website
                    Map<String, String> cookies = CookieUtility.getCookiesFor(enrollmentNumber, dateOfBirth, password);

                    //Login into webkiosk using the cookies
                    Document document = Jsoup.connect(url)
                            .cookies(cookies)
                            .userAgent(Constants.AGENT_MOZILLA)
                            .execute().parse();

                    //Create new subject result
                    final DetailAttendanceResult detailAttendanceResult = new DetailAttendanceResult();

                    //Check if the returned web page contains the string "session timeout"
                    //if yes then login was unsuccessful
                    if (document.body().toString().toLowerCase().contains("session timeout")) {
                        //Throw invalid credentials exception
                        throw new InvalidCredentialsException();
                    } else {
                        //Traverse the html tree to fetch the content
                        Elements elements = document.body()
                                .getElementsByTag("table")
                                .get(2)
                                .getElementsByTag("tbody")
                                .get(0)
                                .children();

                        //Iterate and fetch values for detailed attendance
                        for (Element element : elements) {
                            Elements subElements = element.children();

                            DetailAttendance detailAttendance = new DetailAttendance();
                            detailAttendance.setSerialNumber(StringUtility.convertStringToIntegerForDetailAttendance(subElements.get(0).text()));
                            detailAttendance.setDate(StringUtility.cleanString(subElements.get(1).text()));
                            detailAttendance.setFacultyName(StringUtility.cleanString(subElements.get(2).text()));
                            detailAttendance.setStatus(StringUtility.cleanString(subElements.get(3).text()));
                            detailAttendance.setClassType(StringUtility.cleanString(subElements.get(4).text()));

                            //Try to add the ltp(lecture type), which is sometimes not available when fetching practical attendance
                            if (subElements.size() >= 6) {
                                detailAttendance.setLtp(StringUtility.cleanString(subElements.get(5).text()));
                            }

                            detailAttendanceResult.getDetailAttendances().add(detailAttendance);
                        }
                    }

                    //Check if user has provided a callback
                    mResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onResult(detailAttendanceResult);
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
    * Overloaded method that takes WebkioskCredentials object
    * */
    public KioskDetailAttendance getDetailAttendance(WebkioskCredentials credentials, String attendanceUrl) {
        getAttendance(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(), attendanceUrl);
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    @Override
    public void addResultCallback(ResultCallbackContract<DetailAttendanceResult> callback) {
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
