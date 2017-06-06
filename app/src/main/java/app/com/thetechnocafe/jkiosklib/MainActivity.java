package app.com.thetechnocafe.jkiosklib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import app.com.tedconsulting.jkiosklib.R;
import app.com.thetechnocafe.jkiosklibrary.Apis.Semesters.KioskSemesters;
import app.com.thetechnocafe.jkiosklibrary.Apis.Semesters.SemestersResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.ResultCallbackContract;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KioskSemesters kioskSemesters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        WebkioskCredentials credentials = new WebkioskCredentials("1410309", "18-08-1996", "Sarusethi@1234");

        kioskSemesters = JKiosk.getSemestersApi();

        kioskSemesters.getSemesters(credentials)
                .addResultCallback(new ResultCallbackContract<SemestersResult>() {
                    @Override
                    public void onResult(SemestersResult result) {
                        for (String string : result.getSemesters()) {
                            mTextView.append(string + "\n");
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (kioskSemesters != null) {
            kioskSemesters.removeCallback();
        }
    }
}
