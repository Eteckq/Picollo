package me.yohan.ui;

import Services.GetQuestionsAPI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.yohan.fc.*;

public class Main extends Application {
	
	private Stage stage;
	private enum Plateforme {PC, OTHER};
	private Plateforme plateforme = Plateforme.PC;
	public final static int SCREEN_HEIGHT = 600;
	public final static int SCREEN_WIDTH = 400;
	
	private Scene gameConfigScene;
	
	private Game game;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		this.stage = stage;
		this.stage.setTitle("Picolo");
		switch (this.plateforme) {
		case PC:
			this.stage.setWidth(SCREEN_WIDTH);
			this.stage.setHeight(SCREEN_HEIGHT);
			break;
		case OTHER:
			this.stage.setWidth(Double.MAX_VALUE);
			this.stage.setHeight(Double.MAX_VALUE);
			break;
		default:				
		}
		this.gameConfigScene = new Scene(new GameConfig(this));
		this.stage.setScene(gameConfigScene);
		
		stage.show();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void startGame() {
		this.stage.setScene(new Scene(new CurrentGame(this)));
	}

	public void leaveGame() {
		this.stage.setScene(this.gameConfigScene);
	}
}
