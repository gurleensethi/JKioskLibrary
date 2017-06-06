package app.com.thetechnocafe.jkiosklib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import app.com.tedconsulting.jkiosklib.R;
import app.com.thetechnocafe.jkiosklibrary.Apis.Login.KioskLogin;
import app.com.thetechnocafe.jkiosklibrary.Apis.Login.LoginResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.ResultCallbackContract;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KioskLogin kioskApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        kioskApi = JKiosk.getLoginApi();

        kioskApi.login(credentials)
                .addResultCallback(new ResultCallbackContract<LoginResult>() {
                    @Override
                    public void onResult(LoginResult result) {
                        Toast.makeText(getApplicationContext(), String.valueOf(result.isValidCredentials()), Toast.LENGTH_SHORT).show();
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
