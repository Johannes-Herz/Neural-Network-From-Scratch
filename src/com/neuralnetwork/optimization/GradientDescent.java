package com.neuralnetwork.optimization;

import com.neuralnetwork.NeuralNetwork;
import com.neuralnetwork.layer.ILayer;
import com.neuralnetwork.layer.LinearLayer;
import com.utilities.Matrix;

public class GradientDescent implements IOptimizer{

    public NeuralNetwork neuralNetwork;
    public double learningRate;
    public GradientDescent(NeuralNetwork neuralNetwork, double learningRate){
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
    }
    @Override
    public void step(){
        for(ILayer iLayer : this.neuralNetwork.layerArray){
            if(iLayer instanceof LinearLayer){
                Matrix negativeWeightGradient = Matrix.multiplyValue(((LinearLayer) iLayer).weight.gradient, (double) -1 * this.learningRate);
                ((LinearLayer) iLayer).weight.add(negativeWeightGradient);
                Matrix negativeBiasGradient = Matrix.multiplyValue(((LinearLayer) iLayer).bias.gradient, (double) -1 * this.learningRate);
                ((LinearLayer) iLayer).bias.add(negativeBiasGradient);
            }
        }
    }
}
