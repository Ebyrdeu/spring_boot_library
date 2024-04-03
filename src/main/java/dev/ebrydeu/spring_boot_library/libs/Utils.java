package dev.ebrydeu.spring_boot_library.libs;

import java.util.function.Consumer;

public class Utils {
    private Utils(){}
    public static <T> void isNullable(Consumer<T> consumer, T value) {
        if (value != null) consumer.accept(value);
    }
}
