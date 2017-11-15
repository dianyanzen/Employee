package id.personalia.employe.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import id.personalia.employe.R;

/**
 * Created by root on 14/11/17.
 */

public class Activity_Help extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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

        setTitle(getResources().getString(R.string.help));
    }

}
