package le.oa.core.repositories;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> implements Repository<T> {


    private final Class<T> entityClass;

    protected Provider<EntityManager> emProvider;

    @SuppressWarnings("unchecked")
    public BaseRepository(Provider<EntityManager> emProvider) {
        this.emProvider = emProvider;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    @Override
    public T find(Object primaryKey) {
        return getEntityManager().find(entityClass, primaryKey);
    }

    public List<T> findAll() {
        return createQuery(String.format("from %s", entityClass.getName())).getResultList();
    }

    @Override
    public TypedQuery<T> createQuery(String qlString) {
        return getEntityManager().createQuery(qlString, entityClass);
    }

    public void disableTeamFilter() {
        ((Session) getEntityManager().getDelegate()).disableFilter("team");
    }

    public EntityManager getEntityManager() {
        return emProvider.get();
    }


    protected <S> Optional<S> first(List<S> results) {
        return CollectionUtils.isEmpty(results) ? Optional.empty() : Optional.of(results.get(0));
    }

//    protected <T> Result<T> paginate(Query countQuery, Query query, Map<String, Object> parameters, Pagination pagination) {
//
//        parameters.forEach(countQuery::setParameter);
//        Long count;
//        Object countResult = countQuery.getSingleResult();
//        if (countResult instanceof Integer) {
//            count = ((Integer) countResult).longValue();
//        } else if (countResult instanceof BigInteger) {
//            count = ((BigInteger) countResult).longValue();
//        } else {
//            count = (Long) countResult;
//        }
//        if (count == 0L) {
//            return Result.<T>create(0L, Collections.emptyList());
//        }
//
//        parameters.forEach(query::setParameter);
//
//        List list = query
//                .setFirstResult(pagination.getPageOffset())
//                .setMaxResults(pagination.getPageSize())
//                .getResultList();
//
//        return Result.<T>create(count, list);
//    }

}
