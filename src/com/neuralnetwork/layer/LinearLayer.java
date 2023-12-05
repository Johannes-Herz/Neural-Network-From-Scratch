package com.neuralnetwork.layer;

import com.utilities.Bias;
import com.utilities.Matrix;
import com.utilities.Weight;

/**
 *
 * <pre>
 * {@code
 * LinearLayer l = new LinearLayer(1, 2);
 * }
 * </pre>
 *
 * @author jherz
 * @version 1.0.1
 * @since 05.12.2023
 */
public class LinearLayer implements ILayer{
    private int inputCount = 0;
    private int outputCount = 0;
    private Weight weight = null;
    private Bias bias = null;
    private Matrix input = null;
    private Matrix output = null;

    /**
     *
     * @param inputCount
     * @param outputCount
     */
    public LinearLayer(int inputCount, int outputCount){
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        this.weight = new Weight(this.outputCount, this.inputCount);
        this.bias = new Bias(this.outputCount, 1);
    }

    @Override
    public Matrix forward(Matrix input) {
        this.input = input;
        this.output = Matrix.dotProduct(this.weight, this.input);
        this.output = Matrix.addColumn(this.output, this.bias);
        return this.output;
    }

    @Override
    public Matrix backward(Matrix gradient) {
        return null;
    }
}
