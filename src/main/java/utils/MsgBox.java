/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author huanl
 */
public class MsgBox {
    public static void alert(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg,"BeeC Gear", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean confirm(Component parent, String msg) {
        int result = JOptionPane.showConfirmDialog(parent, msg,"BeeC Gear",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    public static String prompt(Component parent, String msg) {
        return JOptionPane.showInputDialog(parent,msg,"BeeC Gear",JOptionPane.INFORMATION_MESSAGE);
    }
}
