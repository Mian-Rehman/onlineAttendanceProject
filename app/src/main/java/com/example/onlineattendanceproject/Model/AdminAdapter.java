package com.example.onlineattendanceproject.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineattendanceproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminAdapter extends FirebaseRecyclerAdapter<TeacherAttendanceClass,AdminAdapter.MyViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminAdapter(@NonNull FirebaseRecyclerOptions<TeacherAttendanceClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull TeacherAttendanceClass model) {



        holder.tv_att_date.setText("Date: " + model.getDate());
        holder.tv_att_tech_id.setText("Teacher ID: " + model.getTeacherId());
        holder.tv_att_time.setText("Time: " + model.getCurrentTime());
        holder.tv_att.setText("Attendance: " + model.getTeacherPresent());


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_atten_list,parent,false);

        return new MyViewHolder(myview);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_att_date,tv_att_tech_id,tv_att_time,tv_att;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_att_date=itemView.findViewById(R.id.tv_att_date);
            tv_att_tech_id=itemView.findViewById(R.id.tv_att_tech_id);
            tv_att_time=itemView.findViewById(R.id.tv_att_time);
            tv_att=itemView.findViewById(R.id.tv_att);
        }
    }
}
