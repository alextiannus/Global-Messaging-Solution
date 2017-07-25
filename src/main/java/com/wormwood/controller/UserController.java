package com.wormwood.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wormwood.client.DingDingClient;
import com.wormwood.request.AttendanceRequest;
import com.wormwood.response.AttendanceResponse;
import com.wormwood.response.Response;
import com.wormwood.result.ResultEnum;
import com.wormwood.util.DateUtil;
import com.wormwood.vo.user.Attendance;
import com.wormwood.vo.user.AttendanceListVO;
import com.wormwood.vo.user.UserDetail;
import com.wormwood.vo.user.UserListVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kasimodo on 2017-07-10.
 */
@Controller
@RequestMapping("/user")
public class UserController extends DDBaseController {

    @Autowired
    private DingDingClient dingDingClient;

    private final static String ONDUTY = "OnDuty";
    private final static String OFFDUTY = "OffDuty";

    @RequestMapping(value = "/findUserList", method = RequestMethod.GET)
    @ResponseBody
    public Response<UserListVO> findUserList() {
        UserListVO userListVo = dingDingClient.findUserList(getToken().getAccess_token(), "1");
        if (userListVo.getErrcode() != 0) {
            return new Response<>(userListVo, ResultEnum.FAIL);
        }
        return new Response<>(userListVo, ResultEnum.SUCCESS);
    }

    @RequestMapping("/toStaffList")
    public String toStaffList() {
        return "user/staffList";
    }


    @RequestMapping(value = "/findAttendanceList", method = RequestMethod.GET)
    public ModelAndView findAttendanceList(@RequestParam(value = "queryDate", required = false) String queryDate) {
        Date date = new Date();
        if (StringUtils.isNotBlank(queryDate)) {
            date = DateUtil.parseDate(queryDate);
        }
        String workDateFrom = DateUtil.getDateString(DateUtil.getStartTimeOfTheDay(0, date));
        String workDateTo = DateUtil.getDateString(DateUtil.getEndTimeOfTheDay(0, date));
        AttendanceRequest attendanceRequest = new AttendanceRequest();
        attendanceRequest.setWorkDateFrom(workDateFrom);
        attendanceRequest.setWorkDateTo(workDateTo);
        AttendanceListVO attendanceList = dingDingClient.findAttendanceList(getToken().getAccess_token(), attendanceRequest);
        Map<String, Object> model = Maps.newHashMap();
        model.put("data", getResult(attendanceList.getRecordresult()));
        return new ModelAndView("user/staffAttendance", model);
    }

    private List<AttendanceResponse> getResult(List<Attendance> list) {

        List<AttendanceResponse> result = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(list)) {

            for (Attendance item : list) {
                Boolean updateIfExist = updateIfExist(result, item); //存在，更新时间

                if (!updateIfExist) {  //不存在，加入新的
                    AttendanceResponse attendanceResponse = new AttendanceResponse();
                    String userId = item.getUserId();
                    String checkType = item.getCheckType();
                    if (checkType.equalsIgnoreCase(OFFDUTY)) {
                        attendanceResponse.setEndTime(DateUtil.getDateString(new Date(item.getUserCheckTime())));
                    } else {
                        attendanceResponse.setStartTime(DateUtil.getDateString(new Date(item.getUserCheckTime())));
                    }
                    UserDetail userDetail = dingDingClient.getUserDetail(getToken().getAccess_token(), userId);
                    attendanceResponse.setUserId(userId);
                    attendanceResponse.setStaffName(userDetail.getName());
                    result.add(attendanceResponse);
                }

            }

        }
        return result;
    }

    private Boolean updateIfExist(List<AttendanceResponse> list, Attendance attendance) {
        if (!CollectionUtils.isEmpty(list)) {
            for (AttendanceResponse item : list) {
                if (item.getUserId().equalsIgnoreCase(attendance.getUserId())) {
                    String checkType = attendance.getCheckType();
                    if (checkType.equalsIgnoreCase(OFFDUTY)) {
                        item.setEndTime(DateUtil.getDateString(new Date(attendance.getUserCheckTime())));
                    } else {
                        item.setStartTime(DateUtil.getDateString(new Date(attendance.getUserCheckTime())));
                    }
                    return true;
                }
            }
        }
        return false;

    }

}
