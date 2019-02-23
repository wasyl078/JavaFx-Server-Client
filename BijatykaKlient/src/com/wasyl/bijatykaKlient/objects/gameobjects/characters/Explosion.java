package com.wasyl.bijatykaKlient.objects.gameobjects.characters;

import com.wasyl.bijatykaKlient.objects.gameobjects.GameObject;
import com.wasyl.bijatykaKlient.textures.Textures;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion extends GameObject {

    Image grenadeImage;
    Image[] explosionsImage;
    private int timeCounter = 120;
    private int bufDivide;


    public Explosion(int x, int y, Textures textures) {
        super(x, y);
        this.explosionsImage = textures.getExplosions();
        this.grenadeImage = textures.getGrenadeLeft();
        calculatePosition();
    }

    @Override
    public void draw(GraphicsContext gc, int cpx, int cpy) {
        timeCounter--;
        if (timeCounter < 0) {
            timeCounter = 0;
            setDelete(true);
        }
        bufDivide = timeCounter / 20;

        gc.drawImage(explosionsImage[bufDivide], getPositionX() + cpx - explosionsImage[bufDivide].getWidth()*0.5 + grenadeImage.getWidth()*0.5, getPositionY() + cpy - explosionsImage[bufDivide].getHeight()*0.5 + grenadeImage.getHeight()*0.5);
    }

    private void calculatePosition() {

    }
}