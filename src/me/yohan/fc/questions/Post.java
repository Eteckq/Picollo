package me.yohan.fc.questions;

import me.yohan.fc.questions.Question.Type;

public class Post implements Cloneable {

	private Type title;
	private String message;
	private boolean direct;
	private int numApparition;
	
	public Post(String message, boolean direct) {
		this.message = message;
		this.direct = direct;
		this.numApparition = -1;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isDirect() {
		return direct;
	}
	
	public int getNumApparition() {
		return this.numApparition;
	}
	
	public void setNumApparition(int numApparition) {
		this.numApparition = numApparition;
	}

	public Type getTitle() {
		return this.title;
	}

	public void setTitle(Type title) {
		this.title = title;
	}
	
	public Post clone() {
		Post post = new Post(this.message, this.direct);
		post.setTitle(this.title);
		post.setNumApparition(this.numApparition);
		return post;
	}
}
