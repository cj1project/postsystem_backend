package com.esp.user;

import com.esp.models.Esp;
import com.esp.models.User;
import com.esp.models.UserHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserService implements UserRepository {

    @Autowired
    EntityManagerFactory emf;

    public EntityManager getEmf(){
        return emf.createEntityManager();
    }

    @Transactional
    public User createEmptyUser() {
        User user = new User();
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
           // TypedQuery<User> hql = em.createQuery("INSERT INTO user VALUES(?, ?)", User.class);//insert into user (id, name) values (?, ?)
           // user = hql.getSingleResult();
            em.merge(user);//em.persist(user);
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User userData(long  userId){
        User user1 = getUser(userId);
        return user1;
    }

    public long registerNewUser(User user) {
        Esp esp = new Esp();
        UserHistory hist = new UserHistory();
        esp.setId(user.getId());
        hist.setId(user.getId());
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            em.merge(user);
            em.merge(esp);
            em.merge(hist);
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }

        return user.getId();
    }


    public User getUser(long id){
        User user = null;
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            TypedQuery<User> query = em.createQuery("SELECT user FROM User user WHERE user.id=?1", User.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            query.setParameter(1, id);
            user = query.getSingleResult();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        if(user == null){
            System.out.println("User does not exist");
        }
        return user;
    }

    @Override
    public User getUser() {
        return new User();
    }

    @Override
    public List getUserList() {
        return null;
    }

}
