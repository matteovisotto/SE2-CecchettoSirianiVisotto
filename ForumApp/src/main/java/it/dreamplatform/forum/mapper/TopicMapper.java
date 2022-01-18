package it.dreamplatform.forum.mapper;

import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.bean.TopicBean;

public class TopicMapper {
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


