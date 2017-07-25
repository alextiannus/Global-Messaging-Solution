package com.wormwood.vo.user;

import com.wormwood.vo.BaseVO;

import java.util.List;

/**
 * Created by kasimodo on 2017-07-11.
 */
public class UserDetail extends BaseVO {

    private String orderInDepts;
    private String position;
    private List<Long> department;
    private String tel;
    private String unionid;
    private String userid;
    private boolean isSenior;
    private String dingId;
    private boolean isBoss;
    private String name;
    private String stateCode;
    private String avatar;
    private String jobnumber;
    private String isLeaderInDepts;
    private List<UserRole> roles;
    private boolean active;
    private boolean isAdmin;
    private boolean isHide;
    private String mobile;
    private String openId;

    public void setOrderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
    }

    public String getOrderInDepts() {
        return orderInDepts;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setDepartment(List<Long> department) {
        this.department = department;
    }

    public List<Long> getDepartment() {
        return department;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setIsSenior(boolean isSenior) {
        this.isSenior = isSenior;
    }

    public boolean getIsSenior() {
        return isSenior;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public String getDingId() {
        return dingId;
    }

    public void setIsBoss(boolean isBoss) {
        this.isBoss = isBoss;
    }

    public boolean getIsBoss() {
        return isBoss;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setIsLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
    }

    public String getIsLeaderInDepts() {
        return isLeaderInDepts;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public List<UserRole> getRoles() {
        return roles;
    }
}
