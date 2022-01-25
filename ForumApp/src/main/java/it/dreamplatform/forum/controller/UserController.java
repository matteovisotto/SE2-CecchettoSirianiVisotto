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

    /**
     * This function retrieve a User, given its email (since the emails are unique this function will always return 1 user).
     * @param email is the email of the User.
     * @return the Bean of the searched User.
     */
    public UserBean searchUser(String email) {
        return userMapper.mapEntityToBean(userService.getUserByMail(email));
    }

    //Penso sia una saveUser ma non so, quindi per ora non scrivo Javadoc.
    public UserBean searchUser(UserBean userBean) {
        User user = userMapper.mapBeanToEntity(userBean);
        userService.saveUser(user);

        return userMapper.mapEntityToBean(userService.getUserByMail(userBean.getMail()));
    }

    /**
     * This function is used to create a new User.
     * @param userBean is the Bean containing all the information of the User that the function is going to create.
     * @return the id of the User just created.
     */
    public Long createUser(UserBean userBean){
        //Perché ritorna Long?
        return userService.createUser(userMapper.mapBeanToEntity(userBean));
    }
}
