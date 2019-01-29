package me.yohan.fc.questions;

import java.util.ArrayList;

import org.json.JSONArray;

import Services.GetQuestionsAPI;
import me.yohan.fc.questions.Question.Able;
import me.yohan.fc.questions.Question.Type;

public class QuestionsFromBDD implements Questions {

	public final static String BASIC_URL_QUESTIONS = "http://yoro.livehost.fr/getQuestions.php";
	
	@Override
	public ArrayList<Question> getAllQuestions() {
		return GetQuestionsAPI.buildQuestionsFromJArray(GetQuestionsAPI.readJsonFromUrl(BASIC_URL_QUESTIONS));
	}

	@Override
	public ArrayList<Question> getQuestionsWithCriteria(int nbrJoueurs, ArrayList<Able> able, ArrayList<Type> types) {
		String params = "?nbrPlayers="+nbrJoueurs;
		int i = 0;
		for (Able currentAble : Able.values()) {
			if (!able.contains(currentAble)) {
				params += "&able"+i+"="+currentAble.toString();
				i++;
			}
		}
		i = 0;
		for (Type currentType : Type.values()) {
			if (!types.contains(currentType)) {
				params += "&type"+i+"="+currentType.toString();
				i++;
			}
		}
		JSONArray jQuestions = GetQuestionsAPI.readJsonFromUrl(BASIC_URL_QUESTIONS+params);
		return GetQuestionsAPI.buildQuestionsFromJArray(jQuestions);
	}

}
