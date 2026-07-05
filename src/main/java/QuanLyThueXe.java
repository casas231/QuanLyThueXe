
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.EventQueue;
import view.LoginFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ducanh123
 */
public class QuanLyThueXe {
    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame mainFrame = new LoginFrame();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
    }
}
