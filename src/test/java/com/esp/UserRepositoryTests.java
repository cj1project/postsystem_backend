package com.esp;

import com.esp.models.Esp;
import com.esp.models.User;
import com.esp.user.UserConfig;
import com.esp.user.UserRepositoryCustom;
import com.esp.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**This calss is used to test the Repository layer*/
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //to show we will use real database
@Rollback(value = false)    //I dont want to rollback transaction, transaction are committed to the DB
public class UserRepositoryTests {

    @Autowired
    private UserRepositoryCustom userRepo;

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    private TestEntityManager testManager;

    /*public EntityManager getEmf(){
        return emf.createEntityManager();
    } */  // To perform assertion with the db = getEmf();

    @Test
    public void testCreateNewUser(){
        User user = new User();

        user.setEmail("testEmail@email.com");
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setFirstname("testFirstname");
        user.setLastname("testLastname");
        user.setPhonenumber(123654);

        var savedUser = userRepo.save(user);

        //var entityManager = getEmf(); // To perform assertion with the db = getEmf();

        var getSavedUser = testManager.find(User.class, user.getId());
        System.out.println("savedUser: " + savedUser);
        System.out.println("saved: " + getSavedUser);

       assert(savedUser.getEmail()).equals(getSavedUser.getEmail());
    }
}
