package android.example.gomscheduler;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//recycler view for auto scheduler
public class skillRA extends RecyclerView.Adapter<skillRA.mySViewHolder> {

    public ArrayList<Skill> skillList = new ArrayList<>();
    private ArrayList<Skill> masterList;
    public boolean skillPressed = false;
    public String skillPressedName;

    public skillRA(ArrayList<Skill> skillList){
        this.masterList = skillList;
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
    public skillRA.mySViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill,parent,false);
        mySViewHolder mySViewHolder = new mySViewHolder(itemView);



        skillList.addAll(masterList);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean doable = true;

                TextView tv = view.findViewById(R.id.schedule_skill);
                String name = tv.getText().toString();


                if(name.equals(skillPressedName)){
                    skillPressed = false;
                    skillPressedName="";
                    skillList.clear();
                    skillList.addAll(masterList);


                } else{
                    skillPressed = true;
                    skillPressedName=name;
                    //System.out.println(name);
                    Skill s2 = null;


                    for(int i = 0;i<masterList.size();i++){
                        if (masterList.get(i).getName().equals(name)){
                            s2 = masterList.get(i); //get skill that was pressed on
                        }
                    }
                    s2.setDoable(true); //change pressed on skill to doable
                    skillList.clear();
                    skillList.add(s2);
                    for(int i = 0;i<masterList.size();i++) {
                        Skill s1 =masterList.get(i);
                        s1.setDoable(true);
                        if(!(!s1.canDoTogether(s2) && s1 != s2)){
                            if(!skillList.contains(s1)) {
                                skillList.add(s1); // go through other skills and decide if they are doable or not
                            }
                        }

                    }
                }




                notifyDataSetChanged(); //change recycler view with new colours

            }
        });

        return mySViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull skillRA.mySViewHolder holder, int position) {
        String name = skillList.get(position).getName();
        boolean doable = skillList.get(position).isDoable();
        holder.skillName.setBackgroundResource(R.color.orange);
        holder.skillName.setText(name); //set name in RV
        holder.skillName.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);

        if(skillPressed){
            holder.skillName.setBackgroundResource(R.color.blue_light);
        }
        if(name.equals(skillPressedName)){
            holder.skillName.setBackgroundResource(R.color.blue);
            holder.skillName.setTypeface(null, Typeface.BOLD);
        }

//        if(doable){
//            holder.skillName.setVisibility(View.VISIBLE);
//            holder.skillName.setBackgroundResource(R.color.blue); //colour the doable skills blue and the rest orange
//            holder.skillName.setText(name); //set name in RV
//        } else if (skillPressed){
//            holder.skillName.setVisibility(View.GONE);
//        }
//
//        else {
//            holder.skillName.setVisibility(View.VISIBLE);
//            holder.skillName.setBackgroundResource(R.color.orange);
//            holder.skillName.setText(name); //set name in RV
//
//
//        }


    }

    @Override
    public int getItemCount() {
        if(skillList.isEmpty()){
            return masterList.size();
        }
        return skillList.size();
    }
}
