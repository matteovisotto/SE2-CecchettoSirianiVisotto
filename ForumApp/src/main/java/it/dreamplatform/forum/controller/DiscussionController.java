package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.bean.DiscussionContentBean;
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

    /**
     * This function is used to retrieve a DiscussionContentBean.
     * @param discussionId is the id of the selected discussion.
     * @return a Bean containing all the elements of a discussion.
     */
    public DiscussionContentBean getPostsByDiscussionId(Long discussionId){
        return discussionMapper.mapEntityToContentBean(discussionService.getDiscussionById(discussionId));
    }

    /**
     * This function is used to retrieve all the discussion of the selected Policy maker (checking if it exists or not).
     * @param policyMakerId is the id of the selected Policy maker.
     * @return the List of Discussion created by that Policy maker.
     * @throws Exception when the Policy maker is not found.
     */
    public List<DiscussionBean> getDiscussionByPolicyMaker(String policyMakerId) throws Exception {
        if (userService.getUserByPolicyMakerId(policyMakerId) != null) {
            return discussionMapper.mapEntityListToBeanList(discussionService.getDiscussionByPolicyMaker(policyMakerId));
        } else {
            throw new Exception("Policy Maker was not found");
        }
    }

    /**
     * This function is used to delete a discussion from the DB.
     * @param discussionId is the id of the selected discussion.
     * @throws Exception when the discussion is not found.
     */
    public void deleteDiscussion(Long discussionId) throws Exception {
        if(discussionService.getDiscussionById(discussionId) != null){
            discussionService.deleteDiscussion(discussionService.getDiscussionById(discussionId));
        } else {
            throw new Exception("The discussion was not found.");
        }
    }

    /**
     * This function is used to create a new discussion, with also the first post of the created discussion (already set to 1) and save it on the DB.
     * @param discussion is the complete Bean that is going to be the new discussion.
     */
    public void createDiscussion(DiscussionContentBean discussion)/* throws Exception */{
        /*if(discussion.getDiscussionId() != null){
            throw new Exception("The discussion is already present!");
        }*/
        Long newDiscussionId = discussionService.saveDiscussion(discussionMapper.mapContentBeanToEntity(discussion));
        Post post = postMapper.mapBeanToEntity(discussion.getPosts().get(0));
        post.setDiscussion(discussionService.getDiscussionById(newDiscussionId));
        post.setStatus(1);
        postService.savePost(post);
    }
}
