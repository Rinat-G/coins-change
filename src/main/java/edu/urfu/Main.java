package edu.urfu;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;

public class Main {

    private long recursiveCalls = 0;

    public static void main(String[] args) {
        new Main().call(args);
    }

    public List<HeapOfCoins> call(String[] args) {
        int sum;
        List<Coin> coins;
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            /* Получаем входные данные из консоли */
            sum = getSumFromUserInput(sc);
            coins = getCoinsFromUserInput(sc);
        } else {
            /* Получаем входные данные из аргументов запуска */
            sum = getSumFromString(args[0]);
            coins = getCoinsFromString(args[1]);
        }

        /*
        Сортируем коллекцию монет по относительной стоимости (стоимость/масса) в убывающем порядке (от более ценной к менее)
        и преобразуем коллекию монет в коллекцию "кучек" монет. Кучка содержит информацию о монете и ее количестве.
        */
        List<HeapOfCoins> heapsOfCoins = coins.stream()
                .sorted(reverseOrder())
                .map(coin -> new HeapOfCoins(coin, 0))
                .collect(toList());

        /* Простая проверка на выполнимость задачи */
        if (sum < heapsOfCoins.get(heapsOfCoins.size() - 1).getCoin().getValue()) {
            System.out.println("====\nВведенная сумма к размену меньше наименьшей монеты. Решение невозможно.\n====");
            return null;
        }

        /* Вызов рекурсивного метода со стартовыми условиями. */
        IntermediateResult result = recursiveChangeFinder(sum, 0, heapsOfCoins);

        if (result.isSuccess()) {
            System.out.println("====\nРезультат:\n" + result.getHeapsOfCoins() + "\n====");
            /* Самопроверка */
            assert (result.getHeapsOfCoins().stream().mapToInt(heap -> heap.getCoin().getValue() * heap.getQuantity()).sum() == sum);
        } else {
            System.out.println("====\nРезультат:\nНевозможно выполнить размен введенной суммы заданными монетами\n====");
        }

