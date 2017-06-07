package app.com.thetechnocafe.jkiosklibrary.Apis.Subjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleen on 7/6/17.
 */

public class SubjectResult {
    private List<Subject> subjects;

    public SubjectResult() {
        subjects = new ArrayList<>();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
