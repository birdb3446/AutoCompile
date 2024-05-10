package com.example.autocompiletextview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView browser;
    AutoCompleteTextView suggestedURL;
    ArrayAdapter<String> adapter; // Added String type to ArrayAdapter
    Button submit, clear;
    String[] urls = {"google.com", "yahoo.com", "facebook.com", "youtube.com"};

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browser = findViewById(R.id.WebView);
        suggestedURL = findViewById(R.id.txtURL);
        submit = findViewById(R.id.btnURL);
        clear = findViewById(R.id.btnClear);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, urls); // Added generic type
        suggestedURL.setThreshold(2);
        suggestedURL.setAdapter(adapter);

        initializeWebView();
        loadWebURL();
    }

    public void initializeWebView() {
        browser.getSettings().setLoadsImagesAutomatically(true);
        // enabled java script
        browser.getSettings().setJavaScriptEnabled(true);
        // Android webview launches browser when calling loadurl
        browser.setWebViewClient(new WebViewClient());
        // enabled Scrollbar
        browser.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY); // Changed to WebView.SCROLLBARS_INSIDE_OVERLAY
    }

    public void loadWebURL() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = suggestedURL.getText().toString();

                if (!url.startsWith("www.") && !url.startsWith("http://")) {
                    url = "www." + url;
                }
                if (!url.startsWith("http://")) {
                    url = "http://" + url;
                }
                browser.loadUrl(url);
            }
        });
    }
    public void clearUrl(View view) {
        Log.d("MainActivity", "Clear button clicked"); // Log a message indicating that the button was clicked
        suggestedURL.setText(""); // Clear the text in AutoCompleteTextView

        // Clear the URL in the WebView
        browser.loadUrl("about:blank");
    }
}
