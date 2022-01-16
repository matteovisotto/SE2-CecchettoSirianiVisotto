package org.dream.forum.mapper;

import org.dream.forum.bean.TopicBean;
import org.dream.forum.entities.Topic;

public class TopicTopicBeanMapper {
    public TopicBean mapEntityToBean(Topic entity){
        return mapEntityToBean(entity, new TopicBean());
    }

    public TopicBean mapEntityToBean(Topic entity, TopicBean bean) {
        bean.setTopicId(entity.getTopicId());
        bean.setTitle(entity.getTitle());
        bean.setTimestamp(entity.getTimestamp());
        return bean;
    }

    public Topic mapBeanToEntity(TopicBean bean){
        return mapBeanToEntity(new Topic(), bean);
    }

    public Topic mapBeanToEntity(Topic entity, TopicBean bean){
        entity.setTopicId(bean.getTopicId());
        entity.setTitle(bean.getTitle());
        entity.setTimestamp(bean.getTimestamp());
        return entity;
    }
}


