package app.com.thetechnocafe.jkiosklibrary.Apis.Attendance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleen on 9/6/17.
 */

public class AttendanceResult {
    private List<Attendance> attendances;

    public AttendanceResult() {
        attendances = new ArrayList<>();
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
