package com.weknownothing.farmacy.Models;
/**
 * Created by tanya on 29/4/18.
 */
public class Days
{
    private String date;
    private float temp_min_c;
    private float temp_max_c;
    private float humid_min_pct;
    private float humid_max_pct;
    private float precip_total_mm;

    //additional parameters for alertSystem
    private float slp_max_mb ; //integer	millibars	maximum sea level pressure
    private float slp_min_mb ; //integer	millibars	minimum sea level pressure
    private float windspd_max_ms; //1 decimal	metres per second	maximum mean wind speed
    private float windgst_max_ms ; //1 decimal	metres per second	maximum wind gust

    public float getSlp_max_mb() {
        return slp_max_mb;
    }

    public void setSlp_max_mb(float slp_max_mb) {
        this.slp_max_mb = slp_max_mb;
    }

    public float getSlp_min_mb() {
        return slp_min_mb;
    }

    public void setSlp_min_mb(float slp_min_mb) {
        this.slp_min_mb = slp_min_mb;
    }

    public float getWindspd_max_ms() {
        return windspd_max_ms;
    }

    public void setWindspd_max_ms(float windspd_max_ms) {
        this.windspd_max_ms = windspd_max_ms;
    }

    public float getWindgst_max_ms() {
        return windgst_max_ms;
    }

    public void setWindgst_max_ms(float windgst_max_ms) {
        this.windgst_max_ms = windgst_max_ms;
    }

    public Days(String date, float temp_min_c, float temp_max_c, float humid_min_pct, float humid_max_pct, float precip_total_mm) {
        this.date = date;
        this.temp_min_c = temp_min_c;
        this.temp_max_c = temp_max_c;
        this.humid_min_pct = humid_min_pct;
        this.humid_max_pct = humid_max_pct;
        this.precip_total_mm = precip_total_mm;
    }

    public String getDate() {
        return date;
    }

    public float getTemp_min_c() {
        return temp_min_c;
    }

    public float getTemp_max_c() {
        return temp_max_c;
    }

    public float getHumid_min_pct() {
        return humid_min_pct;
    }

    public float getHumid_max_pct() {
        return humid_max_pct;
    }

    public float getPrecip_total_mm() {
        return precip_total_mm;
    }

    @Override
    public String toString() {
        return "Days{" +
                "date='" + date + '\'' +
                ", temp_min_c=" + temp_min_c +
                ", temp_max_c=" + temp_max_c +
                ", humid_min_pct=" + humid_min_pct +
                ", humid_max_pct=" + humid_max_pct +
                ", precip_total_mm=" + precip_total_mm +
                ", slp_max_mb=" + slp_max_mb +
                ", slp_min_mb=" + slp_min_mb +
                ", windspd_max_ms=" + windspd_max_ms +
                ", windgst_max_ms=" + windgst_max_ms +
                '}';
    }
}