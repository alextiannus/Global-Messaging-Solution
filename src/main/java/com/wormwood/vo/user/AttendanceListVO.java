package com.wormwood.vo.user;


import com.wormwood.vo.BaseVO;

import java.util.List;

/**
 * Created by kasimodo on 2017-07-11.
 */
public class AttendanceListVO extends BaseVO {

    private List<Attendance> recordresult;

    public List<Attendance> getRecordresult() {
        return recordresult;
    }

    public void setRecordresult(List<Attendance> recordresult) {
        this.recordresult = recordresult;
    }
}
