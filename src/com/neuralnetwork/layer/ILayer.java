package com.neuralnetwork.layer;

import com.utilities.Matrix;

public interface ILayer {
    Matrix forward(Matrix input);
    Matrix backward(Matrix gradient);
}
