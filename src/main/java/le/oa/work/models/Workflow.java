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
}
