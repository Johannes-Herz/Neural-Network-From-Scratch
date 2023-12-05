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

    public double[][] data = null;
    public int rowCount = 0;
    public int columnCount = 0;

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

    public Matrix fill(EntrySupplier supplier){
        for(int row = 0; row < this.rowCount; row++){
            for(int column = 0; column < this.columnCount; column++){
                this.data[row][column] = supplier.supply(row, column);
            }
        }
        return this;
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
     * @return
     */
    public Matrix ones(){
        for(int row = 0; row < this.rowCount; row++){
            for(int column = 0; column < this.columnCount; column++){
                this.data[row][column] = 1;
            }
        }
        return this;
    }

    /**
     *
     * @return
     */
    public double sum(){
        double sum = 0;
        for(int row = 0; row < this.rowCount; row++){
            for(int column = 0; column < this.columnCount; column++){
                sum += this.data[row][column];
            }
        }
        return sum;
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
        for(int row = 0; row < m1.rowCount; row++){
            for(int column = 0; column < m2.columnCount; column++){
                double sum = 0;
                for(int z = 0; z < m1.columnCount; z++){
                    sum += m1.data[row][z] * m2.data[z][column];
                }
                dotProduct.data[row][column] = sum;
            }
        }
        return dotProduct;
    }

    /**
     *
     * @param m1
     * @param m2
     * @return
     */
    public static Matrix addColumn(Matrix m1, Matrix m2){
        if(m1.rowCount != m2.rowCount){
            throw new IllegalArgumentException("Dimension mismatch!\n" +
                    "Dimension ("+m1.rowCount + ","+m1.columnCount+") " +
                    "cannot be added column-wise with " +
                    "Dimension ("+m2.rowCount+","+m2.columnCount+"!\n");
        }

        Matrix addition = new Matrix(m1.rowCount, m1.columnCount);
        for(int row = 0; row < m1.rowCount; row++){
            for(int column = 0; column < m1.columnCount; column++){
                addition.data[row][column] = m1.data[row][column] + m2.data[row][0];
            }
        }
        return addition;
    }

    /**
     *
     * @param m1
     * @param comparator
     * @return
     */
    public static Matrix forEachEntry(Matrix m1, EntryComparator comparator){
        Matrix forEach = new Matrix(m1.rowCount, m1.columnCount);
        for(int row = 0; row < m1.rowCount; row++){
            for(int column = 0; column < m1.columnCount; column++){
                forEach.data[row][column] = comparator.compare(m1.data[row][column]);
            }
        }
        return forEach;
    }

    /**
     *
     * @param m1
     * @param m2
     * @return
     */
    public static Matrix subtract(Matrix m1, Matrix m2){
        if(m1.rowCount != m2.rowCount || m1.columnCount != m2.columnCount){
            throw new IllegalArgumentException("Dimension mismatch!\n" +
                    "Dimension ("+m1.rowCount + ","+m1.columnCount+") " +
                    "cannot be subtracted with " +
                    "Dimension ("+m2.rowCount+","+m2.columnCount+"!\n");
        }

        Matrix subtraction = new Matrix(m1.rowCount, m1.columnCount);
        for(int row = 0; row < m1.rowCount; row++){
            for(int column = 0; column < m1.columnCount; column++){
                subtraction.data[row][column] = m1.data[row][column] - m2.data[row][column];
            }
        }
        return subtraction;
    }

    /**
     *
     * @param m1
     * @param exponent
     * @return
     */
    public static Matrix powerValue(Matrix m1, double exponent){
        Matrix power = new Matrix(m1.rowCount, m1.columnCount);
        for(int row = 0; row < m1.rowCount; row++){
            for(int column = 0; column < m1.columnCount; column++){
                power.data[row][column] = Math.pow(m1.data[row][column], exponent);
            }
        }
        return power;
    }

    /**
     *
     * @param m1
     * @param exponent
     * @return
     */
    public static Matrix multiplyValue(Matrix m1, double exponent){
        Matrix power = new Matrix(m1.rowCount, m1.columnCount);
        for(int row = 0; row < m1.rowCount; row++){
            for(int column = 0; column < m1.columnCount; column++){
                power.data[row][column] = m1.data[row][column] * exponent;
            }
        }
        return power;
    }

    public static Matrix T(Matrix m1){
        Matrix t = new Matrix(m1.columnCount, m1.rowCount);
        for(int row = 0; row < m1.rowCount; row++){
            for(int column = 0; column < m1.columnCount; column++){
                t.data[column][row] = m1.data[row][column];
            }
        }
        return t;
    }
    public abstract static class EntrySupplier{
        public abstract double supply(int row, int column);
    }
    public abstract static class EntryComparator{
        public abstract double compare(double entry);
    }

}
