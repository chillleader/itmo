package ru.ifmo.se.Karlsson.Lab6;

import ru.ifmo.se.Karlsson.Lab5.Commands;
import ru.ifmo.se.Karlsson.Lab5.IncorrectCommandException;
import ru.ifmo.se.Karlsson.Lab5.Interactive;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerThread implements Runnable {

    private SocketAddress client;
    private DatagramChannel serverChannel;
    private CollectionManager CM;
    private String response;
    private String command;

    public ServerThread(SocketAddress socketAddress, String command) {
        client = socketAddress;
        this.command = command;
        CM = Server.CM;
    }

    @Override
    public void run() {

        ByteBuffer buffer = ByteBuffer.allocate(Server.BUFFER_LENGTH);
        System.out.println("Подключен клиент");

        try {
            System.out.println("Получена команда " + command);
            if (command.toLowerCase().contains("exit")) {
                System.out.println("Отключен клиент");
                return;
            }
            if (command.contains("old")) {
                send(CM.serialize());
                return;
            }
            Interactive.scan(command);
            command = Commands.doLast();
            response = command.trim();
            send(response);
        } catch (IncorrectCommandException e) {
            send("Некорректная команда. Введите help для просмотра списка команд.");
        } catch (IOException e) {
            System.out.println("Ошибка отправки данных");
        }

    }

    private void send(String s) {

        try {
            ByteBuffer buffer = ByteBuffer.allocate(Server.BUFFER_LENGTH);
            buffer.clear();
            buffer.put(s.getBytes());
            buffer.flip();
            Server.serverChannel.send(buffer, client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(byte[] bytes) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(Server.BUFFER_LENGTH);
            buffer.clear();
            buffer.put(bytes);
            buffer.flip();
            Server.serverChannel.send(buffer, client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
