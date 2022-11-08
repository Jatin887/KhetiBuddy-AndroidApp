package com.agriculturalapp.modals;

public class PredictedResult {
    String final_prediction;

    public PredictedResult(String final_prediction){
        this.final_prediction = final_prediction;

    }
    public String getResult(){
        return final_prediction;
    }
    public void setResult(String final_prediction){
        this.final_prediction = final_prediction;
    }
}
