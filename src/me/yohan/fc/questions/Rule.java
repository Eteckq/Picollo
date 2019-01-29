package me.yohan.fc.questions;

import java.util.ArrayList;

public class Rule extends Question {

	public Rule(int nbrPlayers, ArrayList<Post> posts, ArrayList<Able> able) {
		super(nbrPlayers, posts, able);
	}
	
	public Rule(ArrayList<Post> posts, ArrayList<Able> able) {
		super(posts, able);
	}
	
	@Override
	public Type getTitle() {
		return Type.RULE;
	}
	
	@Override
	public Question clone() {
		ArrayList<Post> newPosts = new ArrayList<Post>();
		
		for (Post post : this.getPosts()) {
			newPosts.add(post.clone());
		}
		
		return new Rule(newPosts, new ArrayList<Able>(this.getAble()));
	}
}
