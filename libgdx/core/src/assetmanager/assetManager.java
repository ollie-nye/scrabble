package assetmanager;

import java.awt.Font;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
    
    
    // random harry potter sounds
    public static final AssetDescriptor<Sound> click1 = 
            new AssetDescriptor<Sound>("Spells1_a_2edit.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click2 = 
            new AssetDescriptor<Sound>("Spells1_b.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click3 = 
            new AssetDescriptor<Sound>("Spells1_d.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click4 = 
            new AssetDescriptor<Sound>("Spells1_e.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click5 = 
            new AssetDescriptor<Sound>("Spells1_f.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click6 = 
            new AssetDescriptor<Sound>("Spells2_a.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click7 = 
            new AssetDescriptor<Sound>("Spells2_b.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click8 = 
            new AssetDescriptor<Sound>("Spells2_c.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click9 = 
            new AssetDescriptor<Sound>("Spells2_f.mp3", Sound.class);
    public static final AssetDescriptor<Sound> click10 = 
            new AssetDescriptor<Sound>("Spells2_g.mp3", Sound.class);
    
    // main theme for game
    public static final AssetDescriptor<Music> mainMusic = 
            new AssetDescriptor<Music>("sounds/Flintstones.mp3", Music.class);
    
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
	manager.load(sliderBar);
	manager.load(sliderKnob);
	manager.load(settingsBackground);
	manager.load(icon);
	manager.load(logo);
	manager.load(logoWhite);
	manager.load(logoLoaded);
}

private void loadTextureAtlas(){
	manager.load(gameButtonPack);
	manager.load(mainMenuButtonPack);
	manager.load(texturesTemp);
}

private void loadSounds(){
	manager.load(mainClick);
	manager.load(click1);
	manager.load(click2);
	manager.load(click3);
	manager.load(click4);
	manager.load(click5);
	manager.load(click6);
	manager.load(click7);
	manager.load(click8);
	manager.load(click9);
	manager.load(click10);
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


