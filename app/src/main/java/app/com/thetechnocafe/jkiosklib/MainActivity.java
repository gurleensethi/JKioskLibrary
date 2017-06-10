package app.com.thetechnocafe.jkiosklib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.com.tedconsulting.jkiosklib.R;
import app.com.thetechnocafe.jkiosklibrary.Apis.DetailAttendance.DetailAttendance;
import app.com.thetechnocafe.jkiosklibrary.Apis.DetailAttendance.DetailAttendanceResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.DetailAttendance.KioskDetailAttendance;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.ResultCallbackContract;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KioskDetailAttendance kioskApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        kioskApi = JKiosk.getDetailAttendanceApi();

        String url = "https://webkiosk.jiit.ac.in/StudentFiles/Academic/ViewDatewiseLecAttendance.jsp?EXAM=2014ODDSEM&CTYPE=R&SC=090076&LTP=P&&mRegConfirmDate=08-08-2014&mRegConfirmDateOrg=08-08-2014&prevPFSTID=&mPFSTID=JIIT1403656";

        kioskApi.getDetailAttendance(credentials, url)
                .addResultCallback(new ResultCallbackContract<DetailAttendanceResult>() {
                    @Override
                    public void onResult(DetailAttendanceResult result) {
                        for (DetailAttendance detailAttendance : result.getDetailAttendances()) {
                            System.out.println(detailAttendance.toString());
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
