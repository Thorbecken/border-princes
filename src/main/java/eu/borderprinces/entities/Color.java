package eu.borderprinces.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
    GREEN("\u001B[32m"),
    BLUE("\u001B[36m"),
    WHITE("\u001B[37m"),
    RED("\u001B[31m"),
    PURPLE("\u001B[35m"),
    YELLOW("\u001B[33m");

    private final String code;
}
