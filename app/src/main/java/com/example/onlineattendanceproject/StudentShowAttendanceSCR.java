package com.example.onlineattendanceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlineattendanceproject.Model.AttendanceAdapter;
import com.example.onlineattendanceproject.Model.StudentAttendaceClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentShowAttendanceSCR extends AppCompatActivity {


    private RecyclerView recyclerView_showAtt;
    private AttendanceAdapter mAttendanceAdapter;
    private List<StudentAttendaceClass> mDataList;

    FirebaseDatabase database;
    DatabaseReference myRef;

    ImageView st_att_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_attendance_s_c_r);

        st_att_back=findViewById(R.id.st_att_back);


        st_att_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent= new Intent(StudentShowAttendanceSCR.this,StudentDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });




        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
        String studentId= sp.getString("studentID","").toString();

        // Toast.makeText(StudentShowAttendanceSCR.this, ""+studentId, Toast.LENGTH_SHORT).show();


        String date=getdateWithMonth()+" "+ getCurrentdateOnly();


        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("attendanceStudent");
        DatabaseReference zone1Ref = zonesRef.child("date");
        DatabaseReference zon1 = zone1Ref.child(date);
        DatabaseReference zone1NameRef = zon1.child("studentId");
        DatabaseReference zon = zone1NameRef.child("studentPresent");


    /*
        zon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Toast.makeText(StudentShowAttendanceSCR.this, ""+snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

     */


        recyclerView_showAtt=findViewById(R.id.recyclerView_showAtt);
        //   database = FirebaseDatabase.getInstance();
        //   myRef = database.getReference("attendanceStudent");
        recyclerView_showAtt.setHasFixedSize(true);
        recyclerView_showAtt.setLayoutManager(new LinearLayoutManager(this));


        mDataList=new ArrayList<>();
        mAttendanceAdapter=new AttendanceAdapter(this,mDataList);
        recyclerView_showAtt.setAdapter(mAttendanceAdapter);



        zon1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists())
                {
                    String checkStID=snapshot.child(studentId).child("studentId").getValue(String.class);
                    if (checkStID.equals(studentId))

                    {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {

                            StudentAttendaceClass datalcass = dataSnapshot.getValue(StudentAttendaceClass.class);

                            if (datalcass.getStudentId().equals(studentId))
                            {
                                mDataList.add(datalcass);
                            }

                        }
                        mAttendanceAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(StudentShowAttendanceSCR.this, "No today attendance", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(StudentShowAttendanceSCR.this, "Please Take Attendance", Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String getdateWithMonth()
    {
        return new SimpleDateFormat("LLL", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdateOnly()
    {
        return new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }

}