package edu.urfu;

public class HeapOfCoins {
    private final Coin coin;
    private int quantity;

    HeapOfCoins(Coin coin, int quantity) {
        this.coin = coin;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Coin getCoin() {
        return coin;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "{Монета " +
                coin.getValue() + "р. - " +
                +quantity + "шт." +
                '}';
    }
}
