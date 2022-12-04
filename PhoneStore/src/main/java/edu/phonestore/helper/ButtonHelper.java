/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.helper;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Văn Dự
 */
public class ButtonHelper extends MouseAdapter {

    JButton button;

    public ButtonHelper(JButton button) {
        this.button = button;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!button.isEnabled()) {
            return;
        }
        button.setBackground(new Color(0, 51, 165));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!button.isEnabled()) {
            return;
        }
        button.setBackground(new Color(0, 51, 130));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!button.isEnabled()) {
            return;
        }
        button.setBackground(new Color(0, 102, 102));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!button.isEnabled()) {
            return;
        }
        button.setBackground(new Color(0, 102, 102));
    }

}
