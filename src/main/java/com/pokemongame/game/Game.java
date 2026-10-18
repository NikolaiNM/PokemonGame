package com.pokemongame.game;

import com.pokemongame.battle.Battle;
import com.pokemongame.model.BattleResult;
import com.pokemongame.model.Pokemon;
import com.pokemongame.model.Trainer;
import com.pokemongame.model.TrainerLevel;
import com.pokemongame.storage.GameStateStorage;
import com.pokemongame.view.GameView;

import static com.pokemongame.utils.GameUtils.SCANNER;


public class Game {
    private static Trainer trainer = null;
    private static Pokemon[] wildPokemons = new Pokemon[4];

    public static void main(String[] args) {
        GameView.showWelcome();

        while (true) {
            System.out.print("\n> ");
            String input = SCANNER.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String command = parts[0].toLowerCase();

            switch (command) {
                case "help":
                    handleHelpCommand();
                    break;

                case "new":
                    handleNewCommand(parts);
                    break;

                case "add":
                    handleAddCommand(parts);
                    break;

                case "status":
                    handleStatusCommand();
                    break;

                case "search":
                    handleSearchCommand();
                    break;

                case "attack":
                    handleAttackCommand(parts);
                    break;

                case "save":
                    handleSaveCommand();
                    break;

                case "load":
                    handleLoadCommand();
                    break;

                case "exit":
                        handleExitCommand();
                    break;

                default:
                    GameView.showError("Неизвестная команда. Введите 'help' для справки.");
            }
        }
    }

    private static void handleHelpCommand() {
        GameView.showHelp();
    }

    private static void handleNewCommand(String[] parts) {
        if (trainer != null) {
            GameView.showInfo("Тренер уже создан. Ваш тренер: " + trainer.getName());
            return;
        }

        if (parts.length != 2) {
            GameView.showError("Неверный формат. Введите 'new <ИмяТренераБезПробелов>'");
        } else {
            trainer = new Trainer(parts[1]);
            GameView.showSuccess("Тренер " + trainer.getName() + " создан");
        }
    }

    private static void handleAddCommand(String[] parts) {
        if (isTrainerMissing()) return;

        if (parts.length != 2) {
            GameView.showError("Неверный формат. Введите 'add <ИмяПокемонаБезПробелов>'");
            return;
        }

        Pokemon pokemon = new Pokemon(parts[1]);

        if (trainer.addPokemon(pokemon)) {
            int count = trainer.getActivePokemons().length;
            GameView.showSuccess("Покемон " + parts[1] + " добавлен. Всего " + count + " покемонов");

            if (count == 5) {
                GameView.showPokemonLimitReached();
            }
        } else {
            GameView.showError("Нельзя добавить больше 5 активных покемонов!");
        }
    }

    private static void handleStatusCommand() {
        if (isTrainerMissing()) return;

        GameView.showTrainerStatus(trainer);
    }

    private static void handleSearchCommand() {
        if (isTrainerMissing()) return;

        wildPokemons = Pokemon.searchWild();

        GameView.showWildPokemons(wildPokemons);
    }

    private static void handleAttackCommand(String[] parts) {
        if (isTrainerMissing()) return;

        if (wildPokemons == null) {
            GameView.showError("Найдите противников. Введите 'search'");
            return;
        }

        if (parts.length != 3) {
            GameView.showError("Неверный формат. Введите 'attack <ИмяВашегоПокемона> <IDПротивника>'");
            return;
        }

        String myPokemonName = parts[1];
        int enemyId;

        try {
            enemyId = Integer.parseInt(parts[2]) - 1;
        } catch (NumberFormatException e) {
            GameView.showError("ID должен быть целым числом");
            return;
        }

        if (enemyId >= wildPokemons.length || enemyId < 0) {
            GameView.showError("Выберите врага с ID от 1 до " + wildPokemons.length);
            return;
        }

        Pokemon myPokemon = trainer.findActivePokemonByName(myPokemonName);

        if (myPokemon == null) {
            GameView.showError("Покемон с именем '" + myPokemonName + "' не найден");
            return;
        }

        Pokemon enemy = wildPokemons[enemyId];

        GameView.showBattleStart(myPokemon, enemy);
        BattleResult result = Battle.fight(myPokemon, enemy);
        GameView.showBattleResult(myPokemon, result);

        if (result == BattleResult.PLAYER_WINS) {
            trainer.addWin();
            if (trainer.getLevel() == TrainerLevel.CHAMPION) {
                GameView.showSuccess("Поздравляем, вы достигли максимального уровня и прошли игру!");
                GameView.showInfo("Для выхода введите 'exit'");
            }
        }

        wildPokemons = null;
    }

    private static void handleSaveCommand() {
        if (isTrainerMissing()) return;

        boolean isSuccess = GameStateStorage.save(trainer);

        if (isSuccess) {
            GameView.showSuccess("Игра успешно сохранена!");
        } else {
            GameView.showError("Не удалось сохранить игру!");
        }
    }

    private static void handleLoadCommand() {
        Trainer loadedTrainer = GameStateStorage.load();
        if (loadedTrainer != null) {
            trainer = loadedTrainer;
            GameView.showGameLoaded(trainer);
            wildPokemons = null;
        } else {
            GameView.showInfo("Сохранение не найдено. Начните новую игру!");
        }
    }

    private static void handleExitCommand() {
        GameView.showGoodbye();
        System.exit(0);
    }

    private static boolean isTrainerMissing() {
        if (trainer == null) {
            GameView.showError("Тренер не создан");
            return true;
        }
        return false;
    }
}
