package le.oa.work.service;

import com.google.inject.Inject;
import le.oa.core.models.User;
import le.oa.core.repositories.UserRepository;
import le.oa.work.models.WorkFlowTask;
import le.oa.work.models.WorkInstance;
import le.oa.work.models.WorkInstanceItem;
import le.oa.work.models.WorkInstanceItemStatus;
import le.oa.work.models.WorkInstanceStatus;
import le.oa.work.repositories.WorkInstanceItemRepository;
import le.oa.work.repositories.WorkInstanceRepository;

import java.util.List;
import java.util.Optional;

public class WorkInstanceService {

    private WorkInstanceRepository instanceRepository;
    private WorkInstanceItemRepository instanceItemRepository;
    private UserRepository userRepository;

    @Inject
    public WorkInstanceService(WorkInstanceRepository instanceRepository,
                               WorkInstanceItemRepository instanceItemRepository,
                               UserRepository userRepository) {
        this.instanceRepository = instanceRepository;
        this.instanceItemRepository = instanceItemRepository;
        this.userRepository = userRepository;

    }

    public Optional<WorkInstance> findInstance(Integer id) {
        return instanceRepository.findById(id);
    }

    public void startInstance(WorkInstance instance) {
        instance.setStatus(WorkInstanceStatus.DOING);
        instanceRepository.save(instance);

        List<WorkFlowTask> tasks = instance.getWorkFlow().getTasks();
        if (tasks.size() > 0) {
            WorkInstanceItem instanceItem = new WorkInstanceItem();
            instanceItem.setTaskId(tasks.get(0).getTaskId());
            instanceItem.setStatus(WorkInstanceItemStatus.START);
            instanceItem.setWorkInstance(instance);
            instanceItem.setSendUser(instance.getUser());
            instanceItem.setUser(instance.getUser());
            instanceItemRepository.save(instanceItem);
            WorkFlowTask task = instanceItem.getWorkInstance().getWorkFlow().nextTask(instanceItem.getTaskId());
            WorkInstanceItem nextItem = createrInstanceItem(task, instanceItem.getUser());
            if (nextItem != null) {
                nextItem.setWorkInstance(instanceItem.getWorkInstance());
                instanceItemRepository.save(nextItem);
            }
        }
    }

    public void completeTask(WorkInstanceItem instanceItem, String content) {
        instanceItem.setStatus(WorkInstanceItemStatus.FLOW);
        instanceItem.setContent(content);
        instanceItemRepository.save(instanceItem);
        WorkFlowTask task = instanceItem.getWorkInstance().getWorkFlow().nextTask(instanceItem.getTaskId());
        WorkInstanceItem nextItem = createrInstanceItem(task, instanceItem.getUser());
        if (nextItem != null) {
            nextItem.setWorkInstance(instanceItem.getWorkInstance());
            instanceItemRepository.save(nextItem);
        } else {
            WorkInstance instance = instanceItem.getWorkInstance();
            instance.setStatus(WorkInstanceStatus.FINISH);
            instanceRepository.save(instance);
        }
    }

    /**
     * 任务回退.
     */
    public void backTask(WorkInstanceItem instanceItem, String content) {
        instanceItem.setStatus(WorkInstanceItemStatus.BACK);
        instanceItem.setContent(content);
        instanceItemRepository.save(instanceItem);
        WorkFlowTask task = instanceItem.getWorkInstance().getWorkFlow().previouTask(instanceItem.getTaskId());
        WorkInstanceItem nextItem = createrInstanceItem(task, instanceItem.getUser());
        if (nextItem != null) {
            nextItem.setWorkInstance(instanceItem.getWorkInstance());
            instanceItemRepository.save(nextItem);
        }
    }

    /**
     * 流程停止.
     */
    public void stopTask(WorkInstanceItem instanceItem, String content) {
        instanceItem.setStatus(WorkInstanceItemStatus.STOP);
        instanceItem.setContent(content);
        instanceItemRepository.save(instanceItem);
        WorkInstance instance = instanceItem.getWorkInstance();
        instance.setStatus(WorkInstanceStatus.STOP);
        instanceRepository.save(instance);
    }

    private WorkInstanceItem createrInstanceItem(WorkFlowTask task, User sendUser) {
        if (task == null) {
            return null;
        }
        Optional<User> userOptional = userRepository.findUserById(task.getUserId());
        if (userOptional.isPresent()) {
            WorkInstanceItem item = new WorkInstanceItem();
            item.setTaskId(task.getTaskId());
            item.setStatus(WorkInstanceItemStatus.WAIT);
            item.setSendUser(sendUser);
            item.setUser(userOptional.get());
            return item;
        } else {
            return null;
        }

    }


}
