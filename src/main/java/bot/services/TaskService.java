package bot.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bot.model.Task;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    List<Task> tasks = new ArrayList<>();

    public void createAllTasks(Task task) {
        tasks.add(task);
        saveTasksToFile();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        saveTasksToFile();
    }

    private void saveTasksToFile() {
        try (FileWriter writer = new FileWriter("tasks.json")) {
            Gson gson = new Gson();
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasksFromFile() {
        // TODO: load from ressources folder

        try (FileReader reader = new FileReader("tasks.json")) {
            Gson gson = new Gson();
            Type taskListType = new TypeToken<ArrayList<Task>>() {
            }.getType();
            tasks = gson.fromJson(reader, taskListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        StringBuilder tasksString = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            tasksString.append("\uD83D\uDCDD").append(i + 1).append(". ").append(task.toString()).append("\n");
        }
        return tasksString.toString();
    }

}
