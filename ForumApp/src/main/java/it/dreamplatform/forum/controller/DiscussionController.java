package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.mapper.DiscussionMapper;
import it.dreamplatform.forum.mapper.PostMapper;
import it.dreamplatform.forum.services.DiscussionService;
import it.dreamplatform.forum.services.PostService;
import it.dreamplatform.forum.services.UserService;

import javax.inject.Inject;
import java.util.List;

public class DiscussionController {
    @Inject
    DiscussionMapper discussionMapper;
    @Inject
    DiscussionService discussionService;
    @Inject
    UserService userService;
    @Inject
    PostService postService;
    @Inject
    PostMapper postMapper;

    public DiscussionContentBean getPostByDiscussionId(Long discussionId){
        return discussionMapper.mapEntityToContentBean(discussionService.getDiscussionById(discussionId));
    }

    public List<DiscussionBean> getDiscussionByPolicyMaker(String policyMakerId) throws Exception {
        if (userService.getUserByPolicyMakerId(policyMakerId) != null) {
            return discussionMapper.mapEntityListToBeanList(discussionService.getDiscussionByPolicyMaker(policyMakerId));
        } else {
            throw new Exception("Policy Maker indicato non presente");
        }
    }

    public void deleteDiscussion(DiscussionContentBean discussion) throws Exception {
        if(discussionService.getDiscussionById(discussion.getDiscussionId()) != null){
            discussionService.deleteDiscussion(discussionMapper.mapBeanToEntity(discussion));
        } else {
            throw new Exception("Indicated discussion was not found.");
        }
    }

    public void createDiscussion(DiscussionContentBean discussion) throws Exception {
        if(discussion.getDiscussionId() != null){
            throw new Exception("Indicated discussion is already present!");
        }
        discussionService.saveDiscussion(discussionMapper.mapBeanToEntity(discussion));
        postService.savePost(postMapper.mapBeanToEntity(discussion.getPosts().get(0)));
    }
}
