package com.example.csestudentmate;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.csestudentmate.Activity.FeedbackActivity.Feedback;
import com.example.csestudentmate.Activity.HelpActivity.Help;
import com.example.csestudentmate.Activity.ProfileActivity.Profile;
import com.example.csestudentmate.Class.Class;
import com.example.csestudentmate.Home.Home;
import com.example.csestudentmate.NUResult.NU_Result;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBarSeeting();
        navigationDrawerSettings();

    }

    private void fragmentSetup(int item_id) {

        // This transaction replace one fragment to your desire fragment.
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentFrameId, fragment);
        fragmentTransaction.commit();
        navigationView.setCheckedItem(item_id); // Making item selectable.

        // This condition is for close the navigation drawer if it is open.
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    private void navigationDrawerSettings() {

        // This Layout use to access navigation drawer.
        drawerLayout = findViewById(R.id.drawerLayoutId);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Setting Navigation Drawer icon.
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));

        navigationView = findViewById(R.id.navigationDrawerId);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new Home();
        fragmentSetup(R.id.navHomeId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int item_id = menuItem.getItemId();
        String actionBarTitle = null;
        Intent intent = null;

        fragment = null;

        if(item_id == R.id.navHomeId){
            actionBarTitle = "Home";
            fragment = new Home();
        }else if(item_id == R.id.navClassId){
            actionBarTitle = "Class";
            fragment = new Class();
        }else if(item_id == R.id.navNUResultId){
            actionBarTitle = "NU Result";
            fragment = new NU_Result();
        }else if(item_id == R.id.navProfileId){
            intent = new Intent(MainActivity.this, Profile.class);
        }else if(item_id == R.id.navFeedbackId){
            intent = new Intent(MainActivity.this, Feedback.class);
        }else if(item_id == R.id.navHelpId){
            intent = new Intent(MainActivity.this, Help.class);
        }else{
            fragment = new Home();
        }

        if(fragment != null){
            toolbar.setTitle(actionBarTitle);
            fragmentSetup(item_id);
        }

        if(intent != null){
            startActivity(intent);
        }
        return false;
    }

    public void actionBarSeeting(){
        //Custom toolbar creating and setting.
        toolbar = (Toolbar) findViewById(R.id.mainToolbarId);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
