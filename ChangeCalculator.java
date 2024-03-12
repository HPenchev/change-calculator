import java.math.BigDecimal;
import java.util.*;

public class ChangeCalculator {
    private static final SortedSet<Integer> MONEY_NOMINALS_IN_PENNIES =  new TreeSet<>(Collections.reverseOrder());
    static {
        MONEY_NOMINALS_IN_PENNIES.addAll(Arrays.asList(5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printNominals(getChangeByNominals(new BigDecimal(scanner.nextLine()), new BigDecimal(scanner.nextLine())));
    }

    private static void printNominals(SortedMap<Integer, Integer> nominals) {
        if(nominals.isEmpty()) {
            System.out.println("no change");
            return;
        }

        for (Map.Entry<Integer, Integer> entry : nominals.entrySet()) {
            int nominal = entry.getKey();
            int numberOfNominals = entry.getValue();

            if (nominal >= 100) {
                System.out.println(numberOfNominals + " x " + (nominal / 100) + "£");
            } else { // For pence
                System.out.println(numberOfNominals + " x " + nominal + "p");
            }
        }
    }

    private static SortedMap<Integer, Integer> getChangeByNominals(BigDecimal amount, BigDecimal price) {
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
