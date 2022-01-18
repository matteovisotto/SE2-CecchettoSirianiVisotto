package it.dreamplatform.forum.services;

import it.dreamplatform.forum.entities.Topic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TopicService {
    @PersistenceContext(unitName = "forum-persistence-provider")
    private EntityManager em;

    public TopicService(){}

    public List<Topic> findAllTopics() {
        return em.createNamedQuery("Topic.findAll", Topic.class).getResultList();
    }

    public Topic findById(Long topicId){
        return em.find(Topic.class, topicId);
    }

    public Long createTopic(String title){
        Topic c = new Topic();
        c.setTitle(title);
        em.persist(c);
        em.flush();
        return c.getTopicId();
    }
}
