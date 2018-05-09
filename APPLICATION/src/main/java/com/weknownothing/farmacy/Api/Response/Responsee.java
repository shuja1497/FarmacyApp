package com.weknownothing.farmacy.Api.Response;

public class Responsee
{
    private String crop1;
    private String crop2;
    private String crop3;
    private String crop4;
    private String crop5;

    public Responsee(String crop1, String crop2, String crop3, String crop4) {
        this.crop1 = crop1;
        this.crop2 = crop2;
        this.crop3 = crop3;
        this.crop4 = crop4;
        this.crop5 = crop5;
    }

    @Override
    public String toString() {
        return "Responsee{" +
                "crop1='" + crop1 + '\'' +
                ", crop2='" + crop2 + '\'' +
                ", crop3='" + crop3 + '\'' +
                ", crop4='" + crop4 + '\'' +
                ", crop5='" + crop5 + '\'' +
                '}';
    }

    public String getCrop1()
    {
        return crop1;
    }

    public void setCrop1(String crop1) {
        this.crop1 = crop1;
    }

    public String getCrop2() {
        return crop2;
    }

    public void setCrop2(String crop2) {
        this.crop2 = crop2;
    }

    public String getCrop3() {
        return crop3;
    }

    public String getCrop5() {
        return crop5;
    }

    public void setCrop5(String crop5) {
        this.crop5 = crop5;
    }

    public void setCrop3(String crop3) {
        this.crop3 = crop3;
    }

    public String getCrop4() {
        return crop4;
    }

    public void setCrop4(String crop4) {
        this.crop4 = crop4;
    }
}
