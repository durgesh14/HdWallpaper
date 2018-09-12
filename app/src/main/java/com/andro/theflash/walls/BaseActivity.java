package com.andro.theflash.walls;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class BaseActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                final String appPackageName = getPackageName();

                switch (item.getItemId()) {

                   case R.id.nav_dashboard:
                        Intent dash = new Intent(getApplicationContext(), Category.class);
                        startActivity(dash);
                        finish();
                      drawerLayout.closeDrawers();
                       break;
                    case R.id.Abstract:
                        Intent abstractin = new Intent(getApplicationContext(), Category.class);
                        startActivity(abstractin);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Anime:
                        Intent anime = new Intent(getApplicationContext(), Anime_Activity.class);
                       // anime.putExtra("url","https://wall.alphacoders.com/api2.0/get.php?auth=321a42093f3753ca40879d07bcfbd714&method=category&id=3&page=10&info_level=2");
                        startActivity(anime);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Artistic:
                        Intent arti = new Intent(getApplicationContext(), Category.class);
                        startActivity(arti);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Comics:
                        Intent com = new Intent(getApplicationContext(), Category.class);
                        startActivity(com);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Celebrity:
                        Intent celeb = new Intent(getApplicationContext(), Celebrity_Activity.class);
                        startActivity(celeb);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Search:
                        Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(search);
                        finish();
                        drawerLayout.closeDrawers();
                        break;


//                    case R.id.nav_about_us:
//                        Intent anIntent = new Intent(getApplicationContext(), AboutUS.class);
//                        startActivity(anIntent);
////                        finish();
//                        drawerLayout.closeDrawers();
//                        break;

                }
                return false;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
