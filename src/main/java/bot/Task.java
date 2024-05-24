package bot;

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
        return "Task: " + task + ", Time: " + time + " hour(s)";
    }
   /* public void calculateTime(int time) {
        long time = currentDate.getTime() + (time*3600000);
        this.time = getTime() + time;
    }*/

}
