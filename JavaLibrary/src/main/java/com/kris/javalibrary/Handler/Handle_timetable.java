package com.kris.javalibrary.Handler;


import com.kris.javalibrary.Bean.MySubject;
import com.kris.javalibrary.Model.CAS_Login;
import com.kris.javalibrary.Model.GetMysubject;

import java.util.List;


public class Handle_timetable {


    public List<MySubject> dataDliever() {

        CAS_Login cas_login = new CAS_Login();
        GetMysubject getMysubject = new GetMysubject(cas_login.getMyCookies());

        String message = String.valueOf(getMysubject.getcourses().get(0).getName());
        System.out.println(message);

        return getMysubject.getcourses();
    }

    public void model_trigger() {

    }
}
