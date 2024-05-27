package bot.model;

public class Task {
    private String task;
    private int time;


    public Task(String message, int userTime) {
        this.task = message;
        this.time = userTime;
    }

    public String getTask() {
        return task;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Task: " + task;
    }

}
