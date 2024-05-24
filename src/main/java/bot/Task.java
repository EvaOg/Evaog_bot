package bot;

public class Task {
    private String task;
    private int time;


    public Task(String message, int userTime) {
        this.task = message;
        this.time = userTime;
    }

    @Override
    public String toString() {
        return "Task: " + task + ", Time: " + time + " hour(s)";
    }
   /* public void calculateTime(int time) {
        this.time = getTime() + time;
    }*/

}
