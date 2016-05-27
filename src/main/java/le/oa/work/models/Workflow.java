package le.oa.work.models;

import java.util.ArrayList;
import java.util.List;

public class WorkFlow {
    private List<WorkFlowTask> tasks = new ArrayList<>();

    public List<WorkFlowTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<WorkFlowTask> tasks) {
        this.tasks = tasks;
    }


    public WorkFlowTask nextTask(String currentTaskId) {
        WorkFlowTask currentTask = null;
        for (WorkFlowTask task : tasks) {
            if (currentTask != null) {
                return task;
            }
            if (task.getTaskId().equals(currentTaskId)) {
                currentTask = task;
            }
        }
        return null;
    }

    public WorkFlowTask previouTask(String currentTaskId) {
        WorkFlowTask previouFlowTask = null;
        for (WorkFlowTask task : tasks) {
            previouFlowTask = task;
            if (task.getTaskId().equals(currentTaskId)) {
                return previouFlowTask;
            }
        }
        return null;
    }

}
