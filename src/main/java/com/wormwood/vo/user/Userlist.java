package com.wormwood.vo.user;

import java.util.List;

/**
 * Created by kasimodo on 2017-07-10.
 */
public class Userlist {

    private String position;
    private List<Integer> department;
    private String unionid;
    private String tel;
    private String userid;
    private boolean isLeader;
    private String avatar;
    private String dingId;
    private String jobnumber;
    private boolean isBoss;
    private Long order;
    private String name;
    private boolean active;
    private boolean isAdmin;
    private boolean isHide;
    private String mobile;
    private String openId;

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }

    public List<Integer> getDepartment() {
        return department;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }

    public boolean getIsLeader() {
        return isLeader;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public String getDingId() {
        return dingId;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setIsBoss(boolean isBoss) {
        this.isBoss = isBoss;
    }

    public boolean getIsBoss() {
        return isBoss;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getOrder() {
        return order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsHide(boolean isHide) {
        this.isHide = isHide;
    }

    public boolean getIsHide() {
        return isHide;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

}