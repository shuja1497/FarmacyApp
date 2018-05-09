package com.weknownothing.farmacy.Models;

public class CropInfo
{
    private String crop_name;
    private String gen_info;
    private String soil_info;

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getGen_info() {
        return gen_info;
    }

    public void setGen_info(String gen_info) {
        this.gen_info = gen_info;
    }

    public String getSoil_info() {
        return soil_info;
    }

    public void setSoil_info(String soil_info) {
        this.soil_info = soil_info;
    }

    @Override
    public String toString() {
        return "CropInfo{" +
                "crop_name='" + crop_name + '\'' +
                ", gen_info='" + gen_info + '\'' +
                ", soil_info='" + soil_info + '\'' +
                '}';
    }

}
