/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.client.gui;

import net.craftions.msm.client.gui.uielements.BasicButton;
import net.craftions.msm.client.network.Client;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Home {

    public static void show(){
        JFrame jf = new JFrame("MinecraftServerManager by Craftions.net | msm v1.0");
        JPanel content = new JPanel(null);

        JLabel productName = new JLabel("Minecraft Server Manager");
        JLabel hostFieldName = new JLabel("Host:");
        JLabel portFieldName = new JLabel("Port:");
        JLabel userFieldName = new JLabel("Name:");
        JLabel passFieldName = new JLabel("Pass:");

        BasicButton login = new BasicButton("Login", "default");
        JLabel version = new JLabel("msm v1.0");

        JTextField hostNameInput = new JTextField();
        JTextField portInput = new JTextField();
        JTextField userInput = new JTextField();
        JPasswordField passInput = new JPasswordField();

        productName.setForeground(Color.WHITE);
        productName.setBounds(0, 0, 600, 50);
        productName.setFont(new Font("X-Files", 0, 35));
        productName.setHorizontalAlignment(SwingConstants.CENTER);

        login.setForeground(Color.BLACK);
        login.setBounds(250, 260, 120, 30);
        login.setFont(new Font("X-Files", 0, 20));

        version.setForeground(Color.WHITE);
        version.setBounds(490,270,120,30);
        version.setFont(new Font("Arial", 0, 20));

        hostFieldName.setForeground(Color.WHITE);
        hostFieldName.setBounds(40, 100, 60, 25);
        hostFieldName.setFont(new Font("X-Files", 0, 20));
        hostFieldName.setHorizontalAlignment(SwingConstants.LEFT);

        portFieldName.setForeground(Color.WHITE);
        portFieldName.setBounds(40, 140, 60, 25);
        portFieldName.setFont(new Font("X-Files", 0, 20));
        portFieldName.setHorizontalAlignment(SwingConstants.LEFT);

        userFieldName.setForeground(Color.WHITE);
        userFieldName.setBounds(40, 180, 60, 25);
        userFieldName.setFont(new Font("X-Files", 0, 20));
        userFieldName.setHorizontalAlignment(SwingConstants.LEFT);

        passFieldName.setForeground(Color.WHITE);
        passFieldName.setBounds(40, 220, 60, 25);
        passFieldName.setFont(new Font("X-Files", 0, 20));
        passFieldName.setHorizontalAlignment(SwingConstants.LEFT);

        hostNameInput.setBounds(120, 100, 440, 25);
        hostNameInput.setHorizontalAlignment(SwingConstants.LEFT);

        portInput.setBounds(120, 140, 440, 25);
        portInput.setHorizontalAlignment(SwingConstants.LEFT);

        userInput.setBounds(120, 180, 440, 25);
        userInput.setHorizontalAlignment(SwingConstants.LEFT);

        passInput.setBounds(120, 220, 440, 25);
        passInput.setHorizontalAlignment(SwingConstants.LEFT);

        productName.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                productName.setForeground(Color.RED);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                productName.setForeground(Color.WHITE);
                super.mouseExited(e);
            }
        });

        version.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jf.setCursor(Cursor.HAND_CURSOR);
                version.setForeground(Color.ORANGE);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jf.setCursor(Cursor.DEFAULT_CURSOR);
                version.setForeground(Color.WHITE);
                super.mouseExited(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/CraftionsMC/MinecraftServerManager"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
                super.mouseClicked(e);
            }

        });

        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String host = hostNameInput.getText();
                String port = portInput.getText();
                String userName = userInput.getText();
                String password = new String(passInput.getPassword());
                if(!host.equals("") && !port.equals("") && !userName.equals("") && !password.equals("")){
                    password = DigestUtils.md5Hex(password);
                    System.out.println("Hashed password: " + password);
                    try {
                        if(Client.create(host, Integer.parseInt(port), userName, password)){
                            JOptionPane.showMessageDialog(null, "Successfully connected to the server.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            JOptionPane.showMessageDialog(null, "An error occurred. Maybe you have entered the wrong user credentials or the server is offline.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "An error occurred. Perhaps the server is offline.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        content.add(version);
        content.add(login);
        content.add(passFieldName);
        content.add(userFieldName);
        content.add(portFieldName);
        content.add(hostFieldName);
        content.add(portInput);
        content.add(userInput);
        content.add(passInput);
        content.add(hostNameInput);
        content.add(productName);
        content.setBackground(new Color(51, 51, 51));

        jf.setContentPane(content);
        jf.setSize(600, 340);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
