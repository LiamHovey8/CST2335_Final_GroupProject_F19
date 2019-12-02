package com.example.cst2335_final_groupproject_f19;

public class Conversion {

    /**
     * Value user input for conversion
     */
    private Double inputValue;
    /**
     * Value output by conversion
     */
    private Double outputValue;
    /**
     * Currency of input value
     */
    private String inputCurrency;
    /**
     * Currency of output value
     */
    private String outputCurrency;
    /**
     * ID of conversion
     */
    long id;


    Conversion(){}

    /**
     * Create conversion object
     * @param inputValue
     * @param outputValue
     * @param inputCurrency
     * @param outputCurrency
     * @param id
     */
    Conversion(Double inputValue, Double outputValue, String inputCurrency, String outputCurrency, long id){

        setInputValue(inputValue);
        setOutputValue(outputValue);
        setInputCurrency(inputCurrency);
        setOutputCurrency(outputCurrency);
        setId(id);

    }

    /**
     * Conversion getters and setters
     */

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

    private void setId(long id){this.id=id;}

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

    public long getId() {
        return id;
    }

    /**
     * Get part of conversion that shows input and output currency
     * @return totalListPiece
     */
    public String getListPiece(){
        String myInputCurrency = getInputCurrency();
        String myOutputCurrency = getOutputCurrency();
        String totalListPiece = myInputCurrency+" -> " +myOutputCurrency;
        return totalListPiece;
    }

    public Conversion getConversion(){
        return this;
    }



}
