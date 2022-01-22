package it.dreamplatform.forum.mapper;

import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.Post;
import it.dreamplatform.forum.bean.PostBean;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PostMapper {
    @Inject
    UserMapper userMapper;


    public PostBean mapEntityToBean(Post entity){
        return mapEntityToBean(entity, new PostBean());
    }

    public PostBean mapEntityToBean(Post entity, PostBean bean){
        if(entity == null) {return null;}
        bean.setPostId(entity.getPostId());
        bean.setText(entity.getText());
        bean.setTimestamp(entity.getTimestamp());
        bean.setStatus(entity.getStatus());
        bean.setCreator(userMapper.mapEntityToPublicBean(entity.getCreator()));
        //bean.setDiscussion(entity.getDiscussion());
        return bean;
    }

    public Post mapBeanToEntity(PostBean bean){
        return mapBeanToEntity(new Post(), bean);
    }

    public Post mapBeanToEntity(Post entity, PostBean bean){
        entity.setPostId(bean.getPostId());
        entity.setText(bean.getText());
        entity.setTimestamp(bean.getTimestamp());
        entity.setStatus(bean.getStatus());
        entity.setCreator(userMapper.mapPublicBeanToEntity(bean.getCreator()));
        //entity.setDiscussion(bean.getDiscussion());
        return entity;
    }

    public List<PostBean> mapEntityListToBeanList(List<Post> entities){
        return mapEntityListToBeanList(entities, new ArrayList<>());
    }

    public List<PostBean> mapEntityListToBeanList(List<Post> entities, List<PostBean> beans){
        entities.forEach(e -> {
            beans.add(mapEntityToBean(e));
        });
        return beans;
    }
}
