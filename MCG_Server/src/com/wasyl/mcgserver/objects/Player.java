package com.wasyl.mcgserver.objects;

import com.wasyl.mcgserver.general.Game;
import com.wasyl.mcgserver.objects.distance.DistanceGrenadeThrower;
import com.wasyl.mcgserver.objects.distance.DistancePistol;
import com.wasyl.mcgserver.objects.melee.ForceShield;
import com.wasyl.mcgserver.objects.melee.MeleeAxe;
import com.wasyl.mcgserver.objects.melee.MeleeLightSaber;
import com.wasyl.mcgserver.textures.Textures;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class Player extends GameObject {

    //obiekty
    private Textures textures;
    private GameObject bufGameObject;

    //związane z obrazkami
    private Image imageLeft;
    private Image imageRight;
    private double PlayerImageWidth;
    private double PlayerImageHeight;

    //związane z pozycją oraz stanem gracza
    private int playerNumber;
    private boolean falling = true;
    private boolean jumping = false;
    private double lastPosX = 0;
    private int direction = 1; //true - left       false - right
    private int characterImageNumber = 0;
    private int HP = 0;

    //związane z broniami
    private final MeleeLightSaber lightSaber;
    private final MeleeAxe axe;
    private final DistancePistol pistol;
    private final ForceShield forceShield;
    private final DistanceGrenadeThrower grenadeThrower;
    private int whichWeapon = 1;
    private int lastWeapon = 1;
    private int hittedCounter = 0;
    private int attackingCounter = 0;
    private int attacking = 0;
    private int directionToRecoil = 0;
    private boolean fallen = false;
    private boolean givePenalty = true;
    private int penaltyHPcounter = 100;
    private boolean onlyOnePenalty = true;

    //difoltowe
    private final Random generator = new Random();
    public final double defaultPlayerVelocity = 10;
    private final double gravity = 0.5;
    private final double maxSpeed = 30;


    //konstruktor klasy
    public Player(int x, int y, int playerNumber, ID id, Textures textures, int characterImageNumber) {
        super(x, y, id);
        this.textures = textures;
        setCharacterImageNumber(0);
        setPlayerNumber(playerNumber);
        this.lightSaber = new MeleeLightSaber(0, 0, ID.MeleeLightSaber, this, textures);
        this.axe = new MeleeAxe(0, 0, ID.MeleeAxe, this, textures);
        this.pistol = new DistancePistol(0, 0, ID.DistancePistol, this, textures);
        this.forceShield = new ForceShield(0, 0, ID.ForceShield, this, textures);
        this.grenadeThrower = new DistanceGrenadeThrower(0, 0, ID.DistanceGrenadeThrower, this, textures);
        setAlive("n");
    }


    //updatowanie pozycji gracza
    @Override
    public void update(LinkedList<GameObject> objects, ArrayList<Integer> sounds) {
        setPositionX(getPositionX() + getVelocityX());
        setPositionY(getPositionY() + getVelocityY());

        if (HP <= 0) {
            setPositionY(-300);

            if (getPositionX() > 1000)
                setPositionX(getPositionX() - 10);
            else if (getPositionX() < 920)
                setPositionX(getPositionX() + 10);
            else {
                setPositionX(960);

                fallen = false;
                onlyOnePenalty = true;
                HP = 0;
                setAlive("n");
            }
            return;
        }


        //grawitacja
        if (falling || jumping) {
            setVelocityY(getVelocityY() + gravity);
            if (getVelocityY() > maxSpeed) setVelocityY(maxSpeed);
        }

        if (getPositionX() - lastPosX < 0) direction = 1;
        else if (getPositionX() - lastPosX > 0) direction = 2;
        lastPosX = (int) getPositionX();

        collisions(objects);

        fallen = false;
        if (getPositionY() > Game.screenHeight + 4 * getPlayerImageHeight())
            if (onlyOnePenalty) {
                fallen = true;
                onlyOnePenalty = false;
                addPenaltyHP(100);
            }

        if (getPositionY() > 2000) {
            if (getPositionX() > 1000)
                setPositionX(getPositionX() - 20);
            else if (getPositionX() < 920)
                setPositionX(getPositionX() + 20);
            else {
                int addX = generator.nextInt(30);
                if (generator.nextBoolean()) addX *= -1;

                setPositionX(960 + addX);
                setPositionY(-100);
                fallen = false;
                onlyOnePenalty = true;
            }
        }

        if (fallen || givePenalty) {
            givePenalty = true;

            if (penaltyHPcounter >= 0) {
                penaltyHPcounter--;
                HP--;
                if (penaltyHPcounter == 0) {
                    givePenalty = false;
                }
            }
        }

        if (whichWeapon == 1) lightSaber.update(objects, sounds);
        else if (whichWeapon == 2) axe.update(objects, sounds);
        else if (whichWeapon == 3) pistol.update(objects, sounds);
        else if (whichWeapon == 4) forceShield.update(objects, sounds);
        else if (whichWeapon == 5) grenadeThrower.update(objects, sounds);
    }


    //wykrywanie kolizji z platformami
    private void collisions(LinkedList<GameObject> objects) {
        for (int i = 0; i < objects.size(); i++) {
            bufGameObject = objects.get(i);

            if (bufGameObject.getId() == ID.Platform) {

                //kolizja z podłogą
                if (getBounds().intersects(bufGameObject.getBounds())) {
                    setPositionY(bufGameObject.getPositionY() - PlayerImageHeight);
                    setVelocityY(0);
                    setFalling(false);
                    setJumping(false);
                } else setFalling(true);

                //kolizja z sufitem
                if (getBoundsTop().intersects(bufGameObject.getBounds())) {
                    setPositionY(bufGameObject.getPositionY() + ((Platform) bufGameObject).getHeight());
                    setVelocityY(0);
                } else setFalling(true);

                //kolizja ze ścianą (od lewej strony)
                if (getBoundsLeft().intersects(bufGameObject.getBounds())) {
                    setPositionX(getPositionX() + defaultPlayerVelocity);
                    setVelocityX(0);
                }

                //kolizja ze ścianą (od prawej strony)
                if (getBoundsRight().intersects(bufGameObject.getBounds())) {
                    setPositionX(getPositionX() - defaultPlayerVelocity);
                    setVelocityX(0);
                }
            }
        }
    }


    //ustalenie jakim obrazkiem jest ten gracz
    public void setCharacterImageNumber(int imageNumber) {
        if (imageNumber == this.characterImageNumber) return;
        if (imageNumber == 1) {
            PlayerImageHeight = textures.getLukaszLewoImage().getHeight();
            PlayerImageWidth = textures.getLukaszPrawoImage().getWidth();
        } else if (imageNumber == 2) {
            PlayerImageHeight = textures.getMaciekLewoImage().getHeight();
            PlayerImageWidth = textures.getMaciekPrawoImage().getWidth();
        } else if (imageNumber == 3) {
            PlayerImageHeight = textures.getBotLewoImage().getHeight();
            PlayerImageWidth = textures.getBotPrawoImage().getWidth();
        }

        setPositionX(500);
        setPositionY(200);
        setHP(1000);
        setAlive("t");
        fallen = false;
        givePenalty = false;
        penaltyHPcounter = 0;
        this.characterImageNumber = imageNumber;
    }


    //bounds
    @Override
    public Rectangle2D getBounds() /*Bottom */ {
        return new Rectangle2D((int) getPositionX() + PlayerImageWidth / 5, (int) getPositionY() + PlayerImageHeight * 3 / 4, PlayerImageWidth * 3 / 5, PlayerImageHeight / 4);
    }

    public Rectangle2D getBoundsTop() {
        return new Rectangle2D((int) getPositionX() + PlayerImageWidth / 5, (int) getPositionY(), PlayerImageWidth * 3 / 5, PlayerImageHeight / 4);
    }

    public Rectangle2D getBoundsLeft() {
        return new Rectangle2D((int) getPositionX(), (int) getPositionY() + PlayerImageHeight / 4, PlayerImageWidth / 5, PlayerImageHeight / 2);
    }

    public Rectangle2D getBoundsRight() {
        return new Rectangle2D((int) getPositionX() + PlayerImageWidth * 4 / 5, (int) getPositionY() + PlayerImageHeight / 4, PlayerImageWidth / 5, PlayerImageHeight / 2);
    }

    public Rectangle2D getBoundsCentral() {
        return new Rectangle2D((int) getPositionX(), (int) getPositionY(), PlayerImageWidth, PlayerImageHeight);
    }

    //gettery i settery
    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public int getCharacterImageNumber() {
        return characterImageNumber;
    }

    public int getDirection() {
        return direction;
    }

    public int getWhichWeapon() {
        return whichWeapon;
    }

    public void setWhichWeapon(int whichWeapon) {
        this.whichWeapon = whichWeapon;
    }

    public int getLastWeapon() {
        return lastWeapon;
    }

    public void setLastWeapon(int lastWeapon) {
        this.lastWeapon = lastWeapon;
    }

    public int getHittedCounter() {
        return hittedCounter;
    }

    public void setHittedCounter(int hittedCounter) {
        this.hittedCounter = hittedCounter;
    }

    public int getAttackingCounter() {
        return attackingCounter;
    }

    public void setAttackingCounter(int attackingCounter) {
        this.attackingCounter = attackingCounter;
    }

    public int getAttacking() {
        return attacking;
    }

    public void setAttacking(int attacking) {
        this.attacking = attacking;
    }

    public int getDirectionToRecoil() {
        return directionToRecoil;
    }

    public void setDirectionToRecoil(int directionToRecoil) {
        this.directionToRecoil = directionToRecoil;
    }

    public double getPlayerImageWidth() {
        return PlayerImageWidth;
    }

    public double getPlayerImageHeight() {
        return PlayerImageHeight;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        if(HP>1000)return;
        this.HP = HP;
    }

    public boolean isGivePenalty() {
        return givePenalty;
    }

    public void setGivePenalty(boolean givePenalty) {
        this.givePenalty = givePenalty;
    }

    public int getPenaltyHPcounter() {
        return penaltyHPcounter;
    }

    public void setPenaltyHPcounter(int penaltyHPcounter) {
        this.penaltyHPcounter = penaltyHPcounter;
    }

    public void addPenaltyHP(int additionHP) {
        setPenaltyHPcounter(getPenaltyHPcounter() + additionHP);
    }
}
