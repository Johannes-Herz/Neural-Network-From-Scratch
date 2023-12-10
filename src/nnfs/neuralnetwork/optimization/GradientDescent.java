package nnfs.neuralnetwork.optimization;

import nnfs.neuralnetwork.NeuralNetwork;
import nnfs.neuralnetwork.layer.ILayer;
import nnfs.neuralnetwork.layer.LinearLayer;
import nnfs.utilities.Matrix;

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
