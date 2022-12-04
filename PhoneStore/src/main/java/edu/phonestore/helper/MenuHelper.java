/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.helper;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author Văn Dự
 */
public class MenuHelper extends MouseAdapter {

    JPanel jpanel;

    public MenuHelper(JPanel jpanel) {
        this.jpanel = jpanel;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        jpanel.setBackground(new Color(0, 51, 102));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        jpanel.setBackground(new Color(0, 51, 153));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        jpanel.setBackground(new Color(0, 102, 102));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        jpanel.setBackground(new Color(0, 102, 102));
    }

}
