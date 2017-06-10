package app.com.thetechnocafe.jkiosklibrary.Apis.DetailAttendance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleen on 10/6/17.
 */

public class DetailAttendanceResult {
    private List<DetailAttendance> detailAttendances;

    public DetailAttendanceResult() {
        detailAttendances = new ArrayList<>();
    }

    public List<DetailAttendance> getDetailAttendances() {
        return detailAttendances;
    }

    public void setDetailAttendances(List<DetailAttendance> detailAttendances) {
        this.detailAttendances = detailAttendances;
    }
}
