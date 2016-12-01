package com.depromeet.stepbystep.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.depromeet.stepbystep.common.Define;
import com.depromeet.stepbystep.fragment.CalendarFragment;

import java.util.Calendar;

public class CalendarAdapter extends FragmentPagerAdapter {
    private int currYear; //년도 추가
    private int currMonth;
    private CalendarFragment[] fragments;

    public CalendarAdapter(FragmentManager fm) {
        super(fm);
        currMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currYear = Calendar.getInstance().get(Calendar.YEAR);  //년도 추가
        fragments = new CalendarFragment[Define.CALENDAR_PAGE_SIZE];
    }


    // 최초 로드시 프래그먼트 생성
    @Override
    public Fragment getItem(int position) {
        fragments[position] = new CalendarFragment();
        fragments[position].setDate(fetchInitDate(position));
        return fragments[position];
    }

    // 페이지 개수 지정
    @Override
    public int getCount() {
        return Define.CALENDAR_PAGE_SIZE;
    }

    // 페이지 이동시 프래그먼트의 모든 뷰를 갱신하기 위함
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    // 선택된 날짜를 기준으로 {count}만큼 날짜 이동
    public void skipMonth(int count) {
        currMonth += count;

        while (currMonth > 12){
            currMonth %= 12;
            currYear++;
        }
        while (currMonth < 1){
            currMonth += 12;
            currYear--;
        }
        for (int position = 0; position < fragments.length; position++)
            fragments[position].setDate(fetchInitDate(position));
    }

    // {position}에 해당하는 페이지의 날짜 반환, 012 지난현재다음
    private int[] fetchInitDate(int position) {
        int initYear = currYear;
        int initMonth = currMonth + position - Define.CALENDAR_PAGE_MIDDLE;
        int initDate[] ={initYear, initMonth};

        while (initMonth > 12){
            initMonth %= 12;
            initYear += 1;
        }
        while (initMonth < 1){
            initMonth += 12;
            initYear -= 1;
        }

        return initDate;
    }
}
