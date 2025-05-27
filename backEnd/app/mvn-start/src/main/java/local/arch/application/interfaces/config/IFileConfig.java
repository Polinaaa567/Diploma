package local.arch.application.interfaces.config;

import java.io.IOException;
import java.awt.image.BufferedImage;


public interface IFileConfig {

  public String saveImageFromBase64(String base64Image, String url) throws IOException; 

  void deleteImage(String imagePath) throws IOException;

  public BufferedImage loadTemplateSertificate() throws IOException ;

  public String saveModifiedCertificate(BufferedImage image, Integer userID) throws IOException;
} 
