package app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades;

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
 * Created by gurleensethi on 12/06/17.
 */

public class KioskExamGrades implements KioskContract<ExamGradesResult> {
    private ResultCallbackContract<ExamGradesResult> mCallback;
    private Handler mResultHandler;
    private static String URL = "https://webkiosk.jiit.ac.in/StudentFiles/Exam/StudentEventGradesView.jsp";

    public KioskExamGrades() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/Exam/StudentEventGradesView.jsp url
    * to fetch the list of grades for a given semester
    * */
    private KioskExamGrades getExamGrades(final String enrollmentNumber, final String dateOfBirth, final String password, final String url) {
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
                    final ExamGradesResult examGradesResult = new ExamGradesResult();

                    //Check if the returned web page contains the string "Signin Action"
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

                        //Iterate and fetch the values
                        for (Element element : elements) {
                            Elements subElements = element.children();

                            ExamGrade examGrade = new ExamGrade();
                            examGrade.setSerialNumber(StringUtility.convertStringToIntegerForDetailAttendance(subElements.get(0).text()));
                            examGrade.setSubjectName(StringUtility.getSubjectName(subElements.get(1).text()));
                            examGrade.setSubjectCode(StringUtility.getSubjectCode(subElements.get(1).text()));
                            examGrade.setExamCode(StringUtility.cleanString(subElements.get(2).text()));
                            examGrade.setGrade(StringUtility.cleanString(subElements.get(3).text()));

                            examGradesResult.getExamGrades().add(examGrade);
                        }
                    }

                    //Check if user has provided a callback
                    mResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onResult(examGradesResult);
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
    public KioskExamGrades getExamGrades(WebkioskCredentials credentials, String semester) {
        getExamGrades(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(), URL + Constants.URL_QUERY_PARAM + semester);
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    @Override
    public void addResultCallback(ResultCallbackContract<ExamGradesResult> callback) {
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
