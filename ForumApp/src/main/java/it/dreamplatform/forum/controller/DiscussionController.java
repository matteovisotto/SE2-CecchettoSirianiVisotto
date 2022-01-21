package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.mapper.DiscussionMapper;
import it.dreamplatform.forum.services.DiscussionService;

import javax.inject.Inject;

public class DiscussionController {
    @Inject
    DiscussionMapper discussionMapper;
    @Inject
    DiscussionService discussionService;

    public DiscussionContentBean getPostByDiscussionId(Long discussionId){
        return discussionMapper.mapEntityToContentBean(discussionService.getDiscussionById(discussionId));
    }
}
