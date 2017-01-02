package com.depromeet.stepbystep.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.depromeet.stepbystep.R;
import com.depromeet.stepbystep.adapter.CalendarAdapter;
import com.depromeet.stepbystep.common.Define;
import com.depromeet.stepbystep.fragment.TodoFragment;

import static com.depromeet.stepbystep.common.Define.CALENDAR_PAGE_MIDDLE;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화
        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 플로팅(+) 버튼
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // 캘린더 ViewPager
        final ViewPager vpCalendar = (ViewPager) findViewById(R.id.vpCalendar);
        final CalendarAdapter adCalender = new CalendarAdapter(fragmentManager);
        vpCalendar.setAdapter(adCalender);
        vpCalendar.setOffscreenPageLimit(CALENDAR_PAGE_MIDDLE);
        vpCalendar.setCurrentItem(CALENDAR_PAGE_MIDDLE, false);
        vpCalendar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        int position = vpCalendar.getCurrentItem();
                        if (position < CALENDAR_PAGE_MIDDLE) adCalender.skipMonth(-1);
                        if (position > CALENDAR_PAGE_MIDDLE) adCalender.skipMonth(+1);
                        vpCalendar.setCurrentItem(CALENDAR_PAGE_MIDDLE, false);
                }
            }
        });

        // 네비게이션(투두) 바
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // 투두 Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TodoFragment fragmentTodo = new TodoFragment();
        fragmentTransaction.add(R.id.nav_view, fragmentTodo);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
