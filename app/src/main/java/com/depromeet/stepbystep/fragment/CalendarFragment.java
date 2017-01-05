package com.depromeet.stepbystep.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.depromeet.stepbystep.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.depromeet.stepbystep.common.Define.KEY_MONTH;
import static com.depromeet.stepbystep.common.Define.KEY_YEAR;

public class CalendarFragment extends Fragment {
    private ArrayList<String> list = new ArrayList<>();
    private DateAdapter adDate = new DateAdapter();

    private TextView tvDate;
    private GridView gvDate;

    private int currYear;
    private int currMonth;
    private int today = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        tvDate = (TextView) rootView.findViewById(R.id.fragment_calendar_tv_date);
        gvDate = (GridView) rootView.findViewById(R.id.fragment_calendar_gv_date);
        gvDate.setAdapter(adDate);

        currYear = Calendar.getInstance().get(Calendar.YEAR);
        currMonth = Calendar.getInstance().get(Calendar.MONTH);

        Bundle args = getArguments();
        int thisYear = args.getInt(KEY_YEAR, currYear);
        int thisMonth = args.getInt(KEY_MONTH, currMonth);
        initDate(thisYear, thisMonth);

        return rootView;
    }

    /**
     * 날짜 어댑터
     */
    private class DateAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            String item = getItem(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_calendar_item, parent, false);

                holder = new ViewHolder();
                holder.tvDay = (TextView) convertView.findViewById(R.id.fragment_calendar_item_tv_day);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 일자
            holder.tvDay.setText(String.valueOf(item));
            if (today > 0 && String.valueOf(today).equals(item))
                holder.tvDay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.today, null));
            else holder.tvDay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.day, null));

            return convertView;
        }

        private class ViewHolder {
            TextView tvDay;
        }
    }

    public void initDate(int year, int month) {
        if (year != currYear || month != currMonth + 1) today = 0;
        else today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        // 요일 삽입
        list.clear();
        list.add("일");
        list.add("월");
        list.add("화");
        list.add("수");
        list.add("목");
        list.add("금");
        list.add("토");

        // 1일 이전 일자는 공백으로 삽입
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < dayNum; i ++) {
            list.add("");
        }

        // 1일부터 마지막일까지 삽입
        calendar.set(Calendar.MONTH, month - 1);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < lastDay; i ++) {
            list.add("" + (i + 1));
        }

        // 달력 갱신
        tvDate.setText(year + "년 " + month + "월");
        adDate.notifyDataSetChanged();
    }
}


