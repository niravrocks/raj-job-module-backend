package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.Job;


public interface JobDao {
	void postJob(Job job);
	Job getJobById(int jobid);
	List<Job> getAllJobs(String role);
	Job updateJob(int jobid, Job job);
	boolean deleteJob(int jobid);
	
	
	List<Job> getJobByStatus(String status);	//status=approved or rejected or pending
	List<Job> getJobByExpiry(String hasexpired);	// hasexpired=yes or no
	
	
	
}
