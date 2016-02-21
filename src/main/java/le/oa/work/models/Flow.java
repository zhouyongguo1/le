package le.oa.work.models;

import java.util.ArrayList;
import java.util.List;

public class Flow {
    private List<FlowTask> tasks = new ArrayList<>();

    public List<FlowTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<FlowTask> tasks) {
        this.tasks = tasks;
    }
}
