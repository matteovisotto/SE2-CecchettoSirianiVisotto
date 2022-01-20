package it.dreamplatform.forum.mapper;

import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.bean.DiscussionBean;

public class DiscussionMapper {
    public DiscussionBean mapEntityToBean (Discussion entity){
        return mapEntityToBean(entity, new DiscussionBean());
    }

    public DiscussionBean mapEntityToBean (Discussion entity, DiscussionBean bean){
        bean.setDiscussionId(entity.getDiscussionId());
        bean.setTitle(entity.getTitle());
        bean.setText(entity.getText());
        bean.setTimestamp(entity.getTimestamp());
        bean.setTopicId(entity.getTopic().getTopicId());
        return bean;
    }

    public Discussion mapBeanToEntity (DiscussionBean bean){
        return mapBeanToEntity(new Discussion(), bean);
    }

    public Discussion mapBeanToEntity (Discussion entity, DiscussionBean bean){
        entity.setDiscussionId(bean.getDiscussionId());
        entity.setTitle(bean.getTitle());
        entity.setText(bean.getText());
        entity.setTimestamp(bean.getTimestamp());
        return entity;
    }
}
