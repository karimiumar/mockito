package com.umar.apps;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvSourceTest {

    @CsvSource({
            "India, 10",
            "Brazil, 15",
            "China, 35"
    })
    @ParameterizedTest
    void given_CsvSource_when_processed_then_success(String country, int rank) {
        assertThat(country).containsAnyOf("India", "Brazil", "China");
        assertThat(rank).isNotZero();
    }
}
