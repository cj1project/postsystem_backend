package com.esp.user;

import com.esp.models.*;
import com.esp.classRepOfJoinedTables.UsersRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
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
        List list = new ArrayList<UsersRoles>();
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            Query hql = em.createQuery("SELECT NEW com.esp.classRepOfJoinedTables.UsersRoles(user, role)" +
                    "FROM User user, Role role WHERE user.username =?1 AND role.role_id = user.user_id");   //insert into user (id, name) values (?, ?)
           hql.setParameter(1, username);
            list = (List<UsersRoles>) hql.getResultList();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }

        if(list == null){
            throw new UsernameNotFoundException("username not found");
        }

        UsersRoles resultFromDB = (UsersRoles) list.get(0);

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
        System.out.println("User exist: " + user);
        return user;
    }

    public User userData(String  userId){
        User user1 = getUser(userId);
        return user1;
    }

    public String registerNewUser(User user) {
        System.out.println("user: " + user);
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            em.persist(user);
           // em.persist(esp);
            //em.persist(imageEntity);
            em.flush();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }

        return user.getId();
    }


    public User getUser(String id){
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

    public String getImageEntity_idFromUserTable() {
        String imageEntity_id = UserController.LOGGED_USER_ID;
        var loggedUserClass = new LoggedUserImpl(this);
        String user_id = UserController.LOGGED_USER_ID;
        System.out.println("user_id: " + user_id);

        try{
            EntityManager em = getEmf();
            em.getTransaction().begin(); //SELECT user FROM User user JOIN ImageEntity img ON user.user_id = img.imageEntity_id"
            //em.find(ImageEntity.class, id);
            /*Query query = em.createQuery("SELECT NEW com.esp.classRepOfJoinedTables.Users_Imageentity(user, imageEntity)" +
                    "FROM User user, ImageEntity imageEntity WHERE user.imageEntity_id = imageEntity.user_id");*/ //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            Query query = em.createQuery("SELECT user.imageEntityId FROM User user WHERE user.user_id =?1");
            query.setParameter(1, user_id);
            imageEntity_id = (String) query.getSingleResult();
            //user = (User) query.getSingleResult();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }

        if(imageEntity_id == null){
            System.out.println("imageEntity_id does not exist");
        }

        System.out.println("imageEntity_id: " + imageEntity_id);

        assert imageEntity_id != null;
        System.out.println("user: " + imageEntity_id);
        return imageEntity_id;
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
