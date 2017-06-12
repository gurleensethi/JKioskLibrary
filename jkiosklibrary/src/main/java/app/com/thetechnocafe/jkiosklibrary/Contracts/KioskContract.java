package app.com.thetechnocafe.jkiosklibrary.Contracts;

/**
 * Created by gurleensethi on 12/06/17.
 */

public interface KioskContract<T> {
    void addResultCallback(ResultCallbackContract<T> callback);

    void removeCallback();
}
