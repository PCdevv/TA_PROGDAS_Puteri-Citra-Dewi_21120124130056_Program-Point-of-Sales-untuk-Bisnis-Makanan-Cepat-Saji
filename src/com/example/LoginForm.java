package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JPanel rootPanel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;
    private final JFrame frameLogin = new JFrame("FastPOS | Login");

    public LoginForm() {
        setupPanel();
    }

    private void setupPanel() {
        frameLogin.setContentPane(rootPanel);
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogin.setSize(1920, 1080);
        frameLogin.setResizable(true);
        frameLogin.setVisible(true);

        User admin = new User("admin", "selalubenar");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (admin.authenticate(username, password)) {
                    openMainMenu();
                } else {
                    displayLoginError(username, password);
                }
            }});
    }

    private void openMainMenu() {
        JFrame frame = new JFrame("FastPOS");
        frame.setContentPane(new PosMenu().getRootPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(true);
        frame.setVisible(true);
        frameLogin.dispose();
        JOptionPane.showMessageDialog(frame, "Login SUCCESS", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayLoginError(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frameLogin, "Username or Password field cannot be EMPTY", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frameLogin, "ERROR, Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
