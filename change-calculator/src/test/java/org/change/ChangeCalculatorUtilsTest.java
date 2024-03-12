package org.change;

import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ChangeCalculatorUtilsTest {
    @Test
    public void testGetChangeByNominalsAndExpectValidResultsInDescendingOrder() {
        assertThat(ChaneCalculatorUtils.getChangeByNominals(new BigDecimal(20), new BigDecimal("5.50")))
                .containsExactlyEntriesOf(getExpectedNominals());
    }

    @Test
    public void testGetChangeByNominalsWithAMountLowerThanPriceAndExpectException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    ChaneCalculatorUtils.getChangeByNominals(new BigDecimal(2), new BigDecimal("5.50"));
                });
    }

    @Test
    public void testGetNominalsAsStringAndExpectValidString() {
        assertThat(ChaneCalculatorUtils.getNominalsAsString(getExpectedNominals())).isEqualTo(getExpectedString());
    }

    @Test
    public void testGetNominalsAsStringWithEmptyMapAndExpectNoChangeMessage() {
        assertThat(ChaneCalculatorUtils.getNominalsAsString(new TreeMap<>())).isEqualTo("no change");
    }

    private SortedMap<Integer, Integer> getExpectedNominals() {
        return new TreeMap<>(Collections.reverseOrder()){{
            put(1000, 1);
            put(200, 2);
            put(50, 1);
        }};
    }

    private String getExpectedString() {
        return
        "1 x 10£" + System.lineSeparator() +
        "2 x 2£" +  System.lineSeparator() +
        "1 x 50p";
    }
}
