package app.com.thetechnocafe.jkiosklibrary.Apis.SubjectFaculty;

import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Map;

import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.Exceptions.InvalidCredentialsException;
import app.com.thetechnocafe.jkiosklibrary.ResultCallbackContract;
import app.com.thetechnocafe.jkiosklibrary.Utilities.CookieUtility;

/**
 * Created by gurleen on 6/6/17.
 */

public class KioskSubjectFaculty {
    private ResultCallbackContract<SubjectFacultyResult> mCallback;
    private Handler mResultHandler;

    public KioskSubjectFaculty() {
        mResultHandler = new Handler(Looper.getMainLooper());
    }

    /*
    * Login in into www.webkiosk.jiit.ac.in
    * Get the cookies and hit the https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudSubjectFaculty.jsp?x=&exam={semesterCode} url
    * to fetch the list of all subject faculty for the semester provided
    * */
    public KioskSubjectFaculty getSubjectFaculty(final String enrollmentNumber, final String dateOfBirth, final String password, final String semesterCode) {
        //Execute in different thread
        final Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    //Get the cookies from Webkiosk's website
                    Map<String, String> cookies = CookieUtility.getCookiesFor(enrollmentNumber, dateOfBirth, password);

                    //Login into webkiosk using the cookies
                    Document document = Jsoup.connect("https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudSubjectFaculty.jsp?x=&exam=" + semesterCode)
                            .cookies(cookies)
                            .execute().parse();

                    //Create new subject faculty result
                    final SubjectFacultyResult subjectFacultyResult = new SubjectFacultyResult();

                    //Check if the returned web page contains the string "Session Timeout"
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

                        //Traverse the list of subjects and parse them, while also
                        //cleaning up the text and removing occurrences of '&nbsp;'
                        //and remove ant occurrences of '\u00A0'
                        for (int x = 1; x < elements.size(); x++) {
                            //Create new SubjectFaculty object
                            SubjectFaculty subjectFaculty = new SubjectFaculty();

                            //Get the sub elements of each row
                            Elements subElements = elements.get(x).getElementsByTag("td");
                            subjectFaculty.setSubjectName(subElements.get(1).text().replace("&nbsp;","").replace("\u00A0", "").trim());
                            subjectFaculty.setLectureFaculty(subElements.get(2).text().replace("&nbsp;","").replace("\u00A0", "").trim());
                            subjectFaculty.setTutorialFaculty(subElements.get(3).text().replace("&nbsp;","").replace("\u00A0", "").trim());
                            subjectFaculty.setPracticalFaculty(subElements.get(4).text().replace("&nbsp;","").replace("\u00A0", "").trim());

                            //Add to subject faculty result
                            subjectFacultyResult.getSubjectFaculties().add(subjectFaculty);

                            System.out.println(subjectFaculty.toString());
                        }
                    }

                    //Check if user has provided a callback
                    if (mCallback != null) {
                        mResultHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onResult(subjectFacultyResult);
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
    public KioskSubjectFaculty getSubjectFaculty(WebkioskCredentials credentials, String semesterCode) {
        getSubjectFaculty(credentials.getEnrollmentNumber(), credentials.getDateOfBirth(), credentials.getPassword(), semesterCode);
        return this;
    }

    /*
     * Set a callback to get result from the login API
     */
    public void addResultCallback(ResultCallbackContract<SubjectFacultyResult> callback) {
        this.mCallback = callback;
    }

    /*
    * Remove the provided callback by the user if result is no longer required
    * */
    public void removeCallback() {
        this.mCallback = null;
    }
}
