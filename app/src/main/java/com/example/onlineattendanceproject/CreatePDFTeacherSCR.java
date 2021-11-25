package com.example.onlineattendanceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineattendanceproject.Model.TeacherAttendanceClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePDFTeacherSCR extends AppCompatActivity {


    Button btn_create;
    EditText ed_id;

    String cus_id;
    String cus_date;
    String cus_month;
    String cus_pres;

    private int STORAGE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_p_d_f_teacher_s_c_r);

        btn_create=findViewById(R.id.btn_create);
        ed_id=findViewById(R.id.ed_id);

       if (ContextCompat.checkSelfPermission(CreatePDFTeacherSCR.this,
               Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

           Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
       }
       else
       {
           requestStoragePermission();
       }



        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=ed_id.getText().toString().trim();

                if (TextUtils.isEmpty(id))
                {
                    ed_id.setText("");
                    ed_id.setError("invalid Id");
                    ed_id.requestFocus();
                    return;
                }
                else
                {
                    SharedPreferences preferences=getSharedPreferences("TEACHER_ATT_PDF",MODE_PRIVATE);
                    String date=preferences.getString("VALUE","");

                    DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("attendanceTeacher");
                    DatabaseReference zone1Ref = zonesRef.child("date");
                    DatabaseReference zon1 = zone1Ref.child(date);
                    DatabaseReference zon2=zon1.child(id);

                    zon2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            TeacherAttendanceClass data = snapshot.getValue(TeacherAttendanceClass.class);

                            cus_id=data.getTeacherId();
                            cus_date=data.getDate();
                            cus_month=data.getCurrentMonth();
                            cus_pres=data.getTeacherPresent();

                            Toast.makeText(CreatePDFTeacherSCR.this, ""+data.getTeacherId(), Toast.LENGTH_SHORT).show();
                            createpdf();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }




            }
        });

    }

    private void requestStoragePermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Need Permission")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CreatePDFTeacherSCR.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    STORAGE_PERMISSION_CODE);


                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();



        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == STORAGE_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void createpdf()
    {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint forLinePaint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,350,1).create();
        PdfDocument.Page mypage = myPdfDocument.startPage(myPageInfo);

        Canvas canvas = mypage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));

        canvas.drawText("Online Attendance System",20,20,paint);
        paint.setTextSize(8.5f);

        forLinePaint.setStyle(Paint.Style.STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forLinePaint.setStrokeWidth(2);
        canvas.drawLine(20,65,230,65,forLinePaint);



        canvas.drawText("Teacher Attendance ID:" +cus_id,20,80,paint);
        canvas.drawLine(20,90,230,90,forLinePaint);

        canvas.drawText("Date:" + cus_date,20,135,paint);
        canvas.drawText("Month" + cus_month,20,150,paint);
        canvas.drawText("Attendance" + cus_pres,20,170,paint);

        canvas.drawLine(20,210,230,210,forLinePaint);


        myPdfDocument.finishPage(mypage);

        String myfilePath = Environment.getExternalStorageDirectory().getPath() + "/MyPDFFile.pdf";

        File file = new File(myfilePath);

        try
        {
            myPdfDocument.writeTo(new FileOutputStream(file));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        myPdfDocument.close();
        Toast.makeText(this, "Pdf Saved in Storage", Toast.LENGTH_SHORT).show();
    }




}