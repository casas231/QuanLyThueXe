/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 *
 * @author ducanh123
 */
public class TitleImage {
    
    public static Image getAppIcon() {
        try {
            URL urlIcon = TitleImage.class.getResource("/image/kma.png");
            if (urlIcon != null) {
                return Toolkit.getDefaultToolkit().createImage(urlIcon);
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
