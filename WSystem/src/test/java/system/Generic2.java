package system;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Generic2<T> {
    public List<T> selectAll(Class<T> c, EntityManager manager){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(c);
        Root<T> r = q.from(c);
        q.select(r);

        TypedQuery<T> query = manager.createQuery(q);
        List<T> results = query.getResultList();

        return results;
    }
}
