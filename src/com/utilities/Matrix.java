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

    /**
     *
     * @param m1
     * @param m2
     * @return
     * @throws IllegalArgumentException If the dimensions of the given matrices m1 and m2 do not match
     */
    public static Matrix dotProduct(Matrix m1, Matrix m2) throws IllegalArgumentException {

        if(m1.columnCount != m2.rowCount){
            throw new IllegalArgumentException("Dimension mismatch!\n" +
                    "Dimension ("+m1.rowCount + ","+m1.columnCount+") " +
                    "cannot be multiplied with " +
                    "Dimension ("+m2.rowCount+","+m2.columnCount+"!\n");
        }

        Matrix dotProduct = new Matrix(m1.rowCount, m2.columnCount);
        for(int i = 0; i < m1.rowCount; i++){
            for(int j = 0; j < m2.columnCount; j++){
                double sum = 0;
                for(int z = 0; z < m1.columnCount; z++){
                    sum += m1.data[i][z] * m2.data[z][j];
                }
                dotProduct.data[i][j] = sum;
            }
        }
        return dotProduct;
    }

    public static Matrix addColumn(Matrix m1, Matrix m2){
        if(m1.rowCount != m2.rowCount){
            throw new IllegalArgumentException("Dimension mismatch!\n" +
                    "Dimension ("+m1.rowCount + ","+m1.columnCount+") " +
                    "cannot be added column-wise with " +
                    "Dimension ("+m2.rowCount+","+m2.columnCount+"!\n");
        }

        Matrix addition = new Matrix(m1.rowCount, m1.columnCount);
        for(int i = 0; i < m1.rowCount; i++){
            for(int j = 0; j < m1.columnCount; j++){
                addition.data[i][j] = m1.data[i][j] + m2.data[i][0];
            }
        }
        return addition;
    }

}
