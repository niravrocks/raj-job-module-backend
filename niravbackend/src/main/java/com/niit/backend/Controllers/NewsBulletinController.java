package com.niit.backend.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.niit.backend.model.Error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.backend.dao.NewsBulletinDao;
import com.niit.backend.model.NewsBulletin;
import com.niit.backend.model.User;

@Controller
public class NewsBulletinController {
	@Autowired
	NewsBulletinDao nbDao;

	@RequestMapping(value = "/postNews", method = RequestMethod.POST)
	public ResponseEntity<?> createNewsBulletin(@RequestBody NewsBulletin news,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1,
					"Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else {
			nbDao.createNewsBulletin(news);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getAllNews", method = RequestMethod.GET)
	public ResponseEntity<?> getAllNewsBulletin(HttpSession session) {
		List<NewsBulletin> news = nbDao.getAllNewsBulletins();
		return new ResponseEntity<List<NewsBulletin>>(news, HttpStatus.OK);
	}

	@RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
	public ResponseEntity<NewsBulletin> getNewsBulletinById(
			@PathVariable(value = "id") int id) {
		NewsBulletin news = nbDao.getNewsBulletinById(id);
		if (news == null) {
			System.out.println("news is null..........................");
			return new ResponseEntity<NewsBulletin>(HttpStatus.NOT_FOUND);
		}
		System.out.println("returning news object..........................");
		return new ResponseEntity<NewsBulletin>(news, HttpStatus.OK);
	}

	@RequestMapping(value = "/news/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateNewsBulletin(@PathVariable int id,
			@RequestBody NewsBulletin news, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1,
					"Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN")) {
			NewsBulletin editnews = nbDao.getNewsBulletinById(id);
			if (editnews == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			NewsBulletin updatedNewsBulletin = nbDao.updateNewsBulletin(id,
					news);
			return new ResponseEntity<NewsBulletin>(updatedNewsBulletin,
					HttpStatus.OK);
		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}

	@RequestMapping(value = "/news/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNewsBulletin(@PathVariable int id,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1,
					"Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN"))
		/* else if (user.getRole().equalsIgnoreCase("Admin")) */{
			NewsBulletin news = nbDao.getNewsBulletinById(id);
			if (news == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			nbDao.deleteNewsBulletin(id);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}

	/*
	 * @RequestMapping(value = "/newssta/{status}", method = RequestMethod.GET)
	 * public ResponseEntity<?> getNewsBulletinByStatus(@PathVariable(value =
	 * "status") String status, HttpSession session) { User user = (User)
	 * session.getAttribute("user"); if (user == null) { Error error = new
	 * Error(1, "Unauthorized user.. login using valid credentials"); return new
	 * ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401 } else if
	 * (user.getRole().equalsIgnoreCase("ADMIN")) { List<NewsBulletin> news =
	 * nbDao.getNewsBulletinByStatus(status); return new
	 * ResponseEntity<List<NewsBulletin>>(news, HttpStatus.OK); } else { Error
	 * error = new Error(2, "Unauthorized user.."); return new
	 * ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401 } }
	 */

}
