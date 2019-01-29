package me.yohan.ui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import me.yohan.fc.questions.Post;

public class CurrentGame extends BorderPane {

	private Main main;
	
	Label titre;
	Label principal;
	Label previousTitle;
	Label previous;
	Button buttonAbort;
	Button buttonNext;
	HBox controlsBottom;
	
	public CurrentGame(Main main) {
		this.main = main;
		
		VBox vbox = new VBox();
		//vbox.setStyle("-fx-background-color: blue;");
		this.titre = new Label("");
		this.titre.setFont(new Font(20));
		this.principal = new Label("La partie commence");
		this.principal.setWrapText(true);
		this.principal.setTextAlignment(TextAlignment.CENTER);
		this.principal.setPadding(new Insets(0, 20, 0, 20));
		//this.principal.setStyle("-fx-background-color: red;");
		this.buttonNext = new Button("Suivant");
		this.buttonNext.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				handleNext();			
			}
		});
		this.previousTitle = new Label("precedent : ");
		this.previousTitle.setFont(new Font(13));
		this.previousTitle.setManaged(false);
		this.previousTitle.setVisible(false);
		this.previous = new Label("");
		this.previous.setWrapText(true);
		this.previous.setTextAlignment(TextAlignment.CENTER);
		this.previous.setFont(new Font(10));
		this.previous.setManaged(false);
		this.previous.setVisible(false);
		this.previous.setPadding(new Insets(0, 20, 20, 20));
		vbox.getChildren().addAll(this.previousTitle, this.previous, this.titre, this.principal, this.buttonNext);
		vbox.setAlignment(Pos.CENTER);
		
		this.controlsBottom = new HBox();
		this.controlsBottom.setAlignment(Pos.CENTER);
		this.buttonAbort = new Button("Abandonner la partie");
		this.buttonAbort.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				main.leaveGame();			
			}
		});
		this.controlsBottom.getChildren().add(this.buttonAbort);
		
		this.setCenter(vbox);	
		this.setBottom(this.controlsBottom);
	}
	
	private void handleNext() {
		Post nextPost = this.main.getGame().nextPost();
		this.principal.setText(nextPost.getMessage());
		this.titre.setText(nextPost.getTitle().toString());
		
		ArrayList<Post> already = this.main.getGame().getAlreadyPosted();
		if (already.size() > 0) {
			this.previousTitle.setManaged(true);
			this.previousTitle.setVisible(true);
			this.previous.setManaged(true);
			this.previous.setVisible(true);
			this.previous.setText(already.get(already.size()-1).getMessage());
		}
		
		if (this.main.getGame().isFinished()) {
			this.buttonAbort.setManaged(false);
			this.buttonAbort.setVisible(false);
			this.buttonNext.setManaged(false);
			this.buttonNext.setVisible(false);
			
			Button buttonLeave = new Button("Quitter la partie");
			buttonLeave.setOnAction(new EventHandler<ActionEvent>() {	
				@Override
				public void handle(ActionEvent arg0) {
					main.leaveGame();	
				}
			});
			this.controlsBottom.getChildren().add(buttonLeave);
			
			Label endLabel = new Label("La partie est terminée");
			endLabel.setFont(new Font(30));
			BorderPane.setAlignment(endLabel, Pos.CENTER);
			
			this.setTop(endLabel);
		}
	}
}
