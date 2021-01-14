package com.tweet.app.tweetappapi.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tweet.app.tweetappapi.requestDto.AddPostRequest;
import com.tweet.app.tweetappapi.model.Post;
import com.tweet.app.tweetappapi.requestDto.SearchTextRequest;
import com.tweet.app.tweetappapi.requestDto.SubUnsubRequest;
import com.tweet.app.tweetappapi.requestDto.UserDTO;
import com.tweet.app.tweetappapi.requestDto.UserPostRequest;
import com.tweet.app.tweetappapi.service.UserPostDALImpl;
import com.tweet.app.tweetappapi.model.Userposts;
import com.tweet.app.tweetappapi.repository.UserpostsRepository;

@RestController
public class ApiController {
	
	private final Logger LOG = LoggerFactory.getLogger(ApiController.class);
	@Autowired	
	UserpostsRepository postsRepo;
	@Autowired
	UserPostDALImpl dal;
	
	
	@RequestMapping(value="/addpost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addpost(@RequestBody AddPostRequest postBody) {
		LOG.info("inside add post");
		Post post = new Post(postBody.getPostBody());
		return dal.addPost(postBody.getUser(), post);
	}

	
	@RequestMapping(value="/getposts",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Post> getPosts(@RequestBody UserDTO user) {
		LOG.info("Getting user with ID: {}.", user.getUser());
		List<Post> postList= new ArrayList<>();
		Optional<Userposts> userposts =	postsRepo.findById(user.getUser());
		if(userposts.isPresent()) {
			userposts.get().getPosts().stream().forEach(post->{
				postList.add(post);
			});
			userposts.get().getSubscribed().stream().forEach(subscribed->{
				Optional<Userposts> subscribeduser=postsRepo.findById(subscribed);
				if(subscribeduser.isPresent()) {
					subscribeduser.get().getPosts().stream().forEach(subposts->{
						postList.add(subposts);
					});
				}
			});
		}
	 return postList;
		
	}
	
	
	@DeleteMapping(value="/delpost",produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public String delPosts(@RequestBody UserPostRequest userpost){
		LOG.info("Deleting post with userID: {} and postId {}.", userpost.getUserId(),userpost.getPostId() );
		String msg= null;
		try {
			msg = dal.deleteUserPosts(userpost.getUserId(), userpost.getPostId());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return msg;
	}
	
	
	
	@GetMapping(value="/searchuser",produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Boolean> searchUser(@RequestBody SearchTextRequest search) {
		LOG.info("searching users and subscribers with userID: {} and search text {}.", search.getUserId(),search.getSearchText() );
		
		Optional<List<Userposts>> userlist= postsRepo.findAllByText(search.getSearchText());
		HashMap<String, Boolean> map= new HashMap<>();
		if(userlist.isPresent()) {
			List<Userposts> users=userlist.get();
			Optional<Userposts> Mainuser=postsRepo.findById(search.getUserId());
			if(Mainuser.isPresent()) {
				ArrayList<String> subscribedTo=Mainuser.get().getSubscribed();
				for(Userposts eachUser:users ) {
					for(String sub :subscribedTo ) {
						if(sub.equalsIgnoreCase(eachUser.get_id())) {
							map.put(eachUser.get_id(), true);
							LOG.info("matched {} - {}",sub,eachUser.get_id() );
							break;
						}
						else
						map.put(eachUser.get_id(), false);
					}
				}
			}
		}
		return  map;
	}
	
	@PutMapping(value="/subscriber",consumes = MediaType.APPLICATION_JSON_VALUE )
	public String subscriber(@RequestBody SubUnsubRequest subunsub) {
		String user= subunsub.getUser();
		String subuser= subunsub.getSubuser();;
		return dal.subscribe(user, subuser);
	}
}
