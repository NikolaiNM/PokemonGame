package com.pokemongame.model;

import com.pokemongame.view.GameView;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private static final int MAX_ACTIVE_POKEMONS = 5;

    private final String name;
    private TrainerLevel level;
    private List<Pokemon> pokemons;
    private int totalWins = 0;


    public Trainer(String name) {
        this.name = name;
        this.level = TrainerLevel.BEGINNER;
        this.pokemons = new ArrayList<>(MAX_ACTIVE_POKEMONS);
    }

    public String getName() {
        return this.name;
    }

    public TrainerLevel getLevel() {
        return this.level;
    }

    public void setLevel(TrainerLevel level) {
        this.level = level;
    }

    public int getTotalWins() {
        return this.totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public Pokemon getPokemon(int index) {
        return this.pokemons.get(index);
    }

    public List<Pokemon> getPokemons() {
        return this.pokemons;
    }

    public Pokemon[] getActivePokemons() {
        List<Pokemon> activePokemons = new ArrayList<>();

        for (Pokemon pokemon : this.pokemons) {
            if (pokemon.isActive()) {
                activePokemons.add(pokemon);
            }
        }

        return activePokemons.toArray(new Pokemon[0]);
    }

    public Pokemon findActivePokemonByName(String pokemonName) {
        for (Pokemon pokemon : this.pokemons) {
            if (pokemon.getName().equalsIgnoreCase(pokemonName) && pokemon.isActive()) {
                return pokemon;
            }
        }
        return null;
    }

    public boolean addPokemon(Pokemon pokemon) {
        int activePokemonCount = 0;
        for (Pokemon p : pokemons) {
            if (p.isActive()) {
                activePokemonCount++;
            }
        }

        if (activePokemonCount >= MAX_ACTIVE_POKEMONS) {
            return false;
        }

        pokemons.add(pokemon);
        return true;
    }

    public void addWin() {
        totalWins++;

        if (level != TrainerLevel.CHAMPION) {
            if (totalWins % 3 == 0) {
                this.level = this.level.nextLevel();
            }
        }
    }

    @Override
    public String toString() {
        return name + " (уровень: " + level.getValue() + ", всего побед: " + totalWins + ")\nПокемоны: " + pokemons;
    }
}
