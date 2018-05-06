package com.weknownothing.farmacy.Api.Request;

/**
 * Created by p2 on 6/5/18.
 */

public class TestSet_Model {

    public int mon ;
    public int mday ;
    public int maxtempm;
    public int mintempm;
    public int maxhumidity;
    public int minhumidity;
    public int maxpressurem;
    public int minpressurem ;
    public int maxwspdm;
    public int precipm;

    public int meantempm ;
    public int meanpressurem;
    public int humidity;


    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getMday() {
        return mday;
    }

    public void setMday(int mday) {
        this.mday = mday;
    }

    public int getMaxtempm() {
        return maxtempm;
    }

    public void setMaxtempm(int maxtempm) {
        this.maxtempm = maxtempm;
    }

    public int getMintempm() {
        return mintempm;
    }

    public void setMintempm(int mintempm) {
        this.mintempm = mintempm;
    }

    public int getMaxhumidity() {
        return maxhumidity;
    }

    public void setMaxhumidity(int maxhumidity) {
        this.maxhumidity = maxhumidity;
    }

    public int getMinhumidity() {
        return minhumidity;
    }

    public void setMinhumidity(int minhumidity) {
        this.minhumidity = minhumidity;
    }

    public int getMaxpressurem() {
        return maxpressurem;
    }

    public void setMaxpressurem(int maxpressurem) {
        this.maxpressurem = maxpressurem;
    }

    public int getMinpressurem() {
        return minpressurem;
    }

    public void setMinpressurem(int minpressurem) {
        this.minpressurem = minpressurem;
    }

    public int getMaxwspdm() {
        return maxwspdm;
    }

    public void setMaxwspdm(int maxwspdm) {
        this.maxwspdm = maxwspdm;
    }


    public int getPrecipm() {
        return precipm;
    }

    public void setPrecipm(int precipm) {
        this.precipm = precipm;
    }

    public int getMeantempm() {
        return meantempm;
    }

    public void setMeantempm() {
        this.meantempm = (getMaxtempm()+getMintempm())/2;
    }

    public int getMeanpressurem() {
        return meanpressurem;
    }

    public void setMeanpressurem() {
        this.meanpressurem = (getMaxpressurem()+getMinpressurem())/2;
    }


    public int getHumidity() {
        return humidity;
    }

    public void setHumidity() {
        this.humidity = (getMaxhumidity()+getMinhumidity())/2;
    }

    @Override
    public String toString() {
        return "TestSet_Model{" +
                "mon=" + mon +
                ", mday=" + mday +
                ", maxtempm=" + maxtempm +
                ", mintempm=" + mintempm +
                ", maxhumidity=" + maxhumidity +
                ", minhumidity=" + minhumidity +
                ", maxpressurem=" + maxpressurem +
                ", minpressurem=" + minpressurem +
                ", maxwspdm=" + maxwspdm +
                ", precipm=" + precipm +
                ", meantempm=" + meantempm +
                ", meanpressurem=" + meanpressurem +
                ", humidity=" + humidity +
                '}';
    }
}
