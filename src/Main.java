import com.neuralnetwork.NeuralNetwork;
import com.neuralnetwork.activation.ReLU;
import com.neuralnetwork.layer.ILayer;
import com.neuralnetwork.layer.LinearLayer;
import com.neuralnetwork.loss.CrossEntropyLossOnLogits;
import com.neuralnetwork.loss.MSELoss;
import com.neuralnetwork.optimization.Adam;
import com.neuralnetwork.optimization.GradientDescent;
import com.utilities.DataLoader;
import com.utilities.Matrix;

public class Main {

    public static void main(String[] args) {
        testRegression();
        testClassification();
    }

    public static void testRegression(){
        Matrix X_Train = new Matrix(1, 5, new double[][]{
                {-4, -2, 0, 2, 4}
        });
        Matrix Y_Train = new Matrix(1, 5, new double[][]{
                {-4, -2, 0, 2, 4}
        });
        Matrix X_Test = new Matrix(1, 4, new double[][]{
                {-3, -1, 1, 3}
        });
        Matrix Y_Test = new Matrix(1, 4, new double[][]{
                {-3, -1, 1, 3}
        });
        int batchSize = 2;
        boolean shuffle = true;
        DataLoader dataLoader = new DataLoader(X_Train, Y_Train, batchSize, shuffle);

        NeuralNetwork neuralNetwork = new NeuralNetwork(new ILayer[]{
                new LinearLayer(1, 4),
                new ReLU(),
                new LinearLayer(4, 1)
        });


        MSELoss mseLoss = new MSELoss();

        double learningRate = 0.01;
        double beta1 = 0.9;
        double beta2 = 0.99;
        double epsilon = 0.00001;
        Adam adam = new Adam(neuralNetwork, learningRate, beta1, beta2, epsilon);

        int epochs = 1000;
        int printInterval = 100;
        NeuralNetwork.train(neuralNetwork, dataLoader, X_Test, Y_Test, mseLoss, adam, epochs, printInterval);
    }
    public static void testClassification(){
        Matrix X_Train = new Matrix(2, 4, new double[][]{
                {0, 1, 0, 1},
                {0, 0, 1, 1},
        });
        Matrix Y_Train = new Matrix(2, 4, new double[][]{
                {1, 0, 0, 1},
                {0, 1, 1, 0},
        });
        Matrix X_Test = new Matrix(2, 4, new double[][]{
                {0.1, 1, 0, 0.9},
                {0, 0.1, 0.9, 1},
        });
        Matrix Y_Test = new Matrix(2, 4, new double[][]{
                {1, 0, 0, 1},
                {0, 1, 1, 0},
        });
        int batchSize = 2;
        boolean shuffle = true;
        DataLoader dataLoader = new DataLoader(X_Train, Y_Train, batchSize, shuffle);

        NeuralNetwork neuralNetwork = new NeuralNetwork(new ILayer[]{
                new LinearLayer(2, 4),
                new ReLU(),
                new LinearLayer(4, 2)
        });

        CrossEntropyLossOnLogits crossEntropyLossOnLogits = new CrossEntropyLossOnLogits();

        double learningRate = 0.01;
        GradientDescent gradientDescent = new GradientDescent(neuralNetwork, learningRate);

        int epochs = 1000;
        int printInterval = 100;
        NeuralNetwork.train(neuralNetwork, dataLoader, X_Test, Y_Test, crossEntropyLossOnLogits, gradientDescent, epochs, printInterval);
    }
}
