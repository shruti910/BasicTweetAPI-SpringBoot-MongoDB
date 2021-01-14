## RESTful Microservice using Springboot and MongoDB
---
### MongoDB config:
1. DB name: `FrescoTweet`
2. Collection: `userposts`

### Models in app:
1. `userposts` contains following fields:
	* `_id` : userid of user
	* `posts` : a list of `Post` objects
	* `susbcribed` : a list of user ids of the people whom the current user has subscribed to.

	#### Example
	
	```BSON
	{
	_id: "john@org.com",
	posts: [
		{
		postId:1,
		postBody:"It's a lovely day",
		postDate:"13-01-2021"
		},
		{
		postId:2,
		postBody:"It's a great day",
		postDate:"12-01-2021"
		}
	],
	subscribed : ["shruti@org.com", "parul@org.com"]
	}
	```
	---
2. `Post` contains the following fields:
	* `postId` : id of the post
	* `postBody` : contents of tweet post
	* `postDate` : date of posting of tweet.

	#### Example
	
	```BSON
		{
			postId:1,
			postBody:"It's a lovely day",
			postDate:"13-01-2021"
		}
	```
***
### REST Endpoints
1. /addpost
	* takes a String `user` which is the id of the user and another String `postBody` and save it it in the DB.
	* Request Body:
	```JSON
	{
    "user": "abc@org.com",
    "postBody" : "make today count"
    }
	```
2. /getposts
	* takes a string `user` id of the user and returns all the posts of the user along with the posts of the subscribed users.
	* Request Body:
	```JSON
	{
    "user": "parul@org.com"
	}
	```
3. /delpost
	* takes a String `user` id of a user and the `postId` of the post to be deleted and delete only the post in the database.
	* Request Body:
	```JSON
	{
    "user": "abc@org.com",
    "postId" : 4
	}
	```
4. /searchuser
	* takes a String `user` id of the user and another String `searchText` and returns  a dictionary of users matching the `searchText` and boolean value whether the user had subscribed to them or not.
	* Request Body:
	```JSON
	{
    "user": "john@org.com",
    "searchText" : "hn"
	}
	```
5. /subscriber
	* takes a String `user` id of the user and another String `subuser` and add `subuser` to the subscribed list of the `user` if not already subscribed. If the `subuser` is already subscribed to, then remove it. Similar to subscribe and unsubscribe operation.
	* Request Body:
	```JSON
	{
    "user": "abc@org.com",
    "subuser" : "pqr@gmail.com"
	}
	```


