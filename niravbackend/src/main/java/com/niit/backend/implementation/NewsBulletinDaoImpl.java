package com.niit.backend.implementation;

import java.util.List;




import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.NewsBulletinDao;
import com.niit.backend.model.NewsBulletin;

@Repository
public class NewsBulletinDaoImpl implements NewsBulletinDao  {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void createNewsBulletin(NewsBulletin news) {
		Session session = sessionFactory.openSession();
		session.save(news);
		session.flush();
		session.close();
	}

	public List<NewsBulletin> getAllNewsBulletins() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from NewsBulletin  order by bulletinid desc");
		List<NewsBulletin> nb = query.list();
		session.close();
		return nb;
	}

	public NewsBulletin getNewsBulletinById(int id) {
		Session session = sessionFactory.openSession();
		// select * from personinfo where id=2
		NewsBulletin nb = (NewsBulletin) session.get(NewsBulletin.class, id);
		session.close();
		return nb;

	}

	public NewsBulletin updateNewsBulletin(int bulletinid, NewsBulletin news) {
		Session session = sessionFactory.openSession();
		System.out.println("Id of NewsBulletin is to update is: " + news.getBulletinId());
		if (session.get(NewsBulletin.class, bulletinid) == null)
			return null;
		session.merge(news); //

		NewsBulletin updatedNewsBulletin = (NewsBulletin) session.get(NewsBulletin.class, bulletinid); // select
		// query
		session.flush();
		session.close();
		return updatedNewsBulletin;

	}

	public void deleteNewsBulletin(int bulletinid) {
		Session session = sessionFactory.openSession();

		NewsBulletin nb = (NewsBulletin) session.get(NewsBulletin.class, bulletinid);
		session.delete(nb);

		session.flush();
		session.close();

	}

	/*
	 * public List<NewsBulletin> getNewsBulletinByStatus(String status) {
	 * Session session = sessionFactory.openSession(); Query query =
	 * session.createQuery("from NewsBulletin where status ='" + status +
	 * "' order by bulletinid desc"); List<NewsBulletin> nb = query.list();
	 * session.close(); return nb; }
	 */

}

