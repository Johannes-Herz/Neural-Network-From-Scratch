package com.neuralnetwork.loss;

import com.utilities.Matrix;

public class CrossEntropyLossOnLogits implements ILoss{

    @Override
    public double calculateLoss(Matrix y, Matrix logits) {

        Matrix yHat = softmax(logits);

        double loss = 0;
        for(int row = 0; row < y.rowCount; row++){
            for(int column = 0; column < y.columnCount; column++){
                loss += y.data[row][column] * Math.log(yHat.data[row][column]);
            }
        }
        return -loss/(double)y.columnCount;
    }

    @Override
    public Matrix calculateGradient(Matrix y, Matrix logits) {
        Matrix yHat = softmax(logits);
        return Matrix.subtract(Matrix.T(yHat), Matrix.T(y));
    }

    public static Matrix softmax(Matrix input){
        Matrix exp = Matrix.exp(input);
        Matrix sum = Matrix.sumColumn(exp);
        Matrix softmax = Matrix.divideRow(exp, sum);
        return softmax;
    }
}
