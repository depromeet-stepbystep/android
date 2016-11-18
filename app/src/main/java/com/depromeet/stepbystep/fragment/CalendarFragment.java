package com.depromeet.stepbystep.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.depromeet.stepbystep.R;

public class CalendarFragment extends Fragment {
    int month;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        TextView tvPage = (TextView) rootView.findViewById(R.id.tvPage);
        tvPage.setText(month + "월");

        return rootView;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
