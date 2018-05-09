package com.weknownothing.farmacy.Api.Response;

/**
 * Created by p2 on 6/5/18.
 */

public class AlertResponse {

    int day1 ;
    int day2 ;
    int day3 ;


    public int getDay1() {
        return day1;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public int getDay2() {
        return day2;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }

    public int getDay3() {
        return day3;
    }

    public void setDay3(int day3) {
        this.day3 = day3;
    }

    @Override
    public String toString() {
        return "Response{" +
                "day1=" + day1 +
                ", day2=" + day2 +
                ", day3=" + day3 +
                '}';
    }
}
