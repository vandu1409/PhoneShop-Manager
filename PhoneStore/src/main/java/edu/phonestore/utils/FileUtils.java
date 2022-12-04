/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author Văn Dự
 */
public class FileUtils {

    public static boolean saveLogo(File file) {
        File dir = new File("images");
        // Tạo thư mục nếu chưa tồn tại
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, file.getName());
        try {
            // Copy vào thư mục logos (đè nếu đã tồn tại)
            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static Image resize(Image img, int h, int w) {
        Image result = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return result;
    }

   
    public static ImageIcon readLogo(String fileName, int w, int h) {
        File path = new File("images", fileName);
        Image img = new ImageIcon(path.toString()).getImage();
        Image icon = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        return new ImageIcon(icon);
    }

    public static void deleteImage(String filename) throws IOException {
        try {
            Path path = Path.of("images", filename);
            Files.delete(path);
        } catch (Exception e) {
        }
    }
}
