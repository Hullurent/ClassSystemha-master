package com.kris.classsystem.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kris.classsystem.R;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.model.Schedule;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassTableFragment extends Fragment {

    private TimetableView mtimetableView;
    private List<Schedule> baby;
    public ClassTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the fragment_start for this fragment
        View view=inflater.inflate(R.layout.fragment_class_table, container, false);
        mtimetableView = view.findViewById(R.id.id_timetableView);
        mtimetableView.data(baby).curWeek(1).showView();
        return view;
    }

    
}
