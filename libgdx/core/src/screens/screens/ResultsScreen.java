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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import assetmanager.assetManager;
import screens.ScrabbleLauncher;

/**
 * @author Ben Miller
 * @version 1.0
 */
public class ResultsScreen implements Screen {

	private BitmapFont font;
	ScrabbleLauncher game;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private Table resultsTable;
	private Stage stage;
	private Texture background;

	public ResultsScreen(ScrabbleLauncher game) {

		this.game = game;
		stage = new Stage(new ScreenViewport());
		setupButtonConfig();
	}

	private void setupButtonConfig() {
		// sets up graphics of tiles
		skin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.texturesTemp);
		skin.addRegions(buttonAtlas);
		background = game.getAssetManager().manager.get(assetManager.resultsBackground);
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
	}

	@Override
	public void show() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.background = skin.getDrawable("green");

		LabelStyle labelStyle1 = new LabelStyle();
		labelStyle1.font = font;
		labelStyle1.background = skin.getDrawable("lightblue");

		LabelStyle labelStyle2 = new LabelStyle();
		labelStyle2.font = font;
		labelStyle2.background = skin.getDrawable("yellow");

		resultsTable = new Table();
		resultsTable.setBackground(skin.getDrawable("purple"));

		// +CREATING FULL PLAYER SCORECARD
		Table player = new Table();
		player.pad(25.0f);

		// player names at top of players box;
		// ++PLAYER NAME ROW
		Label playerName = new Label("P1 and stuff", labelStyle);
		resultsTable.add(playerName).colspan(2);
		resultsTable.row();
		// --PLAYER NAME ROW

		// this is the list of letters that the player had left;
		// ++LIST OF TILES LEFT FOR PLAYER
		Table letters = new Table();
		Label[] lettersLeft = new Label[7];
		boolean style = true;
		for (Label letter : lettersLeft) {
			letter = new Label("a", labelStyle1);
			letter.setText("a");
			letters.add(letter);
		}
		resultsTable.add(letters).colspan(2);
		resultsTable.row();
		// --LIST OF TILES LEFT FOR PLAYER

		// this shows current and score to be taken away
		// ++SCORES TO CALCULATE TITLES
		Label currentScoreTitle = new Label("Current Score", labelStyle);
		Label scoreLossTitle = new Label("Penalties", labelStyle2);
		resultsTable.add(currentScoreTitle);
		resultsTable.add(scoreLossTitle);
		resultsTable.row();
		// --SCORES TO CALCULATE TITLES
		
		// this shows the actual score numbers
		// ++SCORES TO CALCULATE
		Label currentScore = new Label("67", labelStyle1);
		Label scoreLoss = new Label("12", labelStyle2);
		resultsTable.add(currentScore);
		resultsTable.add(scoreLoss);
		resultsTable.row();		
		// --SCORES TO CALCULATE
		
		// ++FINAL SCORES
		Label finalScore = new Label("Final Score: " + "54", labelStyle);
		resultsTable.add(finalScore).colspan(2);
		// --FINAL SCORES
		
		
		
		
		resultsTable.setPosition((1280.0f - resultsTable.getWidth()) * 0.5f,
				(720.0f - resultsTable.getHeight()) * 0.5f);
		stage.addActor(resultsTable);
		

	}

	@Override
	public void render(float delta) {
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0);
		stage.getBatch().end();
		stage.draw();
		stage.act();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
