package me.yohan.fc.questions;

import java.util.ArrayList;

import me.yohan.fc.questions.Question.Able;

public final class QuestionsFromCode implements Questions { //Code temporaire, rien a voir avec le commentaire au dessus

	private static volatile QuestionsFromCode instance = null;
	private ArrayList<Question> questionsListe = new ArrayList<>(); //Contient toutes les questions
	
	private QuestionsFromCode () {
		super();
	}
	
	public static QuestionsFromCode getInstance() {
        if (QuestionsFromCode.instance == null) {
        	QuestionsFromCode.instance = new QuestionsFromCode();
        	QuestionsFromCode.instance.build();
        }
        return QuestionsFromCode.instance;
	}
	
	private void build() { //Permet de mettre les questions dans la liste ( est appeler lors du lancement de l'appli )
		//J'ajoute les questions directement dans le code, comme on a pas de fichier ou autre
		ArrayList<Post> posts; //Création des listes ici
		ArrayList<Able> able;
		
		
		posts = new ArrayList<Post>();
		posts.add(new Post("<P1> n'a plus le droit de parler", true));
		posts.add(new Post("<P1> peut de nouveau parler", false));
		questionsListe.add(new Virus(posts, new ArrayList<Able>()));
		
		posts = new ArrayList<Post>();
		posts.add(new Post("Chaque joueur choisit pile ou face", true));
		posts.add(new Post("Pile boit", true));
		posts.add(new Post("Face boit aussi !", true));
		questionsListe.add(new Minigame(posts, new ArrayList<Able>()));

		
		posts = new ArrayList<Post>();
		posts.add(new Post("<P1> lance le dé, il boit le nombre de gorgées équivalent", true));
		posts.add(new Post("Surprise <P1> ! Si tu as fais 6, tu bois cul sec !", true));
		able = new ArrayList<Able>();
		able.add(Question.Able.DE);
		questionsListe.add(new Challenge(posts, able));
		
		posts = new ArrayList<Post>();
		posts.add(new Post("<P1> pioche une carte. (suite juste aprés)", true));
		posts.add(new Post("Si ta carte est un <cardColor>, tu bois deux gorgées !", true));
		able = new ArrayList<Able>();
		able.add(Question.Able.CARTE);
		questionsListe.add(new Challenge(posts, able));
		
		posts = new ArrayList<Post>();
		posts.add(new Post("Jeu de la valise ! Chaque joueur ajoute l'élément de son choix dans la valise "
				+ "en répétant d'abord tous les éléments qu'elle contient déjà", true));
		questionsListe.add(new Minigame(posts, new ArrayList<Able>()));
		
		posts = new ArrayList<Post>();
		posts.add(new Post("<P1> pioche une carte",true));
		posts.add(new Post("Il bois deux gorgées si la carte est entre <card> et <card>",true));
		able = new ArrayList<Able>();
		able.add(Question.Able.CARTE);
		questionsListe.add(new Rule(posts, able));
	}

	@Override
	public ArrayList<Question> getAllQuestions() {
		return this.questionsListe;
	}

	@Override
	public ArrayList<Question> getQuestionsWithCriteria(int nbrJoueurs, ArrayList<Able> able, ArrayList<Question.Type> types) {
		ArrayList<Question> toReturn = new ArrayList<Question>();
		for (Question question : this.questionsListe) {
			if (able.containsAll(question.getAble())) { //Si la liste du materiel dispo est compatible
				if (nbrJoueurs >= question.getNbPlayer()) { // Si le nombre de joueurs est suffisant
					if (types.contains(question.getTitle())) { // Si le type de la question est compatible
						toReturn.add(question);
					}
				}
			}
		}
		return toReturn;
	}
	
}
