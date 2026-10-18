package com.pokemongame.model;

public enum PokemonLevel {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    PokemonLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public PokemonLevel levelUp() {
        if (this == FIVE) {
            return this;
        }
        return PokemonLevel.values()[this.ordinal() + 1];
    }

    public static PokemonLevel fromValue(int value) {
        for (PokemonLevel level : PokemonLevel.values()) {
            if (level.getValue() == value) {
                return level;
            }
        }
        return ONE;
    }
}
