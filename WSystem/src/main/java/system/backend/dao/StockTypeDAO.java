package system.backend.dao;

import system.backend.others.StockType;

import javax.persistence.Query;

public class StockTypeDAO extends AbstractDAO<StockType>{
    public void setAutoIncrement(){
        //String text1 = "DELETE FROM Admin";
        String text2 = "ALTER TABLE StockType AUTO_INCREMENT = 1";
        //Query query1 = manager.createQuery(text1);
        Query query2 = manager.createNativeQuery(text2);
        manager.getTransaction().begin();
        //query1.executeUpdate();
        query2.executeUpdate();
        manager.getTransaction().commit();
    }
}
