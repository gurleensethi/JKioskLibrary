package app.com.tedconsulting.jkiosklib;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.com.thetechnocafe.jkiosklibrary.Apis.Semesters.SemestersResult;
import app.com.thetechnocafe.jkiosklibrary.JKiosk;
import app.com.thetechnocafe.jkiosklibrary.Contracts.ResultCallbackContract;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        JKiosk.getSemestersApi().getSemesters("141029", "234", "234").addResultCallback(new ResultCallbackContract<SemestersResult>() {
            @Override
            public void onResult(SemestersResult result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        assertEquals("app.com.thetechnocafe.jkiosklib", appContext.getPackageName());
    }
}
