package ru.ifmo.se.Karlsson.Lab6;

import jdk.nashorn.internal.scripts.JO;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Auth extends JFrame {

    public Auth() {
        super();
    }

    public boolean   authPassed() {
        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();
        JComponent[] inputs = new JComponent[] {
                new JLabel("Логин:"),
                user,
                new JLabel("Пароль:"),
                pass
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Авторизация",
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (user.getText().equals("admin") && (new String(pass.getPassword())).equals("admin")) return true;
            JOptionPane.showMessageDialog(null, "Неверный пароль!", "Ошибка авторизации",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return false;
        }
    }

}
