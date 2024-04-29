package android.example.gomscheduler;

public class Storage {
    private static Storage instance;
    private String globalString = "skills_storage_3";

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
