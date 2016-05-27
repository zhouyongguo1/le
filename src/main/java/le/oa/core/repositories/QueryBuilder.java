package le.oa.core.repositories;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class QueryBuilder {

    private String resultColumns;
    private String fromTable;
    private List<String> joins = new ArrayList<>();
    private List<String> whereExprs = new ArrayList<>();
    private Map<String, Object> params = new HashMap<>();
    private String orderBy;
    private String groupBy;


    public static QueryBuilder create() {
        return new QueryBuilder();
    }

    public QueryBuilder select(String resultColumns) {
        this.resultColumns = resultColumns;
        return this;
    }

    public QueryBuilder select() {
        return this;
    }

    public QueryBuilder from(String table) {
        this.fromTable = table;
        return this;
    }

    public QueryBuilder join(String expr) {
        joins.add(expr);
        return this;
    }

    public QueryBuilder where(String expr) {
        whereExprs.add(expr);
        return this;
    }

    public QueryBuilder and(String expr) {
        whereExprs.add(expr);
        return this;
    }

    public QueryBuilder groupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public QueryBuilder orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }


    public QueryBuilder param(String name, Object value) {
        this.addParam(name, value);
        return this;
    }

    public Query toNativeQuery(EntityManager em, Class resultClass) {
        String sql = toQueryString();
        Query query = em.createNativeQuery(sql, resultClass);
        params.forEach(query::setParameter);
        return query;
    }


    public Query toNativeQuery(EntityManager em, String resultSetMapping) {
        String sql = toQueryString();
        Query query = em.createNativeQuery(sql, resultSetMapping);
        params.forEach(query::setParameter);
        return query;
    }

    public Query toNativeCountQuery(EntityManager em) {
        String sql = toCountQueryString();
        Query query = em.createNativeQuery(sql);
        params.forEach(query::setParameter);
        return query;
    }

    public <T> TypedQuery<T> toQuery(EntityManager em, Class<T> resultClass) {
        String sql = toQueryString();
        TypedQuery<T> query = em.createQuery(sql, resultClass);
        params.forEach(query::setParameter);
        return query;
    }

    public Query toCountQuery(EntityManager em) {
        String sql = toCountQueryString();
        Query query = em.createQuery(sql);
        params.forEach(query::setParameter);
        return query;
    }

    private void addParam(String name, Object value) {
        params.put(name, value);
    }

    String toQueryString() {
        List<String> parts = new ArrayList<>();

        if (!Strings.isNullOrEmpty(resultColumns)) {
            parts.add("select");
            parts.add(resultColumns);
        }

        parts.add("from");
        parts.add(fromTable);
        if (joins.size() > 0) {
            parts.add(Joiner.on(" left join ").join(joins));
        }

        if (whereExprs.size() > 0) {
            parts.add("where");
            parts.add(Joiner.on(" and ").join(whereExprs));
        }

        if (!Strings.isNullOrEmpty(groupBy)) {
            parts.add("group by");
            parts.add(groupBy);
        }

        if (!Strings.isNullOrEmpty(orderBy)) {
            parts.add("order by");
            parts.add(orderBy);
        }

        return Joiner.on(" ").join(parts);
    }

    String toCountQueryString() {
        List<String> parts = new ArrayList<>();

        parts.add("select count(1)");
        parts.add("from");
        parts.add(fromTable);
        if (joins.size() > 0) {
            parts.add(Joiner.on(" left join ").join(joins));
        }
        if (whereExprs.size() > 0) {
            parts.add("where");
            parts.add(Joiner.on(" and ").join(whereExprs));
        }

        if (!Strings.isNullOrEmpty(groupBy)) {
            parts.add("group by");
            parts.add(groupBy);
        }

        return Joiner.on(" ").join(parts);
    }
}
