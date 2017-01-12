package com.niit.backend.implementation;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import oracle.sql.DATE;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.BlogDao;
import com.niit.backend.model.Blog;

@Repository
public class BlogDaoImpl implements BlogDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String currentDateYYYYMMDD()
	{
		/*SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	    */
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String strDate = dateFormat.format(date);
		return strDate;
	}
	
	public String currentTimeHHmmss()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String strTime=dateFormat.format(date);
		return strTime;
	}
		
	public void createBlog(Blog blog) {
		blog.setCreateddate(currentDateYYYYMMDD());
		Session session = sessionFactory.openSession();
		session.save(blog);
		session.flush();
		session.close();
	}

	public List<Blog> getAllBlogs() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Blog  order by blogid desc");
		List<Blog> blogs = query.list();
		session.close();
		return blogs;
	}

	public Blog getBlogById(int id) {
		Session session = sessionFactory.openSession();
		// select * from personinfo where id=2
		Blog blog = (Blog) session.get(Blog.class, id);
		session.close();
		return blog;

	}

	public Blog updateBlog(int blogid, Blog blog) {
		Session session = sessionFactory.openSession();
		System.out.println("Id of Blog is to update is: " + blog.getBlogid());
		if (session.get(Blog.class, blogid) == null)
			return null;
		session.merge(blog); // update query where personid=?
		// select [after modification]
		Blog updatedBlog = (Blog) session.get(Blog.class, blogid); // select
																	// query
		session.flush();
		session.close();
		return updatedBlog;

	}

	public void deleteBlog(int blogid) {
		Session session = sessionFactory.openSession();

		Blog blog = (Blog) session.get(Blog.class, blogid);
		session.delete(blog);

		session.flush();
		session.close();

	}

	public List<Blog> getBlogByStatus(String status) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Blog where status ='" + status + "' order by blogid desc");
		List<Blog> blogs = query.list();
		session.close();
		return blogs;
	}

}
