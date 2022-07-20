package com.example.battleshipfx.helpz;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class ImageFix {

    public static Image cutImg(Image img, int x, int y, int w, int h) {
        PixelReader pixelReader = img.getPixelReader();
        return new WritableImage(pixelReader, x, y, w, h);
    }

}
