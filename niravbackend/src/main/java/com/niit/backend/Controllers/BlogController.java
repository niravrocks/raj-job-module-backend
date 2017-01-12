package com.niit.backend.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.niit.backend.model.Error;
import com.niit.backend.dao.BlogDao;
import com.niit.backend.model.Blog;
import com.niit.backend.model.User;
@RestController
public class BlogController {


	@Autowired
	private BlogDao blogDao;

	@RequestMapping(value = "/creblog", method = RequestMethod.POST)
	public ResponseEntity<?> createBlog(@RequestBody Blog blog, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else {
			blog.setUserId(user.getId());
			blog.setStatus("P");
			blogDao.createBlog(blog);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getAllBlogs", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogs(HttpSession session) {
		List<Blog> blogs = blogDao.getAllBlogs();
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> getBlogById(@PathVariable(value = "id") int id) {
		Blog blog = blogDao.getBlogById(id);
		if (blog == null) {
			System.out.println("blog is null..........................");
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		System.out.println("returning blog object..........................");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBlog(@PathVariable int id, @RequestBody Blog blog, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN")) {
			Blog editblog = blogDao.getBlogById(id);
			if (editblog == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			Blog updatedBlog = blogDao.updateBlog(id, blog);
			return new ResponseEntity<Blog>(updatedBlog, HttpStatus.OK);
		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}

	@RequestMapping(value = "/blog/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBlog(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN"))
		/* else if (user.getRole().equalsIgnoreCase("Admin")) */ {
			Blog blog = blogDao.getBlogById(id);
			if (blog == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			blogDao.deleteBlog(id);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}

	@RequestMapping(value = "/blogsta/{status}", method = RequestMethod.GET)
	public ResponseEntity<?> getBlogByStatus(@PathVariable(value = "status") String status, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN")) {
			List<Blog> blogs = blogDao.getBlogByStatus(status);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}

}

