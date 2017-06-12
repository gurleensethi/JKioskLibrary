package app.com.thetechnocafe.jkiosklib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.com.tedconsulting.jkiosklib.R;
import app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades.ExamGrade;
import app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades.ExamGradesResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades.KioskExamGrades;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.Contracts.KioskContract;
import app.com.thetechnocafe.jkiosklibrary.Contracts.ResultCallbackContract;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.KioskArray;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mReloadButton;

    private KioskContract kioskApi;
    private KioskArray mKioskArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mKioskArray = new KioskArray();

        mTextView = (TextView) findViewById(R.id.text_view);
        mReloadButton = (Button) findViewById(R.id.realod_button);
        final WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        kioskApi = JKiosk.getExamGradesApi();

        mReloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((KioskExamGrades) kioskApi).getExamGrades(credentials, "2015EVESEM")
                        .addResultCallback(new ResultCallbackContract<ExamGradesResult>() {
                            @Override
                            public void onResult(ExamGradesResult result) {
                                for (ExamGrade examGrade : result.getExamGrades()) {
                                    mTextView.append(examGrade.getGrade() + "\n");
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
            }
        });

        mKioskArray.add(kioskApi);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mKioskArray != null) {
            mKioskArray.removeAllCallbacks();
        }
    }
}
