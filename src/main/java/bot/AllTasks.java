package bot;

import java.util.ArrayList;
import java.util.List;

public class AllTasks {
    List<Task> tasks = new ArrayList<Task>(5);

    public AllTasks() {
    }

    public AllTasks(String task) {
        Task newTask = new Task(task, 15);
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