        System.out.println("Количество рекурсивных вызовов: " + recursiveCalls);
        return result.getHeapsOfCoins();
    }


    private IntermediateResult recursiveChangeFinder(int sumToChange, int currentCoinPosition, List<HeapOfCoins> heapsOfCoins) {
        recursiveCalls++;
        int currentCoinValue = heapsOfCoins.get(currentCoinPosition).getCoin().getValue();

        /* Пытаемся всю текущую сумму разменять текущими монетами. */
        int maxCoinsForChange = sumToChange / currentCoinValue;

        /*
        Если остаток от деления равен нулю, значит нам удалось текущий остаток разменять текущей монетой и мы
        возвращаем управление, возвращая успешный результат записывая в него количество необходимых текущих монет
        для текущего размена
        */
        if (sumToChange % currentCoinValue == 0) {
            heapsOfCoins.get(currentCoinPosition).setQuantity(maxCoinsForChange);
            return new IntermediateResult(heapsOfCoins, true);
        }

        /*
        Если мы обнаруживаем, что мы дошли до конца списка доступных монет, значит текущая ветвь вычисления сдачи зашла
        в тупик. Возвращаем управление на предыдущий этап с неуспешным результатом.
        */
        if (currentCoinPosition == heapsOfCoins.size() - 1) {
            heapsOfCoins.get(currentCoinPosition).setQuantity(0);
            return new IntermediateResult(heapsOfCoins, false);
        }

        /*
        Так как на текущем уровне мы не смогли разменять сдачу текущими монетами, мы передаем управление на следующий
        уровень рекурсии, то есть пытаемся рзаменять оставшуюся сумму менее ценными монетами по соотношению (достоинстово/масса),
        увеличивая currentCoinPosition на 1.
        Получая отрицательный результат мы понимаем, что оставшимися типами монет разменять сумму не удалось, уменьшаем количество
        текущих монет, и оставшуюся сумму снова передаем на следующий уровень рекурсии.
        В случае успешного ответа от следующих уровней рекурсии, мы запоминаем текущее количество монет и возвращаем
        упавление предыдущему уровню с положительным результатом.
        */
        for (int currentCoinAmount = maxCoinsForChange; currentCoinAmount >= 0; currentCoinAmount--) {
            int sumForNextLevel = sumToChange - (currentCoinAmount * currentCoinValue);
            IntermediateResult intermediateResult = recursiveChangeFinder(sumForNextLevel, currentCoinPosition + 1, heapsOfCoins);

            if (intermediateResult.isSuccess()) {
                intermediateResult.getHeapsOfCoins().get(currentCoinPosition).setQuantity(currentCoinAmount);
                return intermediateResult;
            }
        }

        /*
        Перебрав все монеты на текущем уровне, если мы не получили успешный результат, мы передаем управление
        предыдущему уровню рекурсии с отрицательным результатом.
        */
        return new IntermediateResult(heapsOfCoins, false);
    }


    private int getSumFromUserInput(Scanner sc) {
        int sum = 0;
        boolean isInputCorrect = false;
        while (!isInputCorrect) {
            try {
                System.out.println("Введите сумму для сдачи: ");
                sum = sc.nextInt();
                if (sum <= 0) {
                    System.out.println("Сумма не может быть отрицательным числом или нулём.");
                    continue;
                }
                System.out.println("Сумма для сдачи: " + sum);
                isInputCorrect = true;
            } catch (InputMismatchException e) {
                System.out.println("Введено не числовое значение суммы. Попробуйте снова.");
            }
            sc.nextLine();
        }
        return sum;
    }

    private List<Coin> getCoinsFromUserInput(Scanner sc) {
        List<Coin> coins = new ArrayList<>();
        boolean isInputCorrect = false;
        label:
        while (!isInputCorrect) {
            System.out.println("Введите параметры монет в формате \"x1 y1 x2 y2\", где xi - достоинство монеты, а yi - её масса");
            String[] input = sc.nextLine().split(" ");
            try {
                for (int i = 0; i < input.length; i += 2) {
                    int value = parseInt(input[i]);
                    int weight = parseInt(input[i + 1]);
                    if (value <= 0 || weight <= 0) {
                        System.out.println("Масса и достоинство монеты не могут иметь отрицательные или нулевые значения. Попробуйте снова.");
                        continue label;
                    }
                    coins.add(new Coin(value, weight));
                }
                isInputCorrect = true;
            } catch (NumberFormatException e) {
                System.out.println("В стороке обнаружены не числовые значения. Попробуйте снова.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("В стороке обнаружено нечетное количество числовых значений. Попробуйте снова.");
            }
        }
        System.out.println("Используем монеты: " + coins);

        return coins;
    }

    private int getSumFromString(String stringSum) {
        int sum;
        try {
            sum = parseInt(stringSum);
            if (sum <= 0) {
                throw new RuntimeException("Сумма не может быть отрицательным числом или нулём.");
            }
            System.out.println("Сумма для сдачи: " + sum);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Введено не числовое значение суммы первым аргументом. Попробуйте снова.", e);
        }
        return sum;
    }

    private List<Coin> getCoinsFromString(String stringCoins) {
        List<Coin> coins = new ArrayList<>();
        String[] coinsStrings = stringCoins.split(" ");
        try {
            for (int i = 0; i < coinsStrings.length; i += 2) {
                int value = parseInt(coinsStrings[i]);
                int weight = parseInt(coinsStrings[i + 1]);
                if (value <= 0 || weight <= 0) {
                    throw new RuntimeException("Масса и достоинство монеты не могут иметь отрицательные или нулевые значения. Попробуйте снова.");
                }
                coins.add(new Coin(value, weight));
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("В стороке обнаружены не числовые значения. Попробуйте снова.", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("В стороке обнаружено нечетное количество числовых значений. Попробуйте снова.", e);
        }
        System.out.println("Используем монеты: " + coins);

        return coins;
    }

}
