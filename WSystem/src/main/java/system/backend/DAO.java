package system.backend;

import javax.persistence.NoResultException;

public interface DAO {
    //AdminDAO methods
    void save(Admin admin);
    Admin findByDetails(String username, String password) throws NoResultException;
    public void deleteIfExists();

    //AgentDAO methods
    //..

    //OwnerDAO methods
    //..
}
