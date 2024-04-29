package android.example.gomscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity  {

    private ArrayList<ScheduleBlock> scheduleList;
    private RecyclerView recyclerView;
    private TextView mDate;
    private String passedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        recyclerView = findViewById(R.id.ScheduleRecyclerView);
        mDate = findViewById(R.id.date);
        scheduleList = new ArrayList<>();


        setDate();
        setSchedule();
        setAdapter();


        //map buttot to auto scheduler page
        Button mAutoSched = (Button) findViewById(R.id.auto_sched);
        mAutoSched.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context context = Schedule.this;
                Class destinationActivity = AutoSchedule.class;

                Intent startMain = new Intent(context,destinationActivity);
                startMain.putExtra(Intent.EXTRA_TEXT,passedString);
                startActivity(startMain);
            }
        });

        //map back button
        ImageButton mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context context = Schedule.this;
                Class destinationActivity = HomeScreen.class;

                Intent startMain = new Intent(context,destinationActivity);
                startActivity(startMain);
            }
        });
    }

    //set date at top of page
    private void setDate() {
        Intent intentThatStartedThis = getIntent();
        if (intentThatStartedThis.hasExtra(Intent.EXTRA_TEXT)){
            passedString = intentThatStartedThis.getStringExtra(Intent.EXTRA_TEXT);
            mDate.setText(passedString);
        }
    }
    // recycler adapter
    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(scheduleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void setSchedule() {
        //dummy data for scheduler
        for(int i = 10; i<24; i++){
            String timeString = String.valueOf(i) + ":00";
            scheduleList.add(new ScheduleBlock(timeString, "activity " + String.valueOf(i-9)));
        }



    }


}