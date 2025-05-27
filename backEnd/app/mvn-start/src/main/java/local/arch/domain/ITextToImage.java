package local.arch.domain;

import java.awt.image.BufferedImage;

import local.arch.domain.entities.page.User;

public interface ITextToImage { 
    public BufferedImage addTextToImage(BufferedImage template, String eventName, User user);
}