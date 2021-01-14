package com.tweet.app.tweetappapi.repository;

import com.tweet.app.tweetappapi.model.Post;

public interface UserPostDAL {
	
	String deleteUserPosts(String userId, int postId) throws Exception;
	String subscribe(String user, String subuser);
	String addPost(String user, Post post);

}