package android.example.gomscheduler;

import java.lang.reflect.Field;

//skill class containing name, and people involved
public class Skill {
    private String name;


    public boolean aly;
    public boolean andre;
    public boolean annalise;
    public boolean axel;
    public boolean harper;
    public boolean jake;
    public boolean kevin;
    public boolean shani;

    private boolean doable = false;

    public Skill(
            boolean aly,
            boolean andre,
            boolean annalise,
            boolean axel,
            boolean harper,
            boolean jake,
            boolean kevin,
            boolean shani,
            String name) {

        this.name = name;
        System.out.println(name + " added");

        this.aly=aly;
        this.andre=andre;
        this.annalise=annalise;
        this.axel=axel;
        this.harper=harper;
        this.jake=jake;
        this.kevin=kevin;
        this.shani=shani;

    }

    public String getName() {
        return name;
    }

    public boolean isDoable() {
        return doable;
    }

    public void setDoable(boolean doable) {
        this.doable = doable;
    }

    public void setName(String name) {
        this.name = name;
    }

    //checks if 2 skills can be done at the same time
    public boolean canDoTogether(Skill s2) {
        if (
                aly && s2.aly ||
                andre && s2.andre ||
                annalise && s2.annalise ||
                axel && s2.axel ||
                harper && s2.harper ||
                jake && s2.jake ||
                kevin && s2.kevin ||
                shani && s2.shani
        ){
            return false;
        } else {
            return true;
        }
    }

    public boolean isPresent(String person) {
        try {

            Field field = getClass().getField(person);
            return field.getBoolean(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


}
