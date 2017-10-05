package id.personalia.employe.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import id.personalia.employe.Helper.DbUserData;
import id.personalia.employe.Model.UserData;
import id.personalia.employe.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Activity_Splash extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    DbUserData db = new DbUserData(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                // Todo: Check login session, kalau belum login maka direct ke Login Activity class
                //Intent mainIntent = new Intent(Activity_Splash.this, Activity_Login.class);
                //startActivity(mainIntent);
                //finish();
                ceklogin();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    public void ceklogin(){
       // try{
            List<UserData> UserDataList = db.getAllUserDataList();
            int number = UserDataList.size();
            //String employee_id = userData.getEMPLOYEE_ID();
            //Log.e("Data",employee_id);
            if(number > 0 ){
                Intent mainIntent = new Intent(Activity_Splash.this, Activity_Main.class);
                mainIntent.putExtra("FragmentNM", "Dashboard");
                startActivity(mainIntent);
                finish();
            }else{
                Intent mainIntent = new Intent(Activity_Splash.this, Activity_Login.class);
                startActivity(mainIntent);
                finish();
            }
        //} catch(Exception e){
          //  db.deleteAllUserData();
           // Intent mainIntent = new Intent(Activity_Splash.this, Activity_Login.class);
           // startActivity(mainIntent);
           // finish();
        //}



    }


}