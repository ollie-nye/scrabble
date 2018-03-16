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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import assetmanager.assetManager;
import data.Move;
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
	private float deltaTime, timer, penaltiesTimer, endTimer;
	private float[] addingPenalties;
	private float[] tallyPenalties;
	private boolean makeSureTileTimerOnlyCalledOnce;
	private boolean[] timings;
	private boolean[] currentScoreDone;
	private boolean[] tilesFinished;
	private boolean justFinished;

	private Label[] p1CurrentScore, p1FinalScore, p1ScoreLoss, finalScore;
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
		tilesFinished = new boolean[Game.getNumberOfPlayers()];
		for (int i = 0; i < tilesFinished.length; i++) {
			tilesFinished[i] = false;
		}
		finalScore = new Label[Game.getNumberOfPlayers()];

		letterCounter = 0.0f;
		makeSureTileTimerOnlyCalledOnce = false;
		justFinished = true;

	}

	@Override
	public void show() {

		resultsTable = new Table();
		resultsTable.add(player1Scorecard(0));
		resultsTable.add(player1Scorecard(1));
		resultsTable.row();
		Move highestWordz = highestWord();
		Label highestWord = new Label("Highest word: " + highestWordz.getPlayedWord() + " with "
				+ Integer.toString(highestWordz.getMoveScore()) + "\nFound by "
				+ highestWordz.getPlayer().getPlayerName(), labelStyle);
		highestWord.setWrap(false);
		highestWord.setAlignment(Align.center);
		resultsTable.add(highestWord).width(450.0f).colspan(2).align(Align.center).expand().fill();
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

	private Move highestWord() {
		ArrayList<Move> moves = Game.getMoveList();
		Move highestMove = moves.get(0);
		for (Move liberty : moves) {
			if (highestMove.getMoveScore() < liberty.getMoveScore()) {
				highestMove = liberty;
			}
			;
		}
		return highestMove;
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
		if (tilesFinished() == true && justFinished == true) {
			endTimer = 0.0f;
			justFinished = false;
		}
		if (tilesFinished() == true) {
			if (endTimer > 3){
			for (int i = 0; i < p1FinalScore.length; i++) {
				p1FinalScore[i].setVisible(true);
				finalScore[i].setVisible(true);
			}
			}
			endTimer += deltaTime;

		}
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0);
		stage.getBatch().end();
		stage.draw();
		stage.act();
	}

	private boolean tilesFinished() {
		for (int i = 0; i < tilesFinished.length; i++) {
			if (tilesFinished[i] == false) {
				return false;
			}
		}
		return true;

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
				if (p1LettersLeft[i].length == 1) {
					tilesFinished[i] = true;
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
				if (p1LettersLeft[i].length == 2) {
					tilesFinished[i] = true;
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
				if (p1LettersLeft[i].length == 3) {
					tilesFinished[i] = true;
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
				if (p1LettersLeft[i].length == 4) {
					tilesFinished[i] = true;
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
				if (p1LettersLeft[i].length == 5) {
					tilesFinished[i] = true;
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
				if (p1LettersLeft[i].length == 6) {
					tilesFinished[i] = true;
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
					tilesFinished[i] = true;
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
		endTileHeader.setAlignment(Align.center);
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
			letters.add(p1LettersLeft[x][i]).size(40.0f, 40.0f).expand().pad(10.0f);
			p1LettersLeft[x][i].setVisible(false);
		}
		if (remainingTiles[x].length == 0) {
			Label allTilesGone = new Label("You Played All Your Tiles!", labelStyle1);
			tilesFinished[x] = true;
			allTilesGone.setAlignment(Align.center);
			letters.add(allTilesGone).expandX().height(40.0f).fill();
		}
		player.add(letters).colspan(2).expandX().fill();
		letters.pack();
		player.row();
		// --LIST OF TILES LEFT FOR PLAYER

		// this shows current and score to be taken away
		// ++SCORES TO CALCULATE TITLES
		Stack stack = new Stack();

		Table headers = new Table();

		Label currentScoreTitle = new Label("Current Score", labelStyle);
		Label scoreLossTitle = new Label("Penalties", labelStyle2);
		currentScoreTitle.setAlignment(Align.center);
		scoreLossTitle.setAlignment(Align.center);

		stack.add(headers);
		headers.add(currentScoreTitle).uniform().fill().expand();
		headers.add(scoreLossTitle).uniform().fill().expand();
		player.row();
		// --SCORES TO CALCULATE TITLES

		// ++FINAL SCORES
		p1FinalScore[x] = new Label("Final Score", labelStyle);
		p1FinalScore[x].setAlignment(Align.center);
		stack.add(p1FinalScore[x]);
		player.add(stack).expand().fill().colspan(2);
		p1FinalScore[x].setVisible(false);
		player.row();
		// --FINAL SCORES

		// this shows the actual score numbers
		// ++SCORES TO CALCULATE
		Stack scoresStack = new Stack();
		Table oldScores = new Table();
		p1CurrentScore[x] = new Label(" ", labelStyle1);
		p1ScoreLoss[x] = new Label(" ", labelStyle);
		p1CurrentScore[x].setAlignment(Align.center);
		p1ScoreLoss[x].setAlignment(Align.center);
		oldScores.add(p1CurrentScore[x]).expand().fill().uniform();
		oldScores.add(p1ScoreLoss[x]).expand().fill().uniform();
		scoresStack.add(oldScores);

		finalScore[x] = new Label(Integer.toString(currentScores[x] - finalScores[x]), labelStyle1);
		finalScore[x].setAlignment(Align.center);
		scoresStack.add(finalScore[x]);
		player.add(scoresStack).expand().fill();
		player.row();
		finalScore[x].setVisible(false);
		// --SCORES TO CALCULATE

		player.pack();
		return player;
	}
}
