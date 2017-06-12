package app.com.thetechnocafe.jkiosklib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.com.tedconsulting.jkiosklib.R;
import app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades.ExamGrades;
import app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades.ExamGradesResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades.KioskExamGrades;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.Contracts.ResultCallbackContract;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KioskExamGrades kioskApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        kioskApi = JKiosk.getExamGradesApi();

        kioskApi.getExamGrades(credentials, "2015EVESEM")
                .addResultCallback(new ResultCallbackContract<ExamGradesResult>() {
                    @Override
                    public void onResult(ExamGradesResult result) {
                        for (ExamGrades examGrades : result.getExamGrades()) {
                            System.out.println(examGrades.toString());
                        }
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
