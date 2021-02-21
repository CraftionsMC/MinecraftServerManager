/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.client.gui;

import net.craftions.msm.client.network.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Home {

    public static void show(){
        JFrame jf = new JFrame("MinecraftServerManager by Craftions.net | msm v1.0");
        JPanel content = new JPanel();

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu server = new JMenu("Server");
        JMenu mms = new JMenu("MMS");

        JMenuItem disconnect = new JMenuItem("Disconnect");
        JMenuItem connect = new JMenuItem("Connect");
        JMenuItem exit = new JMenuItem("Exit");

        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client.disconnect();
                    Login.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client.disconnect();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jf.setVisible(false);
                Login.show();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem list = new JMenuItem("List");
        JMenuItem create = new JMenuItem("Create");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem stop = new JMenuItem("Stop");
        JMenuItem start = new JMenuItem("Start");
        JMenuItem execute = new JMenuItem("Execute Command");

        JMenuItem about = new JMenuItem("About");
        JMenuItem issue = new JMenuItem("Open Issue");
        JMenuItem discord = new JMenuItem("Join Discord Server");

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "About Minecraft Server Manager (mms)\nVersion: 1.0\nAuthor: Ben Siebert (Craftions.net Development Team)\nLicense: Gnu GPLv3\n\nCopyright (c) 2020-2021 Ben Siebert, Craftions.net", "About MinecraftServerManager", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        issue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/CraftionsMC/MinecraftServerManager/issues/new"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
            }
        });

        discord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://discord.io/Craftions"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
            }
        });

        file.add(disconnect);
        file.add(connect);
        file.add(exit);

        server.add(list);
        server.add(create);
        server.add(delete);
        server.add(stop);
        server.add(start);
        server.add(execute);

        mms.add(about);
        mms.add(issue);
        mms.add(discord);

        menuBar.add(file);
        menuBar.add(server);
        menuBar.add(mms);

        // Frame content

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setJMenuBar(menuBar);
        jf.setResizable(false);
        jf.setSize(600, 300);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
