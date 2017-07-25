package com.wormwood.DTO;

import java.util.List;

public class DepartmentMsg {

    private int errcode;
    private String errmsg;
    private List<DepartmentDetail> department;

    public DepartmentMsg() {
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<DepartmentDetail> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentDetail> department) {
        this.department = department;
    }
}
