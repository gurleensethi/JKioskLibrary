package app.com.thetechnocafe.jkiosklibrary;

import org.junit.Test;

import app.com.thetechnocafe.jkiosklibrary.Apis.Login.KioskLogin;
import app.com.thetechnocafe.jkiosklibrary.Apis.Login.LoginResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.Semesters.KioskSemesters;
import app.com.thetechnocafe.jkiosklibrary.Apis.Semesters.SemestersResult;
import app.com.thetechnocafe.jkiosklibrary.Apis.WebkioskCredentials;

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

    @Test
    public void TestSemesters() throws Exception {
        WebkioskCredentials credentials = new WebkioskCredentials("14103093", "18-08-1996", "Sarusethi@1234");

        KioskSemesters kioskSemesters = JKiosk.getSemestersApi();

        kioskSemesters.addResultCallback(new ResultCallbackContract<SemestersResult>() {
            @Override
            public void onResult(SemestersResult result) {
                assertEquals(true, result.getSemesters().size() > 2);
                for (String str : result.getSemesters()) {
                    System.out.println(str);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });

        kioskSemesters.getSemesters(credentials);

        Thread.sleep(15000);
    }
}
