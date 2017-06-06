package app.com.tedconsulting.jkiosklibrary;

import org.junit.Test;

import app.com.tedconsulting.jkiosklibrary.Apis.KioskLogin;
import app.com.tedconsulting.jkiosklibrary.Apis.LoginResult;
import app.com.tedconsulting.jkiosklibrary.Apis.WebkioskCredentials;

import static org.junit.Assert.assertEquals;


/**
 * Created by gurleensethi on 06/06/17.
 */

public class KioskApiUnitTest {
    @Test
    public void TestLogin() throws InterruptedException {
        WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        KioskLogin loginApi = JKiosk.getLoginApi();

        loginApi.addResultCallback(new ResultCallbackContract<LoginResult>() {
            @Override
            public void onResult(LoginResult result) {
                assertEquals(true, result.isValidCredentials());
                System.out.println("Login Result : " + result.isValidCredentials());
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        loginApi.login(credentials);

        Thread.sleep(10000);
    }
}
