package me.yohan.fc.questions;

import java.util.ArrayList;

import me.yohan.fc.Joueur;

/* Une question doit étre définis par : TYPE,NBRP,COMP,QUESTION
 * -Son type : "Jeu" "Regle" "Defi" "Virus"
 * -Son nombre de joueurs : par exemple 2 pour 2 joueurs
 * -Sa compatibilité : "de" "carte" ou "null"
 * -La question : "Tous le monde finis son verre"
 * -Si elle est en plusieurs partie ( multiple ) : "Direct" "Indirect" "null"
 * Par défaut, une question est toujours en jeu (c'est a dire qu'il faut mini 0 joueur et aucune restriction)
 * 
 */

public abstract class Question implements Cloneable {
	
	public enum Able {DE,CARTE}
	public enum Type {CHALLENGE{
								        public String toString(){
								            return "Défi";
								        }},
						MINIGAME{
								        public String toString(){
								            return "Mini-jeu";
								        }},
						RULE{
								        public String toString(){
								            return "Règle";
								        }},
						VIRUS{
								        public String toString(){
								            return "Virus";
								        }}}
	
	private int nbrPlayer; 
	private ArrayList<Able> ables; 
	private ArrayList<Post> posts;
	
	////////////////Constructeurs//////////////////
	
	public Question (int nbrPlayers, ArrayList<Post> posts, ArrayList<Able> able) { //Si on ajoute la compatibilité
		this.nbrPlayer = nbrPlayers;
		this.posts = posts;
		this.ables = able;
	}
	
	public Question (ArrayList<Post> posts, ArrayList<Able> able) { //Si on ajoute la compatibilité
		this.posts = posts;
		for(Post post : this.posts) {
			post.setTitle(this.getTitle());
			while(post.getMessage().contains("P"+(this.nbrPlayer+1))) { //Je regarde le nombre de Players
				this.nbrPlayer++;										//Sa tiens juste dans cette boucle While et éa marche aussi bien qu'avant !
			}															//c'est tout con j'y ai penser cette nuit ^^
		}
		this.ables = able;
	}

	
	///////////////////////Getters////////////////
	
	public int getNbPlayer() {
		return this.nbrPlayer;
	}
	
	public ArrayList<Able> getAble() {
		return this.ables;
	}
	
	public ArrayList<Post> getPosts() {
		return this.posts;
	}
	
	///////////////Affichage//////////////
	
	public ArrayList<Post> getTransformedPosts(ArrayList<Joueur> joueurs, CustomRandom customRandom) {		
		
		ArrayList<Joueur> availabled = new ArrayList<Joueur>(joueurs);
		ArrayList<Joueur> playersForQuestion = new ArrayList<Joueur>();
		for (int i=0 ; i<this.nbrPlayer; i++) {
			int indexPlayer = 0;
			if (availabled.size() > 1) {
				indexPlayer = customRandom.nextInt(availabled.size());
			}
			playersForQuestion.add(availabled.remove(indexPlayer));
		}
		
		for (Post currentPost : this.posts) {
			for(int i = 0;i<playersForQuestion.size();i++) {
				currentPost.setMessage(currentPost.getMessage().replaceAll("<P"+(i+1)+">", playersForQuestion.get(i).getName()));
			}
			
			transformOtherRandoms(currentPost, customRandom);
		}
		
	    return this.posts;
	}
	
	private void transformOtherRandoms(Post post, CustomRandom customRandom){
		while(post.getMessage().contains("<card>")) {
			post.setMessage(post.getMessage().replaceFirst("<card>", customRandom.card()));
		}
		while(post.getMessage().contains("<cardColor>")) {
			post.setMessage(post.getMessage().replaceFirst("<cardColor>", customRandom.cardColor()));
		}
		while(post.getMessage().contains("<cardRB>")) {
			post.setMessage(post.getMessage().replaceFirst("<cardRB>", customRandom.cardRB()));
		}
		while(post.getMessage().contains("<de>")) {
			post.setMessage(post.getMessage().replaceFirst("<de>", customRandom.de()));
		}
		while(post.getMessage().contains("<piece>")) {
			post.setMessage(post.getMessage().replaceFirst("<piece>", customRandom.PoF()));
		}
		while(post.getMessage().contains("<drink>")) {
			post.setMessage(post.getMessage().replaceFirst("<drink>", customRandom.drink()));
		}
		while(post.getMessage().contains("<hugeDrink>")) {
			post.setMessage(post.getMessage().replaceFirst("<hugeDrink>", customRandom.hugeDrink()));
		}
	}
	
	public abstract Type getTitle();
	
	@Override
	public abstract Question clone();
}
