package ru.ifmo.se.Karlsson;

import ru.ifmo.se.Karlsson.Lab6.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Main {

    public static PriorityBlockingQueue<Human> q = new PriorityBlockingQueue<>();
    public static String file = "kek.xml";

    public static int NUMBER_OF_ACTIONS = 2;
    static List<String> actionList = new ArrayList<>();

    public static ClientGUI gui = new ClientGUI();

    public static void main(String[] args) {

        Client client = new Client(Integer.parseInt(args[0]));

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(client);

        gui.setVisible(true);


        /*Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                new FileIO().save(file);
            }});

        executorService.shutdown();
        */
    }

    public static void runOld() {

        Messages.setMessages();
        Human[] characters = new Human[4];
        characters[0] = new Karlsson(Math.random() * 100);
        characters[1] = new FrekenBok(Math.random() * 100);
        characters[2] = new LilBoy(Math.random() * 100);
        characters[3] = new Julius(Math.random() * 100);
        Human.pudding = new Pudding(5, Pudding.Type.Шоколадный, Pudding.Size.маленькие);

        Police.setNumberOfCrimes(312);
        Police.setMaxCrimes(630);
        Police.setQuality(0.8);

        Human.TakePudding tp = new Human.TakePudding();
        ((Karlsson) characters[0]).estimatePudding();
        ((Karlsson) characters[0]).talk(characters[2]);
        tp.act(characters[0]);
        tp.act(characters[3]);

        tp.act(characters[1]);

        try {
            characters[0].actDirect(characters[1]);
        } catch (NoActionsException e) {
            System.out.print(characters[0].name + "хотел" + (characters[0].isMale ? " " : "а ") +
                    ", но забыл" + (characters[0].isMale ? "" : "а") + ", что. ");
        }
        try {
            characters[2].actDirect(characters[3]);
        } catch (NoActionsException e) {
            System.out.print(characters[2].name + "хотел" + (characters[2].isMale ? " " : "а ") +
                    "что-то сделать, но забыл" + (characters[2].isMale ? "" : "а") + ", что. ");
        }

        System.out.println("\nНа сцену выходят новые герои!");

        actionList = new ArrayList<>();
        for (Human h : q) {
            h.actions = new Action[NUMBER_OF_ACTIONS];
            for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
                h.actions[i] = (Human human) -> act(human);
            }
            h.act();
        }
        Collections.shuffle(actionList);
        Stream<String> stream = actionList.stream();
        stream.forEach(System.out::print);
    }

    static void act(Human h) {
        String s = Messages.getRandomAction(h);
        actionList.add(s);
    }
}
