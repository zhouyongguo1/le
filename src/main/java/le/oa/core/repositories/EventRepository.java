package le.oa.core.repositories;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import le.oa.core.models.Event;
import le.oa.core.models.Role;
import le.oa.core.models.search.EventSearch;
import le.oa.core.models.search.ResultData;

import javax.inject.Provider;
import javax.persistence.EntityManager;

public class EventRepository extends BaseRepository<Role> {

    @Inject
    public EventRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public ResultData<Event> findBySearch(EventSearch search) {
        QueryBuilder query = QueryBuilder.create()
                .select(" a ")
                .from("Event a ");
        if (search.getUserId() != null) {
            query.where("a.user.id = :userId");
            query.param("userId", search.getUserId());
        }
        if (!Strings.isNullOrEmpty(search.getOwnerType())) {
            query.where("a.ownerType = :ownerType");
            query.param("ownerType", search.getOwnerType());

            if (search.getOwnerId() != null) {
                query.where("a.ownerId = :ownerId");
                query.param("ownerId", search.getOwnerId());
            }
        }
        if (search.getStateDate() != null) {
            query.where("a.eventTime >= stateDate");
            query.param("stateDate", search.getStateDate());
        }

        if (search.getEndDate() != null) {
            query.where("a.eventTime <= endDate");
            query.param("endDate", search.getEndDate());
        }
        query.orderBy("a.eventTime desc ");
        return paginate(query, search, Event.class);
    }


}
