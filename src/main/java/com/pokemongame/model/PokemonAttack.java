package com.pokemongame.model;

public enum PokemonAttack {
    WEAK(1),
    MEDIUM(2),
    STRONG(3);

    private final int value;

    PokemonAttack(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public PokemonAttack upgrade() {
        if (this == STRONG) {
            return this;
        }
        return  PokemonAttack.values()[this.ordinal() + 1];
    }

    public static PokemonAttack reset() {
        return WEAK;
    }

    public static PokemonAttack fromValue(int value) {
        for (PokemonAttack attack : PokemonAttack.values()) {
            if (attack.value == value) {
                return attack;
            }
        }
        return WEAK;
    }
}
