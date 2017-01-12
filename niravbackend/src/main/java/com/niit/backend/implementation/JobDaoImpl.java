package com.niit.backend.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.JobDao;
import com.niit.backend.model.Job;

@Repository
public class JobDaoImpl implements JobDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Job job;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void postJob(Job job) {
		Session session = sessionFactory.openSession();
		session.save(job);
		session.flush();
		session.close();
	}

	public Job getJobById(int jobid) {
		Session session = sessionFactory.openSession();
		// select * from personinfo where id=2
		Job job = (Job) session.get(Job.class, jobid);
		session.close();
		return job;
	}

	@Override
	public List<Job> getAllJobs(String role) {
		Session session = sessionFactory.openSession();
		Query query;
		if(role.equalsIgnoreCase("Admin"))
		{
			query = session.createQuery("from Job order by jobid desc");
		}
		else
		{
			query = session.createQuery("from Job where status ='approved' and hasexpired='no'  order by jobid desc"); 
		}
		List<Job> jobs = query.list();
		session.close();
		return jobs;
	}

	@Transactional
	public Job updateJob(int jobid, Job job) {
		// person -> modified value -> 226
		Session session = sessionFactory.openSession();
		// current person -> 226
		// currentPerson, person -> with same id
		// updating only variable person
		// notunique
		// select [before modification]ge
		System.out.println("Id of job is to update is: " + job.getJobId());
		if (session.get(Job.class, jobid) == null) // id doesnt exist in the
													// database
			return null;
		session.merge(job); // update query where personid=?
		// select [after modification]
		Job updatedJob = (Job) session.get(Job.class, jobid); // select query
		session.flush();
		session.close();
		return updatedJob;

	}

	public boolean deleteJob(int jobid) {

		Session session = (Session) sessionFactory.openSession();
		boolean ans = false;
		Job job = (Job) session.get(Job.class, jobid);
		System.out.println(jobid);
		if (job != null) {
			session.delete(job);

			ans = true;

		} else {

			ans = false;

		}
		session.flush();

		session.close();
		return ans;
	}

	@Override
	public List<Job> getJobByStatus(String status) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job where status = '" + status
				+ "'");
		List<Job> jobs = query.list();
		session.close();
		return jobs;
	}

	@Override
	public List<Job> getJobByExpiry(String hasexpired) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job where hasexpired = '"
				+ hasexpired + "'");
		List<Job> jobs = query.list();
		session.close();
		return jobs;
	}

}
