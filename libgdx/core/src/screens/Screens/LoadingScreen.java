package screens.Screens;



import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import assetmanager.assetManager;
import screens.ScrabbleLauncher;

public class LoadingScreen implements Screen {
	
	private ScrabbleLauncher game;
	private Texture logo;
	private Texture logoLoaded;
	private Texture logoWhite;
	private Stage stage;
	private int initalX = -1105;
	private BitmapFont font;

	
	public LoadingScreen(ScrabbleLauncher game){
	
		this.game = game;
		logo = game.getAssetManager().manager.get(assetManager.logo);
		logoWhite = game.getAssetManager().manager.get(assetManager.logoWhite);
		logoLoaded = game.getAssetManager().manager.get(assetManager.logoLoaded);
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
		stage = new Stage();
	}
	

	@Override
	public void show() {
		
		
	}

	@Override
	public void render(float delta) {
		
		float progress = game.getAssetManager().manager.getProgress();
		
		
		stage.getBatch().begin();
		stage.getBatch().draw(logoWhite,0,0);
		stage.getBatch().draw(logoLoaded,initalX,0); 
       
		
			
			if (initalX<=0){
				initalX = (initalX + 2);
				stage.getBatch().draw(logoLoaded, logoLoaded.getWidth() * progress   , 0);
			} else {
				this.dispose();
				game.setScreen(new MainMenu(game));
			}
			

		stage.getBatch().draw(logo, 0, 0);
		stage.getBatch().end();
		
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
		logo.dispose();
		logoLoaded.dispose();
		logoWhite.dispose();
		
		
	}


}
