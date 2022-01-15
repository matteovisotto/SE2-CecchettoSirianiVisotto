package org.dream.forum.services;

import org.dream.forum.entities.Topic;

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
}
