package com.sivalabs.jblogger.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jblogger.entities.Comment;
import com.sivalabs.jblogger.entities.PageView;
import com.sivalabs.jblogger.entities.Post;
import com.sivalabs.jblogger.repositories.CommentRepository;
import com.sivalabs.jblogger.repositories.PageViewRepository;
import com.sivalabs.jblogger.repositories.PostRepository;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class PostService 
{
	@Autowired private PostRepository postRepository;
	@Autowired private CommentRepository commentRepository;
	@Autowired private PageViewRepository pageViewRepository;

	public List<Post> findAllPosts()
	{
		Sort sort = new Sort(Direction.DESC, "createdOn");
		return postRepository.findAll(sort);
	}
	
	public Page<Post> findPosts(PageRequest pageRequest) 
	{		
		Sort sort = new Sort(Direction.DESC, "createdOn");
		int pageNo = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		if(pageNo < 0){
			pageNo = 0;
		}
		if(pageSize < 1) {
			pageSize = 5;
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pageData = postRepository.findAll(pageable);
		
		return pageData;
	}
	
	public Page<Post> findPostsByTag(String tag, PageRequest pageRequest)
	{
		Sort sort = new Sort(Direction.DESC, "createdOn");
		int pageNo = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		if(pageNo < 0){
			pageNo = 0;
		}
		if(pageSize < 1) {
			pageSize = 5;
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pageData = postRepository.findByTags(tag, pageable);
		
		return pageData;
	}
	
	public Optional<Post> findPostById(int postId) {
		return postRepository.findById(postId);
	}
	
	public Post findPostByUrl(String url)
	{
		return postRepository.findByUrl(url);
	}
	
	public Post createPost(Post post) {
		return postRepository.save(post);
	}


	public Post updatePost(Post post) {
		return postRepository.save(post);
	}
	
	public void deletePost(Integer postId)
	{
		postRepository.deleteById(postId);
	}

	public List<Post> searchPosts(String query) {
		return postRepository.searchPosts("%"+query.toLowerCase()+"%");
	}

	public void updateViewCount(Integer postId, Long viewCount) {
		postRepository.updateViewCount(postId, viewCount);
	}
	
	public void savePageView(PageView pageView)
	{
		pageViewRepository.save(pageView);	
	}

	public List<Comment> findAllComments()
	{
		Sort sort = new Sort(Direction.DESC, "createdOn");
		return commentRepository.findAll(sort);
	}

	public Comment createComment(Comment comment) {
		return commentRepository.save(comment);
	}

	public Page<Comment> findComments(PageRequest request)
	{
		Sort sort = new Sort(Direction.DESC, "createdOn");
		int pageNo = request.getPageNumber();
		int pageSize = request.getPageSize();
		if(pageNo < 0){
			pageNo = 0;
		}
		if(pageSize < 1) {
			pageSize = 5;
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Comment> pageData = commentRepository.findAll(pageable);
		
		return pageData;
	}

	public void deleteComment(Integer commentId)
	{
		commentRepository.deleteById(commentId);
	}

}
