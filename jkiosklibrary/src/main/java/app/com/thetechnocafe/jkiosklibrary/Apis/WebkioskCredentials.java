package app.com.thetechnocafe.jkiosklibrary.Apis;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class WebkioskCredentials {
    private String enrollmentNumber;
    private String dateOfBirth;
    private String password;

    public WebkioskCredentials(String enrollmentNumber, String dateOfBirth, String password) {
        this.enrollmentNumber = enrollmentNumber;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
