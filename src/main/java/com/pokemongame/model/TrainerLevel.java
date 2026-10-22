package com.pokemongame.model;

public enum TrainerLevel {
    BEGINNER(1),
    ADVANCED(2),
    EXPERT(3),
    MASTER(4),
    CHAMPION(5);

    private final int value;

    TrainerLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public TrainerLevel nextLevel() {
        if (this == CHAMPION) {
            return this;
        }
        return TrainerLevel.values()[this.ordinal() + 1];
    }

    public static TrainerLevel fromValue(int value) {
        for (TrainerLevel level : TrainerLevel.values()) {
            if (level.getValue() == value) {
                return level;
            }
        }
        return BEGINNER;
    }
}
