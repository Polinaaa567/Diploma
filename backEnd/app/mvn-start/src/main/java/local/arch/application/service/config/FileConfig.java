package local.arch.application.service.config;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import local.arch.application.interfaces.config.IFileConfig;

public class FileConfig implements IFileConfig {
    private static final String BASE_STORAGE_PATH = "storage/";

    @Override
    public String saveImageFromBase64(String base64Image, String url) throws IOException {
        if (!base64Image.startsWith("data:image")) {
            throw new IllegalArgumentException("Неподдерживаемый формат изображения");
        }

        String[] parts = base64Image.split(",");
        String extension = parts[0].split("/")[1].split(";")[0];
        byte[] imageBytes = Base64.getDecoder().decode(parts[1]);

        Path uploadDir = Paths.get(BASE_STORAGE_PATH, url);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String filename;

        if (url.contains("events")) {
            filename = "event_" + UUID.randomUUID() + "." + extension;
        } else if (url.contains("news")) {
            String[] urlParts = url.split("/");
            String newsIDString = urlParts[urlParts.length - 1];
            Integer newsID = Integer.parseInt(newsIDString);

            filename = "news_" + newsID + "_" + UUID.randomUUID() + "." + extension;
        } else if (url.contains("certificates")) {
            String[] urlParts = url.split("/");
            String userIDString = urlParts[urlParts.length - 1];
            Integer userID = Integer.parseInt(userIDString);

            filename = "userID_" + userID + "_certificate_" + UUID.randomUUID() + "." + extension;
        } else {
            filename = "other" + UUID.randomUUID() + "." + extension;
        }

        Path filePath = uploadDir.resolve(filename);

        Files.write(filePath, imageBytes);

        return "" + url + "/" + filename;
    }

    @Override
    public void deleteImage(String imagePath) throws IOException {
        if (imagePath == null || imagePath.isEmpty()) {
            return;
        }
        Path fullPath = Paths.get(BASE_STORAGE_PATH, imagePath).normalize();

        if (Files.exists(fullPath)) {
            Files.delete(fullPath);

            Path directory = fullPath.getParent();

            if (Files.isDirectory(directory) && Files.list(directory).count() == 0) {
                Files.delete(directory);
            }
        }
    }

    @Override
    public BufferedImage loadTemplateSertificate() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("pattern/pattern_certificate.png")) {
            if(is == null) {
                Logger.getLogger(getClass().getName())
                            .log(Level.WARNING, "Шаблон сертификата не найден в ресурсах");
            }
            return ImageIO.read(is);
        }
    }

    @Override
    public String saveModifiedCertificate(BufferedImage image, Integer userID) throws IOException {
        Path outputDir = Paths.get(BASE_STORAGE_PATH, "certificates", String.valueOf(userID));
        Files.createDirectories(outputDir);

        String fileName = "certificate_" + UUID.randomUUID() + ".png";
        Path filePath = outputDir.resolve(fileName);

        ImageIO.write(image, "PNG", filePath.toFile());
        
        return "certificates/" + userID + "/" + fileName;
    }
}
