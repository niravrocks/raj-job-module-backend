package com.niit.backend.implementation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.backend.dao.FileUploadDao;
import com.niit.backend.model.Job;
import com.niit.backend.model.UploadFile;
import com.niit.backend.model.User;

@Repository
public class FileUploadDAOImpl implements FileUploadDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public FileUploadDAOImpl() {
	}

	public FileUploadDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public void save(UploadFile uploadFile) {
		Session session=sessionFactory.openSession();
		session.save(uploadFile);
		session.flush();
		session.close();
	}

	@Override
	public UploadFile getFile(String username) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from UploadFile where username=?");
		query.setParameter(0, username);
        UploadFile uploadFile=(UploadFile)query.uniqueResult();
		session.close();
		return uploadFile;
	}

	@Override
	public UploadFile update(int fileuploadid, UploadFile uploadfile) {
				Session session = sessionFactory.openSession();
				System.out.println("Id of uploadfile to update is: " + uploadfile.getId());
				if (session.get(UploadFile.class, fileuploadid) == null) 
					return null;
				session.merge(uploadfile); 
				UploadFile updatedfile = (UploadFile) session.get(UploadFile.class, fileuploadid); 
				session.flush();
				session.close();
				return updatedfile;
		
	}

}