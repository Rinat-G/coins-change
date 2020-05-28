package edu.urfu;

public class Coin implements Comparable<Coin> {
    private final int value;
    private final int weight;

    public Coin(final int value, final int weight) {
        this.value = value;
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public float getRelativeValue() {
        return ((float) value) / weight;
    }

    @Override
    public String toString() {
        return "{Монета " +
                value + " р." +
                ", массой " + weight + " гр." +
                '}';
    }

    /*
    Сравнение по относительной стоимости (достоинство/масса). Если относительная стоимость одинакова,
    большей считается та монета, у которой выше стоимость. Данный подход позволяет выбрать меньшее количество монет
    при одинаковой массе размена.
    */
    @Override
    public int compareTo(Coin anotherCoin) {
        if (this.getRelativeValue() == anotherCoin.getRelativeValue()) {
            return this.getValue() - anotherCoin.getValue();
        }
        float distinction = this.getRelativeValue() - anotherCoin.getRelativeValue();

        /* Округление до целого значения в меньшую или большую сторону в зависимости от знака */
        if (distinction < 0) {
            return (int) Math.floor(distinction);
        } else {
            return (int) Math.ceil(distinction);
        }
    }
}
