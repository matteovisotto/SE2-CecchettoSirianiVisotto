package it.dreamplatform.forum.services;

import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.entities.Topic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TopicService {
    @PersistenceContext(unitName = "forum-persistence-provider")
    private EntityManager em;

    public TopicService(){}

    public List<Topic> getAllTopics() {
        TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t", Topic.class);
        return query.getResultList();
    }

    public Topic getTopicById(Long topicId){
        return em.find(Topic.class, topicId);
    }

    public List<Topic> getTopicByTitle(String title){
        TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t WHERE t.title =:title", Topic.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    public Long createTopic(String title){
        Topic c = new Topic();
        c.setTitle(title);
        em.persist(c);
        em.flush();
        return c.getTopicId();
    }

    public void saveTopic(Topic topic) {
        if(topic.getTopicId() == null){
            em.persist(topic);
        } else {
            topic = em.merge(topic);
        }
    }

    public void deleteTopic(Topic topic){
        if(em.contains(topic)){
            em.remove(topic);
        } else{
            em.merge(topic);
        }
    }
}
