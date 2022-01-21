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
        UserBean userBean = userMapper.mapEntityToBean(userService.findByMail(email));

        return userBean;
    }


    public UserBean searchUser(UserBean userBean) {
        User user = userMapper.mapBeanToEntity(userBean);
        userService.saveUser(user);

        UserBean userBeanToReturn = userMapper.mapEntityToBean(userService.findByMail(userBean.getMail()));
        return userBeanToReturn;
    }
}
