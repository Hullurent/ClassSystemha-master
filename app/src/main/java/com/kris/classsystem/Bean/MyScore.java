package com.kris.classsystem.Bean;

import java.io.Serializable;

public class MyScore implements Serializable {
    private String my_class;
    private String my_score;

    public String getMy_class() {
        return my_class;
    }

    public void setMy_class(String my_class) {
        this.my_class = my_class;
    }

    public String getMy_score() {
        return my_score;
    }

    public void setMy_score(String my_score) {
        this.my_score = my_score;
    }
}
