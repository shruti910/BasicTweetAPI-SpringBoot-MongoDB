package com.tweet.app.tweetappapi.requestDto;

public class AddPostRequest {
	private String user;
	private String postBody;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPostBody() {
		return postBody;
	}
	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}
	
}
