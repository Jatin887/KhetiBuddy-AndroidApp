package com.agriculturalapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.agriculturalapp.R;

public class YouTubeVideo extends AppCompatActivity {
public final String link = "https://www.youtube.com/results?search_query=latest+agriculture+news+and+technology+in+india";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video);
/*
        Intent browser =new Intent(YouTubeVideo.this, WebActivity.class);
        browser.putExtra("link",link);
        startActivity(browser);*/

        WebView webView = (WebView) findViewById(R.id.youtube_videos);
        webView.loadUrl(link);
    }
}
