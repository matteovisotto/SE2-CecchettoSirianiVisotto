package it.dreamplatform.forum.mapper;

import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.bean.TopicBean;

import java.util.ArrayList;
import java.util.List;

public class TopicMapper {
    public TopicBean mapEntityToBean(Topic entity){
        return mapEntityToBean(entity, new TopicBean());
    }

    public TopicBean mapEntityToBean(Topic entity, TopicBean bean) {
        if(entity == null) {return null;}
        bean.setTopicId(entity.getTopicId());
        bean.setTitle(entity.getTitle());
        bean.setTimestamp(entity.getTimestamp());
        return bean;
    }

    public List<TopicBean> mapEntityListToBeanList(List<Topic> entities){
        return mapEntityListToBeanList(entities, new ArrayList<TopicBean>());
    }
    public List<TopicBean> mapEntityListToBeanList(List<Topic> entities, List<TopicBean> beans){
        for (Topic entity: entities) {
            beans.add(mapEntityToBean(entity, new TopicBean()));
        }
        return beans;
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


