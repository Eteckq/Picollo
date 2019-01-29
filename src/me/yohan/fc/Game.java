package me.yohan.fc;

import java.util.ArrayList;
import java.util.Collections;

import me.yohan.fc.questions.CustomRandom;
import me.yohan.fc.questions.Post;
import me.yohan.fc.questions.Question;

public class Game {

	private CustomRandom random;
	
	private String name;
	
	private ArrayList<Joueur> joueurs;
	
	private ArrayList<Question> remainingQuestions;
	private ArrayList<Post> wainting;
	private Post currentPost;
	private int numPost;
	private ArrayList<Post> alreadyPosted;
	
	public Game(String name, ArrayList<Joueur> joueurs, ArrayList<Question> questions) {
		
		this.random = new CustomRandom(System.currentTimeMillis());
		this.name = name;
		this.joueurs = joueurs;
		this.remainingQuestions = questions;
		Collections.shuffle(this.remainingQuestions, this.random);
		this.wainting = new ArrayList<Post>();
		this.numPost = 0;
		this.alreadyPosted = new ArrayList<Post>();
	}
	
	public Post nextPost() {
		if (this.currentPost != null)
			this.alreadyPosted.add(this.currentPost);
		
		/*On cherche si dans la liste d'attente il y a une phrase à afficher
		 * Soit, si une phrase contient comme num d'apparition, le numero de la question courante ou
		 * le numéro de la prochaine question
		 */
		for (Post post : this.wainting) {
			if (post.getNumApparition() == this.numPost || post.getNumApparition() == this.numPost+1) {
				this.currentPost = post;
				this.wainting.remove(post);
				return this.currentPost;
			}
		}
		
		//Si on passe là, c'est qu'aucune phrase dans la liste d'attente ne devait être affichée
		ArrayList<Post> currentPostsQuestion = getNewQuestion();
		ArrayList<Post> toAddInFirst = new ArrayList<Post>(); //Liste des éléments à traiter directement
		this.currentPost = currentPostsQuestion.remove(0);
		this.currentPost.setNumApparition(this.numPost);
		//On traite les autres phrase de notre "question"
		Post previousPost = this.currentPost;
		for (Post post : currentPostsQuestion) {
			if (post.isDirect()) {
				post.setNumApparition(previousPost.getNumApparition());
			} else {
				//On utilise un random pour le faire apparaitre entre le numéro de question courante et la fin de la partie
				int borneRandom = (this.remainingQuestions.size()+1) - ((previousPost.getNumApparition()-1) - this.numPost);
				int attente = this.random.nextInt(borneRandom);
				post.setNumApparition(previousPost.getNumApparition() + attente);
			}
			
			
			if (post.getNumApparition()== this.numPost) {
				toAddInFirst.add(post);
			}
			previousPost = post;
		}
		//On ajoute la liste en tête de file d'attente pour être sur que les phrases soient traitées en premières
		this.wainting.addAll(0, toAddInFirst);
		//On supprimer les phrases déjà ajoutées
		currentPostsQuestion.removeAll(toAddInFirst);
		//On ajoute toutes les autres
		this.wainting.addAll(currentPostsQuestion);		
		
		return this.currentPost;
	}

	private ArrayList<Post> getNewQuestion() {
		this.numPost++;
		Question question = remainingQuestions.remove(0);
		return question.getTransformedPosts(this.joueurs, this.random);
	}
	
	public boolean isFinished() {
		return (this.remainingQuestions.size()==0 && this.wainting.size()==0);
	}

	public ArrayList<Post> getAlreadyPosted() {
		return alreadyPosted;
	}
}
