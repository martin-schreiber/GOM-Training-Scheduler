package android.example.gomscheduler;

public class Storage {
    private static Storage instance;

    //Change this to change data for OG skills
    private String globalString = "skills_storage_master";


    private Storage() {
        // Private constructor to prevent instantiation
    }

    public static synchronized Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public String getGlobalString() {
        return globalString;
    }

    public void setGlobalString(String value) {
        globalString = value;
    }
}
