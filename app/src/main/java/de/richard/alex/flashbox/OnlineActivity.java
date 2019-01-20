package de.richard.alex.flashbox;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.ClientCertRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OnlineActivity extends AppCompatActivity {

    private WebView webview;
    private String adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        adress = sharedPreferences.getString("adress","http://www.google.com/");

        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if (url.endsWith("/download")) {
                    //TODO no idea what to do here
                }
            }
        });
        webview.loadUrl(adress);
    }
}
