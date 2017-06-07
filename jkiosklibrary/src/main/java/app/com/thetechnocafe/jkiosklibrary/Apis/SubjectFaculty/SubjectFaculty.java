package app.com.thetechnocafe.jkiosklibrary.Apis.SubjectFaculty;

/**
 * Created by gurleen on 6/6/17.
 */

public class SubjectFaculty {
    private String subjectName;
    private String lectureFaculty;
    private String tutorialFaculty;
    private String practicalFaculty;
    private String subjectCode;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLectureFaculty() {
        return lectureFaculty;
    }

    public void setLectureFaculty(String lectureFaculty) {
        this.lectureFaculty = lectureFaculty;
    }

    public String getTutorialFaculty() {
        return tutorialFaculty;
    }

    public void setTutorialFaculty(String tutorialFaculty) {
        this.tutorialFaculty = tutorialFaculty;
    }

    public String getPracticalFaculty() {
        return practicalFaculty;
    }

    public void setPracticalFaculty(String practicalFaculty) {
        this.practicalFaculty = practicalFaculty;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Override
    public String toString() {
        return "SubjectFaculty{" +
                "subjectName='" + subjectName + '\'' +
                ", lectureFaculty='" + lectureFaculty + '\'' +
                ", tutorialFaculty='" + tutorialFaculty + '\'' +
                ", practicalFaculty='" + practicalFaculty + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                '}';
    }
}
