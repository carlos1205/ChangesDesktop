package com.change.server.repository;

import com.change.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO {
    private static UserDAO instance;
//    private EntityManagerFactory emf;

    private List<User> users;

    public static UserDAO getInstance(){
        if(null == instance) instance = new UserDAO();

        return instance;
    }

    private UserDAO(){
        users = new ArrayList<>();
    }

//    private EntityManager getEntityManager(){
//        if(null == emf)
//            emf = Persistence.createEntityManagerFactory("changes");
//        return emf.createEntityManager();
//    }

    public boolean cadastrar(User user){
        if(userExist(user))
            return false;

        UUID uuid = UUID.randomUUID();
        user.setId(uuid.toString());
        createUser(user);

        return true;
    }

    private boolean userExist(User newUser){
//        EntityManager em = getEntityManager();
//        int res = 0;
//        try{
//            em.getTransaction().begin();
//            Query query = em.createQuery("select COUNT(*) from users email = "+ user.getEmail(), User.class);
//            res = query.getFirstResult();
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }finally {
//            em.close();
//            emf.close();
//        }
//        return (res > 0);
        long res = users.stream().filter(user -> user.getEmail().equals(newUser.getEmail())).count();
        return res > 0;
    }

    private void createUser(User user){
//        EntityManager em = getEntityManager();
//        try{
//            em.getTransaction().begin();
//            em.persist(user);
//            em.getTransaction().commit();
//        }catch (Exception e){
//            em.getTransaction().rollback();
//            System.out.println(e.getMessage());
//        }finally {
//            em.close();
//            emf.close();
//        }
        users.add(user);
    }

    public User getUserWithEmail(String email){
        return users.stream().filter(user -> email.equals(user.getEmail())).findFirst().orElse(null);
    }
}
