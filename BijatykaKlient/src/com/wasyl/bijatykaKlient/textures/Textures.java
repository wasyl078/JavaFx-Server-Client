package com.wasyl.bijatykaKlient.textures;


import java.awt.image.BufferedImage;

import com.wasyl.bijatykaKlient.framework.Game;
import javafx.scene.image.Image;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class Textures {

    private BufferedImage spriteSheet = null;
    private BufferedImage level1Image;
    private BufferedImage lukaszPrawoImage;
    private BufferedImage lukaszLewoImage;
    private BufferedImage maciekLewoImage;
    private BufferedImage maciekPrawoImage;
    private BufferedImage botLewoImage;
    private BufferedImage botPrawoImage;
    private BufferedImage mieczLewoImage;
    private BufferedImage mieczPrawoImage;
    private BufferedImage mieczPionowoImage;
    private BufferedImage pistoletPrawo;
    private BufferedImage pistoletLewo;
    private BufferedImage pociskPrawo;
    private BufferedImage pociskLewo;
    private BufferedImage mieczSwietlnyAtakLewoImage;
    private BufferedImage mieczSwietlnyAtakPrawoImage;
    private BufferedImage mieczSwietlnyPionowoLewoImage;
    private BufferedImage mieczSwietlnyPionowoPrawoImage;
    private BufferedImage background1;
    private BufferedImage background2;
    private BufferedImage background3;

    public Textures() {
        SpriteSheetLoader loader = new SpriteSheetLoader();
        try {
            spriteSheet = loader.loadImage("obrazki/sprite.png");
            background1 = loader.loadImage("obrazki/background 1.png");
            background2 = loader.loadImage("obrazki/background 2.png");
            background3 = loader.loadImage("obrazki/background 3.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpriteSheet ss = new SpriteSheet(spriteSheet);
        //TUTAJ PISAĆ JAKIE OBRAZKI TRZEBA WYCIĄĆ
        level1Image = ss.grabImage(4, 1, 128, 128);
        lukaszPrawoImage = ss.grabImage(5, 1, 91, 128);
        lukaszLewoImage = ss.grabImage(6, 1, 91, 128);
        maciekLewoImage = ss.grabImage(7, 1, 94, 128);
        maciekPrawoImage = ss.grabImage(8, 1, 94, 128);
        mieczLewoImage = ss.grabImage(10, 1, 128, 65);
        mieczPrawoImage = ss.grabImage(9, 1, 128, 65);
        mieczPionowoImage = ss.grabImage(11, 1, 65, 128);
        pistoletPrawo = ss.grabImage(12, 1, 128, 89);
        pistoletLewo = ss.grabImage(13, 1, 128, 89);
        pociskPrawo = ss.grabImage(1, 1, 53, 33);
        pociskLewo = ss.grabImage(2, 1, 53, 33);
        mieczSwietlnyPionowoLewoImage = ss.grabImage(15, 1, 15, 128);
        mieczSwietlnyPionowoPrawoImage = ss.grabImage(14, 1, 15, 128);
        mieczSwietlnyAtakPrawoImage = ss.grabImage(2, 2, 90, 90);
        mieczSwietlnyAtakLewoImage = ss.grabImage(1, 2, 90, 90);
        botLewoImage = ss.grabImage(4, 2, 93, 128);
        botPrawoImage = ss.grabImage(3, 2, 93, 128);
    }


    //metoda skalująca obrazki (BufferedImage ---> JavaFx Image)
    public static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }


    //gettery poszczególnych obrazków
    public Image getLukaszPrawoImage() {
        Image bufImage = toFXImage(scale(lukaszPrawoImage, (int) (0.022396 * Game.screenWidth), (int) (0.059259 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getLukaszLewoImage() {
        Image bufImage = toFXImage(scale(lukaszLewoImage, (int) (0.022396 * Game.screenWidth), (int) (0.059259 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMaciekLewoImage() {
        Image bufImage = toFXImage(scale(maciekLewoImage, (int) (0.022396 * Game.screenWidth), (int) (0.059259 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMaciekPrawoImage() {
        Image bufImage = toFXImage(scale(maciekPrawoImage, (int) (0.022396 * Game.screenWidth), (int) (0.059259 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMieczLewoImage() {
        Image bufImage = toFXImage(scale(mieczLewoImage, (int) (0.066666 * Game.screenWidth), (int) (0.060185185 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMieczPrawoImage() {
        Image bufImage = toFXImage(scale(mieczPrawoImage, (int) (0.066666 * Game.screenWidth), (int) (0.060185185 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMieczPionowoImage() {
        Image bufImage = toFXImage(scale(mieczPionowoImage, (int) (0.03385416 * Game.screenWidth), (int) (0.1185185 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getPistoletPrawo() {
        Image bufImage = toFXImage(scale(pistoletPrawo, (int) (0.0520833 * Game.screenWidth), (int) (0.063888 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getPistoletLewo() {
        Image bufImage = toFXImage(scale(pistoletLewo, (int) (0.0520833 * Game.screenWidth), (int) (0.063888 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getPociskPrawo() {
        Image bufImage = toFXImage(scale(pociskPrawo, (int) (0.0078125 * Game.screenWidth), (int) (0.008333 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getPociskLewo() {
        Image bufImage = toFXImage(scale(pociskLewo, (int) (0.0078125 * Game.screenWidth), (int) (0.008333 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMieczSwietlnyAtakLewoImage() {
        Image bufImage = toFXImage(scale(mieczSwietlnyAtakLewoImage, (int) (0.098958 * Game.screenWidth), (int) (0.05864 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMieczSwietlnyAtakPrawoImage() {
        Image bufImage = toFXImage(scale(mieczSwietlnyAtakPrawoImage, (int) (0.098958 * Game.screenWidth), (int) (0.05864 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMieczSwietlnyPionowoLewoImage() {
        Image bufImage = toFXImage(scale(mieczSwietlnyPionowoLewoImage, (int) (0.0130208 * Game.screenWidth), (int) (0.197222 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getMieczSwietlnyPionowoPrawoImage() {
        Image bufImage = toFXImage(scale(mieczSwietlnyPionowoPrawoImage, (int) (0.0130208 * Game.screenWidth), (int) (0.197222 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getBotLewoImage() {
        Image bufImage = toFXImage(scale(botLewoImage, (int) (0.02421875 * Game.screenWidth), (int) (0.059259 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getBotPrawoImage() {
        Image bufImage = toFXImage(scale(botPrawoImage, (int) (0.02421875 * Game.screenWidth), (int) (0.059259 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getBackground1() {
        Image bufImage = toFXImage(scale(background1, (int) (1 * Game.screenWidth), (int) (1 * Game.screenHeight)), null);
        return bufImage;
    }


    public Image getBackground2() {
        Image bufImage = toFXImage(scale(background2, (int) (2 * Game.screenWidth), (int) (1 * Game.screenHeight)), null);
        return bufImage;
    }

    public Image getBackground3() {
        Image bufImage = toFXImage(scale(background3, (int) (2 * Game.screenWidth), (int) (1 * Game.screenHeight)), null);
        return bufImage;
    }
}
