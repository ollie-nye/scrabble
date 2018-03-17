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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	private Skin skin, altSkin;
	private TextureAtlas buttonAtlas, buttonAtlas2;
	private Table resultsTable, endTable, tableThing;
	private Stage stage;
	private Texture background;
	private LabelStyle tileLabelStyle, longLabelStyle, noLabelStyle, shortLabelStyle, boxLabelStyle;
	private String[] names;
	private int[] currentScores, finalScores, penalties;
	private Tile[][] remainingTiles;
	private float deltaTime, timer, endTimer, letterCounter;
	private float[] addingPenalties, tallyPenalties;
	private boolean makeSureTileTimerOnlyCalledOnce, justFinished;
	private boolean[] currentScoreDone, tilesFinished;
	private Label winnerLabel;
	private Label[] p1CurrentScore, p1FinalScore, p1ScoreLoss, finalScore;
	private Label[][] p1LettersLeft;
	private ButtonStyle returnButtonStyle;
	private Button returnButton;

	public ResultsScreen(ScrabbleLauncher game) {
		this.game = game;
		stage = new Stage(new ScreenViewport());
		setupButtonConfig();
		setupResults();
	}

	private void setupButtonConfig() {
		// sets up graphics of tiles
		skin = new Skin();
		altSkin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
		skin.addRegions(buttonAtlas);
		buttonAtlas2 = game.getAssetManager().manager.get(assetManager.gameButtonPack);
		altSkin.addRegions(buttonAtlas2);
		background = game.getAssetManager().manager.get(assetManager.resultsBackground);
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
		styleSetups();

	}

	private void setupResults() {
		ArrayList<Player> players = Game.getPlayers();
		int p = Game.getNumberOfPlayers();
		names = new String[p];
		penalties = new int[p];
		currentScores = new int[p];
		finalScores = new int[p];
		p1LettersLeft = new Label[p][];
		p1CurrentScore = new Label[p];
		p1FinalScore = new Label[p];
		p1ScoreLoss = new Label[p];
		addingPenalties = new float[p];
		tallyPenalties = new float[p];
		currentScoreDone = new boolean[p];
		finalScore = new Label[p];

		for (int i = 0; i < p; i++) {
			tilesFinished = new boolean[p];
			names[i] = players.get(i).getPlayerName();
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
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			int penalty = 0;
			for (int j = 0; j < remainingTiles[i].length; j++) {
				penalty += remainingTiles[i][j].getScore();
			}
			penalties[i] = penalty;
		}
		for (int i = 0; i < Game.getNumberOfPlayers(); i++) {
			finalScores[i] = currentScores[i] - penalties[i];
			System.out.println(currentScores[i] + " " + penalties[i] + finalScores[i]);
		}
		timer = 0;
		for (int i = 0; i < addingPenalties.length; i++) {
			addingPenalties[i] = 0;
			addingPenalties[i] = 0;
			currentScoreDone[i] = false;
			tilesFinished[i] = false;
		}
		letterCounter = 0.0f;
		makeSureTileTimerOnlyCalledOnce = false;
		justFinished = true;
		returnButton = new Button(returnButtonStyle);
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenu(game));

			}
		});

	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);
		
		// ++CREATING PLAYER SCORECARD
		resultsTable = new Table();
		resultsTable.add(player1Scorecard(0));
		resultsTable.add(player1Scorecard(1));
		resultsTable.row();
		Move highestWordz = highestWord();
		Label highestWord = new Label("Highest word: " + highestWordz.getPlayedWord() + " with "
				+ Integer.toString(highestWordz.getMoveScore()) + "\nFound by "
				+ highestWordz.getPlayer().getPlayerName(), longLabelStyle);
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
		// --CREATING PLAYER SCORECARD

		// ++WINNER POPUP
		String winner = " ";
		int scoreFin = -60;
		for (int i = 0; i < finalScores.length; i++) {
			if (finalScores[i] > scoreFin) {
				scoreFin = finalScores[i];
				winner = names[i];
			}
		}

		winnerLabel = new Label(winner + " wins!", boxLabelStyle);
		winnerLabel.setAlignment(Align.center);		
	//	winnerLabel.setSize(500.0f, 300.0f);	
	//	winnerLabel.setPosition((1280.0f - winnerLabel.getWidth()) * 0.5f, (720.0f - winnerLabel.getHeight()) * 0.5f);
		
		returnButton = new Button(returnButtonStyle);
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				//hover.play(game.getSoundVol());
				//menuType = 1;

			}
		});
		Button placeHolderButton = new Button(returnButtonStyle);
		placeHolderButton.setVisible(false);
		tableThing = new Table();
		tableThing.setSize(500.0f, 300.0f);
		tableThing.add(placeHolderButton);
		tableThing.row();
		tableThing.add(winnerLabel).expand().fill();
		tableThing.row();
		tableThing.add(returnButton).align(Align.center);
		tableThing.setPosition((1280.0f - tableThing.getWidth()) * 0.5f, (720.0f - (tableThing.getHeight())) * 0.5f);
		tableThing.setVisible(false);
		stage.addActor(tableThing);
		// --WINNER POPUP

	}

	/**
	 * finds highest played word
	 * 
	 * @return move of highest word
	 */
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
		// current scoreCounter
		if (currentScoreDone() == false) {
			getCurrent(timer - 0.5f);
		}
		// checks when current score is done
		if (currentScoreDone() == true && makeSureTileTimerOnlyCalledOnce == false) {
			timer = 0.5f;
			makeSureTileTimerOnlyCalledOnce = true;
		}
		// makes tiles sequentially appear
		if (currentScoreDone() == true) {
			tileTimer(timer, 1.5f);
		}
		// checks when all tiles are shown
		if (tilesFinished() == true && justFinished == true) {
			endTimer = 0.0f;
			justFinished = false;
		}
		// shows the winner
		if (tilesFinished() == true) {
			if (endTimer > 3) {
				for (int i = 0; i < p1FinalScore.length; i++) {
					p1FinalScore[i].setVisible(true);
					finalScore[i].setVisible(true);
				}
			}
			endTimer += deltaTime;
			if (endTimer > 7) {
				tableThing.setVisible(true);
			}

		}
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0);
		stage.getBatch().end();
		stage.draw();
		stage.act();
	}

	/**
	 * checks when all tiles have appeared
	 * 
	 * @return true if all appeared
	 */
	private boolean tilesFinished() {
		for (int i = 0; i < tilesFinished.length; i++) {
			if (tilesFinished[i] == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if current score counter finished
	 * 
	 * @return true if finished
	 */
	private boolean currentScoreDone() {
		for (int i = 0; i < currentScoreDone.length; i++) {
			if (currentScoreDone[i] == false) {
				return false;
			}
		}
		return true;

	}

	/**
	 * add penalties to a player sequentially when they are added to
	 * addingPenalties
	 */
	public void addPenalties() {

		for (int i = 0; i < addingPenalties.length; i++) {
			if (addingPenalties[i] > 0) {
				addingPenalties[i] -= (deltaTime * 15.0f);
				tallyPenalties[i] += (deltaTime * 15.0f);
				p1ScoreLoss[i].setText(Integer.toString((int) tallyPenalties[i]));

			}
		}
	}

	/**
	 * current score counting animation
	 * 
	 * @param timer
	 *            at timer 0 score will start rising
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

	/**
	 * sequentially shows tiles on scorecards
	 * @param timer once timer is 1 animation starts
	 * @param x effect speed they appear
	 */
	public void tileTimer(float timer, float x) {

		if (timer >= x * 1) {
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
		if (timer >= x * 1.5) {
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
		if (timer >= x * 2.0) {
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
		if (timer >= x * 2.5) {
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
		if (timer >= x * 3.0) {
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
		if (timer >= x * 3.5) {
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
		if (timer >= x * 4.0) {
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

	/**
	 * label styles graphics setups
	 */
	private void styleSetups() {

		tileLabelStyle = new LabelStyle();
		tileLabelStyle.font = font;
		tileLabelStyle.background = altSkin.getDrawable("orangeButton");

		longLabelStyle = new LabelStyle();
		longLabelStyle.font = font;
		longLabelStyle.background = skin.getDrawable("textBarLong");

		shortLabelStyle = new LabelStyle();
		shortLabelStyle.font = font;
		shortLabelStyle.background = skin.getDrawable("textBarShort");

		boxLabelStyle = new LabelStyle();
		boxLabelStyle.font = font;
		boxLabelStyle.background = skin.getDrawable("creationBox");
		
		noLabelStyle = new LabelStyle();
		noLabelStyle.font = font;
		
		returnButtonStyle = new ButtonStyle();
		returnButtonStyle.up = altSkin.getDrawable("homeButton");
		returnButtonStyle.over = altSkin.getDrawable("homeButtonPressed");

	}

	/**
	 * scorecard create
	 * @param x player number (game must have sufficient number of players)
	 * @return scorecard
	 */
	private Table player1Scorecard(int x) {

		// +CREATING FULL PLAYER SCORECARD
		Table player = new Table();
		player.pad(15.0f);
		player.setBackground(skin.getDrawable("creationBox"));

		// player names at top of players box;
		// ++PLAYER NAME ROW
		
		Label playerName = new Label(names[x], longLabelStyle);
		playerName.setAlignment(Align.center);
		player.add(playerName).colspan(2).expandX().width(500.0f);
		player.row();
		// --PLAYER NAME ROW

		// ++END TILE COLLECTION HEADER
		Table tileys = new Table();
		Label endTileHeader = new Label("Tiles left in players hand", noLabelStyle);
		endTileHeader.setAlignment(Align.center);
		playerName.setAlignment(Align.center);
		tileys.add(endTileHeader).colspan(2).expandX().fill();
		player.add(tileys).expand().fill();
		player.row();
		// --END TILE COLLECTION HEADER

		// this is the list of letters that the player had left;
		// ++LIST OF TILES LEFT FOR PLAYER
		Table letters = new Table();
		p1LettersLeft[x] = new Label[remainingTiles[x].length]; // letterLeft
		for (int i = 0; i < remainingTiles[x].length; i++) {
			p1LettersLeft[x][i] = new Label(remainingTiles[x][i].getContent(), tileLabelStyle);
			p1LettersLeft[x][i].setFontScale(0.7f);
			p1LettersLeft[x][i].setAlignment(Align.center);
			letters.add(p1LettersLeft[x][i]).size(40.0f, 40.0f).expand().pad(10.0f);
			p1LettersLeft[x][i].setVisible(false);
		}
		if (remainingTiles[x].length == 0) {
			Label allTilesGone = new Label("You Played All Your Tiles!", longLabelStyle);
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
		Table headers = new Table(); // storing currentScore and scoreLoss in one
		Label currentScoreTitle = new Label("Current Score", shortLabelStyle);
		Label scoreLossTitle = new Label("Penalties", shortLabelStyle);
		currentScoreTitle.setAlignment(Align.center);
		scoreLossTitle.setAlignment(Align.center);

		headers.add(currentScoreTitle).uniform().fill().expand();
		headers.add(scoreLossTitle).uniform().fill().expand();		
		// --SCORES TO CALCULATE TITLES

		// ++FINAL SCORES TITLES
		p1FinalScore[x] = new Label("Final Score", longLabelStyle);
		p1FinalScore[x].setAlignment(Align.center);	
		p1FinalScore[x].setVisible(false);
		// --FINAL SCORES TITLES
		
		// ++SCORES HEADERS STACK
		Stack stack = new Stack(); //stack will store header for current, then final
		stack.add(headers);		
		stack.add(p1FinalScore[x]);
		player.add(stack).expand().fill().colspan(2);
		player.row();
		// --SCORES HEADERS STACK

		// this shows the actual score numbers
		// ++SCORES TO CALCULATE
		Table oldScores = new Table();
		p1CurrentScore[x] = new Label(" ", shortLabelStyle);
		p1ScoreLoss[x] = new Label(" ", shortLabelStyle);
		p1CurrentScore[x].setAlignment(Align.center);
		p1ScoreLoss[x].setAlignment(Align.center);
		oldScores.add(p1CurrentScore[x]).expand().fill().uniform();
		oldScores.add(p1ScoreLoss[x]).expand().fill().uniform();
		// ++SCORES TO CALCULATE
	
		// ++FINAL SCORES
		finalScore[x] = new Label(Integer.toString(finalScores[x]), longLabelStyle);
		finalScore[x].setAlignment(Align.center);
		finalScore[x].setVisible(false);		
		// --FINAL SCORES
		
		// ++SCORES
		Stack scoresStack = new Stack();
		scoresStack.add(oldScores);
		scoresStack.add(finalScore[x]);
		player.add(scoresStack).expand().fill();
		player.row();		
		// --SCORES 

		player.pack();
		return player;
	}
}
