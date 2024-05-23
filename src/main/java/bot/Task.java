package bot;

public class Task {
    private String task;
    private int time;

    public Task(String message, int userTime) {
        this.task = message;
        calculateTime(userTime);
    }

    public String getTask() {
        return task;
    }

    public int getTime() {
        return time;
    }

    public void calculateTime(int time) {
        this.time = getTime() + time;
    }

}
