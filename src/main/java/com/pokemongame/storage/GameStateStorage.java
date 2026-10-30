package com.pokemongame.storage;

import com.pokemongame.model.*;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class GameStateStorage {
    private static final String SAVE_DIR = "saves";
    private static final String SAVE_FILE_NAME = "savegame.csv";

    /**
     * Сохраняет игру в CSV файл
     * @return true если сохранение прошло успешно
     * Формат:
     * Строка 1: ИмяТренера,УровеньТренера,ВсегоПобед
     * Строка 2+: ИмяПокемона,Уровень,Атака,СчетчикПобед,ВсегоПобед,Активен
     */
    public static boolean save(Trainer trainer) {
        File directory = new File(SAVE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, SAVE_FILE_NAME);

        try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
            writer.println(trainer.getName() + "," + trainer.getLevel().getValue() + "," + trainer.getTotalWins());

            for (Pokemon pokemon : trainer.getPokemons()) {
                writer.printf("%s,%d,%d,%d,%d,%b%n",
                        pokemon.getName(),
                        pokemon.getLevel().getValue(),
                        pokemon.getAttack().getValue(),
                        pokemon.getCountWins(),
                        pokemon.getTotalWins(),
                        pokemon.isActive()
                );
            }
            return true;

        } catch (Exception e) {
            System.err.println("DEBUG Ошибка при сохранении: " + e.getMessage());
            return false;
        }
    }

    /**
     * Загружает игру из CSV файла
     * @return объект Trainer или null если файл не найден
     */
    public static Trainer load() {
        File directory = new File(SAVE_DIR);
        File file = new File(directory, SAVE_FILE_NAME);

        if (!file.exists()) {
            System.err.println("DEBUG Файл не найден");
            return null;
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            if (!scanner.hasNextLine()) {
                System.err.println("DEBUG Нет данных");
                return null;
            }

            String[] trainerData = scanner.nextLine().split(",");
            if (trainerData.length != 3) {
                System.err.println("DEBUG Неверный формат сохранения тренера");
                return null;
            }

            Trainer loadedTrainer = new Trainer(trainerData[0]);
            int trainerLevelInt = Integer.parseInt(trainerData[1]);
            loadedTrainer.setLevel(TrainerLevel.fromValue(trainerLevelInt));
            loadedTrainer.setTotalWins(Integer.parseInt(trainerData[2]));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] pokemonData = line.split(",");

                if (pokemonData.length != 6) {
                    System.err.println("DEBUG Проблемный покемон: " + line);
                    continue;
                }

                String name = pokemonData[0];
                int levelInt = Integer.parseInt(pokemonData[1]);
                int attackInt = Integer.parseInt(pokemonData[2]);

                PokemonLevel level = PokemonLevel.fromValue(levelInt);
                PokemonAttack attack = PokemonAttack.fromValue(attackInt);

                int countWins = Integer.parseInt(pokemonData[3]);
                int totalWins = Integer.parseInt(pokemonData[4]);
                boolean isActive = Boolean.parseBoolean(pokemonData[5]);

                Pokemon pokemon = new Pokemon(name, level, attack, countWins, totalWins, isActive);

                loadedTrainer.addPokemon(pokemon);
            }

            return loadedTrainer;

        } catch (Exception e) {
            System.err.println("DEBUG Ошибка при загрузке: " + e.getMessage());
            return null;
        }
    }
}
