package com.wormwood.request;

import java.io.Serializable;

/**
 * Created by kasimodo on 2017-07-11.
 */
public class AttendanceRequest implements Serializable {

    private String userId;
    private String workDateFrom;
    private String workDateTo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkDateFrom() {
        return workDateFrom;
    }

    public void setWorkDateFrom(String workDateFrom) {
        this.workDateFrom = workDateFrom;
    }

    public String getWorkDateTo() {
        return workDateTo;
    }

    public void setWorkDateTo(String workDateTo) {
        this.workDateTo = workDateTo;
    }
}
