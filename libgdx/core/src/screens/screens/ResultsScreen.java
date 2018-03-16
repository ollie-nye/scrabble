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
import com.badlogic.gdx.utils.Timer;
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
	private float deltaTime, timer, penaltiesTimer;
	private float[] addingPenalties;
	private float[] tallyPenalties;
	private boolean makeSureTileTimerOnlyCalledOnce;
	private boolean[] timings;
	private boolean[] currentScoreDone;

	private Label[] p1CurrentScore, p1FinalScore, p1ScoreLoss;
	private Label[][] p1LettersLeft;

	private float letterCounter;

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
		}
		currentScores = new int[Game.getNumberOfPlayers()];
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			currentScores[i] = players.get(i).getScore();
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

			}
			penalties[i] = penalty;
		}
		finalScores = new int[Game.getNumberOfPlayers()];
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			finalScores[i] = currentScores[i] - penalties[i];
		}
		p1LettersLeft = new Label[Game.getNumberOfPlayers()][];
		p1CurrentScore = new Label[Game.getNumberOfPlayers()];
		p1FinalScore = new Label[Game.getNumberOfPlayers()];
		p1ScoreLoss = new Label[Game.getNumberOfPlayers()];
		timer = 0;
		addingPenalties = new float[Game.getNumberOfPlayers()];
		for (int i = 0; i < addingPenalties.length; i++) {
			addingPenalties[i] = 0;
		}
		tallyPenalties = new float[Game.getNumberOfPlayers()];
		for (int i = 0; i < tallyPenalties.length; i++) {
			addingPenalties[i] = 0;
		}
		currentScoreDone = new boolean[Game.getNumberOfPlayers()];
		for (int i = 0; i < currentScoreDone.length; i++) {
			currentScoreDone[i] = false;
		}

		letterCounter = 0.0f;
		makeSureTileTimerOnlyCalledOnce = false;

	}

	@Override
	public void show() {

		resultsTable = new Table();
		resultsTable.add(player1Scorecard(0));
		resultsTable.add(player1Scorecard(1));
		if (Game.getNumberOfPlayers() == 3) {
			resultsTable.row();
			resultsTable.add(player1Scorecard(2)).colspan(2);
		}
		if (Game.getNumberOfPlayers() > 3) {
			resultsTable.row();
			resultsTable.add(player1Scorecard(2));
			resultsTable.add(player1Scorecard(3));
		}

		resultsTable.pack();

		resultsTable.setPosition((1280.0f - resultsTable.getWidth()) * 0.5f,
				(720.0f - resultsTable.getHeight()) * 0.5f);
		stage.addActor(resultsTable);

	}

	@Override
	public void render(float delta) {
		deltaTime = Gdx.graphics.getDeltaTime();
		timer += deltaTime;
		addPenalties();
		if (currentScoreDone() == false) {
			getCurrent(timer - 0.5f);
		}
		if (currentScoreDone() == true && makeSureTileTimerOnlyCalledOnce == false) {
			timer = 0.5f;
			makeSureTileTimerOnlyCalledOnce = true;
		}
		if (currentScoreDone() == true) {
			tileTimer(timer);

		}

		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0);
		stage.getBatch().end();
		stage.draw();
		stage.act();
	}

	private boolean currentScoreDone() {
		for (int i = 0; i < currentScoreDone.length; i++) {
			if (currentScoreDone[i] == false) {
				return false;
			}
		}
		return true;

	}

	public void addPenalties() {

		for (int i = 0; i < addingPenalties.length; i++) {
			if (addingPenalties[i] > 0) {
				addingPenalties[i] -= (deltaTime * 15.0f);
				tallyPenalties[i] += (deltaTime * 15.0f);
				p1ScoreLoss[i].setText(Integer.toString((int) tallyPenalties[i]));
				System.out.println("hi" + addingPenalties[i]);
			}
		}
	}

	/*
	 * scheduling currentScores appearance
	 */
	public void getCurrent(float timer) {

		if (timer > 0) {
			for (int i = 0; i < p1CurrentScore.length; i++) {
				if ((int) letterCounter <= currentScores[i]) {

					p1CurrentScore[i].setText(Integer.toString((int) letterCounter));
				} else {
					p1CurrentScore[i].setText(Integer.toString(currentScores[i]));
					currentScoreDone[i] = true;
				}
			}
			letterCounter += deltaTime * 15.0f;
		}

	}

	/*
	 * scheduling the appearances of unplayed tiles
	 */
	public void tileTimer(float timer) {
		if (timer >= 1) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 0) {
					if (p1LettersLeft[i][0].isVisible() == false) {
						p1LettersLeft[i][0].setVisible(true);
						addingPenalties[i] += remainingTiles[i][0].getScore();

					}
				}
			}
		}
		if (timer >= 1.5) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 1) {
					if (p1LettersLeft[i][1].isVisible() == false) {
						p1LettersLeft[i][1].setVisible(true);
						addingPenalties[i] += remainingTiles[i][1].getScore();
					}
				}
			}
		}
		if (timer >= 2.0) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 2) {
					if (p1LettersLeft[i][2].isVisible() == false) {
						p1LettersLeft[i][2].setVisible(true);
						addingPenalties[i] += remainingTiles[i][2].getScore();
					}
				}
			}
		}
		if (timer >= 2.5) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 3) {
					if (p1LettersLeft[i][3].isVisible() == false) {
						p1LettersLeft[i][3].setVisible(true);
						addingPenalties[i] += remainingTiles[i][3].getScore();
					}
				}
			}
		}
		if (timer >= 3.0) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 4) {
					if (p1LettersLeft[i][4].isVisible() == false) {
						p1LettersLeft[i][4].setVisible(true);
						addingPenalties[i] += remainingTiles[i][4].getScore();
					}
				}
			}
		}
		if (timer >= 3.5) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 5) {
					if (p1LettersLeft[i][5].isVisible() == false) {
						p1LettersLeft[i][5].setVisible(true);
						addingPenalties[i] += remainingTiles[i][5].getScore();
					}
				}
			}
		}
		if (timer >= 4.0) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 6) {
					if (p1LettersLeft[i][6].isVisible() == false) {
						p1LettersLeft[i][6].setVisible(true);
						addingPenalties[i] += remainingTiles[i][6].getScore();
					}
				}
			}
		}
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

	private Table player1Scorecard(int x) {

		// +CREATING FULL PLAYER SCORECARD
		Table player = new Table();
		player.pad(15.0f);

		player.setBackground(skin.getDrawable("purple"));

		// player names at top of players box;
		// ++PLAYER NAME ROW
		Label playerName = new Label(names[x], labelStyle);
		playerName.setAlignment(Align.center);
		player.add(playerName).colspan(2).expandX().width(500.0f);
		player.row();
		// --PLAYER NAME ROW

		// ++END TILE COLLECTION HEADER
		Label endTileHeader = new Label("Tiles left in players hand", labelStyle2);
		playerName.setAlignment(Align.center);
		player.add(endTileHeader).colspan(2).expandX().fill();
		player.row();
		// --END TILE COLLECTION HEADER

		// this is the list of letters that the player had left;
		// ++LIST OF TILES LEFT FOR PLAYER
		Table letters = new Table();
		p1LettersLeft[x] = new Label[remainingTiles[x].length]; // letterLeft
		for (int i = 0; i < remainingTiles[x].length; i++) {
			p1LettersLeft[x][i] = new Label(remainingTiles[x][i].getContent(), labelStyle1);
			p1LettersLeft[x][i].setFontScale(0.7f);
			p1LettersLeft[x][i].setAlignment(Align.center);
			letters.add(p1LettersLeft[x][i]).size(40.0f, 40.0f).expand();
			p1LettersLeft[x][i].setVisible(false);
		}
		if (remainingTiles[x].length == 0) {
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
		p1CurrentScore[x] = new Label(" ", labelStyle1);
		p1ScoreLoss[x] = new Label(" ", labelStyle);
		p1CurrentScore[x].setAlignment(Align.center);
		p1ScoreLoss[x].setAlignment(Align.center);
		player.add(p1CurrentScore[x]).expand().fill();
		player.add(p1ScoreLoss[x]).expand().fill();
		player.row();
		// --SCORES TO CALCULATE

		// ++FINAL SCORES
		p1FinalScore[x] = new Label("Final Score:   " + finalScores[x], labelStyle);
		p1FinalScore[x].setAlignment(Align.center);
		player.add(p1FinalScore[x]).colspan(2).expandX().fill().height(50.0f).expandY();
		// --FINAL SCORES
		player.pack();
		return player;
	}
}
