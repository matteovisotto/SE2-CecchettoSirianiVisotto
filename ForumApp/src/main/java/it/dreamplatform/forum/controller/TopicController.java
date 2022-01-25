package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.TopicBean;
import it.dreamplatform.forum.bean.TopicContentBean;
import it.dreamplatform.forum.mapper.TopicMapper;
import it.dreamplatform.forum.services.TopicService;


import javax.inject.Inject;
import java.util.List;

/**
 * This class contains all the controller used by a Topic entity or a Topic bean of every genre.
 */
public class TopicController {
    @Inject
    TopicMapper topicMapper;
    @Inject
    TopicService topicService;

    /**
     * This function is used to retrieve a TopicBean.
     * @param topicId is the id of the selected Topic.
     * @return a Bean containing all the elements of a Topic, excluding the discussions inside it.
     */
    public TopicBean getTopicById(Long topicId) {
        return topicMapper.mapEntityToBean(topicService.getTopicById(topicId));
    }

    /**
     * This function is used to retrieve a TopicContentBean.
     * @param topicId is the id of the selected Topic.
     * @return a Bean containing all the elements of a Topic, including all the discussions inside it.
     */
    public TopicContentBean getDiscussionByTopicId(Long topicId){
        return topicMapper.mapEntityToContentBean(topicService.getTopicById(topicId));
    }

    /**
     * This function is used to retrieve a List of TopicBean.
     * @return a List of TopicBean containing, for each Topic, all the elements of a Topic, excluding the discussions inside it.
     */
    public List<TopicBean> getAllTopics() {
        return topicMapper.mapEntityListToBeanList(topicService.getAllTopics());
    }
}
