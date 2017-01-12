package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.User;
public interface UserDao {
	User authenticate(User user);
	List<User> getAllUsers();
	User registerUser(User user);
	
	User updateUser(int userid, User user);
	void deleteUser(int id);
	User getUserById(int userid);
	public List<User> getAllUsers(User user);
	
}
