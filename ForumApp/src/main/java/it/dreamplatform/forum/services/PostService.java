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

    public List<Post> getByDiscussionId (Long discussionId){
        TypedQuery<Post> query = em.createNamedQuery("Post.fin" +
                "dByDiscussion" , Post.class);
        return query.setParameter("discussionId", discussionId).getResultList();
    }
}
