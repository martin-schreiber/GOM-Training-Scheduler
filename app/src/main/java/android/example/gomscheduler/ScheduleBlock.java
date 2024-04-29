package android.example.gomscheduler;
//used to add time - activity pairs to the schedule recycler view
public class ScheduleBlock {
    private String time;
    private String activity;

    public ScheduleBlock(String time, String activity) {
        this.time = time;
        this.activity = activity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
