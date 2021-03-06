package app.com.thetechnocafe.jkiosklibrary.Apis.CgpaReport;

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
import app.com.thetechnocafe.jkiosklibrary.Utilities.StringUtility;

/**
 * Created by gurleen on 11/6/17.
 */

public class KioskCgpaReport implements KioskContract<CgpaReportResult> {
    private ResultCallbackContract<CgpaReportResult> mCallback;
    private Handler mResultHandler;
    private static String URL = "https://webkiosk.jiit.ac.in/StudentFiles/Exam/StudCGPAReport.jsp";

    public KioskCgpaReport() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/Exam/StudCGPAReport.jsp url
    * to fetch the list of cgpa and sgpa
    * */
    private KioskCgpaReport getCgpaReport(final String enrollmentNumber, final String dateOfBirth, final String password,final String college, final String url) {
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
                    final CgpaReportResult cgpaReportResult = new CgpaReportResult();

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

                        for (Element element : elements) {
                            Elements subElements = element.children();

                            CgpaReport cgpaReport = new CgpaReport();
                            cgpaReport.setSemesterIndex(Integer.parseInt(StringUtility.cleanString(subElements.get(0).text())));
                            cgpaReport.setGradePoints(Double.parseDouble(StringUtility.cleanString(subElements.get(1).text())));
                            cgpaReport.setCourseCredit(Double.parseDouble(StringUtility.cleanString(subElements.get(2).text())));
                            cgpaReport.setEarnedCredit(Double.parseDouble(StringUtility.cleanString(subElements.get(3).text())));
                            cgpaReport.setPointsSecuredSgpa(Double.parseDouble(StringUtility.cleanString(subElements.get(4).text())));
                            cgpaReport.setPointsSecuredCgpa(Double.parseDouble(StringUtility.cleanString(subElements.get(5).text())));
                            cgpaReport.setSgpa(Double.parseDouble(StringUtility.cleanString(subElements.get(6).text())));
                            cgpaReport.setCgpa(Double.parseDouble(StringUtility.cleanString(subElements.get(7).text())));

                            cgpaReportResult.getCgpaReports().add(cgpaReport);
                        }

                    }

                    //Check if user has provided a callback
                    mResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onResult(cgpaReportResult);
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
    * Overloaded method that takes Webkiosk Credentials
    * */
    public KioskCgpaReport getCgpaReport(WebkioskCredentials credentials) {
        getCgpaReport(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(),credentials.getCollege(), URL);
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    @Override
    public void addResultCallback(ResultCallbackContract<CgpaReportResult> callback) {
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
