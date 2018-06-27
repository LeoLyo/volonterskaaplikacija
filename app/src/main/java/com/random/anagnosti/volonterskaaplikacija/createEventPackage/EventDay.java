package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

public class EventDay {

    private String timeStart;
    private String timeEnd;
    private String attachDayFile;

    public EventDay(){
        this.timeStart="00:00";
        this.timeEnd="00:00";
        this.attachDayFile="nothing_yet";
    }
    public EventDay(String timeStart, String timeEnd, String attachDayFile) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.attachDayFile = attachDayFile;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getAttachDayFile() {
        return attachDayFile;
    }

    public void setAttachDayFile(String attachDayFile) {
        this.attachDayFile = attachDayFile;
    }
}
