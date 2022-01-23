package it.dreamplatform.forum.controller;

import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.bean.UserBean;
import it.dreamplatform.forum.entities.Post;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.mapper.PostMapper;
import it.dreamplatform.forum.mapper.UserMapper;
import it.dreamplatform.forum.services.DiscussionService;
import it.dreamplatform.forum.services.PostService;
import it.dreamplatform.forum.services.UserService;
import it.dreamplatform.forum.utils.StatusEnum;

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

    public void approvePendingPost(Long postId) throws Exception {
        Post post = postService.getPostByPostId(postId);
        if(post == null){
            throw new Exception("The selected post has an invalid Id.");
        }
        if(post.getStatus() == 1){
            throw new Exception("The post has already been accepted.");
        }
        post.setStatus(1);
        postService.savePost(post);
    }

    public void declinePendingPost(Long postId) throws Exception {
        Post post = postService.getPostByPostId(postId);
        if(post == null){
            throw new Exception("The selected post has an invalid Id.");
        }
        if(post.getStatus() == 1){
            throw new Exception("The post can't be declined.");
        }
        postService.deletePost(post);
    }

    public void deletePost(PostBean post) throws Exception{
        if(post.getPostId() == null){
            throw new Exception("Post not valid.");
        }
        if(postService.getPostByPostId(post.getPostId()) != null){
            postService.deletePost(postMapper.mapBeanToEntity(post));
        }
    }

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
        Post postToModify = postService.getPostByPostId(post.getPostId());
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

    public PostBean getPostById(Long postId) throws Exception {
        if(postService.getPostByPostId(postId) != null){
            return postMapper.mapEntityToBean(postService.getPostByPostId(postId));
        } else {
            throw new Exception("Post not found!");
        }
    }

    public List<PostBean> getPostsByUser(Long creatorId) throws Exception {
        if(userService.getUserById(creatorId) != null){
            return postMapper.mapEntityListToBeanList(postService.getPostsByCreator(creatorId));
        } else {
            throw new Exception("The user Id inserted not exist.");
        }
    }

}
