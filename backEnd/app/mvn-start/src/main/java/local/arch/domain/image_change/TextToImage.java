package local.arch.domain.image_change;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import local.arch.domain.ITextToImage;
import local.arch.domain.entities.page.User;

public class TextToImage implements ITextToImage {

    Calendar calendar = Calendar.getInstance();

    static final int CANVAS_WIDTH = 1350;
    static final int CANVAS_HEIGHT = 1080;

    @Override
    public BufferedImage addTextToImage(BufferedImage template, String eventName, User user) {

        String infoUser;

        String date = dateFormaString();

        if (user.getPatronymic() != null && !user.getPatronymic().equals("")) {
            infoUser = user.getLastName() + " " + user.getName() + " " + user.getPatronymic();
        } else {
            infoUser = user.getLastName() + " " + user.getName();
        }

        BufferedImage certificate = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = certificate.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        g.setComposite(AlphaComposite.SrcOver);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Float(0, 0, CANVAS_WIDTH - 1, CANVAS_HEIGHT, 30, 30);
        g.setColor(new Color(243, 252, 255));
        g.fill(roundRectangle2D);

        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLUE);
        RoundRectangle2D roundRectangle = new RoundRectangle2D.Float(0, 0, CANVAS_WIDTH - 1, CANVAS_HEIGHT - 1, 30, 30);
        g.draw(roundRectangle);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("emojis/heart.png")) {
            if (is == null) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "emoji не найден в ресурсах");
            }

            BufferedImage emojiImage = ImageIO.read(is);
            if (emojiImage == null) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Не удалось прочитать изображение emoji");
            }

            int emojiWidth = 160;
            int emojiHeight = 140;

            int x = 100;
            int y = 170;

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

            g.drawImage(emojiImage, x, y, emojiWidth, emojiHeight, null);

        } catch (IOException n) {
        }

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("emojis/earth.png")) {
            if (is == null) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "emoji не найден в ресурсах");
            }

            BufferedImage emojiImage = ImageIO.read(is);
            if (emojiImage == null) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Не удалось прочитать изображение emoji");
            }


            int emojiWidth = 140;
            int emojiHeight = 140;

            int x = 200;
            int y = 700;

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

            g.drawImage(emojiImage, x, y, emojiWidth, emojiHeight, null);

        } catch (IOException n) {
        }
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("emojis/hands.png")) {
            if (is == null) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "emoji не найден в ресурсах");
            }

            BufferedImage emojiImage = ImageIO.read(is);
            if (emojiImage == null) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Не удалось прочитать изображение emoji");
            }

            int emojiWidth = 140;
            int emojiHeight = 100;

            int x = CANVAS_WIDTH - emojiWidth - 100;
            int y = 730;

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

            g.drawImage(emojiImage, x, y, emojiWidth, emojiHeight, null);

        } catch (IOException n) {
        }

        g.setComposite(AlphaComposite.SrcOver);

        certificate(g);

        // надпись "подтверждает, что"
        Font originalFont = new Font("Century", Font.PLAIN, 30);
        g.setFont(originalFont);

        AffineTransform transform = new AffineTransform();
        transform.scale(1.2, 1.0);

        Font scaledFont = originalFont.deriveFont(transform);
        g.setFont(scaledFont);

        g.setColor(new Color(16, 10, 64, 150));

        String text = "Подтверждает, что";
        java.awt.FontMetrics metrics = g.getFontMetrics(scaledFont);
        int textWidth = metrics.stringWidth(text);
        int x = (CANVAS_WIDTH - textWidth) / 2;

        g.drawString(text, x, 270);

        text = "Принял(а) участие в волонтёрском мероприятии";
        metrics = g.getFontMetrics(scaledFont);
        textWidth = metrics.stringWidth(text);
        x = (CANVAS_WIDTH - textWidth) / 2;
        g.drawString(text, x, 470);

        text = "Город:";
        metrics = g.getFontMetrics(scaledFont);
        textWidth = metrics.stringWidth(text);
        g.drawString(text, 50, 720);

        text = "Кемерово";
        metrics = g.getFontMetrics(scaledFont);
        int textWidth2 = metrics.stringWidth(text);

        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
        g.setColor(new Color(63, 37, 18, 100));
        g.setStroke(dashed);
        g.drawLine(50 + textWidth, 725, 50 + textWidth + textWidth2 + 10, 725);

        g.setColor(new Color(16, 10, 64));
        g.drawString(text, 50 + textWidth + 4, 720);

        g.setColor(new Color(16, 10, 64, 150));
        text = "Дата:";
        metrics = g.getFontMetrics(scaledFont);
        textWidth = metrics.stringWidth(text);
        g.drawString(text, (CANVAS_WIDTH / 2) + 150, 720);

        text = date;
        metrics = g.getFontMetrics(scaledFont);
        textWidth2 = metrics.stringWidth(text);

        g.setColor(new Color(63, 37, 18, 100));
        g.setStroke(dashed);
        g.drawLine((CANVAS_WIDTH / 2) + 150 + textWidth, 725, (CANVAS_WIDTH / 2) + 150 + textWidth + textWidth2 + 10,
                725);
        g.setColor(new Color(16, 10, 64));
        g.drawString(text, (CANVAS_WIDTH / 2) + 150 + textWidth + 4, 720);

        g.setColor(new Color(16, 10, 64, 150));
        text = "Организатор:";
        metrics = g.getFontMetrics(scaledFont);
        textWidth = metrics.stringWidth(text);

        String text2 = "Волонтёрский центр КемГУ | Добро.Центр КемГУ";
        textWidth2 = metrics.stringWidth(text2);
        x = (CANVAS_WIDTH - textWidth - textWidth2) / 2;

        g.drawString(text, x, CANVAS_HEIGHT - 200);

        g.setColor(new Color(63, 37, 18, 100));
        g.setStroke(dashed);
        g.drawLine(x + textWidth, CANVAS_HEIGHT - 195, x + textWidth + textWidth2 + 10, CANVAS_HEIGHT - 195);

        g.setColor(new Color(16, 10, 64));
        g.drawString(text2, x + textWidth + 2, CANVAS_HEIGHT - 200);

        // Инфорамация о пользователе
        g.setColor(new Color(63, 37, 18, 100));
        g.setStroke(dashed);
        g.drawLine(50, 375, CANVAS_WIDTH - 50, 375);

        text = infoUser;
        originalFont = new Font("Century", Font.ITALIC | Font.BOLD, 35);

        scaledFont = originalFont.deriveFont(transform);
        g.setFont(scaledFont);

        g.setColor(new Color(16, 10, 64));
        metrics = g.getFontMetrics(scaledFont);
        textWidth = metrics.stringWidth(text);
        x = (CANVAS_WIDTH - textWidth) / 2;

        g.drawString(text, x, 370);

        // название мероприятия
        g.setColor(new Color(63, 37, 18, 100));
        g.setStroke(dashed);
        g.drawLine(50, 575, CANVAS_WIDTH - 50, 575);

        text = eventName;
        g.setFont(scaledFont);
        g.setColor(new Color(16, 10, 64));

        metrics = g.getFontMetrics(scaledFont);
        textWidth = metrics.stringWidth(text);
        x = (CANVAS_WIDTH - textWidth) / 2;

        g.drawString(text, x, 570);

        g.dispose();

        return certificate;
    }

    private String dateFormaString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("ru"));
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        return "«" + dayFormat.format(calendar.getTime()) + "» "
                + monthFormat.format(calendar.getTime()) + " "
                + yearFormat.format(calendar.getTime()) + "г.";
    }

    private void certificate(Graphics2D g) {
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
        g.setColor(Color.BLUE);
        g.setStroke(dashed);
        g.drawLine(CANVAS_WIDTH / 4, 170, CANVAS_WIDTH - (CANVAS_WIDTH / 4), 170);

        String text = "Сертификат";
        Font originalFont = new Font("Century", Font.BOLD, 70);
        g.setFont(originalFont);

        AffineTransform transform = new AffineTransform();
        transform.scale(1.25, 1.0);

        Font scaledFont = originalFont.deriveFont(transform);
        g.setFont(scaledFont);

        g.setColor(new Color(16, 10, 64));
        java.awt.FontMetrics metrics = g.getFontMetrics(scaledFont);
        int textWidth = metrics.stringWidth(text);
        int x = (CANVAS_WIDTH - textWidth) / 2;

        g.drawString(text, x, 165);
    }
}
