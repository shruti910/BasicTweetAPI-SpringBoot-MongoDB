package com.tweet.app.tweetappapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

import com.tweet.app.tweetappapi.model.Post;
import com.tweet.app.tweetappapi.model.Userposts;
import com.tweet.app.tweetappapi.repository.UserPostDAL;
import com.tweet.app.tweetappapi.repository.UserpostsRepository;
import com.mongodb.BasicDBObject;



@Repository
public class UserPostDALImpl implements UserPostDAL {
	private final Logger Log = LoggerFactory.getLogger(UserPostDALImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired	
	UserpostsRepository postsRepo;
	
	@Override
	public String deleteUserPosts(String userId, int postId) throws Exception  {
		try {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		Update update = new Update();
		update.pull("posts",new BasicDBObject("postId",postId) );
		mongoTemplate.updateMulti(query,update, Userposts.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			return "Error while deleting";
		}
		return "OK 200";
	}

	@Override
	public String subscribe(String user, String subuser) {
		Optional<Userposts> userOptional= postsRepo.findById(user);
		if(userOptional.isPresent()) {
			Userposts userOb=userOptional.get();
			ArrayList<String> subsList=userOb.getSubscribed();
			if(subsList.contains(subuser)) { //unsubscribe
				Log.info("unsubscribe {} from {}", subuser,user);
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(user));
				Update update = new Update();
				update.pull("subscribed",subuser );
				mongoTemplate.updateFirst(query, update, Userposts.class) ;
			}
			else {			//subscribe
				Log.info("subscribe {} by {}",subuser,user);
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(user));
				Update update = new Update();
				update.push("subscribed",subuser );
				mongoTemplate.updateFirst(query, update, Userposts.class) ;
			
			}
		
		}
		return "OK 200";
	}

	@Override
	public String addPost(String user, Post post) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(user));
		Update update = new Update();
		update.push("posts", post);
		mongoTemplate.updateFirst(query,update, Userposts.class);
		
		return  "OK 200";
	}

}

