package com.neuralnetwork.loss;

import com.utilities.Matrix;

public interface ILoss {
    public double calculateLoss(Matrix y, Matrix yHat);
    public double calculateGradient(Matrix y, Matrix yHat);

}
