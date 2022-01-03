package com.example.onlineattendanceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlineattendanceproject.Model.StudentAttendaceClass;
import com.example.onlineattendanceproject.Model.TeacherAttendanceClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StudentAttendanceCompleteSCR extends AppCompatActivity {

    Button btn_com_dash,btn_com_logout;

    String parentcounter;
    int count;

    ValueEventListener valueEventListener;

    DatabaseReference reference;


    @Override
    protected void onDestroy() {
        super.onDestroy();

        reference.removeEventListener(valueEventListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_complete_s_c_r);

        btn_com_dash=findViewById(R.id.btn_com_dash);
        btn_com_logout=findViewById(R.id.btn_com_logout);


        btn_com_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadCustomAttendance();

                DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

                counter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        parentcounter = snapshot.child("StudentValue").getValue(String.class);

                        count = Integer.parseInt(parentcounter);
                        count=count+1;
                        parentcounter = String.valueOf(count);
                        counter.child("StudentValue").setValue(parentcounter);

                        Intent backIntent=new Intent(StudentAttendanceCompleteSCR.this,ConfirmScreen.class);
                        startActivity(backIntent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        btn_com_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadCustomAttendance();

                DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

                counter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        parentcounter = snapshot.child("StudentValue").getValue(String.class);

                        count = Integer.parseInt(parentcounter);
                        count=count+1;
                        parentcounter = String.valueOf(count);
                        counter.child("StudentValue").setValue(parentcounter);

                        Intent dashIntent= new Intent(StudentAttendanceCompleteSCR.this,StudentDashboard.class);
                        startActivity(dashIntent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });





        uploadAttendaceToDatabase();

        CounterCall();


    }

    private void uploadCustomAttendance()
    {
        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);

        String studentId=sp.getString("studentID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";
        String date=getdateWithMonth()+" "+ getCurrentdateOnly();



        reference= FirebaseDatabase.getInstance().getReference("CustomAttendanceStudent");



        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
                String name=sp.getString("currentClass","");

                StudentAttendaceClass classData=new StudentAttendaceClass(currentMonth,currentTime,studentId,name,currentDate,studentPresent,date);
                String   key = reference.push().getKey();
                reference.child(studentId).child(currentMonth).child(parentcounter).setValue(classData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(valueEventListener);
    }

    private void CounterCall()
    {
        DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

        counter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parentcounter = snapshot.child("StudentValue").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);

        String studentId=sp.getString("studentID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";
        String date=getdateWithMonth()+" "+ getCurrentdateOnly();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("attendanceStudent");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
                String name=sp.getString("currentClass","");

                StudentAttendaceClass classData=new StudentAttendaceClass(currentMonth,currentTime,studentId,name,currentDate,studentPresent,date);
                String key_value=reference.child(currentMonth).getKey();
                reference.child("date").child(date).child(studentId).setValue(classData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(valueEventListener);




    }
}