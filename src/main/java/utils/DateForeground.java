/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;

/**
 *
 * @author ducanh123
 */
public class DateForeground {
    
    public static void changeForeground(JDateChooser dateChooser) {
        dateChooser.getDateEditor().setEnabled(false);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
        editor.setDisabledTextColor(new Color(204, 204, 204));
    }
}
