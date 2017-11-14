package id.personalia.employe.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import id.personalia.employe.R;

/**
 * Created by root on 10/11/17.
 */

public class Activity_Register extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sharedpreferences;
    public String ENDPOINT="https://personalia.id/";
    public String PMSENDPOINT="https://personalia.id/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ENDPOINT = sharedpreferences.getString("URLEndPoint", "");
        WebView myWebView = (WebView) findViewById(R.id.register);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.clearCache(true);
        myWebView.clearHistory();
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.loadUrl(ENDPOINT+"Register");
        Log.e("Url",ENDPOINT+"Register");
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

        setTitle(getResources().getString(R.string.title_activity_register));
    }
}
