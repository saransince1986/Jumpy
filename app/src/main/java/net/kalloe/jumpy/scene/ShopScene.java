package net.kalloe.jumpy.scene;

import android.widget.Toast;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.SceneManager;
import net.kalloe.jumpy.shop.LifeData;
import net.kalloe.jumpy.shop.ShopData;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 16-4-2016.
 */
public class ShopScene extends AbstractScene implements MenuScene.IOnMenuItemClickListener {

    //Variables
    private IMenuItem itemShield, shopMenuItem;
    private MenuSceneTextItemDecorator soundMenuItem;
    private Text coinText;

    private final int lifeItem = 0;

    @Override
    public void populate() {

        //Creates a new MenuScene object and sets the background color.
        MenuScene menuScene = new MenuScene(camera);
        menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);

        //Initializes the Menu Items (passes the font, text and colors).
        itemShield = new ColorMenuItemDecorator(new TextMenuItem(lifeItem, res.font, "SHIELD", vbom), Color.CYAN, Color.WHITE);
        shopMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2, res.font, "POWER UP 1", vbom), Color.CYAN, Color.WHITE);

        //Adds the menu items to the game's menu scene.
        menuScene.addMenuItem(itemShield);
        menuScene.addMenuItem(shopMenuItem);

        //Enables animation of the game's menu scene.
        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(true);
        menuScene.setOnMenuItemClickListener(this);

        //Creates and attaches a player sprite into the game's menu scene.
//        Sprite player = new Sprite(240, 230, res.playerTextureRegion, vbom);
//        menuScene.attachChild(player);

        //Retrieves the player's coins and displays it with a sprite and text object.
        Sprite coinSprite = new Sprite(240, 600, res.coinTextureRegion, vbom);
        coinText = new Text(240, 550, res.font, String.valueOf(activity.getCoins()), vbom);

        //Attach all the text / sprite objects to the menu scene HUD.
        menuScene.attachChild(coinSprite);
        menuScene.attachChild(coinText);

        setChildScene(menuScene);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        ShopData shopItem = null;
        try {
            switch (pMenuItem.getID()) {
                case lifeItem:
                    shopItem = new LifeData();
                    if (res.activity.getCoins() >= shopItem.getPrice()) {
                        res.activity.setCoins((res.activity.getCoins() - shopItem.getPrice()));
                    }
                    return true;

                default:
                    return false;
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if(shopItem != null) {
                ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundCash);
                coinText.setText(String.valueOf(activity.getCoins()));
                showToast("1 " + shopItem.getName() + " was purchased for " + shopItem.getPrice() + " gold", Toast.LENGTH_LONG);
            }
        }

        return false;
    }

    @Override
    public void onBackKeyPressed() {
        super.onBackKeyPressed();
        SceneManager.getInstance().showMenuScene();
    }

    /**
     * Creates and shows a Toast message using the Android Toast widget.
     * A Toast is run on te UI Thread which every activity has.
     * @param text Text to be displayed by the Toast.
     * @param length Length of the Toast to be displayed.
     */
    public void showToast(final String text, final int length) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, length).show();
            }
        });
    }
}
