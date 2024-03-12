package org.change;

import java.math.BigDecimal;
import java.util.*;

public class ChaneCalculatorUtils {
    private static final SortedSet<Integer> MONEY_NOMINALS_IN_PENNIES =  new TreeSet<>(Collections.reverseOrder());
    static {
        MONEY_NOMINALS_IN_PENNIES.addAll(Arrays.asList(5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1));
    }

    public static String getNominalsAsString(Map<Integer, Integer> nominals) {
        StringBuilder result = new StringBuilder();

        if(nominals.isEmpty()) {
            result.append("no change");
        }

        for (Map.Entry<Integer, Integer> entry : nominals.entrySet()) {
            int nominal = entry.getKey();
            int numberOfNominals = entry.getValue();

            if (nominal >= 100) {
                result.append(numberOfNominals + " x " + (nominal / 100) + "£");
            } else { // For pence
                result.append(numberOfNominals + " x " + nominal + "p");
            }

            result.append(System.lineSeparator());
        }

        return result.toString().trim();
    }

    public static SortedMap<Integer, Integer> getChangeByNominals(BigDecimal amount, BigDecimal price) {
        if(amount.compareTo(price) < 0) {
            throw  new IllegalArgumentException("Amount lower than price");
        }

        int changeInPennies = amount.multiply(new BigDecimal(100)).intValue() - price.multiply(new BigDecimal(100)).intValue();

        SortedMap<Integer, Integer> result = new TreeMap<>(Collections.reverseOrder());

        for (Integer nominal : MONEY_NOMINALS_IN_PENNIES) {
            if(changeInPennies == 0) {
                break;
            }

            if (nominal > changeInPennies) {
                continue;
            }

            int numberOfNominals = changeInPennies / nominal;

            result.put(nominal, numberOfNominals);
            changeInPennies -= (numberOfNominals * nominal);
        }

        return result;
    }

}
