package com.wormwood.dao;

import com.wormwood.DTO.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by kasimodo on 2017-07-06.
 */
@Mapper
public interface ProjectDao extends tk.mybatis.mapper.common.Mapper<Project> {
}
