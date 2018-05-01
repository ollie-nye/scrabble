package screens.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import assetmanager.assetManager;
import data.move.Move;
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
	private Texture background, victory;
	private LabelStyle tileLabelStyle, longLabelStyle, noLabelStyle, shortLabelStyle, boxLabelStyle, resultStyle;
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
	private ButtonStyle returnButtonStyle, menuButtonStyle;;
	private Button returnButton, placeHolderButton;

	private Sound scoreIncrement, finalScoreSound, winfanfare;

	private boolean hasWon, isFinal = false;
	private TextureAtlas buttonAtlass;

	public ResultsScreen(ScrabbleLauncher game) {
		this.game = game;
		// Sound Implementation
		scoreIncrement = game.getAssetManager().manager.get(assetManager.scoreInc);
		finalScoreSound = game.getAssetManager().manager.get(assetManager.finalScores);
		winfanfare = game.getAssetManager().manager.get(assetManager.winFanfare);
		stage = new Stage(new ScreenViewport());
		setupButtonConfig();
		setupResults();
	}

	private void setupButtonConfig() {
		// sets up graphics of tiles
		skin = new Skin();
		altSkin = new Skin();
		buttonAtlass = new TextureAtlas();
		buttonAtlass.addRegion("victory", game.getAssetManager().manager.get(assetManager.victory), 0, 0, 1280, 850);
		buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
		skin.addRegions(buttonAtlas);
		skin.addRegions(buttonAtlass);
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
		Label highestWord = new Label("Highest Word Score: " + Integer.toString(highestWordz.getMoveScore())
				+ "\nFound by " + highestWordz.getPlayer().getPlayerName(), longLabelStyle);
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

		winnerLabel = new Label(winner + " wins!", resultStyle);
		winnerLabel.setAlignment(Align.center);
		// winnerLabel.setSize(500.0f, 300.0f);
		// winnerLabel.setPosition((1280.0f - winnerLabel.getWidth()) * 0.5f,
		// (720.0f - winnerLabel.getHeight()) * 0.5f);

		returnButton = new Button(returnButtonStyle);
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				disposey();

			}
		});
		placeHolderButton = new Button(menuButtonStyle);
		placeHolderButton.setVisible(false);
		tableThing = new Table();
		tableThing.setSize(1280.0f, 850.0f);
		tableThing.add(placeHolderButton);
		tableThing.row();
		tableThing.add(winnerLabel).expand().fill();
		tableThing.row();
		tableThing.add(returnButton).align(Align.center);
		tableThing.setPosition((1280.0f - tableThing.getWidth()) * 0.5f, (720.0f - (tableThing.getHeight())) * 0.5f);
		tableThing.setVisible(false);
		placeHolderButton.setPosition(540, 200);
		placeHolderButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				disposey();

			}
		});
		stage.addActor(tableThing);
		stage.addActor(placeHolderButton);
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
		if (tilesFinished() == true && justFinished == true && currentScoreDone() == true) {
			endTimer = 0.0f;
			justFinished = false;

		}
		// shows the winner
		if (tilesFinished() == true && currentScoreDone() == true) {

			if (endTimer > 3) {
				for (int i = 0; i < p1FinalScore.length; i++) {
					p1FinalScore[i].setVisible(true);
					finalScore[i].setVisible(true);
				}
				// Condition to One Shot Audio Play
				if (isFinal == false) {
					finalScoreSound.play(game.getSoundVol());
					isFinal = true;
				}
			}
			endTimer += deltaTime;
			if (endTimer > 7) {
				tableThing.setVisible(true);
				placeHolderButton.setVisible(true);
				// Condition to one shot play
				if (hasWon == false) {
					winfanfare.play(game.getSoundVol());
					hasWon = true;
				}
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
					scoreIncrement.play(game.getSoundVol());
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
	 * 
	 * @param timer
	 *            once timer is 1 animation starts
	 * @param x
	 *            effect speed they appear
	 */
	public void tileTimer(float timer, float x) {

		if (timer >= x * 1) {
			for (int i = 0; i < p1LettersLeft.length; i++) {
				if (p1LettersLeft[i].length > 0) {
					if (p1LettersLeft[i][0].isVisible() == false) {
						p1LettersLeft[i][0].setVisible(true);
						addingPenalties[i] += remainingTiles[i][0].getScore();
						scoreIncrement.play(game.getSoundVol());

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
						scoreIncrement.play(game.getSoundVol());
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
						scoreIncrement.play(game.getSoundVol());
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
						scoreIncrement.play(game.getSoundVol());
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
						scoreIncrement.play(game.getSoundVol());
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
						scoreIncrement.play(game.getSoundVol());
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
						scoreIncrement.play(game.getSoundVol());
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

	public void disposey() {
		Game.reset();
		this.dispose();
		game.setScreen(new MainMenu(game));
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

		resultStyle = new LabelStyle();
		resultStyle.font = font;
		resultStyle.background = skin.getDrawable("victory");

		noLabelStyle = new LabelStyle();
		noLabelStyle.font = font;

		returnButtonStyle = new ButtonStyle();
		returnButtonStyle.up = altSkin.getDrawable("homeButton");
		returnButtonStyle.over = altSkin.getDrawable("homeButtonPressed");

		menuButtonStyle = new ButtonStyle();
		menuButtonStyle.up = altSkin.getDrawable("mainMenuButton");
		menuButtonStyle.over = altSkin.getDrawable("mainMenuButtonPressed");

	}

	/**
	 * Create the Score Card
	 * 
	 * @param playerNum
	 *            player number (game must have sufficient number of players)
	 * @return Game ScoreCard
	 */
	private Table player1Scorecard(int playerNum) {

		// +CREATING FULL PLAYER SCORECARD
		Table playerScoreCard = new Table();
		playerScoreCard.pad(15.0f);
		playerScoreCard.setBackground(skin.getDrawable("creationBox"));

		// player names at top of players box;
		// ++PLAYER NAME ROW
		Label playerName = new Label(names[playerNum], longLabelStyle);
		playerName.setAlignment(Align.center);
		playerScoreCard.add(playerName).colspan(2).expandX().width(500.0f);
		playerScoreCard.row();
		// --PLAYER NAME ROW

		// ++END TILE COLLECTION HEADER
		Table tileys = new Table();
		Label endTileHeader = new Label("Tiles left in players hand", noLabelStyle);
		endTileHeader.setAlignment(Align.center);
		playerName.setAlignment(Align.center);
		tileys.add(endTileHeader).colspan(2).expandX().fill();
		playerScoreCard.add(tileys).expand().fill();
		playerScoreCard.row();
		// --END TILE COLLECTION HEADER

		// this is the list of letters that the player had left;
		// ++LIST OF TILES LEFT FOR PLAYER
		Table letters = new Table();
		p1LettersLeft[playerNum] = new Label[remainingTiles[playerNum].length]; // letterLeft
		for (int i = 0; i < remainingTiles[playerNum].length; i++) {
			p1LettersLeft[playerNum][i] = new Label(remainingTiles[playerNum][i].getContent(), tileLabelStyle);
			p1LettersLeft[playerNum][i].setFontScale(0.7f);
			p1LettersLeft[playerNum][i].setAlignment(Align.center);
			letters.add(p1LettersLeft[playerNum][i]).size(40.0f, 40.0f).expand().pad(10.0f);
			p1LettersLeft[playerNum][i].setVisible(false);
		}
		if (remainingTiles[playerNum].length == 0) {
			Label allTilesGone = new Label("You Played All Your Tiles!", longLabelStyle);
			tilesFinished[playerNum] = true;
			allTilesGone.setAlignment(Align.center);
			letters.add(allTilesGone).expandX().height(40.0f).fill();
		}
		playerScoreCard.add(letters).colspan(2).expandX().fill();
		letters.pack();
		playerScoreCard.row();
		// --LIST OF TILES LEFT FOR PLAYER

		// this shows current and score to be taken away
		// ++SCORES TO CALCULATE TITLES
		Table headers = new Table(); // storing currentScore and scoreLoss in
										// one
		Label currentScoreTitle = new Label("Current Score", shortLabelStyle);
		Label scoreLossTitle = new Label("Penalties", shortLabelStyle);
		currentScoreTitle.setAlignment(Align.center);
		scoreLossTitle.setAlignment(Align.center);

		headers.add(currentScoreTitle).uniform().fill().expand();
		headers.add(scoreLossTitle).uniform().fill().expand();
		// --SCORES TO CALCULATE TITLES

		// ++FINAL SCORES TITLES
		p1FinalScore[playerNum] = new Label("Final Score", longLabelStyle);
		p1FinalScore[playerNum].setAlignment(Align.center);
		p1FinalScore[playerNum].setVisible(false);
		// --FINAL SCORES TITLES

		// ++SCORES HEADERS STACK
		Stack stack = new Stack(); // stack will store header for current, then
									// final
		stack.add(headers);
		stack.add(p1FinalScore[playerNum]);
		playerScoreCard.add(stack).expand().fill().colspan(2);
		playerScoreCard.row();
		// --SCORES HEADERS STACK

		// this shows the actual score numbers
		// ++SCORES TO CALCULATE
		Table oldScores = new Table();
		p1CurrentScore[playerNum] = new Label(" ", shortLabelStyle);
		p1ScoreLoss[playerNum] = new Label(" ", shortLabelStyle);
		p1CurrentScore[playerNum].setAlignment(Align.center);
		p1ScoreLoss[playerNum].setAlignment(Align.center);
		oldScores.add(p1CurrentScore[playerNum]).expand().fill().uniform();
		oldScores.add(p1ScoreLoss[playerNum]).expand().fill().uniform();
		// ++SCORES TO CALCULATE

		// ++FINAL SCORES
		finalScore[playerNum] = new Label(Integer.toString(finalScores[playerNum]), longLabelStyle);
		finalScore[playerNum].setAlignment(Align.center);
		finalScore[playerNum].setVisible(false);
		// --FINAL SCORES

		// ++SCORES
		Stack scoresStack = new Stack();
		scoresStack.add(oldScores);
		scoresStack.add(finalScore[playerNum]);
		playerScoreCard.add(scoresStack).expand().fill();
		playerScoreCard.row();
		// --SCORES

		playerScoreCard.pack();
		return playerScoreCard;
	}
}
