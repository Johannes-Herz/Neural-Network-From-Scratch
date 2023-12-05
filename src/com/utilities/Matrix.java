package com.utilities;

import java.util.Random;

/**
 *
 * <pre>
 * {@code
 * Matrix a = new Matrix(1, 2);
 * Matrix b = new Matrix(2, 2, new double[][]{{1, 2}, {3, 4}});
 * }
 * </pre>
 *
 * @author jherz
 * @version 1.0.1
 * @since 05.12.2023
 */
public class Matrix {

    private double[][] data = null;
    private int rowCount = 0;
    private int columnCount = 0;

    /**
     *
     * @param rowCount
     * @param columnCount
     */
    public Matrix(int rowCount, int columnCount){
        this(rowCount, columnCount, null);
    }

    /**
     *
     * @param rowCount
     * @param columnCount
     * @param data
     * @throws IllegalArgumentException If the input array does not have the same dimensions as defined in the given parameters rowCount and columnCount
     */
    public Matrix(int rowCount, int columnCount, double[][] data) throws IllegalArgumentException {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.data = new double[this.rowCount][this.columnCount];
        if(data == null) return;
        if(data.length != this.rowCount || data[0].length != this.columnCount){
            throw new IllegalArgumentException("Parameter mismatch: <data: double[][]>!\n" +
                                "Dimension of data array need to match the given parameters <rowCount: int> and <columnCount: int>!");
        }
        for(int row = 0; row < this.rowCount; row++){
            for(int column = 0; column < this.columnCount; column++){
                this.data[row][column] = data[row][column];
            }
        }
    }

    /**
     *
     * @param minValue
     * @param maxValue
     * @return
     */
    public Matrix randomize(double minValue, double maxValue){
        for(int row = 0; row < this.rowCount; row++){
            for(int column = 0; column < this.columnCount; column++){
                this.data[row][column] = new Random().nextDouble(minValue, maxValue);
            }
        }
        return this;
    }

}
