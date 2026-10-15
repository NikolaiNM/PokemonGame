package com.pokemongame;

import java.util.Scanner;


public class Game {
    private static Pokemon[] wildPokemons = new Pokemon[4];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Trainer trainer = null;

        System.out.println("==== Игра начинается ====");
        System.out.println("Введите 'help' для списка команд");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String command = parts[0].toLowerCase();

            switch (command) {
                case "help":
                    printHelp();
                    break;

                case "new":
                    if (trainer != null) {
                        System.out.println("Тренер уже создан");
                        break;
                    }
                    if (parts.length != 2) {
                        System.out.println("Неверный формат");
                        System.out.println("Введите 'new <ИмяТренераБезПробелов>'");
                    } else {
                        trainer = new Trainer(parts[1]);
                        System.out.println("Тренер " + trainer.getName() + " создан");
                    }
                    break;

                case "add":
                    if (trainer == null) {
                        System.out.println("Тренер не создан");
                        break;
                    }
                    if (parts.length != 2) {
                        System.out.println("Неверный формат");
                        System.out.println("Введите 'add <ИмяПокемонаБезПробелов>'");
                        break;
                    }
                    Pokemon pokemon = new Pokemon(parts[1]);
                    if (trainer.addPokemon(pokemon)) {
                        System.out.println("Покемон " + parts[1] + " добавлен. Всего " + trainer.getActivePokemons().length + " покемонов");
                        System.out.print((trainer.getActivePokemons().length == 5) ? "Вы достигли лимита покемонов\n" : "");
                    }
                    break;

                case "status":
                    if (trainer == null) {
                        System.out.println("Тренер не создан");
                        break;
                    } else {
                        System.out.print(trainer.getStatus());
                    }
                    break;

                case "search":
                    if (trainer == null) {
                        System.out.println("Тренер не создан");
                        break;
                    }
                    for (int i = 0; i < wildPokemons.length; i++) {
                        PokemonSpecies wildPokemon = PokemonSpecies.getRandom();
                        wildPokemons[i] = new Pokemon(wildPokemon.getRussianName(), wildPokemon.getBaseAttack());
                    }

                    for (int i = 0; i < wildPokemons.length; i++) {
                        System.out.println((i + 1) + " - " + wildPokemons[i]);
                    }
                    break;

                case "attack":
                    if (trainer == null) {
                        System.out.println("Тренер не создан");
                        break;
                    }

                    if (wildPokemons[0] == null) {
                        System.out.println("Найдите противников. Введите 'search'");
                        break;
                    }

                    if (parts.length != 3) {
                        System.out.println("Неверный формат");
                        System.out.println("Введите 'attack <ИмяВашегоПокемона> <IDПротивника>'");
                        break;
                    }

                    String myPokemonName = parts[1];
                    int enemyId;
                    try {
                        enemyId = Integer.parseInt(parts[2]) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("ID должен быть целым числом");
                        break;
                    }
                    if (enemyId >= wildPokemons.length || enemyId < 0) {
                        System.out.println("Выберите врага с ID от 1 до " + wildPokemons.length);
                        break;
                    }

                    Pokemon myPokemon = trainer.findActivePokemonByName(myPokemonName);
                    if (myPokemon == null) {
                        System.out.println("Покемон с именем '" + myPokemonName + "' не найден");
                        break;
                    }

                    Pokemon enemy = wildPokemons[enemyId];

                    System.out.println("Битва между \n" + myPokemon + " и " + enemy);
                    boolean isWin = Battle.fight(myPokemon, enemy);

                    if (isWin) {
                        myPokemon.win();
                        trainer.addWin();
                    } else {
                        myPokemon.lose();
                    }

                    wildPokemons = new Pokemon[4];
                    break;

                case "save":
                    if (trainer == null) {
                        System.out.println("Тренер не создан");
                    } else {
                        GameStateStorage.save(trainer);
                    }
                    break;

                case "load":
                    Trainer loadedTrainer = GameStateStorage.load();
                    if (loadedTrainer != null) {
                        trainer = loadedTrainer;
                        System.out.println("\nТекущий статус:");
                        System.out.print(trainer.getStatus());

                        wildPokemons = new Pokemon[4];
                    }
                    break;


                case "exit":
                    System.out.println("Спасибо за игру!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Неизвестная команда. Введите 'help' для справки.");
            }
        }
    }

    private static void printHelp() {
        System.out.println("\n========================= СПИСОК КОМАНД =========================");
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
}
