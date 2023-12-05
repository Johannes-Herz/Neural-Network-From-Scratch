package com.neuralnetwork.layer;

import com.utilities.Matrix;

public interface ILayer {
    public Matrix forward(Matrix input);
    public Matrix backward(Matrix gradient);
}
