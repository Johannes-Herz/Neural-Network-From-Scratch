package com.neuralnetwork.activation;

import com.neuralnetwork.layer.ILayer;
import com.utilities.Matrix;

/**
 *
 * <pre>
 * {@code
 * ReLU r = new ReLU();
 * }
 * </pre>
 *
 * @author jherz
 * @version 1.0.1
 * @since 05.12.2023
 */
public class ReLU implements ILayer {

    private Matrix input = null;
    private Matrix output = null;
    public ReLU(){}
    @Override
    public Matrix forward(Matrix input) {
        this.input = input;
        this.output = Matrix.forEachEntry(this.input, new Matrix.EntryComparator(){
            @Override
            public double compare(double entry) {
                return entry > 0 ? entry : 0;
            }
        });
        return output;
    }

    @Override
    public Matrix backward(Matrix gradient) {
        Matrix dx = new Matrix(gradient.rowCount, gradient.columnCount);
        dx.fill(new Matrix.EntrySupplier() {
            @Override
            public double supply(int row, int column) {
                return gradient.data[row][column] * (input.data[column][row] > 0 ? 1 : 0);
            }
        });
        return dx;
    }
}
