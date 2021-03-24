package com.esp.userhistory;

import com.esp.models.Esp;
import com.esp.models.ImageEntity;
import com.esp.models.User;
import com.esp.models.UserHistory;
import com.esp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserHistoryService {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    private UserService userService;

    public EntityManager getEmf(){
        return emf.createEntityManager();
    }

    public List<UserHistory> getAllHistory(User user){
        user = userService.getUser(user.getId());
        List<UserHistory> histroy = new ArrayList<>();
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            TypedQuery<UserHistory> hql = em.createQuery("SELECT hist FROM UserHistory hist WHERE hist.id=?1", UserHistory.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            hql.setParameter(1, user.getId());
            histroy = hql.getResultList();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        if(user == null){
            System.out.println("User History does not exist");
        }
        return histroy;
    }

    @Transactional
    public List<User> getAllUserHistoryOfAUser() {
        var hist = new ArrayList<User>();
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            TypedQuery<User> hql = em.createQuery("SELECT h FROM UserHistory h INNER JOIN h ON User ", User.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            //hql.setParameter(1, "NULL");
            hist = (ArrayList<User>) hql.getResultList();

            //"SELECT d FROM Employee e INNER JOIN e.department d", Department.class);
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }

        if(hist == null){
            System.out.println("Hist is Null");
        }

        return hist;
    }

    public Esp getEspById(long id){
        Esp esp = null;
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            TypedQuery<Esp> query = em.createQuery("SELECT esp FROM Esp esp WHERE esp.ID=?1", Esp.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            query.setParameter(1, id);
            esp = query.getSingleResult();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        if(esp == null){
            System.out.println("Esp does not exist");
        }
        return esp;
    }

    public Esp getAllHistoryOfUser(long id){
        Esp esp = null;
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            TypedQuery<Esp> query = em.createQuery("SELECT esp FROM Esp esp WHERE esp.ID=?1", Esp.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            query.setParameter(1, id);
            esp = query.getSingleResult();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        if(esp == null){
            System.out.println("Esp does not exist");
        }
        return esp;
    }

}
