package me.yohan.fc.questions;

import java.util.ArrayList;

import me.yohan.fc.questions.Question.Able;

public interface Questions {

	public ArrayList<Question> getAllQuestions ();
	
	public ArrayList<Question> getQuestionsWithCriteria(int nbrJoueurs, ArrayList<Able> able, ArrayList<Question.Type> types);
	
	public static ArrayList<Question> cloneQuestions(ArrayList<Question> questions) {
		ArrayList<Question> newQuestions = new ArrayList<Question>();
		
		for (Question question : questions) {
			newQuestions.add(question.clone());
		}
		
		return newQuestions;
	}
}
