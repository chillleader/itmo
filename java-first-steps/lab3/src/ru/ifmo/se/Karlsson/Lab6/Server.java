package ru.ifmo.se.Karlsson.Lab6;

import ru.ifmo.se.Karlsson.Lab5.FileIO;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.Scanner;


public class Server {

    private static int SERVER_PORT = 50000;
    static final int BUFFER_LENGTH = 65507;
    private static SocketAddress client;
    public static DatagramChannel serverChannel;

    public static CollectionManager CM;

    static ServerGUI gui = new ServerGUI();

    private String response;

    public Server(int port) {
        SERVER_PORT = port;
    }

    public static void main(String args[]) {

        Auth auth = new Auth();
        auth.setVisible(true);
        if (!auth.authPassed()) System.exit(-1);
        auth.setVisible(false);
        gui.setVisible(true);

        if (args.length != 0) SERVER_PORT = Integer.parseInt(args[0]);
        try  {
            serverChannel = DatagramChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(SERVER_PORT));
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);
            buffer.clear();
            System.out.println("Сервер запущен на порте " + SERVER_PORT);
            CM = CollectionManager.INSTANCE;
            checkExit();

            while (true) {
                buffer.clear();
                client = serverChannel.receive(buffer);
                (new Thread(new ServerThread(client, new String(buffer.array())))).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkExit() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Scanner s = new Scanner(System.in);
                String a;
                while(true) {
                    a = s.nextLine();
                    if (a.toLowerCase().contains("exit")) {
                        FileIO f = new FileIO();
                        f.save();
                        System.out.println("Сервер завершает работу.");
                        System.exit(0);
                    }
                }
            }
        };
        (new Thread(r)).start();
    }
}
