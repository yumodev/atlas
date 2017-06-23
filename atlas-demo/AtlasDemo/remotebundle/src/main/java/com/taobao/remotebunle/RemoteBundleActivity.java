package com.taobao.remotebunle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.xwalk.core.XWalkInitializer;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class RemoteBundleActivity extends AppCompatActivity {

    private final String LOG_TAG = "RemoteBundleActivity";
    private WebView mWebView;
    private XWalkView mXWalkView;

    protected void onXWalkReady() {
        //解决XWalkView 引起的黑屏问题
        SurfaceView surfaceView = new SurfaceView(this);
        setContentView(surfaceView, new ViewGroup.LayoutParams(0,0));
        XWalkInitializer mXWalkInitializer = new XWalkInitializer(new XWalkInitializer.XWalkInitListener() {
            @Override
            public void onXWalkInitStarted() {
                Log.i(LOG_TAG, "InitStarted");
            }

            @Override
            public void onXWalkInitCancelled() {
                Log.i(LOG_TAG, "initCancelled");
            }

            @Override
            public void onXWalkInitFailed() {
                Log.i(LOG_TAG, "initFailed");
            }

            @Override
            public void onXWalkInitCompleted() {
                Log.i(LOG_TAG, "setXWalkView");
                setWalkViewContent();
            }
        }, this);
        mXWalkInitializer.initAsync();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onXWalkReady();
        //setContentView(R.layout.activity_remote_bundle);
        //setWebViewContent();
        //setWalkViewContent();
    }

    private void setWebViewContent(){
        Log.i(LOG_TAG, "setWebViewContent1");
        mWebView = new WebView(this);
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return super.shouldOverrideUrlLoading(view, url);
                return false;
            }
        });
        setContentView(mWebView);
    }

    private void setWalkViewContent(){
        Log.i(LOG_TAG, "setXWalkView");
        mXWalkView = new XWalkView(this);
        mXWalkView.load("https://m.baidu.com/", null);
        mXWalkView.setResourceClient(new XWalkResourceClient(mXWalkView){
            @Override
            public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
               // return super.shouldOverrideUrlLoading(view, url);
                return false;
            }
        });
        setContentView(mXWalkView);
    }
}
