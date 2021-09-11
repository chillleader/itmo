package ru.ifmo.se.Karlsson.Lab5;

import java.util.Scanner;

public class Interactive {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Выбирает команду из строки и сохраняет ее вместе с аргументами в Commands, откуда ее потом можно исполнить
     *
     * @param args Если есть аргумент, то строка не читается из стандартного ввода, а берется из аргумента
     * @throws IncorrectCommandException Выбрасывается, если команда не распознана
     */
    public static void scan(String ... args) throws IncorrectCommandException {
        String s;
        if (args.length == 0) {
            while (!scanner.hasNext()) {
                try {
                    Thread.sleep(10);
                } catch (java.lang.InterruptedException e) {
                    System.out.println("InterruptedException");
                }
            }
            s = scanner.nextLine();
        }
        else s = args[0];

        //сначала обрабатываются все команды без аргументов
        if (s.contains("help")) {
            Commands.setLastCommand(Commands.help);
            return;
        }
        if (s.contains("old")) {
            Commands.setLastCommand(Commands.old);
            return;
        }
        if (s.contains("exit")) {
            Commands.setLastCommand(Commands.exit);
            return;
        }
        if (s.contains("ping")) {
            Commands.setLastCommand(Commands.ping);
            return;
        }

        //далее все команды с аргументами
        String arg;
        try {arg = s.substring(s.indexOf("{"));}
        catch (Exception e) {
            throw new IncorrectCommandException();
        }
        Commands.setLastArg(arg);

        if (s.contains("remove_lower")) {
            Commands.setLastCommand(Commands.remove_lower);
            return;
        }
        if (s.contains("add_if_max")) {
            Commands.setLastCommand(Commands.add_if_max);
            return;
        }
        if (s.contains("add_if_min")) {
            Commands.setLastCommand(Commands.add_if_min);
            return;
        }
        if (s.contains("import")) {
            Commands.setLastCommand(Commands.imprt);
            return;
        }

        throw new IncorrectCommandException();
    }

}
