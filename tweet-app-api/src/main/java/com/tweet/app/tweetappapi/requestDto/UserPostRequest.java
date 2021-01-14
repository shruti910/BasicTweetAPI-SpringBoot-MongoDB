package com.tweet.app.tweetappapi.requestDto;

public class UserPostRequest {
	private String user;
	private int postId;
	public String getUserId() {
		return user;
	}
	public void setUserId(String userId) {
		this.user = userId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	
}
