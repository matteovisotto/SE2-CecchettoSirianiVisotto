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

    public void approvePendingPost(PostBean post) throws Exception {
        if(post.getPostId()==null){
            throw new Exception("Il post indicato non presenta un Id valido.");
        }
        if(post.getStatus()== 1){
            throw new Exception("Il post è già stato accettato.");
        }
        post.setStatus(1);
        postService.savePost(postMapper.mapBeanToEntity(post));
    }

    public void declinePendingPost(PostBean post) throws Exception {
        if(post.getPostId()==null){
            throw new Exception("Il post indicato non presenta un Id valido.");
        }
        if(post.getStatus()==1){
            throw new Exception("Il post non può essere declinato.");
        }
        postService.deletePost(postMapper.mapBeanToEntity(post));
    }

    public void deletePost(PostBean post) throws Exception{
        if(post.getPostId()==null){
            throw new Exception("Post non valido.");
        }
        if(postService.getPostByPostId(post.getPostId()) != null){
            postService.deletePost(postMapper.mapBeanToEntity(post));
        }
    }

    public void publishPost(PostBean post, UserBean user) throws Exception {
        User utente = userService.findById(user.getUserId());
        if(utente == null){
            throw new Exception("Utente non trovato.");
        }
        if(post.getText() == null){
            throw new Exception("Il post deve contenere del testo.");
        }
        post.setCreator(userMapper.mapEntityToPublicBean(utente));
        if(utente.getPolicyMakerID() == null){
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
            throw new Exception("Il post da modificare non è presente.");
        }
        if(user.getPolicyMakerID() == null){
            if(postToModify.getCreator() != userMapper.mapBeanToEntity(user)){
                throw new Exception("Non hai i permessi per modificare il post.");
            }
        }
        postService.savePost(postMapper.mapBeanToEntity(post));
    }

    public PostBean getPostById(Long postId) throws Exception {
        if(postService.getPostByPostId(postId) != null){
            return postMapper.mapEntityToBean(postService.getPostByPostId(postId));
        } else {
            throw new Exception("Post non trovato!");
        }
    }

    public List<PostBean> getPostsByUser(Long creatorId) throws Exception {
        if(userService.findById(creatorId) != null){
            return postMapper.mapEntityListToBeanList(postService.getPostsByCreator(creatorId));
        } else {
            throw new Exception("L'ID dell'utente indicato non esiste.");
        }
    }

}
