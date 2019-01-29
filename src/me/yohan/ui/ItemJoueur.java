package me.yohan.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import me.yohan.fc.Joueur;

public class ItemJoueur extends BorderPane {
	
	private GameConfig parent;
	
	private TextField nameTextField;
	
	public ItemJoueur(GameConfig parent, int i) {
		this.parent = parent;
		nameTextField = new TextField("Joueur " + i);
		Button buttonSupp = new Button("-");
		buttonSupp.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				handleSupp();
			}
		});
		this.setCenter(this.nameTextField);
		this.setRight(buttonSupp);
	}
	
	private void handleSupp() {
		this.parent.suppJoueur(this);
	}

	public Joueur createPlayer() {
		return new Joueur(this.nameTextField.getText());
	}
}
