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
    public int inputCount = 0;
    public int outputCount = 0;
    public Weight weight = null;
    public Bias bias = null;
    public Matrix input = null;
    public Matrix output = null;

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
        Matrix dw = Matrix.T(Matrix.dotProduct(this.input, gradient));
        Matrix.multiplyValue(dw, (double) 1 / this.input.columnCount);
        this.weight.gradient = dw;

        Matrix ones = new Matrix(1, this.input.columnCount);
        ones.ones();
        Matrix db = Matrix.T(Matrix.dotProduct(ones, gradient));
        Matrix.multiplyValue(db, (double) 1 / this.input.columnCount);
        this.bias.gradient = db;

        Matrix dx = Matrix.dotProduct(gradient, this.weight);
        return dx;
    }
}
