package com.change.server.repository;

import com.change.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO {
    private static UserDAO instance;
//    private EntityManagerFactory emf;

    private final List<User> users;

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

    public boolean editar(User user){
        User newUser = getUserWithId(user.getId());
        if(null == newUser)
            return false;

        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        return true;
    }

    public boolean deletar(String id){
        User user = getUserWithId(id);
        if(null == user)
            return false;

        return users.remove(user);
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

    public boolean sendEmailRestorePass(String email){
        User user = getUserWithEmail(email);
        return null != user;
    }

    public User getUserWithEmail(String email){
        return users.stream().filter(user -> email.equals(user.getEmail())).findFirst().orElse(null);
    }

    public User getUserWithId(String id){
        return users.stream().filter(user -> id.equals(user.getId())).findFirst().orElse(null);
    }
}
