package id.personalia.employe.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class Activity_Login  extends AppCompatActivity {
    TextView regis;
    AppCompatButton login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (AppCompatButton)findViewById(R.id.email_sign_in_button);
        regis  = (TextView)findViewById(R.id.register);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://personalia.id/register"));
                startActivity(browserIntent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Activity_Login.this, Activity_Main.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

}
