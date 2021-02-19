/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.client.gui.uielements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BasicButton extends JButton {

    public BasicButton(String title, String type){
        this.setText(title);
        if(type == "default"){
            this.setBackground(new Color(59, 89, 182));
        }else if(type == "danger"){
            this.setBackground(new Color(250, 192, 0));
        }else if(type == "error"){
            this.setBackground(new Color(201, 0, 0));
        }
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBorderPainted(false);
        this.setSelected(false);
        this.setFocusPainted(false);
    }
}
