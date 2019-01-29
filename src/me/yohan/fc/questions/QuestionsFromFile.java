package me.yohan.fc.questions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import me.yohan.fc.questions.Question.Able;

import java.io.File;
import java.io.FileReader;


public final class QuestionsFromFile implements Questions {

	private static volatile QuestionsFromFile instance = null;
	private ArrayList<Question> questionsListe = new ArrayList<>();
	
	private QuestionsFromFile () {
		super();
	}
	
	public static QuestionsFromFile getInstance() {
        if (QuestionsFromFile.instance == null) {
        	QuestionsFromFile.instance = new QuestionsFromFile();
        	QuestionsFromFile.instance.build();
        }
        return QuestionsFromFile.instance;
	}
	
	
	
	private void build() { //Lis le fichier PostList et construit les questions
		BufferedReader bR;
		try {
			bR = new BufferedReader(new FileReader(new File("PostList")));
			String line;
			Boolean post = false;
			Boolean boolAble = false;
			Boolean type = false;
			Boolean suivie = true;
			
			ArrayList<Post> posts = null;
			ArrayList<Able> able = null;
			
			while ((line = bR.readLine()) != null) {
				   if(line.contains("[Post]")) {
					   posts = new ArrayList<Post>();
					   post = true;
					   type = false;
					   boolAble = false;
				   }
				   if(line.contains("[Able]")) {
					   able = new ArrayList<Able>();
					   post = false;
					   boolAble = true;
					   type = false;
				   }
				   if(line.contains("[Type]")) {
					   post = false;
					   boolAble = false;
					   type = true;
				   }

				   if(post) {
					   if(!line.contains("[Post]")) {
						   String[] newLine = line.split(";");
						   for(String string : newLine) {
							   
							   if(string.contains("true")) {
								   suivie = true;
							   } else if(string.contains("false")) {
								   suivie = false;
							   } else {
								   posts.add(new Post(string, suivie));
							   }
						   }
					   }
				   }
				   if(boolAble) {
					   if(!line.contains("[Able]")) {
						   if(line.contains("CARTE")) {
							   able.add(Question.Able.CARTE);
						   } else if(line.contains("De")) {
							   able.add(Question.Able.DE);
						   }
					   }
				   }
				   if(type) {
					   if(!line.contains("[Type]")) {
						   if(line.contains("Minigame")) {
							 questionsListe.add(new Minigame(posts, able));
						   } else if(line.contains("Challenge")) {
							 questionsListe.add(new Challenge(posts, able));
						   } else if(line.contains("Virus")) {
							 questionsListe.add(new Virus(posts, able));
						   } else {
							 questionsListe.add(new Rule(posts, able));
						   }
					   }
				   }
			}
			bR.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
