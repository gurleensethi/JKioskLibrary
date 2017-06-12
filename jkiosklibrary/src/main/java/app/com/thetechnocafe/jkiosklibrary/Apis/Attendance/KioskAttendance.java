package app.com.thetechnocafe.jkiosklibrary.Apis.Attendance;

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
 * Created by gurleen on 9/6/17.
 */

public class KioskAttendance implements KioskContract<AttendanceResult> {
    private ResultCallbackContract<AttendanceResult> mCallback;
    private Handler mResultHandler;
    private static String URL = "https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudentAttendanceList.jsp";

    public KioskAttendance() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudentAttendanceList.jsp url
    * to fetch the list of attendance for a given semester
    * */
    private KioskAttendance getAttendance(final String enrollmentNumber, final String dateOfBirth, final String password, final String url) {
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
                    final AttendanceResult attendanceResult = new AttendanceResult();

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

                        //Iterate and fetch the attendance values
                        for (Element element : elements) {
                            Elements subElements = element.children();

                            Attendance attendance = new Attendance();
                            attendance.setSubjectName(StringUtility.getSubjectNameFromAttendance(subElements.get(1).text()));
                            attendance.setSubjectCode(StringUtility.getSubjectCodeFromAttendance(subElements.get(1).text()));

                            attendance.setOverallAttendance(StringUtility.convertStringAttendanceToInteger(subElements.get(2).text()));
                            attendance.setLectureAttendance(StringUtility.convertStringAttendanceToInteger(subElements.get(3).text()));
                            attendance.setTutorialAttendance(StringUtility.convertStringAttendanceToInteger(subElements.get(4).text()));
                            attendance.setPracticalAttendance(StringUtility.convertStringAttendanceToInteger(subElements.get(5).text()));

                            //Set the link for detailed attendance
                            attendance.setDetailAttendanceUrl(Constants.INITIAL_KIOSK_ACADEMIC_URL + subElements.get(2).getElementsByTag("a").attr("href"));

                            //Check if the lecture, tutorial and overall attendance is null i.e. it is a practical subject
                            if (attendance.getOverallAttendance() == null
                                    && attendance.getLectureAttendance() == null
                                    && attendance.getTutorialAttendance() == null) {
                                attendance.setOverallAttendance(attendance.getPracticalAttendance());

                                //Check if the url is not null
                                String detailAttendanceUrl = StringUtility.cleanString(subElements.get(5).getElementsByTag("a").attr("href"));

                                if (detailAttendanceUrl != null && detailAttendanceUrl.length() != 0) {
                                    attendance.setDetailAttendanceUrl(Constants.INITIAL_KIOSK_ACADEMIC_URL + detailAttendanceUrl);
                                } else {
                                    attendance.setDetailAttendanceUrl(null);
                                }
                            }

                            attendanceResult.getAttendances().add(attendance);
                        }
                    }

                    //Check if user has provided a callback
                    mResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onResult(attendanceResult);
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
    public KioskAttendance getAttendance(WebkioskCredentials credentials, String semester) {
        getAttendance(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(), URL + Constants.URL_QUERY_PARAM + semester);
        return this;
    }

    /*
    * Overloaded method that loads gets the data for default semester
    * */
    public KioskAttendance getAttendance(WebkioskCredentials credentials) {
        getAttendance(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(), URL);
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    @Override
    public void addResultCallback(ResultCallbackContract<AttendanceResult> callback) {
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
