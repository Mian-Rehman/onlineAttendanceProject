package com.example.onlineattendanceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlineattendanceproject.Model.TeacherAttendanceClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TeacherAttendanceCompleteSCR extends AppCompatActivity {


    Button btn_com_dash_tech,btn_com_logout_tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_complete_s_c_r);

        btn_com_dash_tech=findViewById(R.id.btn_com_dash_tech);
        btn_com_logout_tech=findViewById(R.id.btn_com_logout_tech);


        btn_com_logout_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(TeacherAttendanceCompleteSCR.this,ConfirmScreen.class);
                startActivity(backIntent);
                finish();
            }
        });


        btn_com_dash_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashIntent=new Intent(TeacherAttendanceCompleteSCR.this,TeacherDashboard.class);
                startActivity(dashIntent);
                finish();
            }
        });



        uploadAttendaceToDatabase();

    }

    private String getTimeWithAmPm()
    {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdate()
    {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdateOnly()
    {
        return new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }

    private String getdateWithMonth()
    {
        return new SimpleDateFormat("LLL", Locale.getDefault()).format(new Date());
    }

    private void uploadAttendaceToDatabase()
    {


        SharedPreferences sp=getSharedPreferences("TEACHER_DATA",MODE_PRIVATE);
        String teacherId=sp.getString("teacherID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";
        String date=getdateWithMonth()+" "+ getCurrentdateOnly();


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("attendanceTeacher");

        DatabaseReference monRef =FirebaseDatabase.getInstance().getReference("monthTeacherAttendance");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
                String name=sp.getString("currentClass","");

                TeacherAttendanceClass classData=new TeacherAttendanceClass(currentMonth,currentTime,teacherId,currentDate,studentPresent,date);
                String key_value=reference.child(currentMonth).getKey();
                reference.child("date").child(date).child(teacherId).setValue(classData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}