package app.com.thetechnocafe.jkiosklibrary.Apis.Subjects;

/**
 * Created by gurleen on 7/6/17.
 */

public class Subject {
    private String subjectName;
    private String subjectCode;
    private int subjectCredits;
    private String subjectType;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectCredits() {
        return subjectCredits;
    }

    public void setSubjectCredits(int subjectCredits) {
        this.subjectCredits = subjectCredits;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", subjectCredits=" + subjectCredits +
                ", subjectType='" + subjectType + '\'' +
                '}';
    }
}
