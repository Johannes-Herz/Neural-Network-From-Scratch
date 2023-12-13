## Neural Network Implementation in Java

---

> This project provides a simple implementation of a neural network from scratch in Java. 
> Following components are currently implemented: 
>  - **Linear Layer**: Implementation of a Linear Layer, y = Wx+b
>  - **ReLU**: Rectified Linear Unit activation function for introducing non-linearity.
>  - **MSELoss**: Mean Squared Error Loss for regression tasks
>  - **CrossEntropyLossOnLogits**: Cross Entropy Loss for classification tasks, based on logits
>  - **DataLoader**: Utility for loading batches of a dataset, used for training
>  - **Gradient Descent**: Basic Gradient Descent optimization
>  - **Adam**: Adaptive Moment Estimation optimizer for gradient-based optimization.

### NeuralNetwork.class

---

Core implementation of this project is the **NeuralNetwork** class in the package nnfs.neuralnetwork.
It takes instances of ILayer such as LinearLayer or ReLU Activation to construct a neural network.
It provides a static **train** function utilizing ILoss, IOptimizer and DataLoader to train a network on a given train dataset.

Following code can be used to learn a basic regression task: 
```
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
```

Following code can be used to learn a basic classification task: 
```
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
```
