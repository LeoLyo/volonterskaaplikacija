package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.Date;

public class EventDay {

    private Date date;
    private String timeStart;
    private String timeEnd;
    private String attachDayFile;
    private boolean isFilled;

    public EventDay(){
        this.date=new Date();
        this.timeStart="00:00";
        this.timeEnd="00:00";
        this.attachDayFile="nothing_yet";
        this.isFilled=false;
    }
    public EventDay(Date date, String timeStart, String timeEnd, String attachDayFile) {
        this.date=date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.attachDayFile = attachDayFile;
    }


    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public boolean isFilled() {
        return isFilled;
    }
    public void setFilled(boolean filled) {
        isFilled = filled;
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
