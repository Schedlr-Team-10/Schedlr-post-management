package com.schedlr.Schedlr_post_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/schedulr")
public class SchedlrPostManagementApplication{
	@Autowired
	private static PostRepository postRepository = null;

	public SchedlrPostManagementApplication(PostRepository postRepository) {
		SchedlrPostManagementApplication.postRepository = postRepository;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SchedlrPostManagementApplication.class, args);

		LocalDateTime time = LocalDateTime.now();
		while (time != null) {
			checkAndPost(time);
			time = LocalDateTime.now();
		}
	}


	public static void checkAndPost(LocalDateTime time) throws Exception {
		List<Post> posts = postRepository.findAll();

		for (Post post : posts) {
			if(time.isAfter(post.getSchedule()))
			{
				boolean isChecked = false;

				if(post.isFacebook()) {
					System.out.println("We will use this later");
					isChecked = true;
				}
				if(post.isTwitter())
				{
					System.out.println("We will use this later");
					isChecked = true;
				}
				if(post.isLinkedin())
				{
					System.out.println(getPostById(post.getPostid()));
					isChecked = true;
				}
				if(post.isInstagram()) {
					System.out.println("We will use this later");
					isChecked = true;
				}

				if (!isChecked){
					throw new Exception("No box was checked. Where is the post going?");
				}
			}

			deletePost(post.getPostid());
		}
	}

	@GetMapping
	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	@GetMapping("/{id}")
	public static Post getPostById(@PathVariable int id) {
		return postRepository.findById(id).orElse(null);
	}

	record NewPost(byte[] image,String description, boolean instagram, boolean facebook, boolean twitter, boolean linkedin) {
	}

	@PostMapping
	public void addPost(@RequestBody NewPost newPost) {
		Post post = new Post();
		post.setImage(newPost.image);
		post.setDescription(newPost.description);
		post.setInstagram(newPost.instagram);
		post.setFacebook(newPost.facebook);
		post.setLinkedin(newPost.linkedin);
		post.setTwitter(newPost.twitter);
		postRepository.save(post);
	}

	@DeleteMapping("{postId}")
	public static void deletePost(@PathVariable("postId") Integer postId) {
		postRepository.deleteById(postId);
	}

	@PutMapping("{postId}")
	public void updatePost(@PathVariable("postId") Integer id, @RequestBody NewPost newPost) throws Exception {
		Post post = postRepository.findById(id).orElse(null);

		if(post != null) {
			post.setImage(newPost.image);
			post.setDescription(newPost.description);
			post.setInstagram(newPost.instagram);
			post.setFacebook(newPost.facebook);
			post.setLinkedin(newPost.linkedin);
			post.setTwitter(newPost.twitter);
			postRepository.save(post);
		}
		else{
			throw new Exception("Post not found");
		}
	}
}
