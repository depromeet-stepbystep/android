package com.depromeet.stepbystep.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import com.depromeet.stepbystep.common.Define;
import com.depromeet.stepbystep.fragment.CalendarFragment;

import java.util.Calendar;

public class CalendarAdapter extends FragmentPagerAdapter {
    private SparseArrayCompat<CalendarFragment> fragments = new SparseArrayCompat<>();

    private int thisYear;
    private int thisMonth;

    public CalendarAdapter(FragmentManager fm) {
        super(fm);
        Calendar calendar = Calendar.getInstance();
        thisYear = calendar.get(Calendar.YEAR);
        thisMonth = calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 페이지 개수 지정
     */
    @Override
    public int getCount() {
        return Define.CALENDAR_PAGE_SIZE;
    }

    /**
     * 페이지 이동시 프래그먼트의 모든 뷰를 갱신하기 위함
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 프래그먼트 불러오기
     */
    @Override
    public CalendarFragment getItem(int position) {
        CalendarFragment fragment = fragments.get(position);
        if (fragment == null) {
            fragment = new CalendarFragment();
            fragments.put(position, fragment);
        }
        fragment.setDate(fetchDate(position));
        return fragment;
    }

    /**
     * 선택된 날짜를 기준으로 {count}만큼 날짜 이동
     */
    public void skipMonth(int count) {
        thisMonth += count;

        // 프래그먼트 갱신
        for (int position = 0; position < getCount(); position ++) {
            getItem(position);
        }
    }

    /**
     * {position}에 해당하는 페이지의 날짜 반환
     * @param position 0:전월, 1:금월, 2:익월
     */
    private SparseArrayCompat<Integer> fetchDate(int position) {
        int initYear = thisYear;
        int initMonth = thisMonth + position - Define.CALENDAR_PAGE_MIDDLE;

        while (initMonth > 12) {
            initYear += 1;
            initMonth -= 12;
        }
        while (initMonth < 1) {
            initYear -= 1;
            initMonth += 12;
        }

        SparseArrayCompat<Integer> initDate = new SparseArrayCompat<>();
        initDate.put(Define.KEY_YEAR, initYear);
        initDate.put(Define.KEY_MONTH, initMonth);
        return initDate;
    }
}
