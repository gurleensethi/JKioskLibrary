package app.com.thetechnocafe.jkiosklibrary.Apis.DetailAttendance;

/**
 * Created by gurleen on 10/6/17.
 */

public class DetailAttendance {
    private int serialNumber;
    private String date;
    private String facultyName;
    private String status;
    private String classType;
    private String ltp;

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getLtp() {
        return ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    @Override
    public String toString() {
        return "DetailAttendance{" +
                "serialNumber=" + serialNumber +
                ", date='" + date + '\'' +
                ", facultyName='" + facultyName + '\'' +
                ", status='" + status + '\'' +
                ", classType='" + classType + '\'' +
                ", ltp='" + ltp + '\'' +
                '}';
    }
}
