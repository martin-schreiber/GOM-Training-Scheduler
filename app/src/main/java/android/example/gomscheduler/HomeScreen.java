package android.example.gomscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Boolean accomSmall = true;
    Boolean travelSmall = true;
    List<Day> dayList = new ArrayList<Day>();
    Button mAccomButton;
    Button mTravelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //set up dates for spinner
        List<String> spinnerArray =  new ArrayList<String>();
        dayList.add(new Day("Today"));
        spinnerArray.add("Today");
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String date;
        for( int i = 0; i<6; i++){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            date = dateFormat.format(calendar.getTime());
            spinnerArray.add(date);
            dayList.add(new Day(date));
        }
        //maps dates onto spinner with adapter
        Spinner mSpinner = (Spinner) findViewById(R.id.date_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        //show / hide extra detail about accom when button is pushed
        mAccomButton = (Button) findViewById(R.id.main_accom_button);
        mAccomButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
            String text = mSpinner.getSelectedItem().toString();
                Day day = getDay(dayList,text);
            if(accomSmall){
                mAccomButton.setText(day.accom);
                accomSmall = false;
            } else {
                mAccomButton.setText(day.shortAccom);
                accomSmall = true;
            }

            }
        });
        //show / hide extra detail about travel when button is pushed
        mTravelButton = (Button) findViewById(R.id.main_travel_button);
        mTravelButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {

                String text = mSpinner.getSelectedItem().toString();
                Day day = getDay(dayList,text);
                if(travelSmall){
                    mTravelButton.setText(day.travel);
                    travelSmall = false;
                } else {
                    mTravelButton.setText(day.shortTravel);
                    travelSmall = true;
                }
            }
        });
        //go to schedule page
        Button mScheduleButton = (Button) findViewById(R.id.schedule_button);
        mScheduleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String date = mSpinner.getSelectedItem().toString();
                Context context = HomeScreen.this;
                Class destinationActivity = Schedule.class;

                Intent startSchedule = new Intent(context,destinationActivity);
                startSchedule.putExtra(Intent.EXTRA_TEXT,date); //pass date for daily schedule
                startActivity(startSchedule);
            }
        });












    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Day day = getDay(dayList,text);

        if(accomSmall){

            mAccomButton.setText(day.shortAccom);

        } else {
            mAccomButton.setText(day.accom);

        }
        if(travelSmall){
            mTravelButton.setText(day.shortTravel);

        } else {
            mTravelButton.setText(day.travel);

        }

        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public Day getDay(List<Day> DL, String text){
        Day day = null;
        for (int j = 0; j<7; j++){
            if(DL.get(j).name.equals(text)){
                day = DL.get(j);
            }
        }
        return day;
    }
}