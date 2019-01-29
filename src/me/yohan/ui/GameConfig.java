package me.yohan.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Services.GetQuestionsAPI;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import me.yohan.fc.*;
import me.yohan.fc.questions.Question;
import me.yohan.fc.questions.Questions;
import me.yohan.fc.questions.QuestionsFromBDD;
import me.yohan.fc.questions.QuestionsFromFile;
import me.yohan.fc.questions.Question.Able;
import me.yohan.fc.questions.Question.Type;

public class GameConfig extends BorderPane {
	
	public final static int version = 4; //version; mettre +1 a chaque mise a jour
	
	public final static int HAUTEUR_ET_LARGEUR_BOUTON = 25;

	private final static int NB_MIN_JOUEURS = 3;
	private final static int NB_MAX_JOUEURS = 10;
	
	ScrollPane scrollPane;
	VBox playerList;
	Button buttonAdd;
	Button buttonStart;
	VBox config;
	VBox advancedConfig;
	CheckBox carte;
	CheckBox de;
	CheckBox challenge;
	CheckBox minigame;
	CheckBox rule;
	CheckBox virus;
	
	
	private Main main;
	
	public GameConfig(Main main) {
		//Surement rien a faire ici
		/*try { //j'ajoute une exeption, si le lien de la MaJ n'est pas accesible 
			if(CheckUpdate.Check()!=version) { //version est la version actuelle
				askForUpdate();
			}
		} catch (IOException e) {
			System.out.println("Erreur lors du téléchargement");
			System.out.println(e);
		}*/

		this.main = main;
		
		this.scrollPane = new ScrollPane();
		this.scrollPane.setFitToWidth(true);
		VBox listWithAdd = new VBox();
		this.playerList = new VBox();
		this.buttonAdd = new Button("+");
		this.buttonAdd.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				handleAdd();			
			}
		});
		listWithAdd.getChildren().addAll(this.playerList, this.buttonAdd);
		listWithAdd.setAlignment(Pos.CENTER);
		
		this.scrollPane.setContent(listWithAdd);
		while (this.playerList.getChildren().size() < NB_MIN_JOUEURS) {
			this.playerList.getChildren().add(new ItemJoueur(this, this.playerList.getChildren().size() + 1));
		}
		
		this.config = new VBox();
		this.buttonStart = new Button("Demarrer la partie");
		this.buttonStart.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				handleStart();			
			}
		});
		HBox checkbox = new HBox();
		checkbox.setSpacing(5);
		this.carte = new CheckBox("Carte");
		this.de = new CheckBox("De");
		ToggleButton advancedParams = new ToggleButton();
		ImageView imageViewParams = new ImageView(new Image("params.png"));
		imageViewParams.setFitHeight(HAUTEUR_ET_LARGEUR_BOUTON-7);
		imageViewParams.setFitWidth(HAUTEUR_ET_LARGEUR_BOUTON-7);
		advancedParams.setMinWidth(HAUTEUR_ET_LARGEUR_BOUTON);
		advancedParams.setMaxWidth(HAUTEUR_ET_LARGEUR_BOUTON);
		advancedParams.setGraphic(imageViewParams);
		advancedParams.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (advancedParams.isSelected()) {
					setCenter(advancedConfig);
				} else {
					setCenter(scrollPane);
				}
			}
		});
		checkbox.getChildren().addAll(this.carte, this.de, advancedParams);
		checkbox.setAlignment(Pos.CENTER);
		this.config.getChildren().addAll(checkbox, this.buttonStart);
		this.config.setAlignment(Pos.CENTER);
		
		this.setCenter(this.scrollPane);
		this.setBottom(this.config);
		
		this.playerList.getChildren().addListener(new InvalidationListener() {		
			@Override
			public void invalidated(Observable arg0) {
				checkNbJoueurs();
			}
		});
		
		this.advancedConfig = new VBox();
		this.advancedConfig.setAlignment(Pos.CENTER);
		FlowPane advancedOptions = new FlowPane();
		advancedOptions.setAlignment(Pos.CENTER);
		this.challenge = new CheckBox("Challenge");
		this.challenge.setSelected(true);
		this.minigame = new CheckBox("Mini-jeu");
		this.minigame.setSelected(true);
		this.rule = new CheckBox("Règle");
		this.rule.setSelected(true);
		this.virus = new CheckBox("Virus");
		this.virus.setSelected(true);
		advancedOptions.getChildren().addAll(challenge, minigame, rule, virus);
		this.advancedConfig.getChildren().addAll(new Label("Types de questions pris en compte"), advancedOptions);
	}
	
	private void handleStart() {
		ArrayList<Joueur> joueurs = new ArrayList<>();
		for (Node joueurNode : this.playerList.getChildren()) {
			joueurs.add(((ItemJoueur) joueurNode).createPlayer());
		}
		ArrayList<Able> able = new ArrayList<Able>();
		if (this.carte.isSelected()) able.add(Able.CARTE);
		if (this.de.isSelected()) able.add(Able.DE);
		ArrayList<Question.Type> types = new ArrayList<Question.Type>();
		if (this.challenge.isSelected()) types.add(Type.CHALLENGE);
		if (this.minigame.isSelected()) types.add(Type.MINIGAME);
		if (this.rule.isSelected()) types.add(Type.RULE);
		if (this.virus.isSelected()) types.add(Type.VIRUS);
		//ArrayList<Question> questions = Questions.cloneQuestions(QuestionsFromFile.getInstance().getQuestionsWithCriteria(joueurs.size(), able, types));
		ArrayList<Question> questions = Questions.cloneQuestions(new QuestionsFromBDD().getQuestionsWithCriteria(joueurs.size(), able, types));
		if (questions.size() > 0) {
			this.main.setGame(new Game("DEFAULT", joueurs, questions));
			this.main.startGame();
		} else {
			Alert warning = new Alert(AlertType.INFORMATION);
			warning.setTitle("Attention");
			warning.setContentText("Impossible de lancer la partie");
			warning.setHeaderText("Attention, aucune question ne correspondent à votre configuration de parties.");
			warning.showAndWait();
		}
	}
	
	private void handleAdd() {
		this.playerList.getChildren().add(new ItemJoueur(this, this.playerList.getChildren().size()+1));
	}

	public void suppJoueur(ItemJoueur joueur) {
		this.playerList.getChildren().remove(joueur);
	}
	
	private void checkNbJoueurs() {
		int nbJoueurs = this.playerList.getChildren().size();
		if (nbJoueurs >= NB_MAX_JOUEURS) {
			this.buttonAdd.setManaged(false);
			this.buttonAdd.setVisible(false);
		} else {
			this.buttonAdd.setManaged(true);
			this.buttonAdd.setVisible(true);
			if (nbJoueurs < NB_MIN_JOUEURS) {
				this.buttonStart.setDisable(true);
			} else {
				this.buttonStart.setDisable(false);
			}
		}
	}
	
	private void askForUpdate() throws IOException { //rien a faire lé, mais au moins éa marche
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nouvelle mise à jour !");
        alert.setHeaderText("Une nouvelle mise a jour est disponible !");
        alert.setContentText("Voulez vous l'installer ?");
        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.get() == ButtonType.OK) {
        	CheckUpdate.Update();
        } else {
        	//fais rien
        }
        

        
	}
}
