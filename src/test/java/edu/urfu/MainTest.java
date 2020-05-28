package edu.urfu;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void case1() {
        List<HeapOfCoins> coins = new Main().call(new String[]{"67", "3 2 5 4 10 5"});

        assertEquals(10, coins.get(0).getCoin().getValue());
        assertEquals(5, coins.get(0).getQuantity());

        assertEquals(3, coins.get(1).getCoin().getValue());
        assertEquals(4, coins.get(1).getQuantity());

        assertEquals(5, coins.get(2).getCoin().getValue());
        assertEquals(1, coins.get(2).getQuantity());

        assertEquals(3, coins.size());
    }

    @Test
    public void case2() {
        List<HeapOfCoins> coins = new Main().call(new String[]{"3", "2 1 1 3"});

        assertEquals(2, coins.get(0).getCoin().getValue());
        assertEquals(1, coins.get(0).getQuantity());

        assertEquals(1, coins.get(1).getCoin().getValue());
        assertEquals(1, coins.get(1).getQuantity());

        assertEquals(2, coins.size());
    }

    @Test
    public void case3() {
        List<HeapOfCoins> coins = new Main().call(new String[]{"3", "2 3 1 1"});

        assertEquals(1, coins.get(0).getCoin().getValue());
        assertEquals(3, coins.get(0).getQuantity());

        assertEquals(2, coins.get(1).getCoin().getValue());
        assertEquals(0, coins.get(1).getQuantity());

        assertEquals(2, coins.size());
    }

    @Test
    public void case4() {
        List<HeapOfCoins> coins = new Main().call(new String[]{"5", "2 1 1 1 3 1"});

        assertEquals(3, coins.get(0).getCoin().getValue());
        assertEquals(1, coins.get(0).getQuantity());

        assertEquals(2, coins.get(1).getCoin().getValue());
        assertEquals(1, coins.get(1).getQuantity());

        assertEquals(1, coins.get(2).getCoin().getValue());
        assertEquals(0, coins.get(2).getQuantity());

        assertEquals(3, coins.size());
    }

    @Test
    public void case5() {
        List<HeapOfCoins> coins = new Main().call(new String[]{"" + Integer.MAX_VALUE, "3 2 2 1"});

        assertEquals(2, coins.get(0).getCoin().getValue());
        assertEquals(1073741822, coins.get(0).getQuantity());

        assertEquals(3, coins.get(1).getCoin().getValue());
        assertEquals(1, coins.get(1).getQuantity());

        assertEquals(2, coins.size());
    }

}
