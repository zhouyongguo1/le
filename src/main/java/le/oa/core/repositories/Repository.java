package le.oa.core.repositories;

import javax.persistence.TypedQuery;

public interface Repository<T> {

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    T find(Object primaryKey);

    TypedQuery<T> createQuery(String qlString);

}
