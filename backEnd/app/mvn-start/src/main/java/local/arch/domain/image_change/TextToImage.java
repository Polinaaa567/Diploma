package local.arch.domain.image_change;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.fontbox.afm.FontMetrics;

import local.arch.domain.ITextToImage;
import local.arch.domain.entities.page.User;

public class TextToImage implements ITextToImage {

    Calendar calendar = Calendar.getInstance();

    static final int CANVAS_WIDTH = 1350;
    static final int CANVAS_HEIGHT = 1080;

    @Override
    public BufferedImage addTextToImage(BufferedImage template, String eventName, User user) {

        int xEventName = 50;
        int yEventName = 335;

        String infoUser;

        String date = dateFormaString();

        if (user.getPatronymic() != null && !user.getPatronymic().equals("")) {
            infoUser = user.getLastName() + " " + user.getName() + " " + user.getPatronymic();
        } else {
            infoUser = user.getLastName() + " " + user.getName();
        }

        BufferedImage certificate = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = certificate.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        g.setComposite(AlphaComposite.SrcOver);

        // g.setComposite(AlphaComposite.Clear);
        // g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Float(0, 0, CANVAS_WIDTH - 1, CANVAS_HEIGHT, 30, 30);
        g.setColor(new Color(243, 252, 255));
        g.fill(roundRectangle2D);

        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLUE);
        RoundRectangle2D roundRectangle = new RoundRectangle2D.Float(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT - 1, 30, 30);
        g.draw(roundRectangle);

        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(3));
        g.drawLine(CANVAS_WIDTH / 4, 170, CANVAS_WIDTH - (CANVAS_WIDTH / 4), 170);
        
        Font originalFont = new Font("Arial", Font.BOLD, 70);
        g.setFont(originalFont);
       
        AffineTransform transform = new AffineTransform();
        transform.scale(1.2, 1.0);

        Font scaledFont = originalFont.deriveFont(transform);
        g.setFont(scaledFont);

        g.setColor(new Color(16, 10, 64));
        String text = "Сертификат";
        java.awt.FontMetrics metrics = g.getFontMetrics(scaledFont);
        int textWidth = metrics.stringWidth(text);

        int x = (CANVAS_WIDTH - textWidth) / 2;
        g.drawString(text, x, 165);

        // BufferedImage modifiedImage = new BufferedImage(template.getWidth(),
        // template.getHeight(),
        // BufferedImage.TYPE_INT_ARGB);
        // Graphics2D g = modifiedImage.createGraphics();
        // g.drawImage(template, 0, 0, null);

        // g.setFont(new Font("Arial", Font.BOLD, 20));
        // g.setColor(Color.BLACK);
        // g.drawString(eventName, xEventName, yEventName);
        // g.drawString(infoUser, 50, 235);

        // g.setFont(new Font("Arial", Font.PLAIN, 15));
        // g.drawString(date, 690, 417);
        // g.drawString("Кемерово", 96, 417);
        // g.setFont(new Font("Arial", Font.PLAIN, 15));
        // g.drawString("Кемеровский государственный университет", 300, 505);

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
}
