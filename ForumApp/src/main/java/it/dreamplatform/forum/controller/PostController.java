package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.bean.UserBean;
import it.dreamplatform.forum.entities.Post;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.mapper.PostMapper;
import it.dreamplatform.forum.mapper.UserMapper;
import it.dreamplatform.forum.services.PostService;
import it.dreamplatform.forum.services.UserService;

import javax.inject.Inject;
import java.util.List;

public class PostController {
    @Inject
    PostService postService;
    @Inject
    PostMapper postMapper;
    @Inject
    UserService userService;
    @Inject
    UserMapper userMapper;

    /**
     * This function is used to approve a post, setting its status to 1, that is in pending list. So it's initial status is set 0.
     * @param postId the id of the selected Post.
     * @throws Exception when the post didn't exist or when the post has already been approved.
     */
    public void approvePendingPost(Long postId) throws Exception {
        Post post = postService.getPostById(postId);
        if(post == null){
            throw new Exception("The selected post has an invalid Id.");
        }
        if(post.getStatus() == 1){
            throw new Exception("The post has already been accepted.");
        }
        post.setStatus(1);
        postService.savePost(post);
    }

    /**
     * This function is used to decline a post, deleting it from the DB, that is in pending list. So it's initial status is set 0.
     * @param postId the id of the selected Post.
     * @throws Exception when the post didn't exist or when the post has already been approved.
     */
    public void declinePendingPost(Long postId) throws Exception {
        Post post = postService.getPostById(postId);
        if(post == null){
            throw new Exception("The selected post has an invalid Id.");
        }
        if(post.getStatus() == 1){
            throw new Exception("The post can't be declined.");
        }
        postService.deletePost(post);
    }

    /**
     * This function is used to delete a post from the DB.
     * @param postId is the id of the selected Post.
     * @throws Exception the id of the post is not valid.
     */
    public void deletePost(Long postId) throws Exception{
        if(postId == null){
            throw new Exception("Post not valid.");
        }
        Post post = postService.getPostById(postId);
        if(post != null){
            postService.deletePost(post);
        }
    }
    /*public void deletePost(PostBean post) throws Exception{
        if(post.getPostId() == null){
            throw new Exception("Post not valid.");
        }
        if(postService.getPostByPostId(post.getPostId()) != null){
            postService.deletePost(postMapper.mapBeanToEntity(post));
        }
    }*/

    public void publishPost(PostBean post, UserBean userBean) throws Exception {
        User user = userService.getUserById(userBean.getUserId());
        if(user == null){
            throw new Exception("User not found.");
        }
        if(post.getText() == null){
            throw new Exception("The post must contains text.");
        }
        post.setCreator(userMapper.mapEntityToPublicBean(user));
        if(user.getPolicyMakerID() == null){
            post.setStatus(0);
        }
        else{
            post.setStatus(1);
        }
        postService.savePost(postMapper.mapBeanToEntity(post));
    }

    public void modifyPost(PostBean post, UserBean user) throws Exception {
        Post postToModify = postService.getPostById(post.getPostId());
        if(postToModify == null){
            throw new Exception("The post to modify is not present");
        }
        if(user.getPolicyMakerID() == null){
            if(postToModify.getCreator() != userMapper.mapBeanToEntity(user)){
                throw new Exception("You don't have the permission to modify the post.");
            }
        }
        postService.savePost(postMapper.mapBeanToEntity(post));
    }

    /**
     * This function retrieve a given post, given its id.
     * @param postId is the id of the selected post.
     * @return the Bean of the needed post.
     * @throws Exception when the post is not found.
     */
    public PostBean getPostById(Long postId) throws Exception {
        if(postService.getPostById(postId) != null){
            return postMapper.mapEntityToBean(postService.getPostById(postId));
        } else {
            throw new Exception("Post not found!");
        }
    }

    /**
     * This function retrieve the posts in a given discussion.
     * @param discussionId is the id of the selected discussion.
     * @return a List of Bean with all the posts contained in the discussion.
     * @throws Exception the post is not found.
     */
    public List<PostBean> getPostsByDiscussionId(Long discussionId) throws Exception {
        if(postService.getPostsByDiscussionId(discussionId) != null){
            return postMapper.mapEntityListToBeanList(postService.getPostsByDiscussionId(discussionId));
        } else {
            throw new Exception("Post not found!");
        }
    }

    /**
     * This function retrieve the posts written by a User.
     * @param creatorId is the id of the User.
     * @return a List of Bean with all the posts written by the selected User.
     * @throws Exception when the User does not exist.
     */
    public List<PostBean> getPostsByUser(Long creatorId) throws Exception {
        if(userService.getUserById(creatorId) != null){
            return postMapper.mapEntityListToBeanList(postService.getPostsByCreator(creatorId));
        } else {
            throw new Exception("The user Id inserted not exist.");
        }
    }

}
