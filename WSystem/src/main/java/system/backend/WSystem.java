package system.backend;

import java.util.List;

public class WSystem {
    private static WSystem wsystem;
    private Admin admin;
    private List<Agent> agents;
    private List<Owner> owners;

    public static WSystem getInstance(){
        if(wsystem == null)
            wsystem = new WSystem();
        return wsystem;
    }

    public void createAdmin(String firstName, String lastName,
                             String username, String password){
        Admin admin = new Admin(firstName, lastName, username, password);
        AdminDAO.getInstance().deleteIfExists();
        AdminDAO.getInstance().save(admin);
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public List<Owner> getOwners() {
        return owners;
    }
}
