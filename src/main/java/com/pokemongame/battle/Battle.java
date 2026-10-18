package com.pokemongame.battle;

import com.pokemongame.model.BattleResult;
import com.pokemongame.model.Pokemon;

import static com.pokemongame.utils.GameUtils.RANDOM;


public class Battle {

    public static BattleResult fight(Pokemon playerPokemon, Pokemon enemyPokemon) {
        double playerPower = calculatePower(playerPokemon);
        double enemyPower = calculatePower(enemyPokemon);

        if (playerPower >= enemyPower) {
            playerPokemon.win();
            return BattleResult.PLAYER_WINS;
        } else {
            playerPokemon.lose();
            return BattleResult.PLAYER_LOSES;
        }
    }

    private static double calculatePower(Pokemon pokemon) {
        return pokemon.getLevel().getValue() * pokemon.getAttack().getValue() * 0.1 * (RANDOM.nextInt(100) + 1);

    }
}
