package com.taobao.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.android.ActivityGroupDelegate;

/**
 * Created by yumodev on 17/6/21.
 */

public class TestActivity extends AppCompatActivity{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //switchToActivity("home","com.taobao.firstbundle.FirstBundleActivity");
                    //Intent intent = new Intent();
                    //intent.setClassName(TestActivity.this, "com.taobao.firstbundle.FirstBundleActivity");
                    //startActivity(intent);

                    addTestFragment();
                    return true;
                case R.id.navigation_dashboard:
                    //switchToActivity("second","com.taobao.secondbundle.SecondBundleActivity");
                    return true;
                case R.id.navigation_notifications:
//                    Intent intent3 = new Intent();
//                    intent3.setClassName(getBaseContext(),"com.taobao.firstBundle.FirstBundleActivity");
//                    mActivityDelegate.execStartChildActivityInternal(mActivityGroupContainer,"third",intent3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.e("ddddd","dsfsfsf");

        ((BottomNavigationView)findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //mActivityDelegate = new ActivityGroupDelegate(this,savedInstanceState);
        //mActivityGroupContainer = (ViewGroup) findViewById(R.id.content);
        //switchToActivity("home","com.taobao.firstbundle.FirstBundleActivity");
    }

    public void addTestFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new TestFragment(), "test1");
        transaction.commit();
    }

    public static class TestFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.test_fragment, null, false);
        }
    }
}
