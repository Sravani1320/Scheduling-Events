package com.eventsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class TeacherHomeActivity extends AppCompatActivity {

    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    Button btnaddevent,btnviewevents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        navigationView();
        btnaddevent=(Button)findViewById(R.id.btnaddevent);
        btnaddevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TeacherHomeActivity.this,TeacherAddEventActivity.class);
                startActivity(i);
            }
        });

        btnviewevents=(Button)findViewById(R.id.btnviewevents);
        btnviewevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TeacherHomeActivity.this,ViewEventsActivity.class);
                startActivity(i);
            }
        });
    }
    private void navigationView(){
        dl = (DrawerLayout)findViewById(R.id.draw);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        Intent home=new Intent(getApplicationContext(), TeacherHomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.profile:
                        Intent profile=new Intent(getApplicationContext(), TeacherProfileActivity.class);
                        startActivity(profile);
                        break;

                    case R.id.upcommingevents:
                        Intent up=new Intent(getApplicationContext(), UpComingEventsActivity.class);
                        startActivity(up);
                        break;

                    case R.id.myevents:
                        Intent myevents=new Intent(getApplicationContext(), TeacherMyEventsActivity.class);
                        startActivity(myevents);
                        break;

                    case R.id.logout:
                        Intent logout=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(logout);
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

}