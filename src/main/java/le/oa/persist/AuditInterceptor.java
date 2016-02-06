package le.oa.persist;

import com.google.inject.Inject;
import le.oa.core.CurrentTeamProvider;
import le.oa.core.CurrentUserProvider;
import le.oa.core.models.base.DateModel;
import le.oa.core.models.base.Iteam;
import le.oa.core.models.User;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditInterceptor extends EmptyInterceptor {

    @Inject
    private CurrentUserProvider currentUserProvider;
    @Inject
    private CurrentTeamProvider currentTeamProvider;
    
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
                                Object[] previousState, String[] propertyNames, Type[] types) {

        if (entity instanceof DateModel) {
            auditUpdate(currentState, propertyNames);
            return true;
        }

        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        boolean modified = false;
        if (entity instanceof DateModel) {
            auditSave(state, propertyNames);
            modified = true;
        }
        if (entity instanceof Iteam) {
            setTeam(state, propertyNames);
            modified = true;
        }
        return modified;
    }

    private void setTeam(Object[] state, String[] propertyNames) {
        if (!currentUserProvider.isPresent()) {
            return;
        }
        int teamId = currentTeamProvider.get().getId();
        for (int i = 0; i < propertyNames.length; i++) {
            String property = propertyNames[i];
            switch (property) {
                case "teamId":
                    state[i] = getTeamId(state[i], teamId);
                    break;
                default:
                    break;
            }
        }
    }

    private Object getTeamId(Object oldValue, int teamId) {
        return (Integer) oldValue > 0 ? oldValue : teamId;
    }


    private void auditSave(Object[] state, String[] propertyNames) {
        User currentUser = null;
        if (currentUserProvider.isPresent()) {
            currentUser = currentUserProvider.get();
        }

        for (int i = 0; i < propertyNames.length; i++) {
            String property = propertyNames[i];
            switch (property) {
                case "createdBy":
                    state[i] = currentUser;
                    break;
                case "createdAt":
                    state[i] = LocalDateTime.now();
                    break;
                case "updatedBy":
                    state[i] = currentUser;
                    break;
                case "updatedAt":
                    state[i] = LocalDateTime.now();
                    break;
                default:
                    break;
            }
        }
    }

    private void auditUpdate(Object[] currentState, String[] propertyNames) {
        User currentUser = null;
        if (currentUserProvider.isPresent()) {
            currentUser = currentUserProvider.get();
        }

        for (int i = 0; i < propertyNames.length; i++) {
            String property = propertyNames[i];
            switch (property) {
                case "updatedBy":
                    currentState[i] = currentUser;
                    break;
                case "updatedAt":
                    currentState[i] =LocalDateTime.now();
                    break;
                default:
                    break;
            }
        }
    }
}
