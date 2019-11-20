package com.example.cst2335_final_groupproject_f19;

public class Conversion {

    private Double inputValue;
    private Double outputValue;
    private String inputCurrency;
    private String outputCurrency;

    Conversion(){}

//    Conversion(double inputValue, double outputValue, String inputCurrency, String outputCurrency){
//
//    }

    Conversion(Double inputValue, Double outputValue, String inputCurrency, String outputCurrency){

        setInputValue(inputValue);
        //this.inputValue = inputValue;
        //this.outputValue=outputValue;
        setOutputValue(outputValue);
        //this.inputCurrency=inputCurrency;
        setInputCurrency(inputCurrency);
        //this.outputCurrency=outputCurrency;
        setOutputCurrency(outputCurrency);

    }

    private void setInputValue(Double inputValue) {
        this.inputValue = inputValue;
    }

    private void setOutputValue(Double outputValue) {
        this.outputValue = outputValue;
    }

    private void setInputCurrency(String inputCurrency) {
        this.inputCurrency = inputCurrency;
    }

    private void setOutputCurrency(String outputCurrency) {
        this.outputCurrency = outputCurrency;
    }

    public Double getInputValue() {
        return inputValue;
    }

    public Double getOutputValue() {
        return outputValue;
    }

    public String getInputCurrency() {
        return inputCurrency;
    }

    public String getOutputCurrency() {
        return outputCurrency;
    }

    public String getListPiece(){
        String myInputCurrency = getInputCurrency();
        String myOutputCurrency = getOutputCurrency();
        String totalListPiece = myInputCurrency+" to " +myOutputCurrency;
        return totalListPiece;
    }

    private Conversion getConversion(){
        return this;
    }



}
