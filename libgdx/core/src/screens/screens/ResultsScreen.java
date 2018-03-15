package screens.screens;

import java.util.ArrayList;
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
import data.Tile;
import player.Player;
import scrabble.Game;
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
	private String[] names;
	private int[] currentScores;
	private int[] finalScores;
	private int[] penalties;
	private Tile[][] remainingTiles;

	public ResultsScreen(ScrabbleLauncher game) {

		this.game = game;
		stage = new Stage(new ScreenViewport());
		setupButtonConfig();
		setupResults();
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

	private void setupResults() {
		ArrayList<Player> players = Game.getPlayers();
		names = new String[Game.getNumberOfPlayers()];

		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			names[i] = players.get(i).getPlayerName();
			System.out.println(names[i]);
		}
		currentScores = new int[Game.getNumberOfPlayers()];
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			currentScores[i] = players.get(i).getScore();
			System.out.println(currentScores[i]);
		}

		remainingTiles = new Tile[Game.getNumberOfPlayers()][];
		ArrayList<Tile> tiles;
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			tiles = new ArrayList<Tile>();
			Tile[] currentTiles = players.get(i).getTiles();
			for (int j = 0; j < currentTiles.length; j++) {
				if (currentTiles[j] != null) {
					tiles.add(currentTiles[j]);
				}
			}
			remainingTiles[i] = new Tile[tiles.size()];
			for (int j = 0; j < tiles.size(); j++) {
				remainingTiles[i][j] = tiles.get(j);
			}
		}

		penalties = new int[Game.getNumberOfPlayers()];
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			int penalty = 0;
			for (int j = 0; j < remainingTiles[i].length; j++) {
				penalty += remainingTiles[i][j].getScore();
				System.out.println(remainingTiles[i][j].getScore() + remainingTiles[i][j].getContent());
			}
			penalties[i] = penalty;
		}
		finalScores = new int[Game.getNumberOfPlayers()];
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			finalScores[i] = currentScores[i] - penalties[i];
		}

	}

	@Override
	public void show() {

		resultsTable = new Table();
		resultsTable.add(playerScorecard(names[0], currentScores[0], penalties[0], finalScores[0], remainingTiles[0]));
		resultsTable.add(playerScorecard(names[1], currentScores[1], penalties[1], finalScores[1], remainingTiles[1]));
		if (Game.getNumberOfPlayers() > 2) {
			resultsTable.row();
			resultsTable
					.add(playerScorecard(names[2], currentScores[2], penalties[2], finalScores[2], remainingTiles[2]));
		}
		if (Game.getNumberOfPlayers() > 3) {
			resultsTable
					.add(playerScorecard(names[3], currentScores[3], penalties[3], finalScores[3], remainingTiles[3]));
		}
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

	private void styleSetups() {
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

	private Table playerScorecard(String name, int currentScores, int penalty, int finalScores, Tile[] tiles) {

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
		Label[] lettersLeft = new Label[tiles.length];
		for (int i = 0; i < tiles.length; i++) {
			lettersLeft[i] = new Label(tiles[i].getContent(), labelStyle1);
			lettersLeft[i].setFontScale(0.7f);
			lettersLeft[i].setAlignment(Align.center);
			letters.add(lettersLeft[i]).size(40.0f, 40.0f).expand();
		}
		if (tiles.length == 0){
			Label allTilesGone = new Label("You Played All Your Tiles!", labelStyle1);
			allTilesGone.setAlignment(Align.center);
			letters.add(allTilesGone).expandX().height(40.0f).fill();
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
		Label currentScore = new Label(Integer.toString(currentScores), labelStyle1);
		Label scoreLoss = new Label(Integer.toString(penalty), labelStyle);
		currentScore.setAlignment(Align.center);
		scoreLoss.setAlignment(Align.center);
		player.add(currentScore).expand().fill();
		player.add(scoreLoss).expand().fill();
		player.row();
		// --SCORES TO CALCULATE

		// ++FINAL SCORES
		Label finalScore = new Label("Final Score: " + finalScores, labelStyle);
		finalScore.setAlignment(Align.center);
		player.add(finalScore).colspan(2).expandX().fill().height(50.0f).expandY();
		// --FINAL SCORES
		player.pack();
		return player;
	}

}
