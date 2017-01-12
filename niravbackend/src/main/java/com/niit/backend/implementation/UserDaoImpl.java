package com.niit.backend.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.UserDao;
import com.niit.backend.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User authenticate(User user) {
		logger.debug("USERDAOIMPL :: AUTHENTICATE");
		Session session = sessionFactory.openSession();
		Query query = session
				.createQuery("from User where username=?  and password=?");
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		User validUser = (User) query.uniqueResult();
		session.flush();
		session.close();
		if (validUser != null) {
			logger.debug("VALID USER IS  " + validUser.getUsername() + " "
					+ validUser.getRole() + " " + validUser.isOnline());
		}
		if (validUser == null)
			logger.debug("Valid USER is null");
		return validUser;

	}

	/*
	 * public User updateUser(int userid, User user) {
	 * logger.debug("USERDAOIMPL::UPDATE");
	 * logger.debug("ISONLINE VALUE IS [BEFORE UPDATE]" + user.isOnline());
	 * Session session = sessionFactory.openSession(); User existingUser =
	 * (User) session.get(User.class, user.getId());
	 * existingUser.setOnline(user.isOnline()); session.update(existingUser);
	 * session.flush(); session.close();
	 * logger.debug("ISONLINE VALUE IS [AFTER UPDATE] " +
	 * existingUser.isOnline()); return existingUser; }
	 */

	@Transactional
	public User updateUser(int userid, User user) {
		// person -> modified value -> 226
		Session session = sessionFactory.openSession();
		// current person -> 226
		// currentPerson, person -> with same id
		// updating only variable person
		// notunique
		// select [before modification]ge
		System.out.println("Id of User is to update is: " + user.getId());
		if (session.get(User.class, userid) == null) // id doesnt exist in the
													// database
			return null;
		session.merge(user); // update query where personid=?
		// select [after modification]
		User updatedUser = (User) session.get(User.class, userid); // select query
		session.flush();
		session.close();
		return updatedUser;

	}

	public void deleteUser(int id) {
		Session session = sessionFactory.openSession();
		// make the object persistent - person
		User user = (User) session.get(User.class, id);
		session.delete(user);
		// Transient - person
		session.flush();
		session.close();
	}

	public User registerUser(User user) {
		logger.debug("USERDAOIMPL - registerUser");
		Session session = sessionFactory.openSession();
		session.save(user);
		session.flush();
		session.close();
		logger.debug("User id in Dao " + user.getId());
		return user;

	}

	public List<User> getAllUsers() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User");
		List<User> users = query.list();
		session.close();
		return users;
	}

	public User getUserById(int userid) {
		Session session = sessionFactory.openSession();
		// select * from personinfo where id=2
		User user = (User) session.get(User.class, userid);
		session.close();
		return user;
	}
	@Override
	public List<User> getAllUsers(User user) {
		Session session=sessionFactory.openSession();
		SQLQuery query=session.createSQLQuery("select * from raj_user where username in (select username from raj_user where username!=? minus(select to_id from rk_friend where from_id=? union select from_id from rk_friend where to_id=?))");
		query.setString(0, user.getUsername());
		query.setString(1, user.getUsername());
		query.setString(2, user.getUsername());
		query.addEntity(User.class);
		List<User> users=query.list();
		System.out.println(users);
		session.close();
		return users;
	}
	
}