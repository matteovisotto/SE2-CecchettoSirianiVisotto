package it.dreamplatform.forum.services;

import it.dreamplatform.forum.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "forum-persistence-provider")
    private EntityManager em;

    public UserService() {}

    public User getUserById(Long userId){
        return em.find(User.class, userId);
    }

    public User getUserByMail(String mail){
        TypedQuery<User> query = em.createNamedQuery("User.findByMail" , User.class);
        List<User> result = query.setParameter("mail", mail).getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public User getUserByPolicyMakerId(String policyMakerId){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.policyMakerID =: policyMakerId", User.class);
        query.setParameter("policyMakerId", policyMakerId);
        return query.getSingleResult();
    }

    public Long createUser(User user){
        em.persist(user);
        em.flush();
        return user.getUserId();
    }

    public void saveUser(User user) {
        if(user.getUserId() == null){
            em.persist(user);
        } else {
            user = em.merge(user);
        }
    }

    public void deleteUser(User user){
        if(em.contains(user)){
            em.remove(user);
        } else{
            em.merge(user);
        }
    }
}
