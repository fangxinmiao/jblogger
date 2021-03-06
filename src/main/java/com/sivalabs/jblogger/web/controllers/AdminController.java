/**
 *
 */
package com.sivalabs.jblogger.web.controllers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sivalabs.jblogger.domain.BlogOverview;
import com.sivalabs.jblogger.domain.TimePeriod;
import com.sivalabs.jblogger.entities.Post;
import com.sivalabs.jblogger.entities.Tag;
import com.sivalabs.jblogger.security.AuthenticatedUser;
import com.sivalabs.jblogger.security.SecurityUtils;
import com.sivalabs.jblogger.services.BlogService;
import com.sivalabs.jblogger.services.EmailService;
import com.sivalabs.jblogger.services.PostService;
import com.sivalabs.jblogger.services.TagService;

/**
 * @author Siva
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController
{	
	@Autowired private EmailService emailService;
	@Autowired private BlogService blogService;
	@Autowired private PostService postService;
	@Autowired private TagService tagService;
	
	@RequestMapping("/dashboard")
	public String dashboard(@RequestParam(value="timePeriod", defaultValue="TODAY") String timePeriod, Model model)
	{
		BlogOverview overview = blogService.getBlogOverView(TimePeriod.fromString(timePeriod));
		model.addAttribute("overview", overview);
		return "admin/dashboard";
	}
	
	@RequestMapping(value="/posts/newpost", method=RequestMethod.GET)
	public String newPostForm(Model model) {
		Post post = new Post();
		model.addAttribute("post", post);
		return "admin/newpost";
	}
	
	@RequestMapping(value="/posts", method=RequestMethod.POST)
	public String createPost(@Valid @ModelAttribute("post") Post post, 
							BindingResult result, 
							Model model, HttpServletRequest request) {
		if(result.hasErrors()){
			model.addAttribute("post",post);
	        return "admin/newpost";
		}
		String url = getUrl(post);
		post.setUrl(url);
		
		String[] tagsString = request.getParameterValues("tagsList");
		if(tagsString != null){
			Set<Tag> tags = this.tokenize(tagsString);
			post.setTags(tags);
		}
		AuthenticatedUser loggedinUser = SecurityUtils.getCurrentUser();
		post.setCreatedBy(loggedinUser.getUser());
		Post createdPost = this.postService.createPost(post);
		
		String subject = "A new post created :"+post.getTitle();
		String content = "Content :\n"+post.getShortDescription();
		emailService.send(subject, content);
		
		return "redirect:/posts/"+createdPost.getUrl();
	}
	
	@RequestMapping(value="/posts/{postId}/edit", method=RequestMethod.GET)
	public String editPostForm(@PathVariable("postId") Integer postId, Model model) {
		Optional<Post> post = postService.findPostById(postId);
		model.addAttribute("post", post.get());
		return "admin/editpost";
	}
	
	@RequestMapping(value="/posts/{postId}", method=RequestMethod.POST)
	public String updatePost(@PathVariable("postId") Integer postId, @Valid @ModelAttribute("post") Post post, 
							BindingResult result, 
							Model model, HttpServletRequest request) {
		if(result.hasErrors()){
			model.addAttribute("post",post);
	        return "admin/editpost";
		}
		Post oldPost = postService.findPostById(postId).get();
		
		oldPost.setTitle(post.getTitle());
		oldPost.setShortDescription(post.getShortDescription());
		oldPost.setContent(post.getContent());
		
		String[] tagsString = request.getParameterValues("tagsList");
		if(tagsString != null){
			Set<Tag> tags = this.tokenize(tagsString);
			oldPost.setTags(tags);
		}
		
		oldPost.setUpdatedOn(new Date());
		Post updatedPost = this.postService.updatePost(oldPost);
				
		return "redirect:/posts/"+updatedPost.getUrl();
	}
	
	@ResponseBody
	@RequestMapping(value="/posts/{postId}/delete", method=RequestMethod.DELETE)
	public String deletePost(@PathVariable("postId") Integer postId) 
	{
		postService.deletePost(postId);		
		return "success";
	}
	
	@RequestMapping("/posts")
	public String posts(Model model)
	{
		model.addAttribute("posts", postService.findAllPosts());
		return "admin/posts";
	}
	
	@RequestMapping("/comments")
	public String comments(Model model)
	{
		model.addAttribute("comments", postService.findAllComments());
		return "admin/comments";
	}
	
	@RequestMapping("/comments/delete")
	public String deleteComments(HttpServletRequest request)
	{
		String[] commentIds = request.getParameterValues("comments");
		for (String id : commentIds) {
			postService.deleteComment(Integer.valueOf(id));			
		}
		return "redirect:/admin/comments";
	}
	
	@RequestMapping("/tags")
	public String tags(Model model)
	{
		model.addAttribute("tags", tagService.findAllTags());
		return "admin/tags";
	}
	
	@RequestMapping("/tagsJson")
	@ResponseBody
	public List<Tag> tagsJSON()
	{
		return tagService.findAllTags();
	}
	
	private static String getUrl(Post post)
	{
		String title = post.getTitle().trim().toLowerCase();
		String url = title.replaceAll("\\s+", "-");
		url = url.replaceAll("[^A-Za-z0-9]", "-");
		url = url.replaceAll("-+", "-");
		return url;
	}
	
	public Set<Tag> tokenize(String[] labelsArr) {
		Set<Tag> tags = new HashSet<>();
		for (String label : labelsArr)
		{
			if(StringUtils.isEmpty(label)) continue;
			Tag tag = null;
			try {
				Integer tagId = Integer.parseInt(label.trim());
				tag = tagService.findById(tagId);
			}catch (NumberFormatException e){
				tag = tagService.findByLabel(label.trim());
			}
			if(tag == null) {
				tag = new Tag();
				tag.setLabel(label.trim());
				tag = tagService.createTag(tag);
			}
			tags.add(tag);
		}
		return tags;
	}
}
