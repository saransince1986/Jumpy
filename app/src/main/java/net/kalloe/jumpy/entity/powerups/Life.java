package net.kalloe.jumpy.entity.powerups;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.kalloe.jumpy.entity.CollectableEntity;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.entity.Utils;
import net.kalloe.jumpy.shop.LifeData;
import net.kalloe.jumpy.shop.ShopData;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 16-7-2016.
 */
public class Life extends Sprite implements CollidableEntity, CollectableEntity {

    //Variables
    public static final String TYPE = "COLLECTABLE";
    private Body body;
    private ShopData shopData;

    public Life(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.shopData = new LifeData();
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        Utils.wrapAround(this);
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public ShopData getShopData() {
        return this.shopData;
    }

    @Override
    public void setShoData(ShopData data) {
        this.shopData = data;
    }

    @Override
    public void obtain(Player player) {
        this.setVisible(false);
        this.body.setLinearVelocity(new Vector2(0, -45));

        //If the health of the player is not full, the player will gain 1 health.
        if(player.getHealth() != 3) {
            player.setHealth((player.getHealth() + 1));
        } else {
            player.addCoins((shopData.getPrice()));
        }
    }
}
