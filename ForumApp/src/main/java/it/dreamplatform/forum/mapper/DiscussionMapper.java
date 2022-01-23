package it.dreamplatform.forum.mapper;

import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.entities.Post;
import it.dreamplatform.forum.entities.Topic;

import javax.inject.Inject;
import java.util.*;

public class DiscussionMapper {

    @Inject
    PostMapper postMapper;

    @Inject
    UserMapper userMapper;

    public DiscussionBean mapEntityToBean (Discussion entity){
        return mapEntityToBean(entity, new DiscussionBean());
    }

    public DiscussionBean mapEntityToBean (Discussion entity, DiscussionBean bean){
        if(entity == null) {return null;}
        bean.setDiscussionId(entity.getDiscussionId());
        bean.setTitle(entity.getTitle());
        bean.setText(entity.getText());
        bean.setTimestamp(entity.getTimestamp());
        bean.setTopicId(entity.getTopic().getTopicId());
        bean.setNumberReplies(entity.getPosts().size());
        bean.setCreator(userMapper.mapEntityToPublicBean(entity.getPosts().stream().min(Comparator.comparingLong(Post::getPostId)).get().getCreator()));
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

    public List<DiscussionBean> mapEntityListToBeanList(List<Discussion> entities){
        return mapEntityListToBeanList(entities, new ArrayList<>());
    }

    public List<DiscussionBean> mapEntityListToBeanList(List<Discussion> entities, List<DiscussionBean> beans){
        entities.forEach(e -> {
            beans.add(mapEntityToBean(e));
        });
        return beans;
    }

    public DiscussionContentBean mapEntityToContentBean(Discussion entity){
        return mapEntityToContentBean(entity, new DiscussionContentBean());
    }

    public DiscussionContentBean mapEntityToContentBean(Discussion entity, DiscussionContentBean bean){
        if(entity == null) {return null;}
        bean.setTopicId(entity.getDiscussionId());
        bean.setDiscussionId(entity.getDiscussionId());
        bean.setTitle(entity.getTitle());
        bean.setTimestamp(entity.getTimestamp());
        bean.setCreator(userMapper.mapEntityToPublicBean(entity.getPosts().stream().min(Comparator.comparingLong(Post::getPostId)).get().getCreator()));
        bean.setText(entity.getText());
        bean.setPosts(postMapper.mapEntityListToBeanList(entity.getPosts()));
        return bean;
    }

    public Discussion mapContentBeanToEntity(DiscussionContentBean bean){
        return mapContentBeanToEntity(bean, new Discussion());
    }

    public Discussion mapContentBeanToEntity(DiscussionContentBean bean, Discussion entity){
        if(bean == null) {return null;}
        entity.setText(bean.getText());
        entity.setTimestamp(bean.getTimestamp());
        entity.setTitle(bean.getTitle());
        Topic topic = new Topic();
        topic.setTopicId(bean.getTopicId());
        entity.setTopic(topic);
        return entity;
    }

}
