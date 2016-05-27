package le.oa.core.repositories;

import le.oa.core.models.search.ResultData;
import le.oa.core.models.search.Search;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    protected <T> ResultData<T> paginate(Query countQuery, Query query, Map<String, Object> parameters, Search search) {

        parameters.forEach(countQuery::setParameter);
        Long count;
        Object countResult = countQuery.getSingleResult();
        if (countResult instanceof Integer) {
            count = ((Integer) countResult).longValue();
        } else if (countResult instanceof BigInteger) {
            count = ((BigInteger) countResult).longValue();
        } else {
            count = (Long) countResult;
        }
        if (count == 0L) {
            return ResultData.<T>create(0L, Collections.emptyList());
        }

        parameters.forEach(query::setParameter);
        int pageCount = (int) Math.ceil(count / (double) search.getPageSize());
        if (search.getPageIndex() > pageCount) {
            search.setPageIndex(pageCount);
        }
        List list = query
                .setFirstResult(search.getPageOffset())
                .setMaxResults(search.getPageSize())
                .getResultList();

        return ResultData.<T>create(count, list);
    }

    protected <T> ResultData<T> paginate(QueryBuilder query, Search search, Class<T> resultClass) {
        EntityManager entityManager = getEntityManager();
        return paginate(query.toCountQuery(entityManager),
                query.toQuery(entityManager, resultClass),
                new HashMap<>(), search);
    }
}
