package id.personalia.employe.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import id.personalia.employe.Fragment.AttendanceFragment;
import id.personalia.employe.Fragment.ClaimFragment;
import id.personalia.employe.Fragment.DashboardFragment;
import id.personalia.employe.Fragment.OvertimeFragment;
import id.personalia.employe.Fragment.ReportFragment;
import id.personalia.employe.Fragment.TravelFragment;
import id.personalia.employe.Model.Employee;
import id.personalia.employe.Model.Project;
import id.personalia.employe.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Activity_Main  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Project> Projects;
    Project Project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        projectData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
             this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DashboardFragment fragment = new DashboardFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "DASHBOARD");
        fragmentTransaction.commit();
        FirebaseMessaging.getInstance().subscribeToTopic("Notif");

    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Activity_Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.user)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        Log.d(TAG, "Message Notification Body: " + messageBody);
        Toast.makeText(getApplicationContext(), "Message Notification Body"+messageBody, Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    @Override
    public void onBackPressed() {
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

            DashboardFragment fragment = new DashboardFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "DASHBOARD");
            fragmentTransaction.commit();

        } else {
            //super.onBackPressed();
            //if (frag)
            //DashboardFragment fragment = new DashboardFragment();
            //android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.fragment_container, fragment, "DASHBOARD");
            //fragmentTransaction.commit();
        }
        */
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            DashboardFragment fragment = new DashboardFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "DASHBOARD");
            fragmentTransaction.addToBackStack("DASHBOARD");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_travel) {

            TravelFragment fragment = new TravelFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack("TRAVEL").commit();
            //fragmentTransaction.commit();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "TRAVEL");
            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            // Add to backstack
            fragmentTransaction.addToBackStack("TRAVEL");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_claim) {
            ClaimFragment fragment = new ClaimFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "CLAIM");
            fragmentTransaction.addToBackStack("CLAIM");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_attendance) {
            AttendanceFragment fragment = new AttendanceFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "ATTENDANCE");
            fragmentTransaction.addToBackStack("ATTENDANCE");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_overtime) {
            OvertimeFragment fragment = new OvertimeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "OVERTIME");
            fragmentTransaction.addToBackStack("OVERTIME");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_report) {
            ReportFragment fragment = new ReportFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "REPORT");
            fragmentTransaction.addToBackStack("REPORT");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_logout) {
            Intent mainIntent = new Intent(Activity_Main.this, Activity_Login.class);
            startActivity(mainIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void projectData() {
        Projects = new ArrayList<Project>();

        for(int i=1;i<=20;i++){
            Project = new Project();
            Project.setProjectID(String.valueOf(i));
            Project.setProjectName("Project " + String.valueOf(i));
            Project.setProjectDate("29-09-2017");
            Project.setProjectStatus("On Progress");
            Projects.add(Project);
        }
    }
}
