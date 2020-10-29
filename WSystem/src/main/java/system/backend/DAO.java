package system.backend;

import javax.persistence.NoResultException;

public interface DAO {

    //AdminDAO methods
    void save(Admin admin);
    Admin findByCredentials(String username, String password) throws NoResultException;
    void deleteIfExists();
    void setAutoIncrement();

    //AgentDAO methods
    //..

    //OwnerDAO methods
    //..
}
