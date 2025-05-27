package local.arch.domain;

import local.arch.domain.image_change.TextToImage;

public class Factory {
    public static ITextToImage createTextToImage() {
        return new TextToImage();
    }
}
