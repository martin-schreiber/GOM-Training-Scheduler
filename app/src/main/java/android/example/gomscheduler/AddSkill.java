package android.example.gomscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AddSkill extends AppCompatActivity {

    private String passedString;
    private ArrayList<Skill> skillList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_skill);


        //map back button
        Button mAddSkillButton = (Button) findViewById(R.id.addButton);
        mAddSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeSkill();


                Intent intentThatStartedThis = getIntent();
                if (intentThatStartedThis.hasExtra(Intent.EXTRA_TEXT)) {
                    passedString = intentThatStartedThis.getStringExtra(Intent.EXTRA_TEXT);
                }
                Context context = AddSkill.this;
                Class destinationActivity = AutoSchedule.class;

                Intent startSkill = new Intent(context, destinationActivity);
                startSkill.putExtra(Intent.EXTRA_TEXT, passedString); //pass date back to schedule
                startActivity(startSkill);
            }


        });

    }

    private void makeSkill() {

        EditText textInput = findViewById(R.id.editSkillName);

        if (!textInput.getText().toString().isEmpty()) {


            try {

                String newSkill = "";
                int[] switchIds = {R.id.switch1, R.id.switch2, R.id.switch3, R.id.switch4, R.id.switch5, R.id.switch6, R.id.switch7, R.id.switch8};

                for (int switchId : switchIds) {
                    Switch switchButton = findViewById(switchId);
                    if (switchButton.isChecked()) {
                        newSkill += 1;
                    }
                    newSkill += ',';
                }


                newSkill += textInput.getText().toString();
                System.out.println(newSkill);

                OutputStreamWriter os = new OutputStreamWriter(openFileOutput(Storage.getInstance().getGlobalString(), Context.MODE_APPEND));
                BufferedWriter writer = new BufferedWriter(os);

                writer.write(newSkill + ",1\n");
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}



























