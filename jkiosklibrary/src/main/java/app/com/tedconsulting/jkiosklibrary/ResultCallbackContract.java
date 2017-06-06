package app.com.tedconsulting.jkiosklibrary;

/**
 * Created by gurleensethi on 06/06/17.
 */

public interface ResultCallbackContract<T> {
    void onResult(T result);

    void onError(Exception e);
}
