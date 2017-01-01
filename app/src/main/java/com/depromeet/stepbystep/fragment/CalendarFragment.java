package com.depromeet.stepbystep.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.depromeet.stepbystep.R;
import com.depromeet.stepbystep.common.Define;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment {
    private int newYear, newMonth;
    private int oldYear, oldMonth;
    private int today = 0;

    private DateAdapter adDate;
    private ArrayList<String> list;

    private View rootView;
    private class ViewHolder {
        TextView tvDate;
        GridView gvDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewHolder holder;

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

            holder = new ViewHolder();
            holder.tvDate = (TextView) rootView.findViewById(R.id.tvDate);
            holder.gvDate = (GridView) rootView.findViewById(R.id.gvDate);

            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        // 날짜가 변경되었을 때
        if (newYear != oldYear || newMonth != oldMonth) {
            holder.tvDate.setText(newYear + "년 " + newMonth + "월");

            // 리스트 초기화
            if (list == null)
                list = new ArrayList<>();
            else list.clear();

            // 요일 삽입
            list.add("일");
            list.add("월");
            list.add("화");
            list.add("수");
            list.add("목");
            list.add("금");
            list.add("토");

            // 1일 이전 일자는 공백으로 삽입
            Calendar calendar = Calendar.getInstance();
            calendar.set(newYear, newMonth - 1, 1);
            int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 1; i < dayNum; i ++) {
                list.add("");
            }

            // 1일부터 마지막일까지 삽입
            calendar.set(Calendar.MONTH, newMonth - 1);
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 0; i < lastDay; i ++) {
                list.add("" + (i + 1));
            }

            // 달력 갱신
            if (adDate == null) {
                adDate = new DateAdapter();
                holder.gvDate.setAdapter(adDate);
            } else adDate.notifyDataSetChanged();

            oldYear = newYear;
            oldMonth = newMonth;
        }
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
                holder.tvDay = (TextView) convertView.findViewById(R.id.tv_item_day);

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

    public void setDate(SparseArrayCompat<Integer> date) {
        newYear = date.get(Define.KEY_YEAR);
        newMonth = date.get(Define.KEY_MONTH);

        // 오늘 일자 저장
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        int currMonth = Calendar.getInstance().get(Calendar.MONTH);
        if (newYear == currYear && newMonth == currMonth + 1)
            today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        else today = 0;
    }
}


