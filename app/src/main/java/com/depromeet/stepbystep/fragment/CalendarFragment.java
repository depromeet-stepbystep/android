package com.depromeet.stepbystep.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.depromeet.stepbystep.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment {

    private TextView tvPage;            //연,월 텍스트뷰
    private GridAdapter gridAdapter;    //그리드 뷰 어댑터
    private ArrayList<String> dayList;  //일 저장할 리스트
    private GridView gridView;          //그리드뷰
    private Calendar mCal;              //캘린더 변수

    int month, year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        tvPage = (TextView) rootView.findViewById(R.id.tvPage);
        gridView = (GridView) rootView.findViewById(R.id.gridview);

        /**
         현재 날짜 텍스트뷰에 뿌려줌
         **/
        tvPage.setText(year+"년 " + month + "월");

        /**
         gridview 요일 표시
         **/
        dayList = new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        mCal = Calendar.getInstance();


        /**
         이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
         **/
        mCal.set(year, month - 1, 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);


        for (int i = 1; i < dayNum; i++) {  // '1일-요일' 매칭시키기 위해 공백 add
            dayList.add("");
        }


        setCalendarDate(month);
        gridAdapter = new GridAdapter(getActivity(), dayList);
        gridView.setAdapter(gridAdapter);

        return rootView;

    }



    /**
     해당 월에 표시할 일 수 구함
    **/
    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }

    /**
     그리드 뷰 어댑터
    **/
    private class GridAdapter extends BaseAdapter {
        private final List<String> list;
        private final LayoutInflater inflater;


         /**
         생성자 인수: context, list
         **/
        public GridAdapter(Context context, List<String> list) {

            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


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

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_calendar_item, parent, false);
                holder = new ViewHolder();
                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tvItemGridView.setText("" + getItem(position));


            //해당 날짜 텍스트 컬러,배경 변경
            mCal = Calendar.getInstance();

            //오늘 day 가져옴

            if(month == mCal.get(Calendar.MONTH)+1 && year == mCal.get(Calendar.YEAR)){
                Integer today = mCal.get(Calendar.DAY_OF_MONTH);
                String sToday = String.valueOf(today);


                if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                    holder.tvItemGridView.setTextColor(getResources().getColor(R.color.today));
                }
            }
            return convertView;
        }

    }

    private class ViewHolder {
        TextView tvItemGridView;
    }


    public void setDate(int[] date) {
        this.year=date[0];
        this.month=date[1];
    }
}


