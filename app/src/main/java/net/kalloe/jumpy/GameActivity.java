package net.kalloe.jumpy;

import android.view.MenuItem;

import net.kalloe.jumpy.scene.AbstractScene;
import net.kalloe.jumpy.scene.GameScene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import java.io.IOException;

public class GameActivity extends BaseGameActivity {

    //Variables
    public static final int CAMERA_WIDTH = 480;
    public static final int CAMERA_HEIGHT = 800;

    /**
     * This method defines the (AndEngine) engine options. It's run by the onCreate method first.
     * @return EngineOptions engine options of AndEngine.
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        //Sets the visible (screen) area, passing in the required screen resolution.
        Camera visibleArea = new EntityCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        //Scales the visible (screen) area to fill the entire screen.
        IResolutionPolicy sceneScaling = new FillResolutionPolicy();

        //Sets the screen rotation / mode to portrait (disables landscape rotation mode).
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                sceneScaling, visibleArea);

        //Enables the use of game audio / music.
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);

        //Sets the wake / lock options; the screen will never dim or turn off after x time.
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        //Improves the image rendering, the engine will now use 32-bit colors using small dot sof 16-bit colors.
        engineOptions.getRenderOptions().setDithering(true);

        Debug.i("Engine configured");

        return engineOptions;
    }

    /**
     * The onCreateResources is used to initialize game resources (graphics, fonts, sounds and music).
     * This method is called after the engine options are created.
     * @param pOnCreateResourcesCallback
     * @throws IOException
     */
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        //Instantiate the singleton ResourceManager, passing all the important objects.
        ResourceManager.getInstance().create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());

        //Load in the game resources from the ResourceManager.
        ResourceManager.getInstance().loadFont();
        ResourceManager.getInstance().loadGameAudio();
        ResourceManager.getInstance().loadGameGraphics();

        Debug.i("Successfully loaded all the game resources");

        //Call the callback, indication we are done loading the game resources.
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    /**
     * This method is called from the onCreateResourcesCallback object when the Finished method is called.
     * This method is used to create a scene (or screen) object or objects.
     * The finished method is called from the SceneCallBack to let the engine know the scene is created.
     * @param pOnCreateSceneCallback
     * @throws IOException
     */
    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        //Creates a new GameScene
        Scene scene = new GameScene();

        //Calls callback to tell the engine the scene is created.
        pOnCreateSceneCallback.onCreateSceneFinished(scene);

        Debug.i("Scene configured");
    }

    /**
     * This method is used to populate the scene with entities.
     * @param pScene
     * @param pOnPopulateSceneCallback
     * @throws IOException
     */
    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        //Casts the given scene to an Abstract Scene.
        AbstractScene scene = (AbstractScene) pScene;

        //Calls the populate method, which creates and loads the entities from the ResourceManager for the scene.
        scene.populate();

        //Tells the engine the scene is done populating (loading of resources for the scene).
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
