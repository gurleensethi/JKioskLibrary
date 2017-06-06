package app.com.thetechnocafe.jkiosklibrary.Apis.SubjectFaculty;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gurleen on 6/6/17.
 */

public class SubjectFacultyResult {
    private List<SubjectFaculty> subjectFaculties;

    public SubjectFacultyResult() {
        subjectFaculties = new ArrayList<>();
    }

    public List<SubjectFaculty> getSubjectFaculties() {
        return subjectFaculties;
    }

    public void setSubjectFaculties(List<SubjectFaculty> subjectFaculties) {
        this.subjectFaculties = subjectFaculties;
    }
}
