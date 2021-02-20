/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.client.gui;

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

        JMenuItem about = new JMenuItem("About");
        JMenuItem issue = new JMenuItem("Open Issue");
        JMenuItem discord = new JMenuItem("Join Discord Server");

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

        mms.add(about);
        mms.add(issue);
        mms.add(discord);

        menuBar.add(file);
        menuBar.add(server);
        menuBar.add(mms);

        // Frame content

        jf.setJMenuBar(menuBar);
        jf.setResizable(false);
        jf.setSize(600, 300);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
