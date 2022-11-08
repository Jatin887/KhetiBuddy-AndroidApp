package com.agriculturalapp.modals;

public class DataClass {
    private Double nitrogen;
    private Double phosphorous;
    private  Double pottasium;
    private Double ph;
    private Double rainfall;
    private String city;


    public DataClass(Double nitrogen, Double phosphorous,Double pottasium,Double ph,Double rainfall,String city) {
        this.nitrogen = nitrogen;
        this.phosphorous = phosphorous;
        this.pottasium = pottasium;
        this.ph = ph;
        this.rainfall = rainfall;
        this.city = city;
    }

    public Double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(Double nitrogen) {
        this.nitrogen = nitrogen;
    }
    public void setPhosphorous(Double phosphorous) {
        this.phosphorous = phosphorous;
    }

    public Double getPhosphorous() {
        return phosphorous;
    }
    public Double getPottasium() {
        return pottasium;
    }

    public void setPottasium(Double pottasium) {
        this.pottasium = pottasium;
    }
    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }
    public Double getRainfall() {
        return rainfall;
    }

    public void setRainfall(Double ph) {
        this.rainfall = rainfall;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
