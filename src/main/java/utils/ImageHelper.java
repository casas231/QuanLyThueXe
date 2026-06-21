/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author ducanh123
 */
public class ImageHelper {
    
    public static Image getAppIcon() {
        try {
            URL urlIcon = ImageHelper.class.getResource("/image/kma.png");
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
    
    public static ImageIcon scaleImage(File imageFile, int width, int height) {
        try {
            BufferedImage orginalImage = ImageIO.read(imageFile);
            Image scaledImage = orginalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
