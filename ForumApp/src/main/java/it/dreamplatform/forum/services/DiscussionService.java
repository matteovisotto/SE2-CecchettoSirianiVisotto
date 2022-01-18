package it.dreamplatform.forum.services;

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

    public List<Discussion> getByTopicId (Long topicId){
        TypedQuery<Discussion> query = em.createNamedQuery("Discussion.findByTopic" , Discussion.class);
        return query.setParameter("topicId", topicId).getResultList();
    }
}
