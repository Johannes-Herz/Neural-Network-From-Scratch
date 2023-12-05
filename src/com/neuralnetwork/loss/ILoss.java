package com.neuralnetwork.loss;

import com.utilities.Matrix;

public interface ILoss {
    double calculateLoss(Matrix y, Matrix yHat);
    Matrix calculateGradient(Matrix y, Matrix yHat);

}
