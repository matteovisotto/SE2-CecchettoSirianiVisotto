package it.dreamplatform.forum.mapper;

import it.dreamplatform.forum.bean.PublicUserBean;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.bean.UserBean;

public class UserMapper {
    public UserBean mapEntityToBean(User entity){
        return mapEntityToBean(entity, new UserBean());
    }

    public UserBean mapEntityToBean(User entity, UserBean bean){
        if(entity == null) {return null;}

        bean.setUserId(entity.getUserId());
        bean.setName(entity.getName());
        bean.setSurname(entity.getSurname());
        bean.setMail(entity.getMail());
        bean.setDateOfBirth(entity.getDateOfBirth());
        bean.setAreaOfResidence(entity.getAreaOfResidence());
        bean.setPolicyMakerID(entity.getPolicyMakerID());
        bean.setCreatedAt(entity.getCreatedAt());
        return bean;
    }

    public User mapBeanToEntity(UserBean bean){
        return mapBeanToEntity(new User(), bean);
    }

    public User mapBeanToEntity(User entity, UserBean bean){
        entity.setUserId(bean.getUserId());
        entity.setName(bean.getName());
        entity.setSurname(bean.getSurname());
        entity.setMail(bean.getMail());
        entity.setDateOfBirth(bean.getDateOfBirth());
        entity.setAreaOfResidence(bean.getAreaOfResidence());
        entity.setPolicyMakerID(bean.getPolicyMakerID());
        entity.setCreatedAt(bean.getCreatedAt());
        return entity;
    }

    public PublicUserBean mapEntityToPublicBean(User user) {
        return mapEntityToPublicBean(user, new PublicUserBean());
    }

    public PublicUserBean mapEntityToPublicBean(User entity, PublicUserBean bean) {
        if(entity == null) {return null;}
        bean.setUserId(entity.getUserId());
        bean.setName(entity.getName());
        bean.setSurname(entity.getSurname());
        bean.setPolicyMaker(entity.getPolicyMakerID() != null);
        bean.setAreaOfResidence(entity.getAreaOfResidence());
        return bean;
    }

    public User mapPublicBeanToEntity(PublicUserBean bean, User entity){
        entity.setUserId(bean.getUserId());
        entity.setName(bean.getName());
        entity.setSurname(bean.getSurname());
        entity.setAreaOfResidence(bean.getAreaOfResidence());
        return entity;
    }

    public User mapPublicBeanToEntity(PublicUserBean bean){
        return mapPublicBeanToEntity(bean , new User());
    }

}
