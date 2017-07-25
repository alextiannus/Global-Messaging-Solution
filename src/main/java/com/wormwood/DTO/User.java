package com.wormwood.DTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kasimodo on 2016-07-22.
 */
public class User implements Serializable{
    @Id @GeneratedValue
    private Integer id;
    private String username;
    private String password ;
    private String companyOrgCode;
    private String companyName;
    private String role;
    private String roleType;
    private String email;
    private Date crtDate;
    private Date updDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return password;
    }

    public void setUserPassword(String userPassword) {
        this.password = userPassword;
    }

    public String getCompanyOrgCode() {
        return companyOrgCode;
    }

    public void setCompanyOrgCode(String companyOrgCode) {
        this.companyOrgCode = companyOrgCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}
