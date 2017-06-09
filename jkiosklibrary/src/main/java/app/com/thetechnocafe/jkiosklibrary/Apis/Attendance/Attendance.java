package app.com.thetechnocafe.jkiosklibrary.Apis.Attendance;

/**
 * Created by gurleen on 9/6/17.
 */

public class Attendance {
    private String subjectName;
    private String subjectCode;
    private Integer overallAttendance;
    private Integer lectureAttendance;
    private Integer tutorialAttendance;
    private Integer practicalAttendance;
    private String detailAttendanceUrl;

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

    public Integer getOverallAttendance() {
        return overallAttendance;
    }

    public void setOverallAttendance(Integer overallAttendance) {
        this.overallAttendance = overallAttendance;
    }

    public Integer getLectureAttendance() {
        return lectureAttendance;
    }

    public void setLectureAttendance(Integer lectureAttendance) {
        this.lectureAttendance = lectureAttendance;
    }

    public Integer getTutorialAttendance() {
        return tutorialAttendance;
    }

    public void setTutorialAttendance(Integer tutorialAttendance) {
        this.tutorialAttendance = tutorialAttendance;
    }

    public String getDetailAttendanceUrl() {
        return detailAttendanceUrl;
    }

    public void setDetailAttendanceUrl(String detailAttendanceUrl) {
        this.detailAttendanceUrl = detailAttendanceUrl;
    }

    public Integer getPracticalAttendance() {
        return practicalAttendance;
    }

    public void setPracticalAttendance(Integer practicalAttendance) {
        this.practicalAttendance = practicalAttendance;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", overallAttendance=" + overallAttendance +
                ", lectureAttendance=" + lectureAttendance +
                ", tutorialAttendance=" + tutorialAttendance +
                ", practicalAttendance=" + practicalAttendance +
                ", detailAttendanceUrl='" + detailAttendanceUrl + '\'' +
                '}';
    }
}
