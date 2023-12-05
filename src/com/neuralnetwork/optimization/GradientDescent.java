package com.neuralnetwork.optimization;

import com.neuralnetwork.NeuralNetwork;
import com.neuralnetwork.layer.ILayer;
import com.neuralnetwork.layer.LinearLayer;
import com.utilities.Matrix;

public class GradientDescent {

    public NeuralNetwork neuralNetwork = null;
    public double learningRate = 0;
    public GradientDescent(NeuralNetwork neuralNetwork, double learningRate){
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
    }
    public void step(){
        for(ILayer iLayer : this.neuralNetwork.layerArray){
            if(iLayer instanceof LinearLayer){
                Matrix negativeWeightGradient = Matrix.multiplyValue(((LinearLayer) iLayer).weight.gradient, (double) -1 * this.learningRate);
                ((LinearLayer) iLayer).weight.add(negativeWeightGradient);
                Matrix negativeBiasGradient = Matrix.multiplyValue(((LinearLayer) iLayer).bias.gradient, (double) -1 * this.learningRate);
                ((LinearLayer) iLayer).weight.add(negativeBiasGradient);
            }
        }
    }
    public void zeroGradient(){
        for(ILayer iLayer : this.neuralNetwork.layerArray){
            if(iLayer instanceof LinearLayer){
                ((LinearLayer) iLayer).weight.gradient = null;
                ((LinearLayer) iLayer).bias.gradient = null;
            }
        }
    }
}
