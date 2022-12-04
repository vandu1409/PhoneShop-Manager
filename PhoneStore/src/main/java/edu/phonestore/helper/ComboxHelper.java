/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.helper;

import edu.phonestore.model.Customer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Văn Dự
 */
public class ComboxHelper {

    public static void setSelectedValue(JComboBox comboBox, String value) {
      
        DefaultComboBoxModel cbxmodel = (DefaultComboBoxModel) comboBox.getModel();

        for (int i = 1; i < comboBox.getItemCount(); i++) {
            Object item = (Object) cbxmodel.getElementAt(i);
            String temp = String.valueOf(item);
            String text = temp.substring(0, temp.indexOf("-")).trim();
            System.out.println(item);
            if (text.equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }
}
