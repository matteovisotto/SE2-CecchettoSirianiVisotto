package it.dreamplatform.forum.services;

import it.dreamplatform.forum.entities.Post;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PostService {
    @PersistenceContext(unitName = "forum-persistence-provider")
    private EntityManager em;

    public PostService(){}

    public List<Post> getPostsByDiscussionId (Long discussionId){
        TypedQuery<Post> query = em.createNamedQuery("Post.findByDiscussion" , Post.class);
        query.setParameter("discussionId", discussionId);
        return query.getResultList();
    }

    public Post getPostByPostId(Long postId){
        return em.find(Post.class, postId);
    }

    public List<Post> getPendingPosts(){
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.status = 0", Post.class);
        return query.getResultList();
    }

    public List<Post> getPostsByCreator(Long creatorId) {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.creator.userId =:creatorId", Post.class);
        query.setParameter("creatorId", creatorId);
        return query.getResultList();
    }

    public void savePost(Post post) {
        if(post.getPostId() == null){
            em.persist(post);
        } else {
            post = em.merge(post);
        }
    }

    public void deletePost(Post post){
        if(em.contains(post)){
            em.remove(post);
        } else{
            em.merge(post);
        }
    }
}
