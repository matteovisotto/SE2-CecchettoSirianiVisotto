package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.entities.Post;
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
            throw new Exception("Policy Maker was not found");
        }
    }

    public void deleteDiscussion(Long discussionId) throws Exception {
        if(discussionService.getDiscussionById(discussionId) != null){
            discussionService.deleteDiscussion(discussionService.getDiscussionById(discussionId));
        } else {
            throw new Exception("The discussion was not found.");
        }
    }

    public void createDiscussion(DiscussionContentBean discussion) throws Exception {
        if(discussion.getDiscussionId() != null){
            throw new Exception("The discussion is already present!");
        }
        Long newDiscussionId = discussionService.saveDiscussion(discussionMapper.mapContentBeanToEntity(discussion));
        Post post = postMapper.mapBeanToEntity(discussion.getPosts().get(0));
        post.setDiscussion(discussionService.getDiscussionById(newDiscussionId));
        post.setStatus(1);
        postService.savePost(post);
    }
}
