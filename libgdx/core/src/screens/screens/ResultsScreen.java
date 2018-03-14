package screens.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import assetmanager.assetManager;
import screens.ScrabbleLauncher;

/**
 * @author Ben Miller, Asid Khan
 * @version 1.0
 */
public class ResultsScreen implements Screen {
	
	private BitmapFont font;
	ScrabbleLauncher game;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private Table resultsTable;
	private Stage stage;
	
	public ResultsScreen(ScrabbleLauncher game) {
		
		this.game= game;			
		setupButtonConfig();		
		stage = new Stage(new ScreenViewport());		
	}
		
	private void setupButtonConfig() {
			// sets up graphics of tiles
			font = game.getAssetManager().manager.get(assetManager.PlayTime);
			skin = new Skin();
			buttonAtlas = game.getAssetManager().manager.get(assetManager.texturesTemp);
			skin.addRegions(buttonAtlas);
	}
	
	@Override
	public void show() {		
		resultsTable = new Table();
		resultsTable.setBackground(skin.getDrawable("purple"));
		resultsTable.setSize(600.0f, 600.0f);
		resultsTable.pack();
		
		resultsTable.setPosition((1280.0f-resultsTable.getWidth())*0.5f, (720.0f-resultsTable.getHeight())*0.5f);
		stage.addActor(resultsTable);
		
	}

	@Override
	public void render(float delta) {	
		stage.getBatch().begin();
		
		stage.getBatch().end();
		stage.draw();
		stage.act();
	}

	@Override
	public void resize(int width, int height) {			
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {	}

}
