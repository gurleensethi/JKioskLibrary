package app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades;

/**
 * Created by gurleensethi on 12/06/17.
 */

public class ExamGrade {
    private int serialNumber;
    private String subjectName;
    private String subjectCode;
    private String examCode;
    private String grade;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "ExamGrades{" +
                "serialNumber=" + serialNumber +
                ", subjectName='" + subjectName + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", examCode='" + examCode + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
