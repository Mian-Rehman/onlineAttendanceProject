package com.example.onlineattendanceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TeacherDashboard extends AppCompatActivity {

    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

    Fragment temp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        navMenu=findViewById(R.id.navMenu);
        drayerLayout=findViewById(R.id.drawerlayout);

        //    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new AdminTeacherProfileFrag()).commit();
        temp=new TeacherMyProfileFrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();

        toggle=new ActionBarDrawerToggle(this,drayerLayout,toolbar,R.string.app_name,R.string.app_name);
        drayerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {


                    case R.id.nav_teacher_profile:
                        temp=new TeacherMyProfileFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("My Profile");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_teacher_attendence:
                        Intent attendenceintent = new
                                Intent(TeacherDashboard.this,TeacherAttendanceSCR.class);
                        startActivity(attendenceintent);
                        toolbar.setTitle("My Attendence");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_teacher_stAttendence:

                        Intent stIntent= new Intent(TeacherDashboard.this,TeacherTakeAttendanceStudent.class);
                        startActivity(stIntent);

                        toolbar.setTitle("Add Student Attendence");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_teacher_ShowAttendence:

                        Intent showIntent=new Intent(TeacherDashboard.this,ShowTeacherAttendance.class);
                        startActivity(showIntent);
                        toolbar.setTitle("Show Attendance");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;




                    case R.id.nav_logout:
                        Toast.makeText(TeacherDashboard.this, "Logout", Toast.LENGTH_SHORT).show();
                        Intent logoutIntent=new Intent(TeacherDashboard.this,ConfirmScreen.class);
                        startActivity(logoutIntent);
                        finish();
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;



                }
                return false;
            }
        });

    }
}