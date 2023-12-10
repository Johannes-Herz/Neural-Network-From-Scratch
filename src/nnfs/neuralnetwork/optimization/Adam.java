package nnfs.neuralnetwork.optimization;
import nnfs.neuralnetwork.NeuralNetwork;
import nnfs.neuralnetwork.layer.ILayer;
import nnfs.neuralnetwork.layer.LinearLayer;
import nnfs.utilities.Matrix;

import java.util.ArrayList;
import java.util.Vector;

public class Adam implements IOptimizer {

    NeuralNetwork neuralNetwork;
    ArrayList<Vector<Matrix>> momentum1List = new ArrayList<Vector<Matrix>>();
    ArrayList<Vector<Matrix>> momentum2List = new ArrayList<Vector<Matrix>>();
    double learningRate;
    double beta1;
    double beta2;
    double epsilon;
    long timestamp;
    public Adam(NeuralNetwork neuralNetwork, double learningRate, double beta1, double beta2, double epsilon){
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
        this.beta1 = beta1;
        this.beta2 = beta2;
        this.epsilon = epsilon;
        this.timestamp = 1;

        this.initMomentum();
    }

    public void initMomentum(){
        for(ILayer iLayer: this.neuralNetwork.layerArray){
            if(iLayer instanceof LinearLayer){
                Vector<Matrix> momentum1 = new Vector<Matrix>();
                Matrix momentum1Weights = new Matrix(((LinearLayer) iLayer).weight.rowCount, ((LinearLayer) iLayer).weight.columnCount);
                Matrix momentum1Biases = new Matrix(((LinearLayer) iLayer).bias.rowCount, ((LinearLayer) iLayer).bias.columnCount);
                momentum1Weights.ones();
                momentum1Biases.ones();
                momentum1.add(momentum1Weights);
                momentum1.add(momentum1Biases);
                this.momentum1List.add(momentum1);

                Vector<Matrix> momentum2 = new Vector<Matrix>();
                Matrix momentum2Weights = new Matrix(((LinearLayer) iLayer).weight.rowCount, ((LinearLayer) iLayer).weight.columnCount);
                Matrix momentum2Biases = new Matrix(((LinearLayer) iLayer).bias.rowCount, ((LinearLayer) iLayer).bias.columnCount);
                momentum2Weights.ones();
                momentum2Biases.ones();
                momentum2.add(momentum2Weights);
                momentum2.add(momentum2Biases);
                this.momentum2List.add(momentum2);
            }
        }
    }

    @Override
    public void step(){
        int index = 0;
        for(ILayer iLayer: this.neuralNetwork.layerArray){
            if(iLayer instanceof LinearLayer) {
                Matrix weightGradient = ((LinearLayer) iLayer).weight.gradient;
                Matrix biasGradient = ((LinearLayer) iLayer).bias.gradient;

                Vector<Matrix> momentum1Parameter = this.momentum1List.get(index);
                Vector<Matrix> momentum2Parameter = this.momentum2List.get(index);

                Matrix momentum1WeightsNew = Matrix.add(Matrix.multiplyValue(momentum1Parameter.get(0), this.beta1), Matrix.multiplyValue(weightGradient, (double)1 - this.beta1));
                Matrix momentum1BiasesNew = Matrix.add(Matrix.multiplyValue(momentum1Parameter.get(1), this.beta1), Matrix.multiplyValue(biasGradient, (double)1 - this.beta1));

                Matrix momentum2WeightsNew = Matrix.add(Matrix.multiplyValue(momentum2Parameter.get(0), this.beta2), Matrix.multiplyValue(Matrix.powerValue(weightGradient, 2), (double)1 - this.beta2));
                Matrix momentum2BiasesNew = Matrix.add(Matrix.multiplyValue(momentum2Parameter.get(1), this.beta2), Matrix.multiplyValue(Matrix.powerValue(biasGradient, 2), (double)1 - this.beta2));

                this.momentum1List.get(index).removeAllElements();
                this.momentum2List.get(index).removeAllElements();

                this.momentum1List.get(index).add(momentum1WeightsNew);
                this.momentum1List.get(index).add(momentum1BiasesNew);

                this.momentum2List.get(index).add(momentum2WeightsNew);
                this.momentum2List.get(index).add(momentum2BiasesNew);

                Matrix momentum1WeightsHat = Matrix.divideValue(momentum1WeightsNew, (double)1 - Math.pow(this.beta1, this.timestamp));
                Matrix momentum1BiasesHat = Matrix.divideValue(momentum1BiasesNew, (double)1 - Math.pow(this.beta1, this.timestamp));

                Matrix momentum2WeightsHat = Matrix.divideValue(momentum2WeightsNew, (double)1 - Math.pow(this.beta2, this.timestamp));
                Matrix momentum2BiasesHat = Matrix.divideValue(momentum2BiasesNew, (double)1 - Math.pow(this.beta2, this.timestamp));

                Matrix weightUpdate = Matrix.divide(momentum1WeightsHat, Matrix.addValue(Matrix.sqrt(momentum2WeightsHat), this.epsilon));
                Matrix biasUpdate = Matrix.divide(momentum1BiasesHat, Matrix.addValue(Matrix.sqrt(momentum2BiasesHat), this.epsilon));

                weightUpdate = Matrix.multiplyValue(weightUpdate, (double) -1 * this.learningRate);
                ((LinearLayer) iLayer).weight.add(weightUpdate);
                biasUpdate = Matrix.multiplyValue(biasUpdate, (double) -1 * this.learningRate);
                ((LinearLayer) iLayer).bias.add(biasUpdate);

                index++;
            }
        }
        this.timestamp++;
    }

}
