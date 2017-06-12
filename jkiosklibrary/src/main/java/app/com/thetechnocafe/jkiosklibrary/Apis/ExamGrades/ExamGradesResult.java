package app.com.thetechnocafe.jkiosklibrary.Apis.ExamGrades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleensethi on 12/06/17.
 */

public class ExamGradesResult {
    private List<ExamGrade> examGrades;

    public ExamGradesResult() {
        examGrades = new ArrayList<>();
    }

    public List<ExamGrade> getExamGrades() {
        return examGrades;
    }

    public void setExamGrades(List<ExamGrade> examGrades) {
        this.examGrades = examGrades;
    }
}
