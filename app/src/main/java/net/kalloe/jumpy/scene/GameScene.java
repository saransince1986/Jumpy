package net.kalloe.jumpy.scene;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by Jamie on 28-2-2016.
 */
public class GameScene extends AbstractScene {

    @Override
    public void populate() {
        createBackground();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    /**
     * Creates a simple background entity containing several Sprite object entities, which are passed in
     * into a EntityBackground object which sets the background object as the background of the Scene.
     */
    private void createBackground() {
        Entity background = new Entity();

        //Load the cloud1 and cloud2 texture into the Sprite objects from the ResourceManager.
        Sprite cloud1 = new Sprite(200, 300, res.cloud1TextureRegion, vbom);
        Sprite cloud2 = new Sprite(300, 600, res.cloud2TextureRegion, vbom);

        //Attach the 2 Sprite (Entity) objects to the background entity/object.
        background.attachChild(cloud1);
        background.attachChild(cloud2);

        //Set an EntityBackground (simple entity) to the Scene passing in the background object,
        //Containing the 2 Sprite object / entities.
        setBackground(new EntityBackground(0.82f, 0.96f, 0.97f, background));
    }
}
