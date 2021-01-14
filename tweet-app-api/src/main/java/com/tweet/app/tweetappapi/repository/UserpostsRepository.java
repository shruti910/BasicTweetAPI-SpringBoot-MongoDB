package com.tweet.app.tweetappapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tweet.app.tweetappapi.model.Userposts;

@Repository
public interface UserpostsRepository extends MongoRepository<Userposts, String>{
	
	@Query("{'_id':{'$regex':'?0','$options':'i'}}")  
   Optional <List<Userposts>> findAllByText(String text);

}