package app.com.thetechnocafe.jkiosklibrary.Apis.CgpaReport;

/**
 * Created by gurleen on 11/6/17.
 */

public class CgpaReport {
    private int semesterIndex;
    private double gradePoints;
    private double courseCredit;
    private double earnedCredit;
    private double pointsSecuredSgpa;
    private double pointsSecuredCgpa;
    private double sgpa;
    private double cgpa;

    public int getSemesterIndex() {
        return semesterIndex;
    }

    public void setSemesterIndex(int semesterIndex) {
        this.semesterIndex = semesterIndex;
    }

    public double getGradePoints() {
        return gradePoints;
    }

    public void setGradePoints(double gradePoints) {
        this.gradePoints = gradePoints;
    }

    public double getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(double courseCredit) {
        this.courseCredit = courseCredit;
    }

    public double getEarnedCredit() {
        return earnedCredit;
    }

    public void setEarnedCredit(double earnedCredit) {
        this.earnedCredit = earnedCredit;
    }

    public double getPointsSecuredSgpa() {
        return pointsSecuredSgpa;
    }

    public void setPointsSecuredSgpa(double pointsSecuredSgpa) {
        this.pointsSecuredSgpa = pointsSecuredSgpa;
    }

    public double getPointsSecuredCgpa() {
        return pointsSecuredCgpa;
    }

    public void setPointsSecuredCgpa(double pointsSecuredCgpa) {
        this.pointsSecuredCgpa = pointsSecuredCgpa;
    }

    public double getSgpa() {
        return sgpa;
    }

    public void setSgpa(double sgpa) {
        this.sgpa = sgpa;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "CgpaReport{" +
                "semesterIndex=" + semesterIndex +
                ", gradePoints=" + gradePoints +
                ", courseCredit=" + courseCredit +
                ", earnedCredit=" + earnedCredit +
                ", pointsSecuredSgpa=" + pointsSecuredSgpa +
                ", pointsSecuredCgpa=" + pointsSecuredCgpa +
                ", sgpa=" + sgpa +
                ", cgpa=" + cgpa +
                '}';
    }
}
