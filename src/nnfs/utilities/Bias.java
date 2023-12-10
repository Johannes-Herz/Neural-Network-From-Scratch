package nnfs.utilities;

/**
 *
 * <pre>
 * {@code
 * Bias a = new Bias(1, 2);
 * Bias b = new Bias(2, 2, new double[][]{{1, 2}, {3, 4}});
 * }
 * </pre>
 *
 * @author jherz
 * @version 1.0.1
 * @since 05.12.2023
 */
public class Bias extends Matrix {

    public static double MIN_RANDOM = -1;
    public static double MAX_RANDOM = 1;
    public Matrix gradient = null;

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
