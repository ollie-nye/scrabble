package assetmanager;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/**
 * @author Asid Khan
 * @version 1.0
 */
public class assetManager {

    public AssetManager manager = new AssetManager();


    // graphics for the game/play screen
    public static final AssetDescriptor<Texture> boardBackground =
            new AssetDescriptor<Texture>("graphics/BoardScreen/BoardBackground.png", Texture.class);

    public static final AssetDescriptor<TextureAtlas> gameButtonPack =
            new AssetDescriptor<TextureAtlas>("graphics/BoardScreen/gameButtons.pack", TextureAtlas.class);

    public static final AssetDescriptor<Texture> gameButtons =
            new AssetDescriptor<Texture>("graphics/BoardScreen/gameButtons.png", Texture.class);

    // graphics needed for the main menu
    public static final AssetDescriptor<Texture> mainBackground =
            new AssetDescriptor<Texture>("graphics/MainMenu/mainBackground.png", Texture.class);

    public static final AssetDescriptor<TextureAtlas> mainMenuButtonPack =
            new AssetDescriptor<TextureAtlas>("graphics/MainMenu/MainMenu.pack", TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> texturesTemp =
            new AssetDescriptor<TextureAtlas>("graphics/MainMenu/TexturesTemp.pack", TextureAtlas.class);

    // graphics for settings menu
    public static final AssetDescriptor<Texture> sliderBar =
            new AssetDescriptor<Texture>("graphics/SettingsMenu/bar.png", Texture.class);

    public static final AssetDescriptor<Texture> sliderKnob =
            new AssetDescriptor<Texture>("graphics/SettingsMenu/knob.png", Texture.class);

    public static final AssetDescriptor<Texture> settingsBackground =
            new AssetDescriptor<Texture>("graphics/SettingsMenu/SettingsMenuBackground.png", Texture.class);

    
 // graphics for help menu
    public static final AssetDescriptor<Texture> helpBackground =
            new AssetDescriptor<Texture>("graphics/HelpMenu/HelpBackground.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img1 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img1.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img2 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img2.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img3 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img3.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img4 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img4.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img5 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img5.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img6 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img6.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img7 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img7.png", Texture.class);
    
    public static final AssetDescriptor<Texture> img8 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/img8.png", Texture.class);

    
    public static final AssetDescriptor<Texture> circle =
            new AssetDescriptor<Texture>("graphics/HelpMenu/circle.png", Texture.class);
    
    public static final AssetDescriptor<Texture> circle1 =
            new AssetDescriptor<Texture>("graphics/HelpMenu/circle1.png", Texture.class);
    
    //graphics for results screen
    public static final AssetDescriptor<Texture> resultsBackground =
            new AssetDescriptor<Texture>("graphics/ResultsScreen/Background.png", Texture.class);
    
    public static final AssetDescriptor<Texture> victory =
            new AssetDescriptor<Texture>("graphics/ResultsScreen/victory.png", Texture.class);
    
    // graphics for loading screen
    public static final AssetDescriptor<Texture> logo =
            new AssetDescriptor<Texture>("graphics/LoadingScreen/logo.png", Texture.class);
    public static final AssetDescriptor<Texture> logoLoaded =
            new AssetDescriptor<Texture>("graphics/LoadingScreen/logoRed.png", Texture.class);
    public static final AssetDescriptor<Texture> logoWhite =
            new AssetDescriptor<Texture>("graphics/LoadingScreen/logoWhite.png", Texture.class);

    // task window icon for game
    public static final AssetDescriptor<Texture> icon =
            new AssetDescriptor<Texture>("graphics/icon.png", Texture.class);


    // main clicking sound for menus
    public static final AssetDescriptor<Sound> mainClick =
            new AssetDescriptor<Sound>("sounds/click02.wav", Sound.class);

    // Pick Up Sound
    public static final AssetDescriptor<Sound> click1 =
            new AssetDescriptor<Sound>("sounds/Tile_PickUp.mp3", Sound.class);
    //Place In Sound
    public static final AssetDescriptor<Sound> click6 =
            new AssetDescriptor<Sound>("sounds/Tile_Placed_in.mp3", Sound.class);
    //Change Player Sound
    public static final AssetDescriptor<Sound> click11 =
            new AssetDescriptor<Sound>("sounds/Change Player.mp3", Sound.class);
    //Start Game Sound
    public static final AssetDescriptor<Sound> click12 =
            new AssetDescriptor<Sound>("sounds/Start Game.wav", Sound.class);
    // main theme for game
    public static final AssetDescriptor<Music> mainMusic =
            new AssetDescriptor<Music>("sounds/Flintstones.mp3", Music.class);
    //Time Countdown
    public static final AssetDescriptor<Sound> countdownTimer =
            new AssetDescriptor<Sound>("sounds/time_countdown.mp3", Sound.class);
    //Time Up
    public static final AssetDescriptor<Sound> timesUp = 
    			new AssetDescriptor<Sound>("sounds/Times_up.mp3",Sound.class);
    //End Screen
    		//Score Increment sound
    public static final AssetDescriptor<Sound> scoreInc =
            new AssetDescriptor<Sound>("sounds/score_increment.mp3", Sound.class);
    		//Final Scores
    public static final AssetDescriptor<Sound> finalScores =
            new AssetDescriptor<Sound>("sounds/final_score.mp3", Sound.class);
    		// Fanfare
    public static final AssetDescriptor<Sound> winFanfare =
            new AssetDescriptor<Sound>("sounds/fanfare.mp3", Sound.class);
  
    // the font
    public static final AssetDescriptor<BitmapFont> PlayTime =
            new AssetDescriptor<BitmapFont>("fonts/PlayTime.fnt", BitmapFont.class);


    public void load()
    {
        this.loadTextures();
        this.loadFonts();
        this.loadMusic();
        this.loadSounds();
        this.loadTextureAtlas();
    }


    private void loadTextures() {

        manager.load(boardBackground);
        manager.load(gameButtons);
        manager.load(mainBackground);
        manager.load(helpBackground);
        manager.load(sliderBar);
        manager.load(sliderKnob);
        manager.load(settingsBackground);
        manager.load(img1);
        manager.load(img2);
        manager.load(img3);
        manager.load(img4);
        manager.load(img5);
        manager.load(img6);
        manager.load(img7);
        manager.load(img8);
        manager.load(circle);
        manager.load(circle1);
        manager.load(icon);
        manager.load(logo);
        manager.load(logoWhite);
        manager.load(logoLoaded);
        manager.load(resultsBackground);
        manager.load(victory);
    }

    private void loadTextureAtlas(){
        manager.load(gameButtonPack);
        manager.load(mainMenuButtonPack);
        manager.load(texturesTemp);
    }

    private void loadSounds(){
        manager.load(mainClick);
        manager.load(click1);
        manager.load(click6);
        manager.load(click11);
        manager.load(click12);
        manager.load(scoreInc);
        manager.load(winFanfare);
        manager.load(finalScores);
        manager.load(countdownTimer);
        manager.load(timesUp);
    }

    private void loadMusic(){
        manager.load(mainMusic);
    }

    private void loadFonts(){
        manager.load(PlayTime);
    }

    public void dispose()
    {
        manager.dispose();
    }
}