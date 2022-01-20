package it.dreamplatform.forum.services;

import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.Discussion;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DiscussionService {
    @PersistenceContext(unitName = "forum-persistence-provider")
    private EntityManager em;

    public DiscussionService(){}

    public List<Discussion> getDiscussionsByTopicId (Long topicId){
        TypedQuery<Discussion> query = em.createQuery("SELECT d FROM Discussion d where d.topic.topicId = :topicId" , Discussion.class);
        query.setParameter("topicId", topicId);
        return query.getResultList();
    }

    public Discussion getDiscussionById(Long discussionId){
        return em.find(Discussion.class, discussionId);
    }

    public List<Discussion> getDiscussionByTitle(String title){
        TypedQuery<Discussion> query = em.createQuery("SELECT d FROM Discussion d WHERE d.title =:title", Discussion.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    public void saveDiscussion(Discussion discussion) {
        if(discussion.getDiscussionId() == null){
            em.persist(discussion);
        } else {
            discussion = em.merge(discussion);
        }
    }

    public void deleteDiscussion(Discussion discussion){
        if(em.contains(discussion)){
            em.remove(discussion);
        } else{
            em.merge(discussion);
        }
    }

}
