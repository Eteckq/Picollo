package me.yohan.fc.questions;

import java.util.ArrayList;

public class Virus extends Question {

	public Virus(int nbrPlayers, ArrayList<Post> posts, ArrayList<Able> able) {
		super(nbrPlayers, posts, able);
	}
	
	public Virus(ArrayList<Post> posts, ArrayList<Able> able) {
		super(posts, able);
	}
	
	@Override
	public Type getTitle() {
		return Type.VIRUS;
	}
	
	@Override
	public Question clone() {
		ArrayList<Post> newPosts = new ArrayList<Post>();
		
		for (Post post : this.getPosts()) {
			newPosts.add(post.clone());
		}
		
		return new Virus(newPosts, new ArrayList<Able>(this.getAble()));
	}
}
