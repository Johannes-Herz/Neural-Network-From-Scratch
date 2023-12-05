package com.utilities;

/**
 *
 * <pre>
 * {@code
 * Weight a = new Weight(1, 2);
 * Weight b = new Weight(2, 2, new double[][]{{1, 2}, {3, 4}});
 * }
 * </pre>
 *
 * @author jherz
 * @version 1.0.1
 * @since 05.12.2023
 */
public class Weight extends Matrix {

    public static double MIN_RANDOM = -1;
    public static double MAX_RANDOM = 1;
    public Matrix gradient = null;

    /**
     *
     * @param rowCount
     * @param columnCount
     */
    public Weight(int rowCount, int columnCount) {
        this(rowCount, columnCount, null);
    }

    /**
     *
     * @param rowCount
     * @param columnCount
     * @param data
     */
    public Weight(int rowCount, int columnCount, double data[][]){
        super(rowCount, columnCount, data);
        if(data == null){
            super.randomize(Weight.MIN_RANDOM, Weight.MAX_RANDOM);
        }
    }
}
