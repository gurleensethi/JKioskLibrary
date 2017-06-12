package app.com.thetechnocafe.jkiosklibrary;


import app.com.thetechnocafe.jkiosklibrary.Apis.Attendance.KioskAttendance;
import app.com.thetechnocafe.jkiosklibrary.Apis.CgpaReport.KioskCgpaReport;
import app.com.thetechnocafe.jkiosklibrary.Apis.DetailAttendance.KioskDetailAttendance;
import app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades.KioskExamGrades;
import app.com.thetechnocafe.jkiosklibrary.Apis.Login.KioskLogin;
import app.com.thetechnocafe.jkiosklibrary.Apis.Semesters.KioskSemesters;
import app.com.thetechnocafe.jkiosklibrary.Apis.SubjectFaculty.KioskSubjectFaculty;
import app.com.thetechnocafe.jkiosklibrary.Apis.Subjects.KioskSubjects;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class JKiosk {

    //Returns an object of kiosk login
    public static KioskLogin getLoginApi() {
        return new KioskLogin();
    }

    //Returns an object of Kiosk Semester
    public static KioskSemesters getSemestersApi() {
        return new KioskSemesters();
    }

    //Returns an object of Kiosk Subject Faculty
    public static KioskSubjectFaculty getSubjectFacultyApi() {
        return new KioskSubjectFaculty();
    }

    //Returns an object of Kiosk Subjects
    public static KioskSubjects getSubjectsApi() {
        return new KioskSubjects();
    }

    //Returns an object of Kiosk Attendance
    public static KioskAttendance getAttendanceApi() {
        return new KioskAttendance();
    }

    //Returns an object of Kiosk Detail Attendance
    public static KioskDetailAttendance getDetailAttendanceApi() {
        return new KioskDetailAttendance();
    }

    //Returns an object of Kiosk Cgpa Report
    public static KioskCgpaReport getCgpaReportApi() {
        return new KioskCgpaReport();
    }

    //Returns an object of Kiosk Exam Grades
    public static KioskExamGrades getExamGradesApi() {
        return new KioskExamGrades();
    }
}
