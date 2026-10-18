package com.pokemongame.view;

import com.pokemongame.model.BattleResult;
import com.pokemongame.model.Pokemon;
import com.pokemongame.model.Trainer;

public class GameView {
    public static void showBattleStart(Pokemon player, Pokemon enemy) {
        System.out.println("Битва между " + player.getName() + " и " + enemy.getName());
    }

    public static void showBattleResult(Pokemon player, BattleResult result) {
        switch (result) {
            case PLAYER_WINS:
                System.out.println("\uD83C\uDF89 Твой покемон " + player.getName() + " победил!"); // 🎉
                break;
            case PLAYER_LOSES:
                System.out.println("\uD83D\uDC80 покемон " + player.getName() + " проиграл!"); // 💀
                break;
        }
    }

    public static void showTrainerStatus(Trainer trainer) {
        System.out.println(trainer.getName() + " (уровень: " + trainer.getLevel().getValue() +
                ", всего побед: " + trainer.getTotalWins() + ")");

        Pokemon[] activePokemons = trainer.getActivePokemons();
        if (activePokemons.length == 0) {
            System.out.print("У вас нет активных покемонов\n");
        } else {
            System.out.print("Покемоны:\n");
            for (Pokemon activePokemon : activePokemons) {
                System.out.println(" - " + formatPokemon(activePokemon));
            }
        }
    }

    public static String formatPokemon(Pokemon pokemon) {
        return pokemon.getName() + " (уровень: " + pokemon.getLevel().getValue() +
                ", атака: " + pokemon.getAttack().getValue() + ", всего побед: " + pokemon.getTotalWins() + ")";
    }

    public static void showError(String message) {
        System.out.println("❌ " + message);
    }

    public static void showSuccess(String message) {
        System.out.println("✅ " + message);
    }

    public static void showInfo(String message) {
        System.out.println("ℹ️ " + message);
    }

    public static void showPokemonLimitReached() {
        System.out.println("❗ Вы достигли лимита покемонов");
    }

    public static void showHelp() {
        System.out.println("========================= СПИСОК КОМАНД =========================");
        System.out.println("new <Имя>         - Создать нового тренера");
        System.out.println("add <Имя>         - Поймать покемона (например, 'add Пикачу')");
        System.out.println("status            - Показать статус тренера и команды");
        System.out.println("search            - Найти 4-х диких покемонов для битвы");
        System.out.println("attack <Имя> <ID> - Атаковать врага (например, 'attack Пикачу 1')");
        System.out.println("save              - Сохранить игру");
        System.out.println("load              - Загрузить игру");
        System.out.println("exit              - Выйти из игры");
        System.out.println("=================================================================\n");
    }

    public static void showWelcome() {
        System.out.println("==== Игра начинается ====");
        System.out.println("Введите 'help' для списка команд");
    }

    public static void showGoodbye() {
        System.out.println("Спасибо за игру!");
    }

    public static void showWildPokemons(Pokemon[] wildPokemons) {
        System.out.println("Найдены дикие покемоны:");
        for (int i = 0; i < wildPokemons.length; i++) {
            System.out.println((i + 1) + " - " + formatPokemon(wildPokemons[i]));
        }
    }

    public static void showGameLoaded(Trainer trainer) {
        System.out.println("✅ Игра успешно загружена!\n");
        showTrainerStatus(trainer);
    }
}
