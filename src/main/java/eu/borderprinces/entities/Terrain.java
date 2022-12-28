package eu.borderprinces.entities;

import lombok.Data;

@Data
public class Terrain {

    private final Integer row;
    private final Integer column;
    private final String icon;
}
