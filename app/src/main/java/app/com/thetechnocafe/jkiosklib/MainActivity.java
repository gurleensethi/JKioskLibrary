package app.com.thetechnocafe.jkiosklib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.com.tedconsulting.jkiosklib.R;
import app.com.thetechnocafe.jkiosklibrary.Apis.SubjectFaculty.KioskSubjectFaculty;
import app.com.thetechnocafe.jkiosklibrary.Apis.SubjectFaculty.SubjectFacultyResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.ResultCallbackContract;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KioskSubjectFaculty kioskApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        kioskApi = JKiosk.getSubjectFacultyApi();

        kioskApi.getSubjectFaculty(credentials, "2014ODDSEM")
                .addResultCallback(new ResultCallbackContract<SubjectFacultyResult>() {
                    @Override
                    public void onResult(SubjectFacultyResult result) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (kioskApi != null) {
            kioskApi.removeCallback();
        }
    }
}
