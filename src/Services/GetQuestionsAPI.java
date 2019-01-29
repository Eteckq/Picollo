package Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.yohan.fc.questions.Challenge;
import me.yohan.fc.questions.Minigame;
import me.yohan.fc.questions.Post;
import me.yohan.fc.questions.Question;
import me.yohan.fc.questions.Rule;
import me.yohan.fc.questions.Virus;
import me.yohan.fc.questions.Question.Able;
import me.yohan.fc.questions.Question.Type;

public class GetQuestionsAPI {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONArray readJsonFromUrl(String url) {
		try {
			InputStream is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			is.close();
			return json.getJSONArray("result");
		} catch (Exception e){
			return null;
		}
	}

	public static ArrayList<Question> buildQuestionsFromJArray(JSONArray jarray) {
		ArrayList<Question> questions = new ArrayList<Question>();
		ArrayList<Post> posts;
		ArrayList<Able> ables;
		if (jarray != null) {
			try {
	
				for (int j = 0; j < jarray.length(); j++) {
					posts = new ArrayList<Post>();
					ables = new ArrayList<Able>();
					JSONArray jposts;
					JSONObject jquestion = jarray.getJSONObject(j);
					if ((jposts = jquestion.getJSONArray("posts")) != null) {
						for (int i = 0; i < jposts.length(); i++) {
							JSONObject jpost = jposts.getJSONObject(i);
							posts.add(new Post(jpost.getString("message"), Boolean.getBoolean(jpost.getString("direct"))));
						}
					}
	
					JSONArray jables;
					if ((jables = jquestion.getJSONArray("ables")) != null) {
						for (int i = 0; i < jables.length(); i++) {
							JSONObject jable = jables.getJSONObject(i);
							ables.add(Able.valueOf(jable.getString("able")));
						}
					}
	
					Question toAdd;
					int nbrPlayers = jquestion.getInt("nbrPlayers");
					switch (jquestion.getString("type")) {
					case "Défi":
						toAdd = new Challenge(nbrPlayers, posts, ables);
						break;
					case "Virus":
						toAdd = new Virus(nbrPlayers, posts, ables);
						break;
					case "Mini-jeu":
						toAdd = new Minigame(nbrPlayers, posts, ables);
						break;
					case "Règle":
						toAdd = new Rule(nbrPlayers, posts, ables);
						break;
					default:
						toAdd = null;
					}
					questions.add(toAdd);
				}
	
				return questions;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new ArrayList<Question>();
	}
}
