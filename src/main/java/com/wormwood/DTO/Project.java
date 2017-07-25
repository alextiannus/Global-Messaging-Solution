package com.wormwood.DTO;

import org.hibernate.validator.constraints.NotBlank;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Donnie on 2017/4/24.
 */
@NameStyle(value = Style.camelhumpAndUppercase)
public class Project implements Serializable {
    private static final long serialVersionUID = -9150193155401406256L;
    @Id
    private Integer projectId;
    @NotBlank
    private String projectName;
    @NotBlank
    private String projectLink;
    private String description;
    @NotBlank
    private String projectType;
    private String crtBy;
    private Date crtOn;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCrtBy() {
        return crtBy;
    }

    public void setCrtBy(String crtBy) {
        this.crtBy = crtBy;
    }

    public Date getCrtOn() {
        return crtOn;
    }

    public void setCrtOn(Date crtOn) {
        this.crtOn = crtOn;
    }
}
