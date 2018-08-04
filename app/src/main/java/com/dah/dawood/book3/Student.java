package com.dah.dawood.book3;

/**
 * Created by dawood on 15/02/2018.
 */
public class Student {
    String name = null;
    String number = null;
    String score = null;

    public Student(String Sname, String Snumber,String score) {

        super();

        this.name = Sname;

        this.number = Snumber;
        this.score=score;
    }

    public String getName() {

        return name;

    }
    public void setName(String Name2) {

        this.name = Name2;

    }
    public String getNumber() {

        return number;

    }
    public void setScore(String score2) {

        this.score = score2;

    }
    public String getScore() {

        return score;

    }
    public void setNumber(String number2) {

        this.number = number2;

    }

    @Override
    public String toString() {

        return  name + " " + number ;

    }

}