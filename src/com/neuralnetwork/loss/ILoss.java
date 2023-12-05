package com.neuralnetwork.loss;

import com.utilities.Matrix;

public interface ILoss {
    public double calculateLoss(Matrix y, Matrix yHat);
    public Matrix calculateGradient(Matrix y, Matrix yHat);

}
