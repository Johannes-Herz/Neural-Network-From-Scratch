package nnfs.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class DataLoader {

    public Vector<Matrix> dataset;
    public int batchSize;
    public boolean shuffle;
    public List<Integer> indices;
    public DataLoader(Matrix X, Matrix Y, int batchSize, boolean shuffle){
        this.dataset = new Vector<Matrix>();
        this.dataset.add(X);
        this.dataset.add(Y);
        this.batchSize = batchSize;
        this.shuffle = shuffle;
        this.resetIndices();
    }

    private void resetIndices(){
        this.indices = new ArrayList<Integer>();
        for(int i = 0; i < this.dataset.get(0).columnCount; i++){
            this.indices.add(i);
        }
    }
    public int getBatchCount(){
        return (int) Math.ceil((double)this.dataset.get(0).columnCount / (double)this.batchSize);
    }
    public Vector<Matrix> getNextBatch() {
        if(this.indices.size() == 0){
            this.resetIndices();
        }
        if(this.indices.size() == this.dataset.get(0).columnCount && this.shuffle){
            Collections.shuffle(this.indices);
        }
        int remainingBatchSize = this.batchSize < this.indices.size() ? this.batchSize : this.indices.size();
        int[] sampleIndices = new int[remainingBatchSize];
        for(int i = 0; i < remainingBatchSize; i++){
            sampleIndices[i] = this.indices.remove(0);
        }

        Matrix x_batch = new Matrix(this.dataset.get(0).rowCount, remainingBatchSize);
        Matrix y_batch = new Matrix(this.dataset.get(1).rowCount, remainingBatchSize);

        x_batch.fill(new Matrix.EntrySupplier() {
            @Override
            public double supply(int row, int column) {
                return dataset.get(0).data[row][sampleIndices[column]];
            }
        });
        y_batch.fill(new Matrix.EntrySupplier() {
            @Override
            public double supply(int row, int column) {
                return dataset.get(1).data[row][sampleIndices[column]];
            }
        });

        Vector<Matrix> sampleBatch = new Vector<Matrix>();
        sampleBatch.add(x_batch);
        sampleBatch.add(y_batch);
        return sampleBatch;
    }

}
