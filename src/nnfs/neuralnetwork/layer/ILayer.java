package nnfs.neuralnetwork.layer;

import nnfs.utilities.Matrix;

public interface ILayer {
    Matrix forward(Matrix input);
    Matrix backward(Matrix gradient);
}
