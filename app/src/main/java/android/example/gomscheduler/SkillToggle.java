package android.example.gomscheduler;
//skill class containing name, and people involved
public class SkillToggle {
    private String name;
    private String displayed;



    public SkillToggle(String name, String displayed) {

        this.name = name;
        this.displayed = displayed;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisplayed(){
        return this.displayed.equals("1");
    }

    public void setDisplayed(String displayed){this.displayed = displayed; }

}
