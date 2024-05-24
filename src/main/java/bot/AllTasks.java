package bot;

import java.util.ArrayList;
import java.util.List;

public class AllTasks {
    List<Task> tasks = new ArrayList<Task>();


    public void createAllTasks(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }


    @Override
    public String toString() {
        StringBuilder tasksString = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            tasksString.append(i + 1).append(". ").append(task.toString()).append("\n");
        }
        return tasksString.toString();
    }
}
