package app.com.thetechnocafe.jkiosklibrary.Exceptions;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class InvalidCredentialsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid Credentials Provided. Please check your enrollment number, date of birth and password.";
    }

    @Override
    public String toString() {
        return "Invalid Credentials Provided";
    }
}
