package edu.urfu;

import java.util.List;

public class IntermediateResult {
    private final List<HeapOfCoins> heapsOfCoins;
    private final boolean isSuccess;

    IntermediateResult(List<HeapOfCoins> heapsOfCoins, boolean isSuccess) {
        this.heapsOfCoins = heapsOfCoins;
        this.isSuccess = isSuccess;
    }


    public List<HeapOfCoins> getHeapsOfCoins() {
        return heapsOfCoins;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

}