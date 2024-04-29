package android.example.gomscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Stack;

public class AutoSchedule extends AppCompatActivity {
    private ArrayList<Skill> skillList;
    private ArrayList<Skill> backupSkillList;
    private ArrayList<String> skillstring;
    private RecyclerView recyclerView;
    private String passedString;
    private Stack<String> nameStack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_schedule);

        recyclerView = findViewById(R.id.skillsRecycler);
        skillList = new ArrayList<>();
        backupSkillList = new ArrayList<>();
        skillstring = new ArrayList<>();

        nameStack = new Stack<>();


        String storageName = Storage.getInstance().getGlobalString();
        setupStack();

        populateInternalMemory();
        setSkills();
        setAdapter();


        //map head buttons
        setHeads();
        //map subtract skill button
        ImageButton mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intentThatStartedThis = getIntent();
                if (intentThatStartedThis.hasExtra(Intent.EXTRA_TEXT)) {
                    passedString = intentThatStartedThis.getStringExtra(Intent.EXTRA_TEXT);
                }
                Context context = AutoSchedule.this;
                Class destinationActivity = SubSkill.class;

                Intent startMain = new Intent(context,destinationActivity);
                startMain.putExtra(Intent.EXTRA_TEXT,passedString); //pass date back to schedule
                startActivity(startMain);
            }


        });

        //map add skill button
        ImageButton mAddSkillButton = (ImageButton) findViewById(R.id.addSkillButton);
        mAddSkillButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intentThatStartedThis = getIntent();
                if (intentThatStartedThis.hasExtra(Intent.EXTRA_TEXT)) {
                    passedString = intentThatStartedThis.getStringExtra(Intent.EXTRA_TEXT);
                }
                Context context = AutoSchedule.this;
                Class destinationActivity = AddSkill.class;

                Intent startSkill = new Intent(context,destinationActivity);
                startSkill.putExtra(Intent.EXTRA_TEXT,passedString); //pass date to next activity
                startActivity(startSkill);
            }


        });


    }

    private void setupStack() {
        String[] names = {"aly", "andre", "annalise", "axel", "harper", "jake", "kevin", "shani"};
        for(String name:names){
            nameStack.push(name);
        }
    }

    private void setHeads() {
        int[] headIds = {R.id.AlyButton, R.id.AndreButton, R.id.annaliseButton, R.id.AxelButton, R.id.HarperButton, R.id.JacobButton, R.id.KevinButton, R.id.ShaniButton};
        for( int person : headIds) {
            HeadButton mHead = (HeadButton) findViewById(person);
            mHead.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String name = mHead.getPerson();
                    float alpha =  mHead.getAlpha();
       ;

                    alpha += 0.5;
                    if(alpha > 1){
                        alpha-=1;
                    }

                    mHead.setAlpha(alpha);


                    if(nameStack.search(name)==-1){
                        nameStack.push(name);

                    } else {
                        removeElement(nameStack,name);

                    }
                    for(String n: nameStack){
                        System.out.println(n);
                    }
                    setSkills();
                    setAdapter();



                }


            });
            }
    }

    public static void removeElement(Stack<String> stack, String value) {
        Stack<String> tempStack = new Stack<>();

        while (!stack.isEmpty()) {
            String element = stack.pop();
            if (!element.equals(value)) {
                tempStack.push(element);
            }
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }




    private void populateInternalMemory() {

        skillstring.clear();

        try {

            String fileName = Storage.getInstance().getGlobalString();;
            Context context = AutoSchedule.this;
            File file = new File(context.getFilesDir(), fileName);


            if(!file.exists()){


                System.out.println("new list created");
                InputStreamReader is = new InputStreamReader(getAssets().open("skills.csv"));
                BufferedReader reader = new BufferedReader(is);
                reader.readLine();
                String line;
                while ((line = reader.readLine()) != null) {

                    skillstring.add(line);
                }

                reader.close();

                OutputStreamWriter os = new OutputStreamWriter(openFileOutput(fileName, Context.MODE_PRIVATE));
                BufferedWriter writer = new BufferedWriter(os);
                for (String skill : skillstring) {

                    writer.write(skill + "\n");
                }
                writer.close();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    //set up adapter for recyclerview on autoschedule page
    private void setAdapter() {
        skillRA adapter = new skillRA(skillList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    //method to add dummy skills to the autoscheduler
    private void setSkills(){

    skillList.clear();
    backupSkillList.clear();

    try{


        //InputStreamReader is = new InputStreamReader(getAssets().open("skills.csv"));
        InputStreamReader is = new InputStreamReader(openFileInput(Storage.getInstance().getGlobalString()));

        BufferedReader reader = new BufferedReader(is);
        String line;
        while ((line = reader.readLine()) != null) {
            //System.out.println("line "+line);
            String[] tokens=line.split(",");
            if(tokens[9].equals("1")) { //present in current list, not master


                Skill skill = new Skill(
                        tokens[0].equals("1"),
                        tokens[1].equals("1"),
                        tokens[2].equals("1"),
                        tokens[3].equals("1"),
                        tokens[4].equals("1"),
                        tokens[5].equals("1"),
                        tokens[6].equals("1"),
                        tokens[7].equals("1"),
                        tokens[8]);

                boolean doable = true;
                for(String name : nameStack){
                    if(skill.isPresent(name)){
                        doable = false;
                    }
                }
                if(doable) {
                    skillList.add(skill);
                } else {
                    backupSkillList.add(skill);
                }

            }

        }

        if(skillList.isEmpty() && nameStack.size()==8){
       for(Skill skill: backupSkillList)
            skillList.add(skill);
        }


    } catch (IOException e) {
        e.printStackTrace();
    }






    }
}