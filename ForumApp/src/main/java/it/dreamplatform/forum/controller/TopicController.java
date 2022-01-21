package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.bean.TopicBean;
import it.dreamplatform.forum.bean.TopicContentBean;
import it.dreamplatform.forum.mapper.TopicMapper;
import it.dreamplatform.forum.services.TopicService;


import javax.inject.Inject;
import java.util.List;

public class TopicController {
    @Inject
    TopicMapper topicMapper;
    @Inject
    TopicService topicService;

    //public List<DiscussionBean> getDiscussions(){}

    public TopicBean getTopicById(Long topicId) {
        return topicMapper.mapEntityToBean(topicService.findTopicById(topicId));
    }

    public TopicContentBean getDiscussionByTopicId(Long topicId){
        return topicMapper.mapEntityToContentBean(topicService.findTopicById(topicId));
    }

    public List<TopicBean> findAllTopics() {
        return topicMapper.mapEntityListToBeanList(topicService.findAllTopics());
    }
}
