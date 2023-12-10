package nnfs.neuralnetwork;

import nnfs.neuralnetwork.layer.ILayer;
import nnfs.neuralnetwork.loss.ILoss;
import nnfs.neuralnetwork.optimization.IOptimizer;
import nnfs.utilities.DataLoader;
import nnfs.utilities.Matrix;

import java.util.Vector;

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

    public ILayer[] layerArray;

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

    public void calculateGradients(Matrix gradient){
        Matrix run = gradient;
        for(int i = this.layerArray.length - 1; i >= 0; i--){
            run = this.layerArray[i].backward(run);
        }
    }

    public static void train(NeuralNetwork neuralNetwork, Matrix X_Train, Matrix Y_Train, Matrix X_Test, Matrix Y_Test, ILoss loss, IOptimizer optimizer, int epochs, int printInterval){

        for(int epoch = 0; epoch < epochs; epoch++){

            Matrix YHat_Train = neuralNetwork.call(X_Train);
            double trainLossValue = loss.calculateLoss(Y_Train, YHat_Train);
            neuralNetwork.calculateGradients(loss.calculateGradient(Y_Train, YHat_Train));
            optimizer.step();

            if(epoch % printInterval == 0){
                System.out.println("Epoch: " + epoch + " - train loss: " + trainLossValue);
            }

            if(X_Test != null && Y_Test != null){
                Matrix YHat_Test = neuralNetwork.call(X_Test);
                double testLossValue = loss.calculateLoss(Y_Test, YHat_Test);
                if(epoch % printInterval == 0){
                    System.out.println("Epoch: " + epoch + " - test loss: " + testLossValue);
                }
            }


        }
    }

    public static void train(NeuralNetwork neuralNetwork, DataLoader dataLoader, Matrix X_Test, Matrix Y_Test, ILoss loss, IOptimizer optimizer, int epochs, int printInterval){

        for(int epoch = 0; epoch < epochs; epoch++){

            for(int batchIndex = 0; batchIndex < dataLoader.getBatchCount(); batchIndex++){
                Vector<Matrix> batch = dataLoader.getNextBatch();
                Matrix YHat = neuralNetwork.call(batch.get(0));
                // double lossValue = loss.calculateLoss(batch.get(1), YHat);
                neuralNetwork.calculateGradients(loss.calculateGradient(batch.get(1), YHat));
                optimizer.step();
            }

            Matrix YHat_Train = neuralNetwork.call(dataLoader.dataset.get(0));
            double trainLossValue = loss.calculateLoss(dataLoader.dataset.get(1), YHat_Train);

            if(epoch % printInterval == 0){
                System.out.println("Epoch: " + epoch + " - train loss: " + trainLossValue);
            }

            if(X_Test != null && Y_Test != null){
                Matrix YHat_Test = neuralNetwork.call(X_Test);
                double testLossValue = loss.calculateLoss(Y_Test, YHat_Test);
                if(epoch % printInterval == 0){
                    System.out.println("Epoch: " + epoch + " - test loss: " + testLossValue);
                }
            }
        }
    }

}
