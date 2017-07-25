package com.wormwood.vo.user;

import com.wormwood.vo.BaseVO;

import java.util.List;

/**
 * Created by kasimodo on 2017-07-10.
 */
public class UserListVO extends BaseVO {

    private List<Userlist> userlist;


    public List<Userlist> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<Userlist> userlist) {
        this.userlist = userlist;
    }
}
