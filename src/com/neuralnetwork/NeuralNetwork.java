package com.neuralnetwork;

import com.neuralnetwork.layer.ILayer;
import com.utilities.Matrix;

/**
 *
 * <pre>
 * {@code
 * NeuralNetwork n = new NeuralNetwork(new ILayer[]{new ILayer(:param), new ILayer(:param)});
 * }
 * </pre>
 *
 * @author jherz
 * @version 1.0.1
 * @since 05.12.2023
 */
public class NeuralNetwork {

    public ILayer[] layerArray = null;

    public NeuralNetwork(ILayer... layerArray){
        this.layerArray = layerArray;
    }

    public Matrix call(Matrix input){
        Matrix run = input;
        for(ILayer iLayer : this.layerArray){
            run = iLayer.forward(run);
        }
        return run;
    }

    public Matrix calculateGradients(Matrix gradient){
        Matrix run = gradient;
        for(int i = this.layerArray.length - 1; i >= 0; i--){
            run = this.layerArray[i].backward(run);
        }
        return run;
    }
}
