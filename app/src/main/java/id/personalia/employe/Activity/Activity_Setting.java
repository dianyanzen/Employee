package id.personalia.employe.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.personalia.employe.R;

/**
 * Created by root on 14/11/17.
 */

public class Activity_Setting extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText txtURLEndPoint;
    EditText txtURLPMSEndPoint;
    Button btnSaveURLEndPoint;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Drawable navIcon = getResources().getDrawable(R.drawable.ic_chevron_left);
        navIcon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationIcon(navIcon);

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setTitle(getResources().getString(R.string.settings));
        txtURLEndPoint= (EditText)findViewById(R.id.txtURLEndPoint);
        txtURLPMSEndPoint= (EditText)findViewById(R.id.txtURLPMSEndPoint);
        btnSaveURLEndPoint=(Button)findViewById(R.id.btnSaveURLEndPoint);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sharedpreferences.contains("URLEndPoint") && sharedpreferences.contains("URLPMSEndPoint")){
            txtURLEndPoint.setText(sharedpreferences.getString("URLEndPoint", ""));
            txtURLPMSEndPoint.setText(sharedpreferences.getString("URLPMSEndPoint", ""));

        }

        btnSaveURLEndPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(txtURLEndPoint.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "URL Tidak Boleh Kosong..", Toast.LENGTH_SHORT).show();
                }else if(txtURLPMSEndPoint.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "URL Tidak Boleh Kosong..", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("URLEndPoint", txtURLEndPoint.getText().toString());
                    editor.putString("URLPMSEndPoint", txtURLPMSEndPoint.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(v.getContext(), Activity_Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(txtURLEndPoint.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "URL Tidak Boleh Kosong..", Toast.LENGTH_SHORT).show();
        }else if(txtURLPMSEndPoint.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "URL Tidak Boleh Kosong..", Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("URLEndPoint", txtURLEndPoint.getText().toString());
            editor.putString("URLPMSEndPoint", txtURLPMSEndPoint.getText().toString());
            editor.commit();
            Intent intent = new Intent(this, Activity_Login.class);
            startActivity(intent);
            finish();
        }
    }


}
