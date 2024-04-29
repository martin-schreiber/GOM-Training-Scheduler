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

public class SubSkill extends AppCompatActivity {
    private ArrayList<SkillToggle> skillList;
    private ArrayList<String> skillstring;
    private RecyclerView recyclerView;
    private String passedString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_skill);

        recyclerView = findViewById(R.id.skillsRecycler);
        skillList = new ArrayList<>();
        skillstring = new ArrayList<>();


        setSkills();
        setAdapter();

        //map back button
        ImageButton mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intentThatStartedThis = getIntent();
                if (intentThatStartedThis.hasExtra(Intent.EXTRA_TEXT)) {
                    passedString = intentThatStartedThis.getStringExtra(Intent.EXTRA_TEXT);
                }
                Context context = SubSkill.this;
                Class destinationActivity = AutoSchedule.class;

                Intent startMain = new Intent(context,destinationActivity);
                startMain.putExtra(Intent.EXTRA_TEXT,passedString); //pass date back to schedule
                startActivity(startMain);
            }


        });




    }



    //set up adapter for recyclerview on autoschedule page
    private void setAdapter() {
        skillEditRA adapter = new skillEditRA(skillList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    //method to add  skills to the RV
    private void setSkills(){

        try{
            //InputStreamReader is = new InputStreamReader(getAssets().open("skills.csv"));
            InputStreamReader is = new InputStreamReader(openFileInput(Storage.getInstance().getGlobalString()));

            BufferedReader reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {

                String[] tokens=line.split(",");

                    skillList.add(new SkillToggle(
                            tokens[8],
                            tokens[9]));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }






    }
}