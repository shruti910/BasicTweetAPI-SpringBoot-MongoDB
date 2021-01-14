package com.tweet.app.tweetappapi.requestDto;

public class SearchTextRequest {
	
	private String user;
	private String searchText;
	public String getUserId() {
		return user;
	}
	public void setUserId(String userId) {
		this.user = userId;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	

}
