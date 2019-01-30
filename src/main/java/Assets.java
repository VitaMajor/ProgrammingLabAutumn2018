

import GUI.SpriteSheet;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 64, height = 64;

    public static BufferedImage grass, pit, selected, dangers, knight, sectoid, demon, mage, witch, warrior;

    public static void init() {
        SpriteSheet tileSheet = new SpriteSheet(ImageLoader.loadImage("textures/64x64.png"));

        grass = tileSheet.corp(width * 10, height * 4);
        pit = tileSheet.corp(0, 0);
        dangers = tileSheet.corp(width * 7, height * 4);
        selected = tileSheet.corp(width * 5, height * 10);
        knight = tileSheet.corp(width * 4, height * 7);
        sectoid = tileSheet.corp(0, height * 10);
        demon = tileSheet.corp(width * 13, height * 7);
        mage = tileSheet.corp(width * 13, height * 9);
        witch = tileSheet.corp(width * 7, height * 7);
        warrior = tileSheet.corp(width * 6, height * 7);
    }
}
