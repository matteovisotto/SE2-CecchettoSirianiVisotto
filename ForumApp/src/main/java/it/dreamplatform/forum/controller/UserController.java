package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.UserBean;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.mapper.UserMapper;
import it.dreamplatform.forum.services.UserService;

import javax.inject.Inject;

public class UserController {
    @Inject
    UserMapper userMapper;
    @Inject
    UserService userService;


    public UserBean searchUser(String email) {
        return userMapper.mapEntityToBean(userService.getUserByMail(email));
    }


    public UserBean searchUser(UserBean userBean) {
        User user = userMapper.mapBeanToEntity(userBean);
        userService.saveUser(user);

        UserBean userBeanToReturn = userMapper.mapEntityToBean(userService.getUserByMail(userBean.getMail()));
        return userBeanToReturn;
    }

    public Long createUser(UserBean userBean){
        return userService.createUser(userMapper.mapBeanToEntity(userBean));
    }
}
