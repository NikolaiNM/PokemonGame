package com.pokemongame.model;

import static com.pokemongame.utils.GameUtils.RANDOM;


public class Pokemon {
    private static final int WINS_TO_LEVEL_UP = 3;
    private static final int WILD_POKEMONS = 4;

    private final String name;
    private PokemonLevel level;
    private PokemonAttack attack;
    private int countWins;
    private boolean isActive;
    private int totalWins = 0;

    public Pokemon(String name) {
        this.name = name;
        this.level = PokemonLevel.ONE;
        this.attack = PokemonAttack.WEAK;
        this.countWins = 0;
        this.isActive = true;
    }

    public Pokemon(String name, int attackValue) {
        this.name = name;
        this.level = PokemonLevel.values()[RANDOM.nextInt(2)];
        this.attack = PokemonAttack.fromValue(attackValue);
        this.countWins = 0;
        this.isActive = true;
    }

    public Pokemon(String name, PokemonLevel level, PokemonAttack attack, int countWins, int totalWins, boolean isActive) {
        this.name = name;
        this.level = level;
        this.attack = attack;
        this.countWins = countWins;
        this.totalWins = totalWins;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public PokemonLevel getLevel() {
        return level;
    }

    public PokemonAttack getAttack() {
        return attack;
    }

    public int getCountWins() {
        return countWins;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public static Pokemon[] searchWild() {
        Pokemon[] wildPokemons = new Pokemon[WILD_POKEMONS];

        for (int i = 0; i < WILD_POKEMONS; i++) {
            PokemonSpecies wildPokemon = PokemonSpecies.getRandom();
            wildPokemons[i] = new Pokemon(wildPokemon.getRussianName(), wildPokemon.getBaseAttack());
        }
        return wildPokemons;
    }

    public void win() {
        this.countWins++;
        this.totalWins++;
        this.attack = this.attack.upgrade();

        if (this.countWins >= WINS_TO_LEVEL_UP) {
            if (this.level != PokemonLevel.FIVE) {
                this.level = this.level.levelUp();
                this.attack = PokemonAttack.reset();
            }
            this.countWins = 0;
        }
    }

    public void lose() {
        this.isActive = false;
    }

    @Override
    public String toString() {
        if (isActive) {
            return name + " (уровень: " + level.getValue() + ", атака: " + attack.getValue() + ", всего побед: " + totalWins + ")";
        } else {
            return "";
        }
    }
}
