package com.diss.cabadvertisementdriver.model;

public class WeeklyReportBean {
    String weeklyReportId,ReportDate,TotalKm,totalTime,locNm;

    public String getWeeklyReportId() {
        return weeklyReportId;
    }

    public void setWeeklyReportId(String weeklyReportId) {
        this.weeklyReportId = weeklyReportId;
    }

    public String getReportDate() {
        return ReportDate;
    }

    public void setReportDate(String reportDate) {
        ReportDate = reportDate;
    }

    public String getTotalKm() {
        return TotalKm;
    }

    public void setTotalKm(String totalKm) {
        TotalKm = totalKm;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getLocNm() {
        return locNm;
    }

    public void setLocNm(String locNm) {
        this.locNm = locNm;
    }
}
