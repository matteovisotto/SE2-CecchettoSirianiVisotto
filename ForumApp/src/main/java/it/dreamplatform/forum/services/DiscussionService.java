package it.dreamplatform.forum.services;

import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DiscussionService {
    @PersistenceContext(unitName = "forum-persistence-provider")
    private EntityManager em;

    public DiscussionService(){}

    public List<Discussion> getDiscussionsByTopicId (Long topicId){
        TypedQuery<Discussion> query = em.createQuery("SELECT d FROM Discussion d WHERE d.topic.topicId = :topicId" , Discussion.class);
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

    public List<Discussion> getDiscussionByPolicyMaker(String policyMakerId){
        Query query = em.createNativeQuery("SELECT d.* FROM Discussion d JOIN Post p1 ON p1.discussionId = d.discussionId where p1.postId in (select min(postId) from Post p2 group by p2.discussionId) and p1.creatorId =?", Discussion.class);
        query.setParameter(1, policyMakerId);
        return ((List<Discussion>) query.getResultList());
    }

    public Long saveDiscussion(Discussion discussion) {
        if(discussion.getDiscussionId() == null){
            em.persist(discussion);
            em.flush();
            return discussion.getDiscussionId();
        } else {
            discussion = em.merge(discussion);
            em.flush();
            return discussion.getDiscussionId();
        }
    }

    public void deleteDiscussion(Discussion discussion){
        if(em.contains(discussion)){
            em.remove(discussion);
            em.flush();
        } else{
            em.merge(discussion);
            em.flush();
        }
    }

    public List<User> getDiscussionFollowers(Long discussionId){
        Query query = em.createNativeQuery("SELECT u.* FROM User AS u JOIN Post p ON p.creatorId = u.userId WHERE p.discussionId = ? GROUP BY p.creatorId", User.class);
        query.setParameter(1, discussionId);
        return ((List<User>) query.getResultList());
    }

    public List<Discussion> getMostActiveDiscussions(Integer numberOfDiscussions){
        Query query = em.createNativeQuery("SELECT d.*, COUNT(*) AS n_posts FROM Post as p JOIN Discussion as d ON p.discussionId = d.discussionId WHERE p.status=1 GROUP BY p.discussionId ORDER BY n_posts DESC LIMIT ?", Discussion.class);
        query.setParameter(1,numberOfDiscussions);
        return (List<Discussion>) query.getResultList();
    }

}
