package com.altitude.careerintelligence.mcc;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.altitude.careerintelligence.AboutFragment;
import com.altitude.careerintelligence.R;

public class CareerNews extends Fragment {

    private ProgressBar progressBar;


        public static CareerNews newInstance() {
            // Required empty public constructor
            CareerNews fragment = new CareerNews();
            return fragment;
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_career_news, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        WebView webView = (WebView) view.findViewById(R.id.webView);

        webView.setWebViewClient(new MyBrowser());


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://nationalcareersservice.direct.gov.uk/job-profiles");


        return view;
    }


        private class MyBrowser extends WebViewClient {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        }
}
