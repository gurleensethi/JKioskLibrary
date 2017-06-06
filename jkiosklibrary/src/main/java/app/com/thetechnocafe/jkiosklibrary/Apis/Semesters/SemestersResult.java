package app.com.thetechnocafe.jkiosklibrary.Apis.Semesters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleensethi on 06/06/17.
 */

public class SemestersResult {
    private List<String> semesters;

    public SemestersResult() {
        semesters = new ArrayList<>();
    }

    public List<String> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<String> semesters) {
        this.semesters = semesters;
    }
}
