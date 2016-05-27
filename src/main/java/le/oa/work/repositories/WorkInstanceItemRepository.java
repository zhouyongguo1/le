package le.oa.work.repositories;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import le.oa.core.models.search.ResultData;
import le.oa.core.repositories.BaseRepository;
import le.oa.core.repositories.QueryBuilder;
import le.oa.work.models.WorkInstanceItem;
import le.oa.work.models.search.WorkInstanceSearch;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkInstanceItemRepository extends BaseRepository<WorkInstanceItem> {

    @Inject
    public WorkInstanceItemRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<WorkInstanceItem> findById(Integer id) {
        List<WorkInstanceItem> item = emProvider.get().createQuery("from WorkInstanceItem a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(item);
    }

    public List<WorkInstanceItem> findByInstanceId(Integer instanceId) {
        List<WorkInstanceItem> items = emProvider.get().createQuery("from WorkInstanceItem a " +
                "where a.workInstance.id=:instanceId")
                .setParameter("instanceId", instanceId)
                .getResultList();
        return items;
    }

    public ResultData<WorkInstanceItem> findBySearch(WorkInstanceSearch search) {
        QueryBuilder query = QueryBuilder.create()
                .select(" a ")
                .from("WorkInstanceItem a ");
        if (!Strings.isNullOrEmpty(search.getTitle())) {
            query.where("a.workInstance.title like :title");
            query.param("title", "%" + search.getTitle() + "%");
        }

        if (search.getIsArchived() != null) {
            query.where("a.workInstance.isArchived=:isArchived");
            query.param("isArchived", search.getIsArchived());
        }
        query.orderBy("a.id");
        return paginate(query, search, WorkInstanceItem.class);
    }
}
