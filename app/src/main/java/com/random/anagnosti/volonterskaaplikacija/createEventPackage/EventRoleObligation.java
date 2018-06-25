package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import java.util.ArrayList;

public class EventRoleObligation {
    private String title;
    private String description;
    private ArrayList<EventObligationStatus> listOfStatuses;
    private String timeOfStart;
    private String timeOfEnd;
    private String remark;

    public EventRoleObligation(String title, String description, ArrayList<EventObligationStatus> listOfStatuses, String timeOfStart, String timeOfEnd, String remark) {
        this.title = title;
        this.description = description;
        this.listOfStatuses = listOfStatuses;
        this.timeOfStart = timeOfStart;
        this.timeOfEnd = timeOfEnd;
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<EventObligationStatus> getListOfStatuses() {
        return listOfStatuses;
    }

    public void setListOfStatuses(ArrayList<EventObligationStatus> listOfStatuses) {
        this.listOfStatuses = listOfStatuses;
    }

    public String getTimeOfStart() {
        return timeOfStart;
    }

    public void setTimeOfStart(String timeOfStart) {
        this.timeOfStart = timeOfStart;
    }

    public String getTimeOfEnd() {
        return timeOfEnd;
    }

    public void setTimeOfEnd(String timeOfEnd) {
        this.timeOfEnd = timeOfEnd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
