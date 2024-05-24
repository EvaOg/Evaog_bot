package bot;

import java.util.ArrayList;
import java.util.List;

public class AllTasks {
    List<Task> tasks = new ArrayList<Task>();

    public AllTasks() {
    }


    public void createAllTasks(String task) {
        Task newTask = new Task(task);
        tasks.add(newTask);
    }


    @Override
    public String toString() {

        StringBuilder tasksString = new StringBuilder();
        for (Task task : tasks) {
            tasksString.append(task.toString());
        }
        return tasksString.toString();
    }
}
