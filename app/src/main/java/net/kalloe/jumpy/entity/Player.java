package net.kalloe.jumpy.entity;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 28-2-2016.
 */
public class Player extends TiledSprite {

    //Variables
    boolean dead = false;

    public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    /**
     * Returns the (playing) state of the Player object.
     * @return Boolean dead
     */
    public boolean isDead() {
        return this.dead;
    }

    /**
     * Sets the (playing) state of the Player object.
     * @param dead Boolean
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Mirrors / flips the displayed Player Sprite (tile) to the left.
     */
    public void turnLeft() {
        setFlippedHorizontal(true);
    }

    /**
     * Mirrors  / flips the displayed Player Sprite (tile) to the right.
     */
    public void turnRight() {
        setFlippedHorizontal(false);
    }

    /**
     * Sets the displayed Player Sprite tile to the first (0) image.
     */
    public void fly() {
        setCurrentTileIndex(0);
    }

    /**
     * Sets the displayed Player Sprite tile to the second (1) image.
     */
    public void fall() {
        setCurrentTileIndex(1);
    }

    /**
     * Sets the (playing) state of the Player to false.
     * Sets the displayed Sprite of the Player to the last sprite.
     */
    public void die() {
        this.setDead(true);
        setCurrentTileIndex(2);
    }
}