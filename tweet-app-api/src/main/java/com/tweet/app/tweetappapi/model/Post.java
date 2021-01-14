package com.tweet.app.tweetappapi.model;

public class Post{
	private static int counter=0;
	private int postId;
	private String postBody;
	private String postDate;
	
	
	public Post(String postBody) {
		super();
		this.postId = ++counter;
		this.postBody = postBody;
		this.postDate = java.time.LocalDate.now().toString();
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPostBody() {
		return postBody;
	}
	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	@Override
	public String toString() {
		return "Post [postId=" + postId + ", postBody=" + postBody + ", postDate=" + postDate + "]";
	}
}

