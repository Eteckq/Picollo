package me.yohan.fc.questions;

import java.util.ArrayList;

public class Minigame extends Question {

	public Minigame(int nbrPlayers, ArrayList<Post> posts, ArrayList<Able> able) {
		super(nbrPlayers, posts, able);
	}
	
	public Minigame(ArrayList<Post> posts, ArrayList<Able> able) {
		super(posts, able);
	}
	
	@Override
	public Type getTitle() {
		return Type.MINIGAME;
	}

	@Override
	public Question clone() {
		ArrayList<Post> newPosts = new ArrayList<Post>();
		
		for (Post post : this.getPosts()) {
			newPosts.add(post.clone());
		}
		
		return new Minigame(newPosts, new ArrayList<Able>(this.getAble()));
	}
}
