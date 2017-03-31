package com.droiddigger.mhlushan.aqib;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeNavDrawer();
    }

    private void initializeNavDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.home:
//                        startActivity(new Intent(this,H));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.hotel:
                        startActivity(new Intent(getApplicationContext(),Hotel.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.airlines:
                        startActivity(new Intent(getApplicationContext(),Airlines.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.special:
                        startActivity(new Intent(getApplicationContext(),SpecialPackagePrev.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.sight:
                        startActivity(new Intent(getApplicationContext(),SightSeeing.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.umrah:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        drawerLayout.closeDrawers();
                        break;

                }

                return true;
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.login){
            startActivity(new Intent(this,LoginActivity.class));
//            startActivity(new Intent(this,FeedbackActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
