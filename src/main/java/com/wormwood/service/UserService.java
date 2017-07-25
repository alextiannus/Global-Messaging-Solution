package com.wormwood.service;

import com.wormwood.DTO.User;
import com.wormwood.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Donnie on 2017/4/25.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findByName(String username){
        return userDao.findByName(username);
    }

    public User findByName1() {
        User user = new User();
        user.setRole("CN=PF_AO,CN=PF_OFFR,OU=MPAINTRANET,DC=gov,DC=sg");
        user.setCompanyName("company");
        user.setCompanyOrgCode("com");
        user.setUsername("donnie");
        user.setUserPassword("donnie");
        user.setRoleType("mpa");
        return user;
    }
}
