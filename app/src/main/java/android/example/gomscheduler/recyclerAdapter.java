package android.example.gomscheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


// recycler adapter for the schedule
public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.myViewHolder> {

    private ArrayList<ScheduleBlock> scheduleList;


    public recyclerAdapter(ArrayList<ScheduleBlock> scheduleList){
        this.scheduleList = scheduleList;
    }

    // map onto each text view for schedule
    public class myViewHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private TextView activity;

        public myViewHolder(final View view){
            super(view);
            time = view.findViewById(R.id.schedule_time);
            activity = view.findViewById(R.id.schedule_activity);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_items,parent,false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.myViewHolder holder, int position) {
        String time = scheduleList.get(position).getTime();
        String activity = scheduleList.get(position).getActivity();

        holder.time.setText(time);
        holder.activity.setText(activity);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
