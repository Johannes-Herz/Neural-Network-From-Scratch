package com.neuralnetwork.loss;

import com.utilities.Matrix;

public class MSELoss implements ILoss{

    public MSELoss(){}
    @Override
    public double calculateLoss(Matrix y, Matrix yHat) {

        Matrix subtraction = Matrix.subtract(yHat, y);
        Matrix square = Matrix.pow(subtraction, 2);
        double loss = (double) 1 / (double) y.columnCount * square.sum();
        return loss;
    }

    @Override
    public double calculateGradient(Matrix y, Matrix yHat) {
        return 0;
    }
}
