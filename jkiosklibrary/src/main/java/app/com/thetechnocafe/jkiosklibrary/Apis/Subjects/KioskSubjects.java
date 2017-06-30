package app.com.thetechnocafe.jkiosklibrary.Apis.Subjects;

import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Map;

import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.Consts.Constants;
import app.com.thetechnocafe.jkiosklibrary.Exceptions.InvalidCredentialsException;
import app.com.thetechnocafe.jkiosklibrary.Contracts.ResultCallbackContract;
import app.com.thetechnocafe.jkiosklibrary.Utilities.CookieUtility;
import app.com.thetechnocafe.jkiosklibrary.Contracts.KioskContract;
import app.com.thetechnocafe.jkiosklibrary.Utilities.StringUtility;

/**
 * Created by gurleen on 7/6/17.
 */

public class KioskSubjects implements KioskContract<SubjectResult> {
    private ResultCallbackContract<SubjectResult> mCallback;
    private Handler mResultHandler;
    private static String URL = "https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudSubjectTaken.jsp";

    public KioskSubjects() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudSubjectTaken.jsp url
    * to fetch the list of subjects for a given semester
    * */
    private KioskSubjects getSubjects(final String enrollmentNumber, final String dateOfBirth, final String password, final String college, final String url) {
        //Execute in different thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    //Get the cookies from Webkiosk's website
                    Map<String, String> cookies = CookieUtility.getCookiesFor(enrollmentNumber, dateOfBirth, password,college);

                    //Login into webkiosk using the cookies
                    Document document = Jsoup.connect(url)
                            .cookies(cookies)
                            .userAgent(Constants.AGENT_MOZILLA)
                            .execute().parse();

                    //Create new subject result
                    final SubjectResult subjectResult = new SubjectResult();

                    //Check if the returned web page contains the string "session timeout"
                    //if yes then login was unsuccessful
                    if (document.body().toString().toLowerCase().contains("session timeout")) {
                        //Throw invalid credentials exception
                        throw new InvalidCredentialsException();
                    } else {
                        //Traverse the html data to get the list of semesters
                        Elements elements = document.body()
                                .getElementsByTag("table")
                                .get(2).getElementsByTag("tbody")
                                .get(0).children();

                        //Iterate over the list of table rows and fetch the subjects and their corresponding info
                        //Neglect first and last row as they are garbage
                        for (int x = 1; x < elements.size() - 1; x++) {
                            Elements subElements = elements.get(x).children();

                            //Create subject and add to subject result list
                            Subject subject = new Subject();
                            subject.setSubjectName(StringUtility.getSubjectName(subElements.get(1).text()));
                            subject.setSubjectCode(StringUtility.getSubjectCode(subElements.get(1).text()));
                            subject.setSubjectCredits(Integer.parseInt(StringUtility.cleanString(subElements.get(2).text())));
                            subject.setSubjectType(StringUtility.cleanString(subElements.get(3).text()));

                            subjectResult.getSubjects().add(subject);
                        }

                    }

                    //Check if user has provided a callback
                    mResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onResult(subjectResult);
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
    public KioskSubjects getSubjects(WebkioskCredentials credentials, String semester) {
        getSubjects(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(),credentials.getCollege(), URL + Constants.URL_QUERY_PARAM + semester);
        return this;
    }

    /*
    * Overloaded method that loads gets the data for default semester
    * */
    public KioskSubjects getSubjects(WebkioskCredentials credentials) {
        getSubjects(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(),credentials.getCollege(), URL);
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    @Override
    public void addResultCallback(ResultCallbackContract<SubjectResult> callback) {
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
