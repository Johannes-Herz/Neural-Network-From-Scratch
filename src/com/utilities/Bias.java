package com.utilities;

public class Bias extends Matrix {

    public static double MIN_RANDOM = -1;
    public static double MAX_RANDOM = 1;

    /**
     *
     * @param rowCount
     * @param columnCount
     */
    public Bias(int rowCount, int columnCount) {
        this(rowCount, columnCount, null);
    }

    /**
     *
     * @param rowCount
     * @param columnCount
     * @param data
     */
    public Bias(int rowCount, int columnCount, double[][] data){
        super(rowCount, columnCount, data);
        if(data == null){
            super.randomize(Bias.MIN_RANDOM, Bias.MAX_RANDOM);
        }
    }
}
