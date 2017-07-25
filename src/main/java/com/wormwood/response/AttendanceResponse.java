package com.wormwood.response;

import java.io.Serializable;

/**
 * Created by kasimodo on 2017-07-11.
 */
public class AttendanceResponse implements Serializable {
    private String userId;
    private String staffName;
    private String startTime;
    private String endTime;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
