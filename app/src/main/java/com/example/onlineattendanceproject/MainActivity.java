package com.example.onlineattendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    TextView text_splash;
    Lottie mylottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text_splash=findViewById(R.id.text_splash);




        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Animation myanimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        text_splash.setAnimation(myanimation);


        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(MainActivity.this,ConfirmScreen.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        thread.start();



/*

        try {
            //Get IP Address uisng WifiManager Manger
            String myip="192.168";
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
            //Toast.makeText(MainActivity.this, ip, Toast.LENGTH_SHORT).show();
            //text_splash.setText(ip);
            String substr=ip.substring(0,7);
            if(myip.matches(substr))
            {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            Intent intent = new Intent(MainActivity.this,ConfirmScreen.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
                thread.start();
            }
            else
            {
                //android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
                sleep(1000);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Exit Application?");
                alertDialogBuilder
                        .setMessage("Plz Connect the Specific Switch")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                        System.exit(1);
                                    }
                                });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }





        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       */


    }
}