package me.yohan.fc.questions;

import java.util.ArrayList;

public class Challenge extends Question {

	public Challenge(int nbrPlayers, ArrayList<Post> posts, ArrayList<Able> able) {
		super(nbrPlayers, posts, able);
	}
	
	public Challenge(ArrayList<Post> posts, ArrayList<Able> able) {
		super(posts, able);
	}
	
	@Override
	public Type getTitle() {
		return Type.CHALLENGE;
	}

	@Override
	public Question clone() {
		ArrayList<Post> newPosts = new ArrayList<Post>();
		
		for (Post post : this.getPosts()) {
			newPosts.add(post.clone());
		}
		
		return new Challenge(newPosts, new ArrayList<Able>(this.getAble()));
	}
}
