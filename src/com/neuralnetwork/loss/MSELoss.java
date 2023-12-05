package com.neuralnetwork.loss;

import com.utilities.Matrix;

public class MSELoss implements ILoss{

    public MSELoss(){}
    @Override
    public double calculateLoss(Matrix y, Matrix yHat) {
        Matrix subtraction = Matrix.subtract(yHat, y);
        Matrix square = Matrix.powerValue(subtraction, 2);
        double loss = (double) 1 / (double) y.columnCount * square.sum();
        return loss;
    }

    @Override
    public Matrix calculateGradient(Matrix y, Matrix yHat) {
        Matrix subtraction = Matrix.subtract(yHat, y);
        Matrix multiplication = Matrix.multiplyValue(subtraction, (double) 2 * (double) 1 / (double) y.columnCount);
        return Matrix.T(multiplication);
    }
}
