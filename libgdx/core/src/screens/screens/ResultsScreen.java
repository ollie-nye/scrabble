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
import com.badlogic.gdx.utils.Align;
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
	private LabelStyle labelStyle, labelStyle1, labelStyle2; 

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
		styleSetups();
	}

	@Override
	public void show() {
			
		resultsTable = new Table();
		
		resultsTable.add(playerScorecard("p1"));
	    resultsTable.add(playerScorecard("Mike"));
	    resultsTable.row();
	    resultsTable.add(playerScorecard("p1"));
	    resultsTable.add(playerScorecard("p1"));
		resultsTable.pack();
		
		
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
	private void styleSetups(){
		labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.background = skin.getDrawable("green");

		labelStyle1 = new LabelStyle();
		labelStyle1.font = font;
		labelStyle1.background = skin.getDrawable("lightblue");

		labelStyle2 = new LabelStyle();
		labelStyle2.font = font;
		labelStyle2.background = skin.getDrawable("yellow");
		
	}
	
	private Table playerScorecard(String name){
		
		// +CREATING FULL PLAYER SCORECARD
		Table player = new Table();
		player.pad(15.0f);
	
		player.setBackground(skin.getDrawable("purple"));
	
		// player names at top of players box;
		// ++PLAYER NAME ROW
		Label playerName = new Label(name, labelStyle);
		playerName.setAlignment(Align.center);
		player.add(playerName).colspan(2).expandX().width(500.0f);
		player.row();
		
		// --PLAYER NAME ROW

		// this is the list of letters that the player had left;
		// ++LIST OF TILES LEFT FOR PLAYER
		Table letters = new Table();		
		Label[] lettersLeft = new Label[7];		
		for (Label letter : lettersLeft) {
			letter = new Label("a", labelStyle1);
			letter.setText("a");
			letter.setFontScale(0.7f);			
			letter.setAlignment(Align.center);
			letters.add(letter).size(40.0f, 40.0f).expand();
		}			
		player.add(letters).colspan(2).expandX().fill();
		letters.pack();		
		player.row();
		// --LIST OF TILES LEFT FOR PLAYER

		// this shows current and score to be taken away
		// ++SCORES TO CALCULATE TITLES
		Label currentScoreTitle = new Label("Current Score", labelStyle);
		Label scoreLossTitle = new Label("Penalties", labelStyle2);
		currentScoreTitle.setAlignment(Align.center);
		scoreLossTitle.setAlignment(Align.center);
		player.add(currentScoreTitle).uniform().fill();
		player.add(scoreLossTitle).uniform().fill();
		player.row();
		// --SCORES TO CALCULATE TITLES
		
		// this shows the actual score numbers
		// ++SCORES TO CALCULATE
		Label currentScore = new Label("67", labelStyle1);
		Label scoreLoss = new Label("12", labelStyle);
		currentScore.setAlignment(Align.center);
		scoreLoss.setAlignment(Align.center);
		player.add(currentScore).expand().fill();
		player.add(scoreLoss).expand().fill();
		player.row();		
		// --SCORES TO CALCULATE
		
		// ++FINAL SCORES
		Label finalScore = new Label("Final Score: " + "54", labelStyle);
		finalScore.setAlignment(Align.center);
		player.add(finalScore).colspan(2).expandX().fill();
		// --FINAL SCORES
		player.pack();
		return player;
	}

}
