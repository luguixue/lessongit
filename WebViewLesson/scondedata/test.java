<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.qianfeng.zhouyi.drawlayouttest.MainActivity">
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <android.support.design.widget.NavigationView
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity = "left"
        android:id="@+id/nvLeft"
        app:headerLayout = "@layout/layout_navhead"
        app:menu =  "@menu/mymenu"
        />




</android.support.v4.widget.DrawerLayout>



package com.qianfeng.zhouyi.drawlayouttest;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;


    /**
     * 给抽屉设置开关
     */
    protected void setToggle(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        toggle.syncState();//初始化
        drawerLayout.setDrawerListener(toggle);//设置监听开关
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nvLeft);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //设置toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("抽屉");
        setSupportActionBar(toolbar);

        setToggle();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id){

                    case R.id.item1:
                        Toast.makeText(MainActivity.this,"item1",Toast.LENGTH_SHORT).show();

                }

                item.setChecked(true);
                return false;
            }
        });
    }
}

