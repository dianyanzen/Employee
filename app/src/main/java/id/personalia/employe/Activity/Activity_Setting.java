package id.personalia.employe.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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
