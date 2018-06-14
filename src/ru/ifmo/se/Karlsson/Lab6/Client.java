package ru.ifmo.se.Karlsson.Lab6;


import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Main;

import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class Client implements Runnable {

    private static int SERVER_PORT = 50000;
    private static final int BUFFER_LENGTH = 65507;

    static boolean isConnected = false;

    public Client(int serverPort) {
        SERVER_PORT = serverPort;
    }

    @Override
    public void run() {

        try (DatagramSocket socket = new DatagramSocket(0)) {
            socket.setSoTimeout(2000);
            System.out.println("Клиент запущен");

            while (true) {
                try {
                    if (ClientGUI.askForRefresh) {
                        System.out.println("Обновление");
                        byte[] b = null;
                        send("old", socket);
                        b = receiveBytes(socket);
                        PriorityBlockingQueue<Human> q = deserialize(b);
                        ClientGUI.q = q;
                        ClientGUI.askForRefresh = false;
                        isConnected = true;
                        ClientGUI.connected.setVisible(false);
                        Main.gui.drawCircles();
                        ClientGUI.checkCircles();
                        Main.gui.repaint();
                    }
                    Thread.sleep(100);
                    //String s = readConsole();
                    //if (s == null) continue;
                    //send(s, socket);
                    /*isConnected = true;
                    if (s.toLowerCase().contains("exit")) {
                        System.out.println("Завершение работы клиента");
                        System.exit(0);
                    }
                    if (s.contains("old")) {
                        byte[] _b = receiveBytes(socket);
                        PriorityBlockingQueue<Human> _q = deserialize(_b);
                        Main.q = _q;
                        Main.runOld();
                        continue;
                    }
                    s = receive(socket);
                    System.out.println("Клиент: получен ответ " + s);*/
                } catch (SocketTimeoutException e) {
                    System.out.println("Сервер недоступен, попробуйте позже");
                    isConnected = false;
                    ClientGUI.askForRefresh = false;
                    ClientGUI.connected.setVisible(true);
                    ClientGUI.q = null;
                } catch (InterruptedException e) {}
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void send(String s, DatagramSocket ds) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(
                s.getBytes(),
                s.getBytes().length,
                InetAddress.getLocalHost(),
                SERVER_PORT
        );
        ds.send(datagramPacket);
    }

    private String receive(DatagramSocket s) throws IOException {
        DatagramPacket dp = new DatagramPacket(
                new byte[BUFFER_LENGTH],
                BUFFER_LENGTH
        );
        s.receive(dp);
        return new String(dp.getData());
    }

    private static byte[] receiveBytes(DatagramSocket s) throws IOException {
        DatagramPacket dp = new DatagramPacket(
                new byte[BUFFER_LENGTH],
                BUFFER_LENGTH
        );
        s.receive(dp);
        return dp.getData();
    }


    private static PriorityBlockingQueue<Human> deserialize(byte[] buffer) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer));
        return (PriorityBlockingQueue<Human>) ois.readObject();
    }
}
