package com.random.anagnosti.volonterskaaplikacija;

public class EventObligationStatus {
    private String statusName;
    private boolean statusState;

    public EventObligationStatus(String statusName, boolean statusState) {
        this.statusName = statusName;
        this.statusState = statusState;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public boolean isStatusState() {
        return statusState;
    }

    public void setStatusState(boolean statusState) {
        this.statusState = statusState;
    }
}
