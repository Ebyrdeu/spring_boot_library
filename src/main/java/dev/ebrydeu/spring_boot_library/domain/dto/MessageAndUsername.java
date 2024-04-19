package dev.ebrydeu.spring_boot_library.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record MessageAndUsername(Long id, LocalDate date, LocalDate lastChanged, String title,
                                 String Body, String userUserName) implements Serializable {
}