package le.oa.core.repositories;

import javax.persistence.TypedQuery;

public interface Repository<T> {

    void save(T entity);

    void delete(T entity);

    TypedQuery<T> createQuery(String qlString);

}
