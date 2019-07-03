package com.kris.classsystem.Handler;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.kris.classsystem.Bean.MySubject;
import com.kris.classsystem.Model.CAS_Login;
import com.kris.classsystem.Model.GetMysubject;
import com.zhuangfei.timetable.TimetableView;

import java.util.List;

public class Handle_timetable extends AsyncTask<Integer, Void, List<MySubject>> {
    private List<MySubject> mySubjects;
    private TimetableView mtimetableView;
    private View view;

    public Handle_timetable(TimetableView timetableView) {
        super();
        this.mtimetableView = timetableView;
    }

    public List<MySubject> dataDeliver() {


        CAS_Login cas_login = new CAS_Login();
        GetMysubject getMysubject = new GetMysubject(cas_login.getMyCookies());

        List<MySubject> mySubjects = getMysubject.getcourse();
        Log.d("t2", "dataDeliver: ok");
        //Log.d("t2", mySubjects.get(0).getName());
        return mySubjects;
    }


    @Override
    protected List<MySubject> doInBackground(Integer... integers) {
        Log.d("t1", "doInBackground: ");
        mySubjects = dataDeliver();
        return mySubjects;
    }

    @Override
    protected void onPostExecute(List<MySubject> mySubjects) {

        mtimetableView.source(mySubjects).showView();

    }


}
