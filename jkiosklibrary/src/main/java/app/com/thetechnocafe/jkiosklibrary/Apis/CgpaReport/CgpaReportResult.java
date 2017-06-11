package app.com.thetechnocafe.jkiosklibrary.Apis.CgpaReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleen on 11/6/17.
 */

public class CgpaReportResult {
    private List<CgpaReport> cgpaReports;

    public CgpaReportResult() {
        cgpaReports = new ArrayList<>();
    }

    public List<CgpaReport> getCgpaReports() {
        return cgpaReports;
    }

    public void setCgpaReports(List<CgpaReport> cgpaReports) {
        this.cgpaReports = cgpaReports;
    }
}
