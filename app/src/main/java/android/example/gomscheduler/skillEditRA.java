package android.example.gomscheduler;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

//recycler view for auto scheduler
public class skillEditRA extends RecyclerView.Adapter<skillEditRA.mySViewHolder> {

    private ArrayList<SkillToggle> skillList;
    private ArrayList<String> masterSkill;
    private Context context;


    public skillEditRA(ArrayList<SkillToggle> skillList, Context context){
        this.skillList = skillList;
        this.context = context;
    }

    //map text view of skill name
    public class mySViewHolder extends RecyclerView.ViewHolder{
        private TextView skillName;


        public mySViewHolder(final View view){
            super(view);
            skillName = view.findViewById(R.id.schedule_skill);
        }
    }

    @NonNull
    @Override
    public skillEditRA.mySViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill,parent,false);
        mySViewHolder mySViewHolder = new mySViewHolder(itemView);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TextView tv = view.findViewById(R.id.schedule_skill);
                String name = tv.getText().toString();
                SkillToggle s = null;

                for(int i = 0;i<skillList.size();i++){
                    if (skillList.get(i).getName().equals(name)){
                        s = skillList.get(i); //get skill that was pressed on
                    }
                }

                if(s.isDisplayed()){
                    s.setDisplayed("0");
                    changeMasterRecord(name,"0");

                } else {
                    s.setDisplayed("1");
                    changeMasterRecord(name,"1");
                }

                notifyDataSetChanged(); //change recycler view with new colours

            }
        }        );

        return mySViewHolder;
    }

    private void changeMasterRecord(String name,String s) {
        try {

            InputStreamReader is = new InputStreamReader(context.openFileInput(Storage.getInstance().getGlobalString()));

            BufferedReader reader = new BufferedReader(is);
            String line;

            masterSkill = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                System.out.println("line " + line);
                String[] tokens = line.split(",");
                if(tokens[8].equals(name)){
                    System.out.println(tokens[0] + "," + tokens[1] + "," +tokens[2] + "," +tokens[3] + "," +tokens[4] + "," +tokens[5] + "," +tokens[6] + "," +tokens[7] + "," +tokens[8] + "," +s);
                    masterSkill.add(tokens[0] + "," + tokens[1] + "," +tokens[2] + "," +tokens[3] + "," +tokens[4] + "," +tokens[5] + "," +tokens[6] + "," +tokens[7] + "," +tokens[8] + "," +s);
                } else {
                    masterSkill.add(line);
                }
            }
            reader.close();

            OutputStreamWriter os = new OutputStreamWriter(context.openFileOutput(Storage.getInstance().getGlobalString(), Context.MODE_PRIVATE));
            BufferedWriter writer = new BufferedWriter(os);
            for (String skill : masterSkill) {

                writer.write(skill + "\n");  // Assuming Skill class has a method getName()
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull skillEditRA.mySViewHolder holder, int position) {
        String name = skillList.get(position).getName();
//        System
        boolean doable = skillList.get(position).isDisplayed();
        if(doable){
            holder.skillName.setBackgroundResource(R.color.orange); //colour the doable skills blue and the rest orange
        } else {
            holder.skillName.setBackgroundResource(R.color.grey);
        }
        holder.skillName.setText(name); //set name in RV

    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }
}
