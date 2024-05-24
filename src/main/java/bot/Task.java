package bot;

public class Task {
    private String task;
    private int time;



    public Task(String message) {
        this.task = message;
    }

    public Task(int userTime) {
       this.time = userTime;
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
