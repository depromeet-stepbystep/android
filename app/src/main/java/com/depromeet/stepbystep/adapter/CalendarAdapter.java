package com.depromeet.stepbystep.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.depromeet.stepbystep.fragment.CalendarFragment;

import java.util.Calendar;
import java.util.HashMap;

import static com.depromeet.stepbystep.common.Define.CALENDAR_PAGE_MIDDLE;
import static com.depromeet.stepbystep.common.Define.CALENDAR_PAGE_SIZE;
import static com.depromeet.stepbystep.common.Define.KEY_MONTH;
import static com.depromeet.stepbystep.common.Define.KEY_YEAR;

public class CalendarAdapter extends FragmentPagerAdapter {
    private SparseArray<CalendarFragment> fragments;
    private int thisYear;
    private int thisMonth;

    public CalendarAdapter(FragmentManager fm) {
        super(fm);
        fragments = new SparseArray<>();
        thisYear = Calendar.getInstance().get(Calendar.YEAR);
        thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 페이지 개수 지정
     */
    @Override
    public int getCount() {
        return CALENDAR_PAGE_SIZE;
    }

    /**
     * 프래그먼트 불러오기
     */
    @Override
    public CalendarFragment getItem(int position) {
        CalendarFragment fragment = fragments.get(position);
        if (fragment == null) {
            HashMap<String, Integer> date = fetchDate(position);
            int year = date.get(KEY_YEAR);
            int month = date.get(KEY_MONTH);

            Bundle args = new Bundle();
            args.putInt(KEY_YEAR, year);
            args.putInt(KEY_MONTH, month);

            fragment = new CalendarFragment();
            fragment.setArguments(args);
            fragments.put(position, fragment);
        }
        return fragment;
    }

    /**
     * 선택된 날짜를 기준으로 {count}만큼 날짜 이동
     */
    public void skipMonth(int count) {
        thisMonth += count;

        // 프래그먼트 갱신
        for (int position = 0; position < getCount(); position ++) {
            HashMap<String, Integer> date = fetchDate(position);
            int year = date.get(KEY_YEAR);
            int month = date.get(KEY_MONTH);
            getItem(position).initDate(year, month);
        }
    }

    /**
     * {position}에 해당하는 페이지의 날짜 반환
     * @param position 0:전월, 1:금월, 2:익월
     */
    private HashMap<String, Integer> fetchDate(int position) {
        int initYear = thisYear;
        int initMonth = thisMonth + position - CALENDAR_PAGE_MIDDLE;

        while (initMonth > 12) {
            initYear += 1;
            initMonth -= 12;
        }
        while (initMonth < 1) {
            initYear -= 1;
            initMonth += 12;
        }

        HashMap<String, Integer> initDate = new HashMap<>();
        initDate.put(KEY_YEAR, initYear);
        initDate.put(KEY_MONTH, initMonth);
        return initDate;
    }
}
