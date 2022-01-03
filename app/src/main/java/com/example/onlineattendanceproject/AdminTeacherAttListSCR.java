package com.example.onlineattendanceproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineattendanceproject.Model.AdminAdapter;
import com.example.onlineattendanceproject.Model.TeacherAttendanceClass;
import com.example.onlineattendanceproject.Model.UserAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminTeacherAttListSCR extends AppCompatActivity {


    RecyclerView recyclerView_showAllAtt;
    private AdminAdapter mAttendanceAdapter;
    private List<TeacherAttendanceClass> mDataList;

    FirebaseDatabase database;
    DatabaseReference myRef;



    Button btn_search,btn_pdf;
    EditText ed_date;

    int pageWidth = 1200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_att_list_s_c_r);

        btn_search=findViewById(R.id.btn_search);
        ed_date=findViewById(R.id.ed_date);
        btn_pdf=findViewById(R.id.btn_pdf);

        mDataList=new ArrayList<>();
        recyclerView_showAllAtt=findViewById(R.id.recyclerView_showAllAtt);
        recyclerView_showAllAtt.setLayoutManager(new LinearLayoutManager(this));
        mAttendanceAdapter=new AdminAdapter(this,mDataList);
        recyclerView_showAllAtt.setAdapter(mAttendanceAdapter);

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("attendanceTeacher");
        DatabaseReference zone1Ref = zonesRef.child("date");

      //  DatabaseReference zone1NameRef = zon1.child("teacherId");
     //   DatabaseReference zon = zone1NameRef.child("teacherPresent");

        btn_pdf.setVisibility(View.INVISIBLE);

      /*  btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent netOntent=new Intent(AdminTeacherAttListSCR.this,CreatePDFTeacherSCR.class);
               startActivity(netOntent);



            }
        });

       */


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date=ed_date.getText().toString().trim();
                SharedPreferences preferences=getSharedPreferences("TEACHER_ATT_PDF",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("VALUE",date);
                editor.apply();

                if (TextUtils.isEmpty(date))
                {
                    ed_date.setError("Enter Date");
                    ed_date.requestFocus();
                    ed_date.setText("");
                    return;
                }
                else
                {
                    btn_pdf.setVisibility(View.VISIBLE);
                    DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("attendanceTeacher");
                    DatabaseReference zone1Ref = zonesRef.child("date");
                    DatabaseReference zon1 = zone1Ref.child(date);


                    Query myquery = zon1;

                    myquery.addChildEventListener(new ChildEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.R)
                        @Override

                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            if (snapshot.exists())
                            {
                                TeacherAttendanceClass data = snapshot.getValue(TeacherAttendanceClass.class);


                                mDataList.add(data);
                                mAttendanceAdapter.notifyDataSetChanged();


                            }

                            else
                            {
                                ed_date.setError("invalid date");
                                Toast.makeText(AdminTeacherAttListSCR.this, "No date Found!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });







    }




}