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

import id.personalia.employe.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Activity_Splash extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                // Todo: Check login session, kalau belum login maka direct ke Login Activity class
                Intent mainIntent = new Intent(Activity_Splash.this, Activity_Main.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}