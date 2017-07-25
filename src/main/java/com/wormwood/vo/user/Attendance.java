package com.wormwood.vo.user;

import java.io.Serializable;

/**
 * Created by kasimodo on 2017-07-11.
 */
public class Attendance implements Serializable {
    private static final long serialVersionUID = -1406025348656069193L;

    private Long recordId;
    private String sourceType;
    private Long workDate;
    private String locationResult;
    private Long planId;
    private Long baseCheckTime;
    private Long id;
    private Long groupId;
    private Long userCheckTime;
    private String userId;
    private String checkType;
    private String timeResult;
    private String corpId;

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setWorkDate(Long workDate) {
        this.workDate = workDate;
    }

    public Long getWorkDate() {
        return workDate;
    }

    public void setLocationResult(String locationResult) {
        this.locationResult = locationResult;
    }

    public String getLocationResult() {
        return locationResult;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setBaseCheckTime(Long baseCheckTime) {
        this.baseCheckTime = baseCheckTime;
    }

    public Long getBaseCheckTime() {
        return baseCheckTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setUserCheckTime(Long userCheckTime) {
        this.userCheckTime = userCheckTime;
    }

    public Long getUserCheckTime() {
        return userCheckTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setTimeResult(String timeResult) {
        this.timeResult = timeResult;
    }

    public String getTimeResult() {
        return timeResult;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpId() {
        return corpId;
    }


}
