package screens.screens;

import assetmanager.assetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import scrabble.Game;
import screens.ScrabbleLauncher;

import java.util.ArrayList;

public class MainMenu implements Screen {

    private ScrabbleLauncher game;
    private Table playOptions, namingPlayer, tempTable;
    private TextButton play, settings, rules, exit, website;
    private int menuType, playerCounter, aiNumber, playerNumber, screen;
    private Label[] playerLabel = new Label[4];
    private TextField[] playerNameEntry = new TextField[4];
    private Label noPlayers;
    private Stage stage;
    private Sound hover;
    //fiddy
    private Sound gunit;
    private Texture background;
    private BitmapFont font;


    public MainMenu(ScrabbleLauncher game) {
        this.game = game;
        hover = game.getAssetManager().manager.get(assetManager.mainClick);
        //fiddy
        gunit = game.getAssetManager().manager.get(assetManager.click12);
        background = game.getAssetManager().manager.get(assetManager.mainBackground);
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        menuType = 0;
        playerCounter = 0;
        aiNumber = 0;
        playerNumber = 0;
        font = game.getAssetManager().manager.get(assetManager.PlayTime);
        screen = 0;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin();
        TextureAtlas buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
        skin.addRegions(buttonAtlas);


        Skin tempSkin = new Skin();
        TextureAtlas tempTextures = game.getAssetManager().manager.get(assetManager.texturesTemp);
        tempSkin.addRegions(tempTextures);


        TextButtonStyle playButtonStyle = new TextButtonStyle();
        playButtonStyle.up = skin.getDrawable("play");
        playButtonStyle.over = skin.getDrawable("playPressed");
        playButtonStyle.checked = skin.getDrawable("playPressed");
        playButtonStyle.font = font;
        play = new TextButton("", playButtonStyle);
        play.setPosition(515, 330f);
        play.setSize(254.0f, 65.0f);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                hover.play(game.getSoundVol());
                menuType = 1;

            }
        });
        stage.addActor(play);

        TextButtonStyle settingsButtonStyle = new TextButtonStyle();
        settingsButtonStyle.up = skin.getDrawable("settings");
        settingsButtonStyle.over = skin.getDrawable("settingsPressed");
        settingsButtonStyle.checked = skin.getDrawable("settingsPressed");
        settingsButtonStyle.font = font;
        settings = new TextButton("", settingsButtonStyle);
        settings.setPosition(480, 240f);
        settings.setSize(320.0f, 80.0f);
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                hover.play(game.getSoundVol());
                game.setScreen(new SettingsMenu(game));

            }
        });
        stage.addActor(settings);

        TextButtonStyle rulesButtonStyle = new TextButtonStyle();
        rulesButtonStyle.up = skin.getDrawable("help");
        rulesButtonStyle.over = skin.getDrawable("helpPressed");
        rulesButtonStyle.checked = skin.getDrawable("helpPressed");
        rulesButtonStyle.font = font;
        rules = new TextButton("", rulesButtonStyle);
        rules.setPosition(520f, 160f);
        rules.setSize(244.0f, 69.0f);
        rules.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                hover.play(game.getSoundVol());
                game.setScreen(new HelpScreen(game));

            }
        });
        stage.addActor(rules);

        TextButtonStyle exitButtonStyle = new TextButtonStyle();
        exitButtonStyle.up = skin.getDrawable("exitButton");
        exitButtonStyle.over = skin.getDrawable("exitPressed");
        exitButtonStyle.checked = skin.getDrawable("exitPressed");
        exitButtonStyle.font = font;
        exit = new TextButton("", exitButtonStyle);
        exit.setPosition(515, 80f);
        exit.setSize(260.0f, 75.0f);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                hover.play(game.getSoundVol());
                Gdx.app.exit();

            }
        });
        stage.addActor(exit);

        TextButtonStyle websiteButtonStyle = new TextButtonStyle();
        websiteButtonStyle.up = skin.getDrawable("website");
        websiteButtonStyle.over = skin.getDrawable("websiteHover");
        websiteButtonStyle.font = font;
        website = new TextButton("", websiteButtonStyle);
        website.setPosition(1190f, 0f);
        website.setSize(90.0f, 90.0f);
        website.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.net.openURI("https://scrabbleemphasis.wordpress.com/");

            }
        });
        stage.addActor(website);

        /*
         * creating the menu that comes up after clicking play
         */
        // first adding the button styles on
        TextButtonStyle tempButtonStyle = new TextButtonStyle();
        tempButtonStyle.up = tempSkin.getDrawable("purple");
        tempButtonStyle.over = tempSkin.getDrawable("yellow");
        tempButtonStyle.font = font;

        TextButtonStyle leftArrowStyle = new TextButtonStyle();
        leftArrowStyle.up = skin.getDrawable("leftArrow");
        leftArrowStyle.over = skin.getDrawable("leftArrowPressed");
        leftArrowStyle.font = font;

        TextButtonStyle rightArrowStyle = new TextButtonStyle();
        rightArrowStyle.up = skin.getDrawable("rightArrow");
        rightArrowStyle.over = skin.getDrawable("rightArrowPressed");
        rightArrowStyle.font = font;

        TextButtonStyle altButtonStyle = new TextButtonStyle();
        altButtonStyle.up = tempSkin.getDrawable("lightblue");
        altButtonStyle.over = tempSkin.getDrawable("blue");
        altButtonStyle.font = font;

        // setting up a label style and font
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.background = tempSkin.getDrawable("green");

        LabelStyle altLabelStyle = new LabelStyle();
        altLabelStyle.font = font;
        altLabelStyle.background = tempSkin.getDrawable("blue");

        LabelStyle counterLabelStyle = new LabelStyle();
        counterLabelStyle.font = font;
        counterLabelStyle.background = skin.getDrawable("counter");

        TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.background = tempSkin.getDrawable("yellow");
        textFieldStyle.messageFont = font;
        textFieldStyle.fontColor = new Color(0.5f,0.5f,0.5f,1f);
        textFieldStyle.focusedBackground = tempSkin.getDrawable("purple");

        // creating the main table
        playOptions = new Table();
        float gameStartY = 350;
        playOptions.setPosition(640.0f - (gameStartY * (5.0f / 7.0f)), 360.0f - 300.0f);
        playOptions.setBackground(tempSkin.getDrawable("orange"));
        playOptions.setSize(gameStartY * (10.0f / 7.0f), gameStartY);

        // creating main header and also headers above ai and player
        Label header = new Label("Create Players", labelStyle);
        header.setAlignment(Align.center);

        // playersBox is a table to store the number of players selection button
        // collection for non ai
        Table playersBox = new Table();
        playersBox.setBackground(tempSkin.getDrawable("lightblue"));

        Label playerHeader = new Label("Add Players", labelStyle);
        playerHeader.setAlignment(Align.center);

        final Label playersBoxText = new Label("0", counterLabelStyle);
        playersBoxText.setAlignment(Align.center);
        TextButton playersBoxLeftArrow = new TextButton("", leftArrowStyle);
        TextButton playersBoxRightArrow = new TextButton("", rightArrowStyle);

        playersBoxRightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerCounter < 4) {
                    playersBoxText.setText(Integer.toString(Integer.parseInt(playersBoxText.getText().toString()) + 1));
                    playerCounter += 1;
                    System.out.println(playerCounter);
                    playerNumber += 1;
                }
            }
        });
        playersBoxLeftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerCounter > 0 && Integer.parseInt(playersBoxText.getText().toString()) != 0) {
                    playersBoxText.setText(Integer.toString(Integer.parseInt(playersBoxText.getText().toString()) - 1));
                    playerCounter -= 1;
                    playerNumber -= 1;
                }
            }
        });

        playersBox.add(playerHeader).colspan(3).padBottom(gameStartY / 28).height(gameStartY / 14)
                .width(gameStartY * (17.0f / 28.0f));
        playersBox.row();
        playersBox.add(playersBoxLeftArrow);
        playersBox.add(playersBoxText).align(Align.center).size(125.0f, 88.0f);
        playersBox.add(playersBoxRightArrow);


        // playersBox is a table to store the number of players selection button
        // collection for ai
        Table aIBox = new Table();
        aIBox.setBackground(tempSkin.getDrawable("lightblue"));

        Label aiHeader = new Label("Add CPU", labelStyle);
        aiHeader.setAlignment(Align.center);

        final Label aIBoxText = new Label("0", counterLabelStyle);
        aIBoxText.setAlignment(Align.center);
        TextButton aIBoxLeftArrow = new TextButton("", leftArrowStyle);
        //aIBoxLeftArrow.setSize(47.0f, 47.0f);
        TextButton aIBoxRightArrow = new TextButton("", rightArrowStyle);



        aIBoxRightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerCounter < 4) {
                    aIBoxText.setText(Integer.toString(Integer.parseInt(aIBoxText.getText().toString()) + 1));
                    aiNumber += 1;
                    playerCounter += 1;
                }
            }
        });
        aIBoxLeftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerCounter > 0 && Integer.parseInt(aIBoxText.getText().toString()) != 0) {
                    aIBoxText.setText(Integer.toString(Integer.parseInt(aIBoxText.getText().toString()) - 1));
                    aiNumber -= 1;
                    playerCounter -= 1;
                }
            }
        });

        aIBox.add(aiHeader).colspan(3).padBottom(gameStartY / 28).height(gameStartY / 14)
                .width(gameStartY * (17.0f / 28.0f));
        aIBox.row();
        //aIBox.add(aIBoxLeftArrow).size(gameStartY * (6.5f / 28.0f), gameStartY * (4.5f / 14.0f));
        aIBox.add(aIBoxLeftArrow);
        aIBox.add(aIBoxText).align(Align.center).size(125.0f, 88.0f);
        aIBox.add(aIBoxRightArrow);
        ;

        // adding these to one table
        tempTable = new Table();
        tempTable.setBackground(tempSkin.getDrawable("purple"));
        tempTable.add(playersBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padLeft(gameStartY / 14)
                .padRight(gameStartY / 56);
        tempTable.add(aIBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padRight(gameStartY / 14)
                .padLeft(gameStartY / 56);

        namingPlayer = new Table();
        namingPlayer.setBackground(tempSkin.getDrawable("green"));

        // namingPlayer.add(p1);
        namingPlayer.setSize(gameStartY * (9.0f / 7.0f), gameStartY * (6.0f / 14.0f));

        namingPlayer.setVisible(false);

        playerLabel[0] = new Label("Player 1", altLabelStyle);
        playerLabel[0].setAlignment(Align.center);
        playerNameEntry[0] = new TextField("", textFieldStyle);
        playerNameEntry[0].setAlignment(Align.center);

        playerLabel[1] = new Label("Player 2", altLabelStyle);
        playerLabel[1].setAlignment(Align.center);
        playerNameEntry[1] = new TextField("", textFieldStyle);
        playerNameEntry[1].setAlignment(Align.center);

        playerLabel[2] = new Label("Player 3", altLabelStyle);
        playerNameEntry[2] = new TextField("", textFieldStyle);
        playerLabel[2].setAlignment(Align.center);
        playerNameEntry[2].setAlignment(Align.center);

        playerLabel[3] = new Label("Player 4", altLabelStyle);
        playerNameEntry[3] = new TextField("", textFieldStyle);
        playerLabel[3].setAlignment(Align.center);
        playerNameEntry[3].setAlignment(Align.center);

        namingPlayer.add(playerLabel[0]).width(gameStartY /14*8.75f).padRight(gameStartY /56.0f);
        namingPlayer.add(playerNameEntry[0]).height(30.0f).width(gameStartY /14*8.75f).padLeft(gameStartY /56.0f);
        namingPlayer.row();
        namingPlayer.add(playerLabel[1]).width(gameStartY /14*8.75f).padRight(gameStartY /56.0f);
        namingPlayer.add(playerNameEntry[1]).height(30.0f).width(gameStartY /14*8.75f).padLeft(gameStartY /56.0f);
        namingPlayer.row();
        namingPlayer.add(playerLabel[2]).width(gameStartY /14*8.75f).padRight(gameStartY /56.0f);
        namingPlayer.add(playerNameEntry[2]).height(30.0f).width(gameStartY /14*8.75f).padLeft(gameStartY /56.0f);
        namingPlayer.row();
        namingPlayer.add(playerLabel[3]).width(gameStartY /14*8.75f).padRight(gameStartY /56.0f);;
        namingPlayer.add(playerNameEntry[3]).height(30.0f).width(gameStartY /14*8.75f).padLeft(gameStartY /56.0f);
        namingPlayer.setWidth(gameStartY /14.0f*18.0f);

        noPlayers = new Label("Add Some Players First", altLabelStyle);
        noPlayers.setAlignment(Align.center);
        noPlayers.setVisible(false);

        Stack stack = new Stack();
        stack.add(tempTable);
        stack.add(namingPlayer);
        stack.add(noPlayers);

        // start button, at bottom of box, starts the game
        TextButton start = new TextButton("Quickstart", altButtonStyle);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerCounter > 1){
                    setPlayerArray();
                    for(int i = 0; i < playerNumber; i++) {
                        Game.addPlayer(playerNameEntry[i].getText(),1);
                    }
                    for(int i = 0; i < aiNumber; i++) {
                        Game.addPlayer("AI " + i,2);
                    }
                    Game.start();

                    //fiddy
                    gunit.play();

                    game.setScreen(new GameScreen(game));
                }
            }
        });

        // naming button, at bottom of box, changes screen to name player screen
        final TextButton naming = new TextButton("Name Players", altButtonStyle);
        naming.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (screen == 0) {
                    naming.setText("Edit Players");
                    screen = 1;
                } else if (screen == 1) {
                    naming.setText("Name Players");
                    screen = 0;
                }
            }
        });

        // creating the main table
        playOptions.add(header).colspan(2).size(gameStartY / 14 * 12, gameStartY / 7)
                .pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 14)
                .size(gameStartY / 14 * 12, gameStartY / 7);
        playOptions.row();
        playOptions.add(stack).colspan(2).maxWidth(500.0f);
        playOptions.row();
        playOptions.add(naming).pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 56)
                .size(gameStartY / 28.0f * 18.0f, gameStartY / 7);
        playOptions.add(start).pad(gameStartY / 14, gameStartY / 56, gameStartY / 14, gameStartY / 14)
                .size(gameStartY / 28.0f * 18.0f, gameStartY / 7);

        stage.addActor(playOptions);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // for animation of the play button
        if (play.isOver()) {
            play.setSize(274f, 85f);
            play.setPosition(507f, 322f);
        } else {
            play.setPosition(515, 330f);
            play.setSize(254.0f, 65.0f);
        }

        // for animation of the settings button
        if (settings.isOver()) {
            settings.setSize(331f, 91f);
            settings.setPosition(475, 232f);
        } else {
            settings.setPosition(480, 240f);
            settings.setSize(320.0f, 80.0f);
        }

        // for animation of the rules button
        if (rules.isOver()) {
            rules.setSize(264f, 85f);
            rules.setPosition(505f, 153f);
        } else {
            rules.setPosition(520f, 160f);
            rules.setSize(244.0f, 69.0f);
        }

        // for animation of the exit button
        if (exit.isOver()) {
            exit.setSize(270, 80f);
            exit.setPosition(510f, 79f);
        } else {
            exit.setPosition(515, 80f);
            exit.setSize(260.0f, 75.0f);
        }

        if (menuType == 0) {
            setGameMenuInvisible();
            setMainMenuVisible();

        }
        else if (menuType == 1) {
            setGameMenuVisible();
            setMainMenuInvisible();
        }
        if (screen == 0) {
            tempTable.setVisible(true);
            namingPlayer.setVisible(false);
            noPlayers.setVisible(false);

        }	else {
            tempTable.setVisible(false);
            namingPlayer.setVisible(true);
            noPlayers.setVisible(false);

        }
        switch (playerCounter){
            case 0:
                playerNameEntry[0].setVisible(false);
                playerNameEntry[1].setVisible(false);
                playerNameEntry[2].setVisible(false);
                playerNameEntry[3].setVisible(false);
                playerLabel[0].setVisible(false);
                playerLabel[1].setVisible(false);
                playerLabel[2].setVisible(false);
                playerLabel[3].setVisible(false);
                if (screen == 1){
                    noPlayers.setVisible(true);
                }
                break;
            case 1:
                playerNameEntry[0].setVisible(true);
                playerNameEntry[1].setVisible(false);
                playerNameEntry[2].setVisible(false);
                playerNameEntry[3].setVisible(false);
                playerLabel[0].setVisible(true);
                playerLabel[1].setVisible(false);
                playerLabel[2].setVisible(false);
                playerLabel[3].setVisible(false);
                noPlayers.setVisible(false);
                break;

            case 2:
                playerNameEntry[0].setVisible(true);
                playerNameEntry[1].setVisible(true);
                playerNameEntry[2].setVisible(false);
                playerNameEntry[3].setVisible(false);
                playerLabel[0].setVisible(true);
                playerLabel[1].setVisible(true);
                playerLabel[2].setVisible(false);
                playerLabel[3].setVisible(false);
                noPlayers.setVisible(false);
                break;
            case 3:
                playerNameEntry[0].setVisible(true);
                playerNameEntry[1].setVisible(true);
                playerNameEntry[2].setVisible(true);
                playerNameEntry[3].setVisible(false);
                playerLabel[0].setVisible(true);
                playerLabel[1].setVisible(true);
                playerLabel[2].setVisible(true);
                playerLabel[3].setVisible(false);
                noPlayers.setVisible(false);
                break;
            case 4:
                playerNameEntry[0].setVisible(true);
                playerNameEntry[1].setVisible(true);
                playerNameEntry[2].setVisible(true);
                playerNameEntry[3].setVisible(true);
                playerLabel[0].setVisible(true);
                playerLabel[1].setVisible(true);
                playerLabel[2].setVisible(true);
                playerLabel[3].setVisible(true);
                noPlayers.setVisible(false);
                break;
            default:
                playerNameEntry[0].setVisible(false);
                playerNameEntry[1].setVisible(false);
                playerNameEntry[2].setVisible(false);
                playerNameEntry[3].setVisible(false);
                playerLabel[0].setVisible(false);
                playerLabel[1].setVisible(false);
                playerLabel[2].setVisible(false);
                playerLabel[3].setVisible(false);
                noPlayers.setVisible(false);
                break;
        }
        switch (playerNumber){
            case 0:
                playerLabel[0].setText("AI P1");
                playerLabel[1].setText("AI P2");
                playerLabel[2].setText("AI P3");
                playerLabel[3].setText("AI P4");
                break;
            case 1:
                playerLabel[0].setText("Human P1");
                playerLabel[1].setText("AI P1");
                playerLabel[2].setText("AI P2");
                playerLabel[3].setText("AI P3");
                break;
            case 2:
                playerLabel[0].setText("Human P1");
                playerLabel[1].setText("Human P2");
                playerLabel[2].setText("AI P1");
                playerLabel[3].setText("AI P2");
                break;
            case 3:
                playerLabel[0].setText("Human P1");
                playerLabel[1].setText("Human P2");
                playerLabel[2].setText("Human P3");
                playerLabel[3].setText("AI P1");
                break;
            case 4:
                playerLabel[0].setText("Human P1");
                playerLabel[1].setText("Human P2");
                playerLabel[2].setText("Human P3");
                playerLabel[3].setText("Human P4");
                break;

        }

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0);
        stage.getBatch().end();

        stage.draw();
        stage.act();

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    private void setGameMenuVisible() {
        playOptions.setVisible(true);
    }

    private void setGameMenuInvisible() {
        playOptions.setVisible(false);
    }

    private void setMainMenuVisible() {
        settings.setVisible(true);
        exit.setVisible(true);
        play.setVisible(true);
        rules.setVisible(true);
    }

    private void setMainMenuInvisible() {
        settings.setVisible(false);
        exit.setVisible(false);
        play.setVisible(false);
        rules.setVisible(false);
    }
    private ArrayList<String> setPlayerArray(){
        ArrayList<String> x = new ArrayList<String>();
        if(playerNameEntry[0].getSelection() != null){
            x.add(playerNameEntry[0].getSelection());
        }
        else{
            x.add("FuckYouLibgdx");
        }
        if(playerNameEntry[1].getSelection() != null){
            x.add(playerNameEntry[1].getSelection());
        }
        else{
            x.add("FuckYouLibgdx");
        }
        if(playerNameEntry[2].getSelection() != null){
            x.add(playerNameEntry[2].getSelection());
        }
        else{
            x.add("FuckYouLibgdx");
        }
        if(playerNameEntry[3].getSelection() != null){
            x.add(playerNameEntry[3].getSelection());
        }
        else{
            x.add("FuckYouLibgdx");
        }
        return x;
    };
}