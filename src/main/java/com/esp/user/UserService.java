package com.esp.user;

import com.esp.models.Esp;
import com.esp.models.Role;
import com.esp.models.User;
import com.esp.models.UserHistory;
import com.esp.security.dbAuthWithRole.ResultFromDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Array;
import java.util.*;

@Service
public class UserService implements UserRepository {

    @Autowired
    EntityManagerFactory emf;

    public EntityManager getEmf(){
        return emf.createEntityManager();
    }

    public User getUserWithUserNameAndRole(String username){
        //SELECT u, u.roles as role FROM User u WHERE u.username = :username AND u.user_id = role.role_id"

        User user = null;
        Role role = null;
        List list = new ArrayList<ResultFromDB>();
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            Query hql = em.createQuery("SELECT NEW com.esp.security.dbAuthWithRole.ResultFromDB(user, role)" +
                    "FROM User user, Role role WHERE user.username =?1 AND role.role_id = user.user_id");   //insert into user (id, name) values (?, ?)
           hql.setParameter(1, username);
            list = (List<ResultFromDB>) hql.getResultList();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }

        if(list == null){
            throw new UsernameNotFoundException("username not found");
        }

        ResultFromDB resultFromDB = (ResultFromDB) list.get(0);
        /*System.out.println("resultFromDB: " + resultFromDB);

        System.out.println("resultFromDB.getUser(): " + resultFromDB.getUser());
        System.out.println("resultFromDB.getRole(): " + resultFromDB.getRole());

        user = resultFromDB.getUser();
        role = resultFromDB.getRole();

        var roleSet = new HashSet<Role>();

        roleSet.add(resultFromDB.getRole());

        System.out.println("roleSet: " + roleSet);
        System.out.println("role: " + role.getName());
        var uRole = user.getRoles();
        for (Role value : uRole) {
            System.out.println("value: " + value.getName());
        }*/

        return resultFromDB.getUser();
    }

    //SELECT user.user_id, user.username FROM User user INNER JOIN Role role ON user.user_id = role.role_id WHERE user.username =:username
    // SELECT u, u.roles as role FROM User u WHERE u.username = :username AND u.user_id = role.role_id"
    // SELECT user.user_id, role.role_id, role.name FROM User user, Role role WHERE user.username =:username AND role.role_id = user.user_id

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

    public List<User> getAllUsers(){
        List<User> user = new ArrayList<>();
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT user FROM User user", User.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            user = query.getResultList();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        if(user == null){
            System.out.println("User does not exist");
        }
        return user;
    }

    public User getUserWithUsername(String  username){
        User user = null;
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            TypedQuery<User> query = em.createQuery("SELECT user FROM User user WHERE user.username=?1", User.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            query.setParameter(1, username);
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
           // em.merge(role);
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
            TypedQuery<User> query = em.createQuery("SELECT user FROM User user WHERE user.user_id=?1", User.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
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
    public List<User> getUserList() {
        return getAllUsers();
    }

}
