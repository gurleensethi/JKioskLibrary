package app.com.thetechnocafe.jkiosklib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.com.tedconsulting.jkiosklib.R;
import app.com.thetechnocafe.jkiosklibrary.Apis.CgpaReport.CgpaReport;
import app.com.thetechnocafe.jkiosklibrary.Apis.CgpaReport.CgpaReportResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.CgpaReport.KioskCgpaReport;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.ResultCallbackContract;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KioskCgpaReport kioskApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        kioskApi = JKiosk.getCgpaReportApi();

        String url = "https://webkiosk.jiit.ac.in/StudentFiles/Academic/ViewDatewiseLecAttendance.jsp?EXAM=2014ODDSEM&CTYPE=R&SC=090076&LTP=P&&mRegConfirmDate=08-08-2014&mRegConfirmDateOrg=08-08-2014&prevPFSTID=&mPFSTID=JIIT1403656";

        kioskApi.getCgpaReport(credentials)
                .addResultCallback(new ResultCallbackContract<CgpaReportResult>() {
                    @Override
                    public void onResult(CgpaReportResult result) {
                        for (CgpaReport cgpaReport : result.getCgpaReports()) {
                            System.out.println(cgpaReport.toString());
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
