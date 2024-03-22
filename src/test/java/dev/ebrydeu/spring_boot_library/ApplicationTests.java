package dev.ebrydeu.spring_boot_library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        boolean s = true;
        assertThat(s).isTrue();
    }

}
