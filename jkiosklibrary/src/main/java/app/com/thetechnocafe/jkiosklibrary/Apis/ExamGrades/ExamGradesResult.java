package app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleensethi on 12/06/17.
 */

public class ExamGradesResult {
    private List<ExamGrades> examGrades;

    public ExamGradesResult() {
        examGrades = new ArrayList<>();
    }

    public List<ExamGrades> getExamGrades() {
        return examGrades;
    }

    public void setExamGrades(List<ExamGrades> examGrades) {
        this.examGrades = examGrades;
    }
}
