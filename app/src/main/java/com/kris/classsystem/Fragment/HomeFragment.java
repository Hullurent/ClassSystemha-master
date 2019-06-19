package com.kris.classsystem.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kris.classsystem.R;
import com.kris.classsystem.SubinterfaceActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Button mTimetable;
    private Button mSearch;
    private Button mExamination;
    private Button mYellowpage;
    private Button mRank;
    private Button mLibrary;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment_start for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTimetable = view.findViewById(R.id.timetable); // 成功实例化；

        mTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SubinterfaceActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
