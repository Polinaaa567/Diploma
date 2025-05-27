package local.arch.domain.image_change;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import local.arch.domain.ITextToImage;
import local.arch.domain.entities.page.User;

public class TextToImage implements ITextToImage {

    Calendar calendar = Calendar.getInstance();

    @Override
    public BufferedImage addTextToImage(BufferedImage template, String eventName, User user) {

        int xEventName = 50;
        int yEventName = 335;

        String infoUser;
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("ru"));
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        
        String date = "«" + dayFormat.format(calendar.getTime()) + "» " 
                     + monthFormat.format(calendar.getTime()) + " " 
                     + yearFormat.format(calendar.getTime()) + "г.";
                     
        if (user.getPatronymic() != null && !user.getPatronymic().equals("")) {
            infoUser = user.getLastName() + " " + user.getName() + " " + user.getPatronymic();
        } else {
            infoUser = user.getLastName() + " " + user.getName();
        }

        BufferedImage modifiedImage = new BufferedImage(template.getWidth(), template.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = modifiedImage.createGraphics();
        g.drawImage(template, 0, 0, null);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString(eventName, xEventName, yEventName);
        g.drawString(infoUser, 50, 235);

        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString(date, 690, 417);
        g.drawString("Кемерово", 96, 417);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Кемеровский государственный университет", 300, 505);

        g.dispose();

        return modifiedImage;
    }
}
