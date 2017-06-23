package com.taobao.firstbundle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yumodev on 17/6/21.
 */

public class FirstBundleTestActivity extends AppCompatActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWebViewContent();
    }

    private void setWebViewContent(){
        mWebView = new WebView(this);
        mWebView.loadUrl("https://m.baidu.com/");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return super.shouldOverrideUrlLoading(view, url);
                return false;
            }
        });
        setContentView(mWebView);
    }
}
